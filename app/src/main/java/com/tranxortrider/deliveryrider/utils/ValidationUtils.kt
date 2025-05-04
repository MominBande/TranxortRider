package com.tranxortrider.deliveryrider.utils

import android.util.Patterns
import java.util.regex.Pattern

/**
 * Utility class for input validation
 */
object ValidationUtils {
    
    /**
     * Email validation pattern for checking valid email formats
     */
    private val EMAIL_PATTERN = Patterns.EMAIL_ADDRESS
    
    /**
     * Password pattern:
     * - At least 8 characters
     * - Contains at least one digit
     * - Contains at least one lowercase letter
     * - Contains at least one uppercase letter
     * - Contains at least one special character
     */
    private val PASSWORD_PATTERN = Pattern.compile(
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$"
    )
    
    /**
     * Validate email format
     */
    fun isValidEmail(email: String): Boolean {
        return email.isNotBlank() && EMAIL_PATTERN.matcher(email).matches()
    }
    
    /**
     * Validate password strength
     */
    fun isValidPassword(password: String): Boolean {
        return password.isNotBlank() && password.length >= 8
    }
    
    /**
     * Validate password strength with strong pattern
     */
    fun isStrongPassword(password: String): Boolean {
        return password.isNotBlank() && PASSWORD_PATTERN.matcher(password).matches()
    }
} 