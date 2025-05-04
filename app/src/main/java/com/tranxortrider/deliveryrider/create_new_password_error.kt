package com.tranxortrider.deliveryrider

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class create_new_password_error : AppCompatActivity() {
    
    // UI elements
    private lateinit var errorTitleText: TextView
    private lateinit var errorMessageText: TextView
    private lateinit var backToForgotPasswordButton: MaterialButton
    private lateinit var signInText: TextView
    
    // State variables
    private lateinit var email: String
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_create_new_password_error)
        
        // Initialize window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        // Get data from intent
        email = intent.getStringExtra("EMAIL") ?: ""
        
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
        backToForgotPasswordButton = findViewById(R.id.backToForgotPasswordButton)
        
        // Find the sign in text
        val signInTextView = findViewById<TextView>(R.id.signInText)
        if (signInTextView != null) {
            signInText = signInTextView
        }
    }
    
    private fun setErrorDetails() {
        // Get error details from intent
        val errorTitle = intent.getStringExtra("ERROR_TITLE") ?: "Password Reset Failed"
        val errorMessage = intent.getStringExtra("ERROR_MESSAGE") 
            ?: "There was an error resetting your password. Please try again."
        
        // Set the error details
        errorTitleText.text = errorTitle
        errorMessageText.text = errorMessage
    }
    
    private fun setupClickListeners() {
        // Back to Forgot Password button click
        backToForgotPasswordButton.setOnClickListener {
            navigateToForgotPassword()
        }
        
        // "Already remember? Sign In" text click
        if (::signInText.isInitialized) {
            signInText.setOnClickListener {
                navigateToSignIn()
            }
        }
    }
    
    private fun navigateToForgotPassword() {
        val intent = Intent(this, forgot_password_1::class.java)
        if (email.isNotEmpty()) {
            intent.putExtra("EMAIL", email)
        }
        startActivity(intent)
        finish() // Close this error screen
    }
    
    private fun navigateToSignIn() {
        val intent = Intent(this, sign_in_screen::class.java)
        startActivity(intent)
        finish() // Close this error screen
    }
}