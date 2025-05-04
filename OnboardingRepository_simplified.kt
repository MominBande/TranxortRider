package com.tranxortrider.deliveryrider.repositories

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tranxortrider.deliveryrider.api.DocumentType
import com.tranxortrider.deliveryrider.api.DocumentUploadResponse
import com.tranxortrider.deliveryrider.api.SetupProfileResponse
import com.tranxortrider.deliveryrider.api.VerificationStatusResponse
import com.tranxortrider.deliveryrider.models.Document
import com.tranxortrider.deliveryrider.models.User
import com.tranxortrider.deliveryrider.utils.FileUtils
import com.tranxortrider.deliveryrider.utils.SupabaseClient
import io.github.jan.supabase.storage.BucketApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.File
import java.util.Date

/**
 * Repository class for handling user onboarding operations using Firebase
 * This is a simplified version that assumes database collections already exist
 */
class OnboardingRepository {
    private val TAG = "OnboardingRepository"
    private val db: FirebaseFirestore = Firebase.firestore
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val documentsBucket: BucketApi = SupabaseClient.getDocumentsBucket()
    
    /**
     * Setup user profile with personal information
     * This version updates existing documents rather than creating them from scratch
     */
    fun setupProfile(
        userId: String,
        fullName: String,
        phoneNumber: String,
        address: String? = null,
        city: String? = null,
        state: String? = null,
        zipCode: String? = null,
        vehicleType: String? = null,
        onResponse: (Boolean, String, SetupProfileResponse?) -> Unit
    ) {
        // Create user data map with only the fields that need to be updated
        val userData = hashMapOf<String, Any>(
            "name" to fullName,
            "phone" to phoneNumber,
            "email" to (auth.currentUser?.email ?: ""),
            "address" to (address ?: ""),
            "city" to (city ?: ""),
            "state" to (state ?: ""),
            "zipCode" to (zipCode ?: ""),
            "vehicleType" to (vehicleType ?: ""),
            "updatedAt" to Date()
        )
        
        // Update user document
        db.collection("users")
            .document(userId)
            .update(userData)
            .addOnSuccessListener {
                Log.d(TAG, "User profile updated for $userId")
                
                // Update rider entry as well
                val riderData = hashMapOf<String, Any>(
                    "name" to fullName,
                    "phone" to phoneNumber,
                    "email" to (auth.currentUser?.email ?: "")
                )
                
                db.collection("riders")
                    .document(userId)
                    .update(riderData)
                    .addOnSuccessListener {
                        Log.d(TAG, "Rider profile updated for $userId")
                        
                        // Convert response to User object and return
                        val user = User(
                            id = userId,
                            email = auth.currentUser?.email ?: "",
                            name = fullName,
                            phone = phoneNumber,
                            profilePicUrl = "",
                            address = address ?: "",
                            vehicleType = vehicleType ?: "",
                            isAvailable = true,
                            isVerified = false
                        )
                        
                        val response = SetupProfileResponse(
                            success = true,
                            message = "Profile setup successful",
                            user = user
                        )
                        
                        onResponse(true, "Profile setup successful", response)
                    }
                    .addOnFailureListener { e ->
                        Log.e(TAG, "Error updating rider profile", e)
                        onResponse(false, "Failed to update rider profile: ${e.message}", null)
                    }
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error updating user profile", e)
                
                // If the document doesn't exist yet, create it
                if (e.message?.contains("NOT_FOUND") == true) {
                    createNewUserProfile(userId, fullName, phoneNumber, address, city, state, zipCode, vehicleType, onResponse)
                } else {
                    onResponse(false, "Failed to update user profile: ${e.message}", null)
                }
            }
    }
    
