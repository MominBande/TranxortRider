package com.tranxortrider.deliveryrider.models

import com.tranxortrider.deliveryrider.models.OrderStatus
import java.util.Date

/**
 * Data class representing a delivery history item with all necessary details
 */
data class DeliveryHistoryItem(
    val id: String,
    val orderId: String,
    val customerName: String,
    val restaurantName: String,
    val deliveryAddress: String = "",
    val amount: Double,
    val earning: Double,
    val date: Date,
    val deliveryTime: Int = 0, // in minutes
    val distance: Double = 0.0, // in kilometers
    val status: OrderStatus,
    val statusUpdates: List<StatusUpdate> = emptyList()
)

/**
 * Data class representing a status update for tracking order progress
 */
data class StatusUpdate(
    val status: OrderStatus,
    val timestamp: Date,
    val notes: String? = null
) 