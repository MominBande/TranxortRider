package com.tranxortrider.deliveryrider.repositories

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.tranxortrider.deliveryrider.models.Notification
import java.util.Date

/**
 * Repository for handling notification data
 */
class NotificationRepository {
    
    private val db = FirebaseFirestore.getInstance()
    private val notificationsCollection = db.collection("notifications")
    
    /**
     * Get all notifications for the current user
     *
     * @param callback Callback with success status, message, and notifications list
     */
    fun getNotifications(userId: String? = null, callback: (Boolean, String, List<Notification>?) -> Unit) {
        if (userId == null) {
            callback(false, "User ID is required", null)
            return
        }
        
        notificationsCollection
            .whereEqualTo("userId", userId)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents ->
                val notifications = documents.map { doc ->
                    try {
                        Notification(
                            id = doc.id,
                            title = doc.getString("title") ?: "",
                            message = doc.getString("message") ?: "",
                            type = doc.getString("type")?.let {
                                try {
                                    Notification.Type.valueOf(it)
                                } catch (e: Exception) {
                                    Notification.Type.ANNOUNCEMENT
                                }
                            } ?: Notification.Type.ANNOUNCEMENT,
                            createdAt = doc.getTimestamp("createdAt")?.toDate() ?: Date(),
                            isRead = doc.getBoolean("isRead") ?: false,
                            relatedId = doc.getString("relatedId")
                        )
                    } catch (e: Exception) {
                        Log.e("NotificationRepository", "Error parsing notification", e)
                        null
                    }
                }.filterNotNull()
                
                callback(true, "Notifications fetched successfully", notifications)
            }
            .addOnFailureListener { e ->
                Log.e("NotificationRepository", "Error fetching notifications", e)
                callback(false, "Failed to fetch notifications: ${e.message}", null)
                
                // Fallback to mock data in case of failure
                val notifications = getMockNotifications()
                callback(true, "Using fallback notifications", notifications)
            }
    }
    
    /**
     * Mark a notification as read
     *
     * @param notificationId ID of the notification to mark as read
     * @param callback Callback with success status and message
     */
    fun markAsRead(notificationId: String, callback: (Boolean, String) -> Unit) {
        notificationsCollection.document(notificationId)
            .update("isRead", true)
            .addOnSuccessListener {
                callback(true, "Notification marked as read")
            }
            .addOnFailureListener { e ->
                Log.e("NotificationRepository", "Error marking notification as read", e)
                callback(false, "Failed to mark notification as read: ${e.message}")
            }
    }
    
    /**
     * Delete a notification
     *
     * @param notificationId ID of the notification to delete
     * @param callback Callback with success status and message
     */
    fun deleteNotification(notificationId: String, callback: (Boolean, String) -> Unit) {
        notificationsCollection.document(notificationId)
            .delete()
            .addOnSuccessListener {
                callback(true, "Notification deleted")
            }
            .addOnFailureListener { e ->
                Log.e("NotificationRepository", "Error deleting notification", e)
                callback(false, "Failed to delete notification: ${e.message}")
            }
    }
    
    /**
     * Clear all notifications for the current user
     *
     * @param callback Callback with success status and message
     */
    fun clearAllNotifications(userId: String? = null, callback: (Boolean, String) -> Unit) {
        if (userId == null) {
            callback(false, "User ID is required", )
            return
        }
        
        // Get all notifications for the user
        notificationsCollection
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { documents ->
                // Use a batch write to delete them all
                val batch = db.batch()
                documents.forEach { doc ->
                    batch.delete(notificationsCollection.document(doc.id))
                }
                
                // Commit the batch
                batch.commit()
                    .addOnSuccessListener {
                        callback(true, "All notifications cleared")
                    }
                    .addOnFailureListener { e ->
                        Log.e("NotificationRepository", "Error clearing notifications", e)
                        callback(false, "Failed to clear notifications: ${e.message}")
                    }
            }
            .addOnFailureListener { e ->
                Log.e("NotificationRepository", "Error fetching notifications to clear", e)
                callback(false, "Failed to clear notifications: ${e.message}")
            }
    }
    
    /**
     * Get unread notification count for the current user
     *
     * @param callback Callback with the count of unread notifications
     */
    fun getUnreadCount(userId: String? = null, callback: (Int) -> Unit) {
        if (userId == null) {
            callback(0)
            return
        }
        
        notificationsCollection
            .whereEqualTo("userId", userId)
            .whereEqualTo("isRead", false)
            .get()
            .addOnSuccessListener { documents ->
                callback(documents.size())
            }
            .addOnFailureListener { e ->
                Log.e("NotificationRepository", "Error getting unread count", e)
                callback(0)
            }
    }
    
    /**
     * Mock data for testing - used as fallback only
     */
    private fun getMockNotifications(): List<Notification> {
        val now = Date()
        val oneDayAgo = Date(now.time - 24 * 60 * 60 * 1000)
        val twoDaysAgo = Date(now.time - 2 * 24 * 60 * 60 * 1000)
        
        return listOf(
            Notification(
                id = "mock1",
                title = "Welcome to TranxortRider",
                message = "Thank you for joining our delivery team. Check your 'Available Orders' tab to start accepting deliveries.",
                type = Notification.Type.ANNOUNCEMENT,
                createdAt = now,
                isRead = false,
                relatedId = null
            ),
            Notification(
                id = "mock2",
                title = "New Features Available",
                message = "We've updated the app with new tracking features. Swipe down to refresh your home screen.",
                type = Notification.Type.ANNOUNCEMENT,
                createdAt = oneDayAgo,
                isRead = true,
                relatedId = null
            ),
            Notification(
                id = "mock3",
                title = "First Earnings",
                message = "Complete your first delivery to start earning. All earnings are processed weekly.",
                type = Notification.Type.EARNINGS,
                createdAt = twoDaysAgo,
                isRead = false,
                relatedId = null
            )
        )
    }
} 