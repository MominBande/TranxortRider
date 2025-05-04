package com.tranxortrider.deliveryrider.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.tranxortrider.deliveryrider.R
import com.tranxortrider.deliveryrider.home_screen
import com.tranxortrider.deliveryrider.utils.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.tranxortrider.deliveryrider.order_details

/**
 * Service for handling Firebase Cloud Messaging (FCM) notifications
 */
class PushNotificationService : FirebaseMessagingService() {
    
    private val TAG = "PushNotificationService"
    private val firestoreService = FirestoreService()
    
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        
        Log.d(TAG, "From: ${remoteMessage.from}")
        
        // Check if message contains a data payload
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")
            
            // Handle different notification types
            when (remoteMessage.data["type"]) {
                "verification_status" -> {
                    handleVerificationStatusNotification(remoteMessage.data)
                }
                "document_status" -> {
                    handleDocumentStatusNotification(remoteMessage.data)
                }
                "new_order" -> {
                    handleNewOrderNotification(remoteMessage.data)
                }
                "order_assignment" -> {
                    handleOrderAssignmentNotification(remoteMessage.data)
                }
                "order_cancellation" -> {
                    handleOrderCancellationNotification(remoteMessage.data)
                }
                else -> {
                    // Handle other notification types or show a generic notification
                    showGenericNotification(remoteMessage)
                }
            }
        }
        
        // Check if message contains a notification payload
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
            // If there's no data payload, show a notification with the notification payload
            if (remoteMessage.data.isEmpty()) {
                showGenericNotification(remoteMessage)
            }
        }
    }
    
    /**
     * Called when a new token is generated
     */
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "Refreshed token: $token")
        
        // Save the token to shared preferences
        val sessionManager = SessionManager(this)
        sessionManager.saveFirebaseToken(token)
        
        // If the user is logged in, update the token on the server
        sessionManager.getUser()?.let { user ->
            // Here you would call your API to update the token
            // For now, we'll just log it
            Log.d(TAG, "Updating token for user ${user.id}")
        }
    }
    
    /**
     * Handle verification status notifications
     */
    private fun handleVerificationStatusNotification(data: Map<String, String>) {
        val status = data["status"] ?: "pending"
        val notificationService = NotificationService(this)
        notificationService.showAccountVerificationNotification(status)
    }
    
    /**
     * Handle document status notifications
     */
    private fun handleDocumentStatusNotification(data: Map<String, String>) {
        val documentType = data["document_type"] ?: ""
        val status = data["status"] ?: "pending"
        val notificationService = NotificationService(this)
        notificationService.showDocumentVerificationNotification(documentType, status)
    }
    
    /**
     * Handle new order notifications
     */
    private fun handleNewOrderNotification(data: Map<String, String>) {
        val orderId = data["order_id"] ?: ""
        val restaurantName = data["restaurant_name"] ?: "Restaurant"
        val deliveryAddress = data["delivery_address"] ?: "Address"
        
        val title = "New Order Available"
        val message = "New order from $restaurantName to $deliveryAddress"
        
        // Create intent for notification tap action
        val intent = Intent(this, home_screen::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            putExtra("OPEN_AVAILABLE_ORDERS", true)
        }
        
        val notificationService = NotificationService(this)
        notificationService.showOrderNotification(
            title, 
            message, 
            intent,
            "new_order_channel",
            true // Play sound and vibrate
        )
    }
    
    /**
     * Handle order assignment notifications
     */
    private fun handleOrderAssignmentNotification(data: Map<String, String>) {
        val orderId = data["order_id"] ?: ""
        val restaurantName = data["restaurant_name"] ?: "Restaurant"
        val customerName = data["customer_name"] ?: "Customer"
        
        val title = "New Order Assigned"
        val message = "You have been assigned an order from $restaurantName for $customerName"
        
        // Create intent to open order details
        val intent = Intent(this, order_details::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            putExtra("ORDER_ID", orderId)
        }
        
        val notificationService = NotificationService(this)
        notificationService.showOrderNotification(
            title, 
            message, 
            intent,
            "order_assignment_channel",
            true // Play sound and vibrate
        )
    }
    
    /**
     * Handle order cancellation notifications
     */
    private fun handleOrderCancellationNotification(data: Map<String, String>) {
        val orderId = data["order_id"] ?: ""
        val reason = data["reason"] ?: "No reason provided"
        
        val title = "Order Cancelled"
        val message = "Order #$orderId has been cancelled. Reason: $reason"
        
        val notificationService = NotificationService(this)
        notificationService.showVerificationStatusNotification(title, message, "warning")
    }
    
    /**
     * Show a generic notification for messages that don't have a specific handler
     */
    private fun showGenericNotification(remoteMessage: RemoteMessage) {
        val title = remoteMessage.notification?.title ?: "TranxortRider"
        val message = remoteMessage.notification?.body ?: "You have a new notification."
        val notificationService = NotificationService(this)
        notificationService.showVerificationStatusNotification(title, message, "info")
    }
} 