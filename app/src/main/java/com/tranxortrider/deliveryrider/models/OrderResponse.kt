package com.tranxortrider.deliveryrider.models

/**
 * A response wrapper for paginated order lists
 */
data class OrderResponse(
    val orders: List<Order>,
    val totalPages: Int,
    val currentPage: Int,
    val totalCount: Int
) 