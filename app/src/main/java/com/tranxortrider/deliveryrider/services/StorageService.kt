package com.tranxortrider.deliveryrider.services

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await
import java.util.UUID

/**
 * Service for handling Firebase Storage operations
 */
class StorageService {
    
    private val storage = FirebaseStorage.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val TAG = "StorageService"
    
    /**
     * Get a reference to the Firebase Storage
     * 
     * @return StorageReference to the root of Firebase Storage
     */
    fun getStorageReference(): StorageReference {
        return storage.reference
    }
    
    /**
     * Upload a profile image to Firebase Storage
     * 
     * @param userId The user ID to associate with the image
     * @param imageUri The URI of the image to upload
     * @return Result containing the download URL if successful, or an error if failed
     */
    suspend fun uploadProfileImage(userId: String, imageUri: Uri): Result<String> {
        return try {
            val currentUser = auth.currentUser ?: return Result.failure(Exception("No authenticated user"))
            
            // Ensure the user is uploading their own profile image
            if (currentUser.uid != userId) {
                return Result.failure(Exception("Not authorized to upload for this user"))
            }
            
            // Reference to the profile images folder
            val storageRef = storage.reference.child("profile_images")
            
            // Generate a unique filename with the user ID
            val filename = "${userId}_${UUID.randomUUID()}"
            val fileRef = storageRef.child(filename)
            
            // Upload the file
            val uploadTask = fileRef.putFile(imageUri).await()
            
            // Get the download URL
            val downloadUrl = fileRef.downloadUrl.await().toString()
            
            Result.success(downloadUrl)
        } catch (e: Exception) {
            Log.e(TAG, "Error uploading profile image", e)
            Result.failure(e)
        }
    }
} 