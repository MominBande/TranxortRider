package com.tranxortrider.deliveryrider.models

/**
 * Data class representing an emergency contact for the rider
 */
data class EmergencyContact(
    val id: String = "",
    val name: String = "",
    val phoneNumber: String = "",
    val relationship: String = ""
) 