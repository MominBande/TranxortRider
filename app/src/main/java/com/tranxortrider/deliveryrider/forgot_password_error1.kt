package com.tranxortrider.deliveryrider

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class forgot_password_error1 : AppCompatActivity() {
    
    // UI elements
    private lateinit var errorTitleText: TextView
    private lateinit var errorMessageText: TextView
    private lateinit var tryAgainButton: MaterialButton
    private lateinit var signUpText: TextView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_forgot_password_error1)
        
        // Initialize window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        // Initialize UI elements
        initializeUI()
        
        // Set error details from intent
        setErrorDetails()
        
        // Set up click listeners
        setupClickListeners()
    }
    
    private fun initializeUI() {
        // Find views
        errorTitleText = findViewById(R.id.errorTitleText)
        errorMessageText = findViewById(R.id.errorMessageText)
        tryAgainButton = findViewById(R.id.tryAgainButton)
        
        // Find the sign up text
        val signUpTextView = findViewById<TextView>(R.id.signUpText)
        if (signUpTextView != null) {
            signUpText = signUpTextView
        }
    }
    
    private fun setErrorDetails() {
        // Get error details from intent
        val errorTitle = intent.getStringExtra("ERROR_TITLE") ?: "Password Reset Failed"
        val errorMessage = intent.getStringExtra("ERROR_MESSAGE") 
            ?: "There was an error processing your password reset request. Please try again."
        
        // Set the error details
        errorTitleText.text = errorTitle
        errorMessageText.text = errorMessage
    }
    
    private fun setupClickListeners() {
        // Try Again button click
        tryAgainButton.setOnClickListener {
            navigateToForgotPassword()
        }
        
        // "Don't have an account? Sign Up" text click
        if (::signUpText.isInitialized) {
            signUpText.setOnClickListener {
                navigateToSignUp()
            }
        }
    }
    
    private fun navigateToForgotPassword() {
        val intent = Intent(this, forgot_password_1::class.java)
        startActivity(intent)
        finish() // Close this error screen
    }
    
    private fun navigateToSignUp() {
        val intent = Intent(this, sign_up_screen::class.java)
        startActivity(intent)
        finish() // Close this error screen
    }
}