package com.tranxortrider.deliveryrider

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.DatePicker
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.tranxortrider.deliveryrider.api.SetupProfileResponse
import com.tranxortrider.deliveryrider.repositories.OnboardingRepository
import com.tranxortrider.deliveryrider.utils.NetworkUtils
import com.tranxortrider.deliveryrider.utils.SessionManager
import com.tranxortrider.deliveryrider.utils.UIUtils
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class setup_profile : AppCompatActivity() {
    
    // UI elements
    private lateinit var firstNameEditText: TextInputEditText
    private lateinit var lastNameEditText: TextInputEditText
    private lateinit var mobileNumberEditText: TextInputEditText
    private lateinit var dateOfBirthEditText: TextInputEditText
    private lateinit var nextButton: MaterialButton
    
    // Dependencies
    private lateinit var onboardingRepository: OnboardingRepository
    private lateinit var sessionManager: SessionManager
    
    // Calendar for date picker
    private val calendar = Calendar.getInstance()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_setup_profile)
        
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
        
        // Set up date picker
        setupDatePicker()
    }
    
    private fun initializeUI() {
        // Find views
        firstNameEditText = findViewById(R.id.firstNameEditText)
        lastNameEditText = findViewById(R.id.lastNameEditText)
        mobileNumberEditText = findViewById(R.id.mobileNumberEditText)
        dateOfBirthEditText = findViewById(R.id.dateOfBirthEditText)
        nextButton = findViewById(R.id.nextButton)
        
        // Make date field non-editable directly (will use date picker)
        dateOfBirthEditText.inputType = InputType.TYPE_NULL
        dateOfBirthEditText.keyListener = null
        dateOfBirthEditText.isFocusable = false
        dateOfBirthEditText.isFocusableInTouchMode = false
    }
    
    private fun setupClickListeners() {
        // Next button click
        nextButton.setOnClickListener {
            submitProfile()
        }
    }
    
    private fun setupDatePicker() {
        // Set up date picker for DOB field
        val dateSetListener = DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, month: Int, day: Int ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)
            updateDateInView()
        }
        
        // Show date picker when clicking on the field
        dateOfBirthEditText.setOnClickListener {
            DatePickerDialog(
                this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).apply {
                // Set max date to 18 years ago (minimum age requirement)
                val eighteenYearsAgo = Calendar.getInstance()
                eighteenYearsAgo.add(Calendar.YEAR, -18)
                datePicker.maxDate = eighteenYearsAgo.timeInMillis
                
                // Set min date to 100 years ago (reasonable limit)
                val hundredYearsAgo = Calendar.getInstance()
                hundredYearsAgo.add(Calendar.YEAR, -100)
                datePicker.minDate = hundredYearsAgo.timeInMillis
            }.show()
        }
    }
    
    private fun updateDateInView() {
        val format = SimpleDateFormat("MM/dd/yyyy", Locale.US)
        dateOfBirthEditText.setText(format.format(calendar.time))
    }
    
    private fun submitProfile() {
        // Get input values
        val firstName = firstNameEditText.text.toString().trim()
        val lastName = lastNameEditText.text.toString().trim()
        val fullName = "$firstName $lastName"
        val mobileNumber = mobileNumberEditText.text.toString().trim()
        val dateOfBirth = dateOfBirthEditText.text.toString().trim()
        
        // Validate input
        if (!validateInput(firstName, lastName, mobileNumber, dateOfBirth)) {
            return
        }
        
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
        
        // Show loading state
        showLoading(true)
        
        // Log API call for debugging
        android.util.Log.d("SetupProfile", "Calling setupProfile API for user: $userId, name: $fullName, phone: $mobileNumber")
        
        try {
        // Submit profile data to server
        onboardingRepository.setupProfile(
            userId = userId,
            fullName = fullName,
            phoneNumber = mobileNumber,
            // Other fields are optional for the initial setup
        ) { success, message, response ->
            // Hide loading state
            runOnUiThread {
                showLoading(false)
                    
                    android.util.Log.d("SetupProfile", "API Response: success=$success, message=$message")
                
                if (success && response?.user != null) {
                    // Update session with new user data
                    sessionManager.saveUserData(response.user)
                    
                    // Show success message
                    UIUtils.showToast(this, "Profile updated successfully")
                    
                    // Navigate to document upload screen
                    navigateToUploadDocument()
                } else {
                    // Show error message
                    UIUtils.showSnackbar(findViewById(android.R.id.content), message)
                        
                        // If the API is unreachable, allow user to proceed anyway for development
                        if (message.contains("Network error") || message.contains("Failed to connect")) {
                            android.util.Log.w("SetupProfile", "API unreachable, proceeding to next screen for development")
                            UIUtils.showToast(this, "Development mode: Proceeding despite API error")
                            navigateToUploadDocument()
                        }
                    }
                }
            }
        } catch (e: Exception) {
            // Handle any unexpected exceptions
            android.util.Log.e("SetupProfile", "Exception in setupProfile", e)
            runOnUiThread {
                showLoading(false)
                UIUtils.showSnackbar(findViewById(android.R.id.content), "Error: ${e.message}")
                
                // In development mode, allow proceeding anyway
                UIUtils.showToast(this, "Development mode: Proceeding despite error")
                navigateToUploadDocument()
            }
        }
    }
    
    private fun validateInput(firstName: String, lastName: String, mobileNumber: String, dateOfBirth: String): Boolean {
        var isValid = true
        
        // Validate first name
        if (firstName.isEmpty()) {
            firstNameEditText.error = "First name cannot be empty"
            isValid = false
        } else {
            firstNameEditText.error = null
        }
        
        // Validate last name
        if (lastName.isEmpty()) {
            lastNameEditText.error = "Last name cannot be empty"
            isValid = false
        } else {
            lastNameEditText.error = null
        }
        
        // Validate mobile number
        if (mobileNumber.isEmpty()) {
            mobileNumberEditText.error = "Mobile number cannot be empty"
            isValid = false
        } else if (mobileNumber.length < 10) {
            mobileNumberEditText.error = "Please enter a valid mobile number"
            isValid = false
        } else {
            mobileNumberEditText.error = null
        }
        
        // Validate date of birth
        if (dateOfBirth.isEmpty()) {
            dateOfBirthEditText.error = "Date of birth cannot be empty"
            isValid = false
        } else {
            dateOfBirthEditText.error = null
        }
        
        return isValid
    }
    
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            // Disable button and show loading state
            nextButton.isEnabled = false
            nextButton.text = "Saving..."
        } else {
            // Enable button and restore text
            nextButton.isEnabled = true
            nextButton.text = "Next"
        }
    }
    
    private fun navigateToUploadDocument() {
        val intent = Intent(this, upload_document::class.java)
        startActivity(intent)
        finish() // Close this screen
    }
    
    private fun navigateToSignIn() {
        val intent = Intent(this, sign_in_screen::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish() // Close this screen
    }
}