package com.tranxortrider.deliveryrider.models

import java.util.Date

/**
 * Data class representing an earnings item
 */
data class EarningItem(
    val id: String,
    val orderId: String,
    val amount: Double,
    val date: Date,
    val status: String,
    val customerName: String? = null,
    val restaurantName: String? = null
) 