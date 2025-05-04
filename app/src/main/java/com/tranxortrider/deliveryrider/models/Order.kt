package com.tranxortrider.deliveryrider.models

import java.util.Date
import com.tranxortrider.deliveryrider.models.OrderStatus

/**
 * Data class representing a delivery order
 */
data class Order(
    var id: String,
    val orderId: String,
    val customerName: String,
    val customerAddress: String,
    val customerPhone: String,
    val restaurantName: String,
    val restaurantAddress: String,
    val restaurantPhone: String,
    val items: List<Any>,
    val totalAmount: Double,
    val deliveryFee: Double,
    val status: OrderStatus,
    val paymentMethod: String,
    val specialInstructions: String? = null,
    val distance: Double,
    val estimatedDeliveryTime: Int, // in minutes
    val createdAt: Date,
    val acceptedAt: Date? = null,
    val pickedUpAt: Date? = null,
    val deliveredAt: Date? = null,
    val canceledAt: Date? = null,
    val cancelReason: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val customerLatitude: Double? = null,
    val customerLongitude: Double? = null,
    val restaurantLatitude: Double? = null,
    val restaurantLongitude: Double? = null,
    val batchId: String? = null
)

/**
 * Data class representing an item in an order
 */
data class OrderItem(
    val name: String,
    val quantity: Int,
    val price: Double,
    val notes: String? = null
) 