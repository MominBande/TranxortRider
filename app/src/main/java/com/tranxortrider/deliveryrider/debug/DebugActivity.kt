package com.tranxortrider.deliveryrider.debug

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.tranxortrider.deliveryrider.R
import com.tranxortrider.deliveryrider.services.FirebaseAuthService
import com.tranxortrider.deliveryrider.services.FirestoreService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Activity for debugging the app functionality
 * This provides a simple UI for testing different services and components
 */
class DebugActivity : AppCompatActivity() {
    private val TAG = "DebugActivity"
    private lateinit var logTextView: TextView
    private lateinit var authService: FirebaseAuthService
    private lateinit var firestoreService: FirestoreService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_debug)

        // Initialize services
        authService = FirebaseAuthService()
        firestoreService = FirestoreService()

        // Set up UI components
        logTextView = findViewById(R.id.logTextView)
        
        // Set up test buttons
        val testAuthButton: Button = findViewById(R.id.testAuthButton)
        val testFirestoreButton: Button = findViewById(R.id.testFirestoreButton)
        val testOrdersButton: Button = findViewById(R.id.testOrdersButton)
        val clearLogButton: Button = findViewById(R.id.clearLogButton)

        testAuthButton.setOnClickListener {
            testAuth()
        }

        testFirestoreButton.setOnClickListener {
            testFirestore()
        }

        testOrdersButton.setOnClickListener {
            testOrders()
        }

        clearLogButton.setOnClickListener {
            clearLog()
        }

        // Log startup
        log("Debug Activity Started")
    }

    private fun testAuth() {
        log("Testing Authentication Service...")
        
        val currentUser = authService.getCurrentUser()
        if (currentUser != null) {
            log("User is signed in: ${currentUser.email}")
        } else {
            log("No user is currently signed in")
        }
    }

    private fun testFirestore() {
        log("Testing Firestore Service...")
        
        lifecycleScope.launch {
            try {
                val userResult = firestoreService.getUserProfile()
                userResult.onSuccess { user ->
                    if (user != null) {
                        log("Got user profile: ${user.name}")
                    } else {
                        log("User profile not found")
                    }
                }.onFailure { exception ->
                    log("Failed to get user profile: ${exception.message}")
                }
            } catch (e: Exception) {
                log("Exception testing Firestore: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    private fun testOrders() {
        log("Testing Orders...")
        
        lifecycleScope.launch {
            try {
                val pendingOrdersResult = firestoreService.getPendingOrders()
                pendingOrdersResult.onSuccess { orders ->
                    log("Got ${orders.size} pending orders")
                    orders.forEach { order ->
                        log("Order ID: ${order.orderId}")
                    }
                }.onFailure { exception ->
                    log("Failed to get pending orders: ${exception.message}")
                }
            } catch (e: Exception) {
                log("Exception testing orders: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    private fun log(message: String) {
        Log.d(TAG, message)
        runOnUiThread {
            logTextView.append("$message\n\n")
        }
    }

    private fun clearLog() {
        runOnUiThread {
            logTextView.text = ""
        }
    }
} 