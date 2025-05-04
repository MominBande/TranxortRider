package com.tranxortrider.deliveryrider

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.tranxortrider.deliveryrider.repositories.AuthRepository
import com.tranxortrider.deliveryrider.utils.NetworkUtils
import com.tranxortrider.deliveryrider.utils.UIUtils
import com.tranxortrider.deliveryrider.utils.ValidationUtils

class create_new_password : AppCompatActivity() {
    
    // UI elements
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var confirmPasswordEditText: TextInputEditText
    private lateinit var passwordInputLayout: TextInputLayout
    private lateinit var confirmPasswordInputLayout: TextInputLayout
    private lateinit var passwordStrengthIndicator: LinearProgressIndicator
    private lateinit var passwordStrengthText: TextView
    private lateinit var resetButton: MaterialButton
    
    // Dependencies
    private lateinit var authRepository: AuthRepository
    
    // State variables
    private lateinit var email: String
    private lateinit var code: String
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_create_new_password)
        
        // Initialize window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        // Get data from intent
        email = intent.getStringExtra("EMAIL") ?: ""
        code = intent.getStringExtra("CODE") ?: ""
        
        // Check if required data is missing, if so, navigate back
        if (email.isEmpty() || code.isEmpty()) {
            navigateToForgotPassword()
            return
        }
        
        // Initialize dependencies
        authRepository = AuthRepository()
        
        // Initialize UI elements
        initializeUI()
        
        // Set up click listeners
        setupClickListeners()
        
        // Set up text change listeners
        setupTextChangeListeners()
    }
    
    private fun initializeUI() {
        // Find views
        passwordEditText = findViewById(R.id.passwordEditText)
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText)
        passwordInputLayout = findViewById(R.id.passwordInputLayout)
        confirmPasswordInputLayout = findViewById(R.id.confirmPasswordInputLayout)
        passwordStrengthIndicator = findViewById(R.id.passwordStrengthIndicator)
        passwordStrengthText = findViewById(R.id.passwordStrengthText)
        resetButton = findViewById(R.id.resetButton)
        
        // Initialize password strength indicator
        passwordStrengthIndicator.progress = 0
    }
    
    private fun setupClickListeners() {
        // Reset button click
        resetButton.setOnClickListener {
            resetPassword()
        }
    }
    
    private fun setupTextChangeListeners() {
        // Password text change listener for checking strength
        passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            
            override fun afterTextChanged(s: Editable?) {
                updatePasswordStrengthIndicator(s.toString())
            }
        })
        
        // Confirm password text change listener for real-time validation
        confirmPasswordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            
            override fun afterTextChanged(s: Editable?) {
                validatePasswordsMatch()
            }
        })
    }
    
    private fun updatePasswordStrengthIndicator(password: String) {
        // Get password strength requirements
        val hasMinLength = password.length >= 8
        val hasUpperCase = password.any { it.isUpperCase() }
        val hasLowerCase = password.any { it.isLowerCase() }
        val hasDigit = password.any { it.isDigit() }
        val hasSpecialChar = password.any { !it.isLetterOrDigit() }
        
        // Calculate strength percentage (0-100)
        var strength = 0
        if (hasMinLength) strength += 20
        if (hasUpperCase) strength += 20
        if (hasLowerCase) strength += 20
        if (hasDigit) strength += 20
        if (hasSpecialChar) strength += 20
        
        // Update the progress indicator
        passwordStrengthIndicator.progress = strength
        
        // Update the strength text based on percentage
        when {
            strength < 40 -> {
                passwordStrengthText.text = "Weak"
                passwordStrengthText.setTextColor(getColor(R.color.red))
                passwordStrengthIndicator.setIndicatorColor(getColor(R.color.red))
            }
            strength < 80 -> {
                passwordStrengthText.text = "Medium"
                passwordStrengthText.setTextColor(getColor(R.color.yellow))
                passwordStrengthIndicator.setIndicatorColor(getColor(R.color.yellow))
            }
            else -> {
                passwordStrengthText.text = "Strong"
                passwordStrengthText.setTextColor(getColor(R.color.aesthgreen))
                passwordStrengthIndicator.setIndicatorColor(getColor(R.color.aesthgreen))
            }
        }
        
        // Show or hide password strength elements
        if (password.isEmpty()) {
            passwordStrengthIndicator.visibility = View.INVISIBLE
            passwordStrengthText.visibility = View.INVISIBLE
        } else {
            passwordStrengthIndicator.visibility = View.VISIBLE
            passwordStrengthText.visibility = View.VISIBLE
        }
    }
    
    private fun validatePasswordsMatch(): Boolean {
        val password = passwordEditText.text.toString()
        val confirmPassword = confirmPasswordEditText.text.toString()
        
        // Skip validation if confirm password is empty
        if (confirmPassword.isEmpty()) {
            confirmPasswordInputLayout.error = null
            return false
        }
        
        // Check if passwords match
        if (password != confirmPassword) {
            confirmPasswordInputLayout.error = "Passwords do not match"
            return false
        } else {
            confirmPasswordInputLayout.error = null
            return true
        }
    }
    
    private fun resetPassword() {
        // Get input values
        val password = passwordEditText.text.toString()
        val confirmPassword = confirmPasswordEditText.text.toString()
        
        // Validate inputs
        if (!validateInput(password, confirmPassword)) {
            return
        }
        
        // Check network connectivity
        if (!NetworkUtils.isNetworkAvailable(this)) {
            UIUtils.showSnackbar(findViewById(android.R.id.content), "No internet connection.")
            return
        }
        
        // Show loading state
        showLoading(true)
        
        // Call API to reset password
        authRepository.resetPassword(email = email, code = code, newPassword = password) { success, message ->
            // Hide loading state
            runOnUiThread {
                showLoading(false)
                
                if (success) {
                    // Show success message
                    UIUtils.showToast(this, message)
                    
                    // Navigate to sign in screen
                    navigateToSignIn("Password Reset Successful", "Your password has been reset successfully. Please sign in with your new password.")
                } else {
                    // Show error message
                    UIUtils.showSnackbar(findViewById(android.R.id.content), message)
                    
                    // Handle specific error cases
                    if (message.contains("expired", ignoreCase = true) || 
                        message.contains("invalid", ignoreCase = true)) {
                        navigateToCreateNewPasswordError("Password Reset Failed", "The reset link has expired or is invalid. Please try again.")
                    }
                }
            }
        }
    }
    
    private fun validateInput(password: String, confirmPassword: String): Boolean {
        var isValid = true
        
        // Validate password
        if (password.isEmpty()) {
            passwordInputLayout.error = "Password cannot be empty"
            isValid = false
        } else if (!ValidationUtils.isValidPassword(password)) {
            passwordInputLayout.error = "Password must be at least 8 characters"
            isValid = false
        } else {
            passwordInputLayout.error = null
        }
        
        // Validate confirm password
        if (confirmPassword.isEmpty()) {
            confirmPasswordInputLayout.error = "Please confirm your password"
            isValid = false
        } else if (password != confirmPassword) {
            confirmPasswordInputLayout.error = "Passwords do not match"
            isValid = false
        } else {
            confirmPasswordInputLayout.error = null
        }
        
        return isValid
    }
    
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            // Disable button and show loading state
            resetButton.isEnabled = false
            resetButton.text = "Resetting..."
        } else {
            // Enable button and restore text
            resetButton.isEnabled = true
            resetButton.text = "Reset Password"
        }
    }
    
    private fun navigateToForgotPassword() {
        val intent = Intent(this, forgot_password_1::class.java)
        startActivity(intent)
        finish()
    }
    
    private fun navigateToSignIn(title: String? = null, message: String? = null) {
        val intent = Intent(this, sign_in_screen::class.java)
        if (title != null && message != null) {
            // Pass success message to be displayed on sign in screen
            intent.putExtra("SUCCESS_TITLE", title)
            intent.putExtra("SUCCESS_MESSAGE", message)
        }
        startActivity(intent)
        finish()
    }
    
    private fun navigateToCreateNewPasswordError(title: String, message: String) {
        val intent = Intent(this, create_new_password_error::class.java).apply {
            putExtra("ERROR_TITLE", title)
            putExtra("ERROR_MESSAGE", message)
            putExtra("EMAIL", email)
        }
        startActivity(intent)
    }
}