    /**
     * Create a new user profile if it doesn't exist yet
     * This is a fallback method in case the database setup script wasn't run
     */
    private fun createNewUserProfile(
        userId: String,
        fullName: String,
        phoneNumber: String,
        address: String? = null,
        city: String? = null,
        state: String? = null,
        zipCode: String? = null,
        vehicleType: String? = null,
        onResponse: (Boolean, String, SetupProfileResponse?) -> Unit
    ) {
        // Create user data map
        val userData = hashMapOf(
            "id" to userId,
            "name" to fullName,
            "phone" to phoneNumber,
            "email" to (auth.currentUser?.email ?: ""),
            "address" to (address ?: ""),
            "city" to (city ?: ""),
            "state" to (state ?: ""),
            "zipCode" to (zipCode ?: ""),
            "vehicleType" to (vehicleType ?: ""),
            "createdAt" to Date(),
            "updatedAt" to Date(),
            "isAvailable" to true,
            "isVerified" to false,
            "verificationStatus" to "pending"
        )
        
        // Write to Firestore
        db.collection("users")
            .document(userId)
            .set(userData)
            .addOnSuccessListener {
                Log.d(TAG, "User profile created for $userId")
                
                // Create rider entry as well
                val riderData = hashMapOf(
                    "userId" to userId,
                    "name" to fullName,
                    "phone" to phoneNumber,
                    "email" to (auth.currentUser?.email ?: ""),
                    "createdAt" to Date(),
                    "status" to "pending",
                    "isOnline" to false
                )
                
                db.collection("riders")
                    .document(userId)
                    .set(riderData)
                    .addOnSuccessListener {
                        Log.d(TAG, "Rider profile created for $userId")
                        
                        // Convert response to User object and return
                        val user = User(
                            id = userId,
                            email = auth.currentUser?.email ?: "",
                            name = fullName,
                            phone = phoneNumber,
                            profilePicUrl = "",
                            address = address ?: "",
                            vehicleType = vehicleType ?: "",
                            isAvailable = true,
                            isVerified = false
                        )
                        
                        val response = SetupProfileResponse(
                            success = true,
                            message = "Profile setup successful",
                            user = user
                        )
                        
                        onResponse(true, "Profile setup successful", response)
                    }
                    .addOnFailureListener { e ->
                        Log.e(TAG, "Error creating rider profile", e)
                        onResponse(false, "Failed to create rider profile: ${e.message}", null)
                    }
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error creating user profile", e)
                onResponse(false, "Failed to create user profile: ${e.message}", null)
            }
    }
    
