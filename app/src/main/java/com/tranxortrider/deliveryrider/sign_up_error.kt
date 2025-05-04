package com.tranxortrider.deliveryrider

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class sign_up_error : AppCompatActivity() {
    
    // UI elements
    private lateinit var errorTitleText: TextView
    private lateinit var errorMessageText: TextView
    private lateinit var tryAgainButton: MaterialButton
    private lateinit var signInText: TextView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up_error)
        
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
        
        // Find the sign in text
        val signInTextView = findViewById<TextView>(R.id.signInText)
        if (signInTextView != null) {
            signInText = signInTextView
        }
    }
    
    private fun setErrorDetails() {
        // Get error details from intent
        val errorTitle = intent.getStringExtra("ERROR_TITLE") ?: "Sign Up Failed"
        val errorMessage = intent.getStringExtra("ERROR_MESSAGE") 
            ?: "There was an error creating your account. Please try again."
        
        // Set the error details
        errorTitleText.text = errorTitle
        errorMessageText.text = errorMessage
    }
    
    private fun setupClickListeners() {
        // Try Again button click
        tryAgainButton.setOnClickListener {
            navigateToSignUp()
        }
        
        // "Already have an account? Sign In" text click
        if (::signInText.isInitialized) {
            signInText.setOnClickListener {
                navigateToSignIn()
            }
        }
    }
    
    private fun navigateToSignUp() {
        val intent = Intent(this, sign_up_screen::class.java)
        startActivity(intent)
        finish() // Close this error screen
    }
    
    private fun navigateToSignIn() {
        val intent = Intent(this, sign_in_screen::class.java)
        startActivity(intent)
        finish() // Close this error screen
    }
}