package com.tranxortrider.deliveryrider

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tranxortrider.deliveryrider.services.FirebaseAuthService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class sign_up_screen : AppCompatActivity() {
    private lateinit var authService: FirebaseAuthService
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var signUpButton: Button
    private lateinit var signInText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_screen)

        // Initialize Firebase Auth Service
        authService = FirebaseAuthService()
        authService.initializeGoogleSignIn(this)

        // Initialize views
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText)
        signUpButton = findViewById(R.id.signUpButton)
        signInText = findViewById(R.id.signInTextView)

        // Set click listeners
        signUpButton.setOnClickListener {
            signUpWithEmail()
        }

        signInText.setOnClickListener {
            startActivity(Intent(this, sign_in_screen::class.java))
            finish()
        }
    }

    private fun signUpWithEmail() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        val confirmPassword = confirmPasswordEditText.text.toString()

        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }

        if (password.length < 6) {
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
            return
        }
        
        // Show loading state
        setLoading(true)
        
        // Log signup attempt
        android.util.Log.d("SignUp", "Attempting to sign up with email: $email")

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val result = authService.createUserWithEmailAndPassword(email, password)
                result.onSuccess {
                    android.util.Log.d("SignUp", "Account created successfully, user: ${it.uid}")
                    // Navigate to profile setup
                    val intent = Intent(this@sign_up_screen, setup_profile::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }.onFailure { exception ->
                    android.util.Log.e("SignUp", "Sign up failed", exception)
                    setLoading(false)
                    Toast.makeText(this@sign_up_screen, 
                        "Sign up failed: ${exception.message}", 
                        Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                android.util.Log.e("SignUp", "Exception during sign up", e)
                setLoading(false)
                Toast.makeText(this@sign_up_screen, 
                    "An error occurred: ${e.message}", 
                    Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun setLoading(isLoading: Boolean) {
        signUpButton.isEnabled = !isLoading
        signUpButton.text = if (isLoading) "Creating Account..." else "Sign Up"
    }
}