    /**
     * Upload a document for verification
     */
    fun uploadDocument(
        context: Context,
        userId: String,
        documentType: DocumentType,
        documentUri: Uri,
        onResponse: (success: Boolean, message: String, response: String?) -> Unit
    ) {
        // First convert the URI to a file
        try {
            Log.d(TAG, "Starting document upload process for type: ${documentType.name}")
            
            val file = FileUtils.getFileFromUri(context, documentUri)
            if (file == null) {
                Log.e(TAG, "Failed to read file from URI: $documentUri")
                onResponse(false, "Failed to read file", null)
                return
            }
            
            Log.d(TAG, "Original file: ${file.name}, size: ${file.length()} bytes")
            
            // Check file size - Supabase free tier has 50MB limit
            val maxSize = 50 * 1024 * 1024 // 50MB in bytes
            if (file.length() > maxSize) {
                Log.e(TAG, "File exceeds Supabase size limit: ${file.length()} bytes")
                onResponse(false, "File size exceeds 50MB Supabase limit", null)
                return
            }
            
            // For images that are too large but under Supabase limit, compress them for better performance
            val finalFile = if (FileUtils.isFileSizeExceeded(file) && FileUtils.isCompressibleImage(file)) {
                Log.d(TAG, "Compressing image file...")
                val compressed = FileUtils.compressImageFile(file)
                Log.d(TAG, "Compressed file size: ${compressed.length()} bytes")
                compressed
            } else {
                file
            }
            
            // Upload to Supabase
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    // Create file name with timestamp and random UUID to avoid conflicts
                    val timestamp = System.currentTimeMillis()
                    val randomPart = java.util.UUID.randomUUID().toString().substring(0, 8)
                    val fileExtension = FileUtils.getFileExtension(finalFile.name)
                    val fileName = "${documentType.name.lowercase()}_${timestamp}_${randomPart}.$fileExtension"
                    val storagePath = "riders/$userId/documents/$fileName"
                    
                    Log.d(TAG, "Uploading to Supabase path: $storagePath")
                    
                    // Upload to Supabase with retry
                    var fileUrl: String? = null
                    var retryCount = 0
                    val maxRetries = 2
                    
                    while (fileUrl == null && retryCount <= maxRetries) {
                        if (retryCount > 0) {
                            Log.d(TAG, "Retrying upload, attempt $retryCount")
                            delay(1000) // Wait 1 second before retry
                        }
                        
                        fileUrl = uploadToSupabase(finalFile, storagePath)
                        retryCount++
                    }
                    
                    if (fileUrl != null) {
                        Log.d(TAG, "Upload successful, updating Firestore")
                        withContext(Dispatchers.Main) {
                            // Update Firestore with document data
                            updateRiderDocument(userId, documentType, fileUrl, "supabase", storagePath) { success, uploadMessage ->
                                onResponse(success, uploadMessage, fileUrl)
                            }
                        }
                    } else {
                        Log.e(TAG, "Upload to Supabase failed after $maxRetries retries")
                        withContext(Dispatchers.Main) {
                            onResponse(false, "Failed to upload to Supabase after multiple attempts", null)
                        }
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Error in upload process: ${e.javaClass.simpleName}: ${e.message}")
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        onResponse(false, "Error: ${e.message}", null)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception in uploadDocument: ${e.javaClass.simpleName}: ${e.message}")
            e.printStackTrace()
            onResponse(false, "Error processing file: ${e.message}", null)
        }
    }
    
    /**
     * Upload file to Supabase storage
     */
    private suspend fun uploadToSupabase(file: File, path: String): String? {
        return try {
            Log.d(TAG, "Starting upload to Supabase: ${file.name}, size: ${file.length()} bytes, path: $path")
            
            if (!file.exists() || !file.canRead()) {
                Log.e(TAG, "File doesn't exist or can't be read: ${file.absolutePath}")
                return null
            }
            
            // Upload file to Supabase bucket with explicit content type
            val fileExtension = FileUtils.getFileExtension(file.name)
            val contentType = FileUtils.getMimeType(fileExtension)
            
            Log.d(TAG, "Uploading with content type: $contentType")
            
            // Upload with options
            documentsBucket.upload(
                path = path,
                file = file,
                contentType = contentType,
                upsert = true // Overwrite if exists
            )
            
            // Get the public URL
            val publicUrl = documentsBucket.publicUrl(path)
            Log.d(TAG, "File uploaded successfully to Supabase: $publicUrl")
            
            publicUrl
        } catch (e: Exception) {
            Log.e(TAG, "Error uploading to Supabase: ${e.javaClass.simpleName}: ${e.message}")
            e.printStackTrace()
            null
        }
    }
    
    /**
     * Update rider document in Firestore
     * This method adds a new document to the userDocuments collection
     * and also updates the rider's documents field for admin verification
     */
    private fun updateRiderDocument(
        userId: String, 
        documentType: DocumentType, 
        fileUrl: String,
        storageProvider: String = "supabase",
        storagePath: String? = null,
        callback: (Boolean, String) -> Unit
    ) {
        // Create document data
        val documentData = hashMapOf(
            "userId" to userId,
            "documentType" to documentType.name,
            "fileName" to "${documentType.name.lowercase()}_${System.currentTimeMillis()}",
            "fileUrl" to fileUrl,
            "status" to "pending",
            "uploadedAt" to Date(),
            "updatedAt" to Date(),
            "storageProvider" to storageProvider,
            "storagePath" to storagePath
        )
        
        // Add to userDocuments collection
        db.collection("userDocuments")
            .add(documentData)
            .addOnSuccessListener { documentRef ->
                Log.d(TAG, "Document added with ID: ${documentRef.id}")
                
                // Now update the rider document to include this document link
                // This makes it easier for admins to view all documents in one place
                val fieldName = getDocumentFieldName(documentType)
                
                // Create a document info map
                val documentInfo = hashMapOf<String, Any>(
                    "url" to fileUrl,
                    "status" to "pending",
                    "reviewedBy" to null,
                    "reviewDate" to null
                )
                
                // Update the rider document
                db.collection("riders")
                    .document(userId)
                    .get()
                    .addOnSuccessListener { riderDoc ->
                        if (riderDoc.exists()) {
                            // Check if documents map exists
                            val updateData = hashMapOf<String, Any>()
                            
                            if (riderDoc.contains("documents")) {
                                // Documents field exists, update the specific document type
                                updateData["documents.$fieldName"] = documentInfo
                            } else {
                                // Documents field doesn't exist, create it
                                val documents = hashMapOf<String, Any>()
                                documents[fieldName] = documentInfo
                                updateData["documents"] = documents
                            }
                            
                            // Update the rider document
                            db.collection("riders")
                                .document(userId)
                                .update(updateData)
                                .addOnSuccessListener {
                                    Log.d(TAG, "Rider document updated with document link")
                                    callback(true, "Document uploaded successfully")
                                }
                                .addOnFailureListener { e ->
                                    Log.e(TAG, "Error updating rider document with document link", e)
                                    // Still consider this a success since the document was uploaded
                                    callback(true, "Document uploaded successfully, but rider profile update failed")
                                }
                        } else {
                            // Rider document doesn't exist
                            Log.e(TAG, "Rider document doesn't exist")
                            callback(true, "Document uploaded successfully, but rider profile not found")
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.e(TAG, "Error getting rider document", e)
                        // Still consider this a success since the document was uploaded
                        callback(true, "Document uploaded successfully, but rider profile update failed")
                    }
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error adding document", e)
                callback(false, "Failed to store document metadata: ${e.message}")
            }
    }
    
    /**
     * Get the field name for a document type in the rider's documents map
     */
    private fun getDocumentFieldName(documentType: DocumentType): String {
        return when (documentType) {
            DocumentType.DRIVER_LICENSE -> "driverLicense"
            DocumentType.GOVERNMENT_ID -> "governmentId"
            DocumentType.VEHICLE_REGISTRATION -> "vehicleRegistration"
            DocumentType.VEHICLE_INSURANCE -> "vehicleInsurance"
            DocumentType.WORK_AUTHORIZATION -> "workAuthorization"
        }
    }
    
    /**
     * Get verification status for user
     */
    fun getVerificationStatus(
        userId: String,
        onResponse: (Boolean, String, VerificationStatusResponse?) -> Unit
    ) {
        db.collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val status = documentSnapshot.getString("verificationStatus") ?: "pending"
                    
                    // Create a map for document statuses, one entry per DocumentType
                    val documentStatuses = mutableMapOf<String, String>()
                    
                    // Get all user documents
                    db.collection("userDocuments")
                        .whereEqualTo("userId", userId)
                        .get()
                        .addOnSuccessListener { documents ->
                            for (document in documents) {
                                val docType = document.getString("documentType") ?: continue
                                val docStatus = document.getString("status") ?: "pending"
                                documentStatuses[docType] = docStatus
                            }
                            
                            // Fill in missing document types with "not_submitted"
                            for (type in DocumentType.values()) {
                                if (!documentStatuses.containsKey(type.name)) {
                                    documentStatuses[type.name] = "not_submitted"
                                }
                            }
                            
                            // Create the response with required parameters
                            val response = VerificationStatusResponse(
                                success = true,
                                message = "Verification status retrieved",
                                verificationStatus = status,
                                documentsStatus = documentStatuses
                            )
                            
                            onResponse(true, "Verification status: $status", response)
                        }
                        .addOnFailureListener { e ->
                            Log.e(TAG, "Error getting document statuses", e)
                            
                            // Create a basic response with just the overall status
                            val response = VerificationStatusResponse(
                                success = true,
                                message = "Verification status retrieved (partial)",
                                verificationStatus = status,
                                documentsStatus = mapOf(
                                    "GOVERNMENT_ID" to "unknown",
                                    "DRIVER_LICENSE" to "unknown",
                                    "VEHICLE_REGISTRATION" to "unknown",
                                    "VEHICLE_INSURANCE" to "unknown",
                                    "WORK_AUTHORIZATION" to "unknown"
                                )
                            )
                            
                            onResponse(true, "Verification status: $status (partial data)", response)
                        }
                } else {
                    onResponse(false, "User not found", null)
                }
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error getting verification status", e)
                onResponse(false, "Failed to get verification status: ${e.message}", null)
            }
    }
    
