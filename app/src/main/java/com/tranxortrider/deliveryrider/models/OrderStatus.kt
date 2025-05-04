package com.tranxortrider.deliveryrider.models

/**
 * Enum representing different order statuses in the delivery lifecycle
 */
enum class OrderStatus {
    PENDING,         // Order is pending assignment to a rider
    ASSIGNED,        // Order has been assigned to a rider by admin
    ACCEPTED,        // Rider has accepted the order
    PICKED_UP,       // Rider has picked up the order from restaurant
    IN_TRANSIT,      // Order is in transit (between pickup and delivery)
    COMPLETED,       // Order has been delivered successfully
    CANCELLED,       // Order was cancelled
    FAILED           // Order delivery failed
} 