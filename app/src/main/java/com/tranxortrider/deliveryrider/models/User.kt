package com.tranxortrider.deliveryrider.models

/**
 * Data class representing a user in the system
 */
data class User(
    val id: String = "",
    val email: String = "",
    val name: String = "",
    val phone: String = "",
    val profilePicUrl: String = "",
    val isVerified: Boolean = false,
    val token: String? = null,
    val address: String = "",
    val vehicleType: String = "",
    val vehicleMake: String = "",
    val vehicleModel: String = "",
    val vehiclePlate: String = "",
    val isAvailable: Boolean = false,
    val photoUrl: String = ""
) 