    /**
     * Delete a document from storage
     * Also removes the document link from the rider profile
     */
    fun deleteDocument(
        documentId: String,
        onResponse: (success: Boolean, message: String) -> Unit
    ) {
        // First get the document metadata from Firestore
        db.collection("userDocuments")
            .document(documentId)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val userId = documentSnapshot.getString("userId") ?: ""
                    val documentType = documentSnapshot.getString("documentType")
                    val storageProvider = documentSnapshot.getString("storageProvider") ?: "firebase"
                    val storagePath = documentSnapshot.getString("storagePath")
                    
                    // Delete from the appropriate storage provider
                    if (storageProvider == "supabase" && storagePath != null) {
                        // Delete from Supabase
                        CoroutineScope(Dispatchers.IO).launch {
                            try {
                                documentsBucket.delete(storagePath)
                                
                                withContext(Dispatchers.Main) {
                                    // Delete the document metadata from Firestore
                                    db.collection("userDocuments")
                                        .document(documentId)
                                        .delete()
                                        .addOnSuccessListener {
                                            // Now remove the document link from the rider profile
                                            if (userId.isNotEmpty() && documentType != null) {
                                                try {
                                                    val docTypeEnum = DocumentType.valueOf(documentType)
                                                    val fieldName = getDocumentFieldName(docTypeEnum)
                                                    
                                                    // Remove the field from the rider document
                                                    db.collection("riders")
                                                        .document(userId)
                                                        .update("documents.$fieldName", FieldValue.delete())
                                                        .addOnSuccessListener {
                                                            Log.d(TAG, "Document link removed from rider profile")
                                                            onResponse(true, "Document deleted successfully")
                                                        }
                                                        .addOnFailureListener { e ->
                                                            Log.e(TAG, "Error removing document link from rider profile", e)
                                                            onResponse(true, "Document deleted, but link removal failed")
                                                        }
                                                } catch (e: Exception) {
                                                    Log.e(TAG, "Error parsing document type", e)
                                                    onResponse(true, "Document deleted successfully")
                                                }
                                            } else {
                                                onResponse(true, "Document deleted successfully")
                                            }
                                        }
                                        .addOnFailureListener { e ->
                                            onResponse(false, "Failed to delete document metadata: ${e.message}")
                                        }
                                }
                            } catch (e: Exception) {
                                withContext(Dispatchers.Main) {
                                    onResponse(false, "Failed to delete from Supabase: ${e.message}")
                                }
                            }
                        }
                    } else {
                        onResponse(false, "Invalid storage provider or path")
                    }
                } else {
                    onResponse(false, "Document not found")
                }
            }
            .addOnFailureListener { e ->
                onResponse(false, "Failed to get document: ${e.message}")
            }
    }
} 