package com.tranxortrider.deliveryrider

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.tranxortrider.deliveryrider.repositories.AuthRepository
import com.tranxortrider.deliveryrider.utils.UIUtils
import java.util.concurrent.TimeUnit

class verification_code : AppCompatActivity() {
    
    private lateinit var codeEditText: TextInputEditText
    private lateinit var resendCodeButton: Button
    private lateinit var verifyButton: Button
    private lateinit var countdownTimerText: TextView
    
    private lateinit var authRepository: AuthRepository
    private var countdownTimer: CountDownTimer? = null
    private val resendTimeInMillis = 60000L // 60 seconds
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_verification_code)
        
        // Add ID for main parent if needed
        findViewById<View>(android.R.id.content).id = R.id.main
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        // Initialize repository
        authRepository = AuthRepository()
        
        // Initialize views
        codeEditText = findViewById(R.id.codeEditText)
        resendCodeButton = findViewById(R.id.resendButton)
        verifyButton = findViewById(R.id.verifyButton)
        countdownTimerText = findViewById(R.id.countdownTimerText)
        
        // Get email from intent
        val email = intent.getStringExtra("EMAIL") ?: ""
        
        // Set up listeners
        setupCodeEditTextListener()
        
        // Set up resend button
        resendCodeButton.setOnClickListener {
            resendVerificationCode(email)
        }
        
        // Set up verify button
        verifyButton.setOnClickListener {
            val code = codeEditText.text.toString().trim()
            if (code.length == 6) {
                verifyCode(code)
            } else {
                UIUtils.showSnackbar(findViewById(android.R.id.content), "Please enter a valid 6-digit code")
            }
        }
        
        // Start countdown timer
        startCountdownTimer()
    }
    
    private fun setupCodeEditTextListener() {
        codeEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            
            override fun afterTextChanged(s: Editable?) {
                val code = s.toString().trim()
                verifyButton.isEnabled = code.length == 6
            }
        })
    }
    
    private fun startCountdownTimer() {
        countdownTimer?.cancel()
        
        resendCodeButton.isEnabled = false
        
        countdownTimer = object : CountDownTimer(resendTimeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60
                val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                countdownTimerText.text = "Resend code in ${String.format("%02d:%02d", minutes, seconds)}"
            }
            
            override fun onFinish() {
                countdownTimerText.text = "Didn't receive the code?"
                resendCodeButton.isEnabled = true
            }
        }.start()
    }
    
    private fun resendVerificationCode(email: String) {
        // Show loading state
        showLoading()
        
        // In a real app, call the repository to resend code
        // For now, we'll just simulate a success response after a delay
        Handler(Looper.getMainLooper()).postDelayed({
            hideLoading()
            UIUtils.showSnackbar(findViewById(android.R.id.content), "Verification code sent")
            startCountdownTimer()
        }, 1500)
    }
    
    private fun verifyCode(code: String) {
        // Show loading state
        showLoading()
        
        // In a real app, call the repository to verify code
        // For now, we'll just simulate a success response after a delay
        Handler(Looper.getMainLooper()).postDelayed({
            hideLoading()
            
            // Navigate to password reset screen on success
            val intent = Intent(this, create_new_password::class.java)
            intent.putExtra("TOKEN", code)
            startActivity(intent)
            finish()
        }, 1500)
    }
    
    private fun showLoading() {
        // Show loading UI
        verifyButton.isEnabled = false
        resendCodeButton.isEnabled = false
    }
    
    private fun hideLoading() {
        // Hide loading UI
        verifyButton.isEnabled = true
        val isCountdownRunning = countdownTimer != null && 
                countdownTimerText.text.toString().contains(":")
        resendCodeButton.isEnabled = !isCountdownRunning
    }
    
    override fun onDestroy() {
        super.onDestroy()
        countdownTimer?.cancel()
    }
}