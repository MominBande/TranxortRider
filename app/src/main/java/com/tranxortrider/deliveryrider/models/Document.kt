package com.tranxortrider.deliveryrider.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Data class for user documents
 */
@Parcelize
data class Document(
    val id: String = "",
    val userId: String = "",
    val type: String = "",
    val name: String = "",
    val fileUrl: String = "",
    val status: String = "pending",
    val uploadedAt: Long = 0,
    val updatedAt: Long = 0,
    val reviewedAt: Long = 0,
    val reviewedBy: String = "",
    val rejectionReason: String = "",
    val storageProvider: String = "firebase",
    val storagePath: String = ""
) : Parcelable 