package com.tranxortrider.deliveryrider.models

import java.util.Date

/**
 * Represents a notification in the app
 */
data class Notification(
    val id: String,
    val title: String,
    val message: String,
    val type: Type,
    val createdAt: Date,
    val isRead: Boolean = false,
    val relatedId: String? = null
) {
    /**
     * Defines the type of notification
     */
    enum class Type {
        NEW_ORDER,          // When a new order is available
        ORDER_STATUS_CHANGED, // When an order's status changes
        EARNINGS,           // When earnings are updated or deposited
        ANNOUNCEMENT,       // General announcements from the platform
        BATCH_ASSIGNMENT    // When assigned to a batch of orders
    }
} 