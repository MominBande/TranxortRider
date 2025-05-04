package com.tranxortrider.deliveryrider

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.util.TypedValue
import android.graphics.Color
import android.view.ViewGroup
import android.view.View

class verification_code_error : AppCompatActivity() {
    
    // UI elements
    private lateinit var errorTitleText: TextView
    private lateinit var errorMessageText: TextView
    private lateinit var tryAgainButton: MaterialButton
    private lateinit var cancelButton: TextView
    
    // State variables
    private lateinit var email: String
    private var source: String = "SIGNUP" // Default source
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_verification_code_error)
        
        // Initialize window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        // Get data from intent
        email = intent.getStringExtra("EMAIL") ?: ""
        source = intent.getStringExtra("SOURCE") ?: "SIGNUP"
        
        // Initialize UI elements
        initializeUI()
        
        // Set error details from intent
        setErrorDetails()
        
        // Set up click listeners
        setupClickListeners()
    }
    
    private fun initializeUI() {
        // Find views
        errorTitleText = findViewById(R.id.errorTitleText) ?: findViewById(R.id.titleText)
        errorMessageText = findViewById(R.id.errorMessageText) ?: findViewById(R.id.errorMessage)
        tryAgainButton = findViewById(R.id.tryAgainButton) ?: findViewById(R.id.submitButton)
        
        // Create a cancelButton programmatically
        createCancelButton()
    }
    
    private fun createCancelButton() {
        cancelButton = TextView(this).apply {
            text = "Cancel"
            setTextColor(Color.parseColor("#FF4081")) // Pink color
            textSize = 16f
            gravity = Gravity.CENTER
            setPadding(0, 24, 0, 24) // Add some padding
            isClickable = true
            setOnClickListener {
                navigateToInitialScreen()
            }
        }
        
        // Find a suitable parent view - use the root content view
        val rootView = findViewById<View>(android.R.id.content)
        val parentView = (rootView as? ViewGroup) ?: (findViewById<View>(R.id.main) as? ViewGroup)
        
        if (parentView != null) {
            // Create layout params based on parent view type
            val layoutParams = when (parentView) {
                is LinearLayout -> {
                    LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        gravity = Gravity.CENTER
                        topMargin = TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP,
                            16f,
                            resources.displayMetrics
                        ).toInt()
                    }
                }
                is RelativeLayout -> {
                    RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        addRule(RelativeLayout.CENTER_HORIZONTAL)
                        addRule(RelativeLayout.BELOW, R.id.tryAgainButton)
                        topMargin = TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP,
                            16f,
                            resources.displayMetrics
                        ).toInt()
                    }
                }
                else -> {
                    ViewGroup.MarginLayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    ).apply {
                        topMargin = TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP,
                            16f,
                            resources.displayMetrics
                        ).toInt()
                    }
                }
            }
            
            // Add the TextView to the layout
            parentView.addView(cancelButton, layoutParams)
        } else {
            // As a fallback, try adding directly to the activity's content view
            val fallbackView = window.decorView.findViewById<ViewGroup>(android.R.id.content)
            fallbackView.addView(cancelButton)
        }
    }
    
    private fun setErrorDetails() {
        // Get error details from intent
        val errorTitle = intent.getStringExtra("ERROR_TITLE") ?: "Verification Failed"
        val errorMessage = intent.getStringExtra("ERROR_MESSAGE") 
            ?: "There was an error with the verification code. Please try again."
        
        // Set the error details
        errorTitleText.text = errorTitle
        errorMessageText.text = errorMessage
    }
    
    private fun setupClickListeners() {
        // Try Again button click
        tryAgainButton.setOnClickListener {
            navigateToVerificationCode()
        }
        
        // "Cancel" text click is already set in createCancelButton()
    }
    
    private fun navigateToVerificationCode() {
        val intent = Intent(this, verification_code::class.java).apply {
            putExtra("EMAIL", email)
            putExtra("SOURCE", source)
        }
        startActivity(intent)
        finish() // Close this error screen
    }
    
    private fun navigateToInitialScreen() {
        if (source == "FORGOT_PASSWORD") {
            val intent = Intent(this, forgot_password_1::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this, sign_up_screen::class.java)
            startActivity(intent)
        }
        finish() // Close this error screen
    }
}