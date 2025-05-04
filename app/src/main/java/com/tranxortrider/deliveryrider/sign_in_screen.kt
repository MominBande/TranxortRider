package com.tranxortrider.deliveryrider

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.tranxortrider.deliveryrider.services.FirebaseAuthService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class sign_in_screen : AppCompatActivity() {
    private lateinit var authService: FirebaseAuthService
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signInButton: Button
    private lateinit var googleSignInButton: Button
    private lateinit var forgotPasswordText: TextView
    private lateinit var signUpText: TextView

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) ||
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                // Location permission granted
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                    requestForegroundLocationPermission()
                }
            }
            else -> {
                Toast.makeText(this, "Location permission is required for this app", Toast.LENGTH_LONG).show()
            }
        }
    }

    private val foregroundLocationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            Toast.makeText(this, "Background location permission is required for this app", Toast.LENGTH_LONG).show()
        }
    }

    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            handleGoogleSignIn(result.data)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_screen)

        // Request location permissions
        requestLocationPermissions()

        // Initialize Firebase Auth Service
        authService = FirebaseAuthService()
        authService.initializeGoogleSignIn(this)

        // Initialize views
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        signInButton = findViewById(R.id.signInButton)
        googleSignInButton = findViewById(R.id.googleSignInButton)
        forgotPasswordText = findViewById(R.id.forgotPasswordText)
        signUpText = findViewById(R.id.signUpText)

        // Set click listeners
        signInButton.setOnClickListener {
            signInWithEmail()
        }

        googleSignInButton.setOnClickListener {
            signInWithGoogle()
        }

        forgotPasswordText.setOnClickListener {
            startActivity(Intent(this, forgot_password_1::class.java))
        }

        signUpText.setOnClickListener {
            startActivity(Intent(this, sign_up_screen::class.java))
        }

        // Check if user is already signed in
        if (authService.getCurrentUser() != null) {
            navigateToHome()
        }
    }

    private fun requestLocationPermissions() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                // Permission already granted
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                    requestForegroundLocationPermission()
                }
            }
            else -> {
                locationPermissionRequest.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        }
    }

    private fun requestForegroundLocationPermission() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.FOREGROUND_SERVICE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // Permission already granted
                }
                else -> {
                    foregroundLocationPermissionRequest.launch(Manifest.permission.FOREGROUND_SERVICE_LOCATION)
                }
            }
        }
    }

    private fun signInWithEmail() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val result = authService.signInWithEmailAndPassword(email, password)
                result.onSuccess {
                    navigateToHome()
                }.onFailure { exception ->
                    Toast.makeText(this@sign_in_screen, 
                        "Sign in failed: ${exception.message}", 
                        Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@sign_in_screen, 
                    "An error occurred: ${e.message}", 
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signInWithGoogle() {
        val signInIntent = authService.getGoogleSignInIntent()
        googleSignInLauncher.launch(signInIntent)
    }

    private fun handleGoogleSignIn(data: Intent?) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val result = authService.signInWithGoogle(data)
                result.onSuccess {
                    navigateToHome()
                }.onFailure { exception ->
                    Toast.makeText(this@sign_in_screen, 
                        "Google sign in failed: ${exception.message}", 
                        Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@sign_in_screen, 
                    "An error occurred: ${e.message}", 
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, home_screen::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}