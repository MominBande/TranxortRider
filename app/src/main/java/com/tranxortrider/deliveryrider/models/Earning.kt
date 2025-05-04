package com.tranxortrider.deliveryrider.models

import java.util.Date

/**
 * Data class representing an earning transaction
 */
data class Earning(
    val id: String,
    val orderId: String,
    val amount: Double,
    val date: Date,
    val description: String,
    val orderReference: String
) 