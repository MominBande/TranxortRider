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

class forgot_password_1 : AppCompatActivity() {
    private lateinit var authService: FirebaseAuthService
    private lateinit var emailEditText: EditText
    private lateinit var sendCodeButton: Button
    private lateinit var signInText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password_1)

        // Initialize Firebase Auth Service
        authService = FirebaseAuthService()
        authService.initializeGoogleSignIn(this)

        // Initialize views
        emailEditText = findViewById(R.id.emailEditText)
        sendCodeButton = findViewById(R.id.sendCodeButton)
        signInText = findViewById(R.id.signInText)

        // Set click listeners
        sendCodeButton.setOnClickListener {
            sendPasswordResetEmail()
        }

        signInText.setOnClickListener {
            startActivity(Intent(this, sign_in_screen::class.java))
            finish()
        }
    }

    private fun sendPasswordResetEmail() {
        val email = emailEditText.text.toString()

        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val result = authService.sendPasswordResetEmail(email)
                result.onSuccess {
                    Toast.makeText(this@forgot_password_1, 
                        "Password reset email sent. Please check your inbox.", 
                        Toast.LENGTH_LONG).show()
                    
                    // Navigate back to sign in
                    startActivity(Intent(this@forgot_password_1, sign_in_screen::class.java))
                    finish()
                }.onFailure { exception ->
                    Toast.makeText(this@forgot_password_1, 
                        "Failed to send reset email: ${exception.message}", 
                        Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@forgot_password_1, 
                    "An error occurred: ${e.message}", 
                    Toast.LENGTH_SHORT).show()
            }
        }
    }
}