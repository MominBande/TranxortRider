package com.tranxortrider.deliveryrider.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.tranxortrider.deliveryrider.R
import com.tranxortrider.deliveryrider.application_verification
import android.media.RingtoneManager
import android.net.Uri

/**
 * Service for handling notifications
 */
class NotificationService(private val context: Context) {
    
    companion object {
        private const val CHANNEL_ID = "tranxort_rider_channel"
        private const val CHANNEL_NAME = "TranxortRider Notifications"
        private const val CHANNEL_DESCRIPTION = "Notifications for TranxortRider app"
        
        private const val ORDER_CHANNEL_ID = "tranxort_rider_order_channel"
        private const val ORDER_CHANNEL_NAME = "Order Notifications"
        private const val ORDER_CHANNEL_DESCRIPTION = "Notifications for orders"
        
        private const val ORDER_ASSIGNMENT_CHANNEL_ID = "tranxort_rider_assignment_channel"
        private const val ORDER_ASSIGNMENT_CHANNEL_NAME = "Order Assignment Notifications"
        private const val ORDER_ASSIGNMENT_CHANNEL_DESCRIPTION = "Notifications for order assignments"
        
        private const val DELIVERY_AREA_CHANNEL_ID = "tranxort_rider_delivery_area_channel"
        private const val DELIVERY_AREA_CHANNEL_NAME = "Delivery Area Notifications"
        private const val DELIVERY_AREA_CHANNEL_DESCRIPTION = "Notifications for delivery area transitions"
        
        // Notification IDs
        const val NOTIFICATION_VERIFICATION_ID = 1001
        const val NOTIFICATION_NEW_ORDER_ID = 1002
        const val NOTIFICATION_ORDER_UPDATE_ID = 1003
        const val NOTIFICATION_ORDER_ASSIGNMENT_ID = 1004
        const val NOTIFICATION_DELIVERY_AREA_ID = 1005
    }
    
    init {
        // Create notification channels for Android O and above
        createNotificationChannels()
    }
    
    /**
     * Create the notification channels
     */
    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            
            // General notifications channel
            val generalChannel = NotificationChannel(
                CHANNEL_ID, 
                CHANNEL_NAME, 
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = CHANNEL_DESCRIPTION
            }
            
            // Order notifications channel
            val orderChannel = NotificationChannel(
                ORDER_CHANNEL_ID, 
                ORDER_CHANNEL_NAME, 
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = ORDER_CHANNEL_DESCRIPTION
                enableVibration(true)
                enableLights(true)
            }
            
            // Order assignment notifications channel
            val assignmentChannel = NotificationChannel(
                ORDER_ASSIGNMENT_CHANNEL_ID, 
                ORDER_ASSIGNMENT_CHANNEL_NAME, 
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = ORDER_ASSIGNMENT_CHANNEL_DESCRIPTION
                enableVibration(true)
                enableLights(true)
            }
            
            // Delivery area notifications channel
            val deliveryAreaChannel = NotificationChannel(
                DELIVERY_AREA_CHANNEL_ID,
                DELIVERY_AREA_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = DELIVERY_AREA_CHANNEL_DESCRIPTION
                enableVibration(true)
            }
            
