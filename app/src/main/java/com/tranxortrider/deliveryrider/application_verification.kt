package com.tranxortrider.deliveryrider

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.tranxortrider.deliveryrider.repositories.OnboardingRepository
import com.tranxortrider.deliveryrider.utils.NetworkUtils
import com.tranxortrider.deliveryrider.utils.SessionManager
import com.tranxortrider.deliveryrider.utils.UIUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class application_verification : AppCompatActivity() {
    
    // UI elements
    private lateinit var titleText: TextView
    private lateinit var messageText: TextView
    private lateinit var governmentIdStatusText: TextView
    private lateinit var driverLicenseStatusText: TextView
    private lateinit var vehicleRegistrationStatusText: TextView
    private lateinit var estimatedTimeText: TextView
    private lateinit var contactUsButton: MaterialButton
    private lateinit var reuploadButton: MaterialButton
    
    // Dependencies
    private lateinit var onboardingRepository: OnboardingRepository
    private lateinit var sessionManager: SessionManager
    
    // Status polling handler
    private val handler = Handler(Looper.getMainLooper())
    private var statusCheckRunnable: Runnable? = null
    private val STATUS_CHECK_INTERVAL = 60000L // 1 minute
    
    // Document statuses
    private var verificationStatus = "pending"
    private var documentStatuses = mapOf<String, String>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Fix for surface animation issues
        window.setWindowAnimations(android.R.style.Animation_Activity)
        window.attributes.windowAnimations = android.R.style.Animation_Activity
        
        // Prevent window animation issues by disabling hardware acceleration
        // only for this activity which has animation issues
        window.setFlags(
            WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
            WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
        )
        
        enableEdgeToEdge()
        setContentView(R.layout.activity_application_verification)
        
        // Initialize window insets
        val rootView = findViewById<View>(android.R.id.content)
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        // Initialize dependencies
        onboardingRepository = OnboardingRepository()
        sessionManager = SessionManager(this)
        
        // Check if user is logged in
        val user = sessionManager.getUser()
        if (user == null) {
            // If not logged in, redirect to login
            navigateToSignIn()
            return
        }
        
        // Initialize UI elements
        initializeUI()
        
        // Set up click listeners
        setupClickListeners()
        
        // Start periodic status checks
        startStatusChecks()
    }
    
    override fun onResume() {
        super.onResume()
        // Check verification status immediately when screen is shown
        checkVerificationStatus()
    }
    
    override fun onPause() {
        super.onPause()
        // Stop status checks when screen is not visible
        stopStatusChecks()
    }
    
    override fun onDestroy() {
        super.onDestroy()
        // Make sure to clean up the handler when activity is destroyed
        stopStatusChecks()
    }
    
    private fun initializeUI() {
        // Find views
        titleText = findViewById(R.id.titleText)
        messageText = findViewById(R.id.messageText)
        estimatedTimeText = findViewById(R.id.estimatedTimeText)
        governmentIdStatusText = findViewById(R.id.governmentIdStatusText)
        driverLicenseStatusText = findViewById(R.id.driverLicenseStatusText)
        vehicleRegistrationStatusText = findViewById(R.id.workAuthorizationStatusText)
        contactUsButton = findViewById(R.id.contactUsButton)
        
        // Initialize reupload button if it exists in layout
        try {
            reuploadButton = findViewById(R.id.reuploadButton)
            reuploadButton.visibility = View.GONE
        } catch (e: Exception) {
            // Button doesn't exist in layout, that's fine
        }
    }
    
    private fun setupClickListeners() {
        // Contact support button
        contactUsButton.setOnClickListener {
            contactSupport()
        }
        
        // Reupload button (if it exists)
        if (::reuploadButton.isInitialized) {
            reuploadButton.setOnClickListener {
                navigateToDocumentUpload()
            }
        }
    }
    
    private fun startStatusChecks() {
        statusCheckRunnable = object : Runnable {
            override fun run() {
                checkVerificationStatus()
                handler.postDelayed(this, STATUS_CHECK_INTERVAL)
            }
        }
        
        statusCheckRunnable?.let {
            handler.post(it)
        }
    }
    
    private fun stopStatusChecks() {
        statusCheckRunnable?.let {
            handler.removeCallbacks(it)
        }
    }
    
    private fun checkVerificationStatus() {
        // Check network connectivity
        if (!NetworkUtils.isNetworkAvailable(this)) {
            UIUtils.showSnackbar(findViewById(android.R.id.content), "No internet connection.")
            return
        }
        
        // Get user id from session
        val userId = sessionManager.getUser()?.id
        if (userId.isNullOrEmpty()) {
            UIUtils.showSnackbar(findViewById(android.R.id.content), "User information not found. Please sign in again.")
            navigateToSignIn()
            return
        }
        
        // Show loading indicator
        UIUtils.showLoading(this, "Checking verification status...")
        
        // Get verification status
        onboardingRepository.getVerificationStatus(userId) { success, message, response ->
            runOnUiThread {
                // Hide loading
                UIUtils.hideLoading()
                
                if (success && response != null) {
                    // Store the verification status
                    verificationStatus = response.verificationStatus
                    documentStatuses = response.documentsStatus
                    
                    // If verification status is approved, update the user object in session
                    if (verificationStatus.equals("approved", ignoreCase = true)) {
                        val user = sessionManager.getUser()
                        if (user != null && !user.isVerified) {
                            // Create a new user object with isVerified set to true
                            val updatedUser = user.copy(isVerified = true)
                            // Save the updated user to session
                            sessionManager.saveUserData(updatedUser)
                        }
                    }
                    
                    // Update UI
                    updateUIWithVerificationStatus(verificationStatus, documentStatuses)
                } else {
                    UIUtils.showSnackbar(findViewById(android.R.id.content), "Failed to get verification status: $message")
                }
            }
        }
    }
    
    private fun updateUIWithVerificationStatus(overallStatus: String, documentsStatus: Map<String, String>) {
        // Update overall status
        when (overallStatus.lowercase()) {
            "approved" -> {
                titleText.text = "Verification Complete"
                messageText.text = "Your account has been verified successfully!"
                estimatedTimeText.text = "You can now start accepting delivery requests."
                
                // Show reupload button if it exists
                if (::reuploadButton.isInitialized) {
                    reuploadButton.visibility = View.GONE
                }
                
                // Navigate to home screen after showing confirmation for a moment
                handler.postDelayed({
                    navigateToHome()
                }, 2000)
            }
            "rejected" -> {
                titleText.text = "Verification Failed"
                messageText.text = "We're sorry, but your application was not approved."
                estimatedTimeText.text = "Please check document status below and reupload if needed."
                
                // Show reupload button if it exists
                if (::reuploadButton.isInitialized) {
                    reuploadButton.visibility = View.VISIBLE
                }
            }
            else -> {
                titleText.text = "Verification in Progress"
                messageText.text = "Your account will be activated within 48 hours"
                estimatedTimeText.text = "We'll notify you once your application is approved"
                
                // Show reupload button if it exists
                if (::reuploadButton.isInitialized) {
                    reuploadButton.visibility = View.GONE
                }
            }
        }
        
        // Update document statuses
        updateDocumentStatus(governmentIdStatusText, documentsStatus["GOVERNMENT_ID"] ?: "PENDING")
        updateDocumentStatus(driverLicenseStatusText, documentsStatus["DRIVER_LICENSE"] ?: "PENDING")
        updateDocumentStatus(vehicleRegistrationStatusText, documentsStatus["VEHICLE_REGISTRATION"] ?: "PENDING")
        
        // Check if any document is rejected and show reupload button
        val hasRejectedDocuments = documentsStatus.values.any { it.equals("rejected", ignoreCase = true) }
        if (hasRejectedDocuments && ::reuploadButton.isInitialized) {
            reuploadButton.visibility = View.VISIBLE
        }
    }
    
    private fun updateDocumentStatus(textView: TextView, status: String) {
        when (status.uppercase()) {
            "APPROVED" -> {
                textView.text = "Approved"
                textView.setTextColor(ContextCompat.getColor(this, R.color.aesthgreen))
                textView.setBackgroundResource(R.drawable.status_badge_success_background)
            }
            "REJECTED" -> {
                textView.text = "Rejected"
                textView.setTextColor(ContextCompat.getColor(this, R.color.red))
                textView.setBackgroundResource(R.drawable.status_badge_error_background)
            }
            "NOT_SUBMITTED" -> {
                textView.text = "Not Submitted"
                textView.setTextColor(ContextCompat.getColor(this, android.R.color.darker_gray))
                textView.setBackgroundResource(R.drawable.status_badge_background)
            }
            else -> {
                textView.text = "Pending"
                textView.setTextColor(ContextCompat.getColor(this, R.color.yellow))
                textView.setBackgroundResource(R.drawable.status_badge_background)
            }
        }
    }
    
    private fun contactSupport() {
        // Open email app with support email
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:support@tranxortrider.com")
            putExtra(Intent.EXTRA_SUBJECT, "TranxortRider Application Verification - ${sessionManager.getUser()?.id}")
            
            // Add document statuses to email body
            val body = StringBuilder()
            body.append("Verification Status: $verificationStatus\n\n")
            body.append("Document Statuses:\n")
            body.append("- Government ID: ${documentStatuses["GOVERNMENT_ID"] ?: "Unknown"}\n")
            body.append("- Driver License: ${documentStatuses["DRIVER_LICENSE"] ?: "Unknown"}\n")
            body.append("- Vehicle Registration: ${documentStatuses["VEHICLE_REGISTRATION"] ?: "Unknown"}\n")
            putExtra(Intent.EXTRA_TEXT, body.toString())
        }
        
        try {
            startActivity(intent)
        } catch (e: Exception) {
            UIUtils.showSnackbar(findViewById(android.R.id.content), "No email app found.")
        }
    }
    
    private fun navigateToHome() {
        // Get the current user and update the verification status in the session
        val user = sessionManager.getUser()
        if (user != null) {
            // Create a new user object with isVerified set to true
            val updatedUser = user.copy(isVerified = true)
            // Save the updated user to session
            sessionManager.saveUserData(updatedUser)
        }
        
        val intent = Intent(this, home_screen::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        
        // Fix for animation issues when navigating between activities
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        
        startActivity(intent)
        finish() // Close this screen
    }
    
    private fun navigateToSignIn() {
        val intent = Intent(this, sign_in_screen::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        
        // Fix for animation issues when navigating between activities
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        
        startActivity(intent)
        finish() // Close this screen
    }
    
    private fun navigateToDocumentUpload() {
        val intent = Intent(this, upload_document::class.java)
        
        // Fix for animation issues when navigating between activities
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        
        startActivity(intent)
    }
    
    override fun finish() {
        super.finish()
        // Apply exit animation to avoid SurfaceFlinger issues
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}