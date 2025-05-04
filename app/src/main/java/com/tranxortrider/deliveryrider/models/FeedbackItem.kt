package com.tranxortrider.deliveryrider.models

import java.util.Date

/**
 * Data class representing customer feedback on a delivery
 */
data class FeedbackItem(
    val id: String,
    val customerName: String,
    val rating: Int,
    val comment: String,
    val date: Date
) 