            // Register the channels with the system
            notificationManager.createNotificationChannel(generalChannel)
            notificationManager.createNotificationChannel(orderChannel)
            notificationManager.createNotificationChannel(assignmentChannel)
            notificationManager.createNotificationChannel(deliveryAreaChannel)
        }
    }
    
    /**
     * Show a verification status notification
     * 
     * @param title The notification title
     * @param message The notification message
     * @param status The verification status
     */
    fun showVerificationStatusNotification(title: String, message: String, status: String) {
        val intent = Intent(context, application_verification::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        
        val pendingIntent = PendingIntent.getActivity(
            context, 
            0, 
            intent, 
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        
        val smallIcon = when (status.lowercase()) {
            "approved" -> R.drawable.ic_check_circle
            "rejected" -> R.drawable.ic_error
            else -> R.drawable.ic_pending
        }
        
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(smallIcon)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        
        with(NotificationManagerCompat.from(context)) {
            try {
                notify(NOTIFICATION_VERIFICATION_ID, builder.build())
            } catch (e: SecurityException) {
                // Handle notification permission not granted
                // This can happen on Android 13+ if notification permission is not granted
            }
        }
    }
    
    /**
     * Show a document verification notification
     * 
     * @param documentType The type of document
     * @param status The verification status
     */
    fun showDocumentVerificationNotification(documentType: String, status: String) {
        val documentName = when (documentType) {
            "GOVERNMENT_ID" -> "Government ID"
            "DRIVER_LICENSE" -> "Driver License"
            "VEHICLE_REGISTRATION" -> "Vehicle Registration"
            "VEHICLE_INSURANCE" -> "Vehicle Insurance"
            else -> documentType
        }
        
        val title = when (status.lowercase()) {
            "approved" -> "Document Approved"
            "rejected" -> "Document Rejected"
            else -> "Document Status Update"
        }
        
        val message = when (status.lowercase()) {
            "approved" -> "Your $documentName has been approved."
            "rejected" -> "Your $documentName was rejected. Please reupload."
            else -> "Your $documentName status has been updated."
        }
        
        showVerificationStatusNotification(title, message, status)
    }
    
    /**
     * Show an account verification notification
     * 
     * @param status The verification status
     */
    fun showAccountVerificationNotification(status: String) {
        val title = when (status.lowercase()) {
            "approved" -> "Account Approved"
            "rejected" -> "Account Verification Failed"
            else -> "Account Status Update"
        }
        
        val message = when (status.lowercase()) {
            "approved" -> "Your account has been verified. You can now start accepting orders."
            "rejected" -> "Your account verification failed. Please check your documents."
            else -> "Your account verification status has been updated."
        }
        
        showVerificationStatusNotification(title, message, status)
    }
    
    /**
     * Show an order notification
     * 
     * @param title The notification title
     * @param message The notification message
     * @param intent The intent to launch when the notification is tapped
     * @param channelId The channel ID to use
     * @param alertUser Whether to play sound and vibrate
     */
    fun showOrderNotification(
        title: String,
        message: String,
        intent: Intent,
        channelId: String = ORDER_CHANNEL_ID,
        alertUser: Boolean = false
    ) {
        val pendingIntent = PendingIntent.getActivity(
            context, 
            0, 
            intent, 
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        
        // Add sound and vibration if needed
        if (alertUser) {
            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            builder
                .setSound(defaultSoundUri)
                .setVibrate(longArrayOf(0, 500, 200, 500))
        }
        
        // Show notification
        with(NotificationManagerCompat.from(context)) {
            try {
                val notificationId = when (channelId) {
                    ORDER_CHANNEL_ID -> NOTIFICATION_NEW_ORDER_ID
                    ORDER_ASSIGNMENT_CHANNEL_ID -> NOTIFICATION_ORDER_ASSIGNMENT_ID
                    else -> NOTIFICATION_ORDER_UPDATE_ID
                }
                notify(notificationId, builder.build())
            } catch (e: SecurityException) {
                // Handle notification permission not granted
                // This can happen on Android 13+ if notification permission is not granted
            }
        }
    }
    
    /**
     * Show a delivery area notification
     */
    fun showDeliveryAreaNotification(
        title: String,
        message: String,
        channelId: String = DELIVERY_AREA_CHANNEL_ID
    ) {
        // Create intent for notification tap action
        val intent = Intent(context, com.tranxortrider.deliveryrider.home_screen::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("SHOW_DELIVERY_AREA_INFO", true)
        }
        
        val pendingIntent = PendingIntent.getActivity(
            context, 
            0, 
            intent, 
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_location)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        
        // Add sound
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        builder.setSound(defaultSoundUri)
        
        with(NotificationManagerCompat.from(context)) {
            try {
                notify(NOTIFICATION_DELIVERY_AREA_ID, builder.build())
            } catch (e: SecurityException) {
                // Handle notification permission not granted
                // This can happen on Android 13+ if notification permission is not granted
            }
        }
    }
} 