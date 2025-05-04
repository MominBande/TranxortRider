package com.tranxortrider.deliveryrider.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.tranxortrider.deliveryrider.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

/**
 * Session manager class for handling user session with Firebase
 */
class SessionManager(private val context: Context) {
    
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val TAG = "SessionManager"
    
    companion object {
        private const val PREF_NAME = "TranxortRiderPrefs"
        private const val USER_KEY = "user_data"
        private const val USER_ONLINE = "is_rider_online"
        private const val LAST_SYNC_TIME = "last_sync_time"
        private const val USER_ID = "user_id"
        private const val USER_EMAIL = "user_email"
        private const val USER_NAME = "user_name"
        private const val USER_PHONE = "user_phone"
        private const val USER_PROFILE_PIC = "user_profile_pic"
        private const val USER_IS_VERIFIED = "user_is_verified"
        private const val USER_TOKEN = "user_token"
        private const val FIREBASE_TOKEN = "firebase_token"
    }
    
    /**
     * Save user data to shared preferences
     */
    fun saveUserData(user: User) {
        val editor = sharedPreferences.edit()
        val userJson = gson.toJson(user)
        editor.putString(USER_KEY, userJson)
        editor.putLong(LAST_SYNC_TIME, System.currentTimeMillis())
        editor.apply()
    }
    
    /**
     * Get user data from shared preferences, with an option to refresh from Firestore
     */
    fun getUser(forceRefresh: Boolean = false): User? {
        // Check if logged in with Firebase first
        val firebaseUser = auth.currentUser
        if (firebaseUser == null) {
            Log.d(TAG, "No Firebase user logged in")
            return null
        }
        
        // If force refresh or data is stale (older than 30 minutes), get fresh data
        val lastSyncTime = sharedPreferences.getLong(LAST_SYNC_TIME, 0)
        val isDataStale = System.currentTimeMillis() - lastSyncTime > 30 * 60 * 1000 // 30 minutes
        
        if (forceRefresh || isDataStale) {
            Log.d(TAG, "Data is stale or refresh forced, fetching from Firestore")
            refreshUserData(firebaseUser.uid)
        }
        
        // Return cached data while refresh happens in background
        val userJson = sharedPreferences.getString(USER_KEY, null)
        return if (userJson != null) {
            gson.fromJson(userJson, User::class.java)
        } else {
            // If we don't have cached data yet, create basic user object from Firebase
            User(
                id = firebaseUser.uid,
                email = firebaseUser.email ?: "",
                name = firebaseUser.displayName ?: "",
                phone = firebaseUser.phoneNumber ?: "",
                profilePicUrl = "",
                isVerified = false,
                isAvailable = false,
                photoUrl = firebaseUser.photoUrl?.toString() ?: ""
            )
        }
    }
    
    /**
     * Refresh user data from Firestore
     */
    private fun refreshUserData(userId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val userDoc = db.collection("users").document(userId).get().await()
                
                if (userDoc.exists()) {
                    // Convert Firestore document to User object matching the exact User class fields
                    val user = User(
                        id = userId,
                        email = userDoc.getString("email") ?: "",
                        name = userDoc.getString("name") ?: "",
                        phone = userDoc.getString("phone") ?: "",
                        profilePicUrl = userDoc.getString("profilePicUrl") ?: "",
                        address = userDoc.getString("address") ?: "",
                        vehicleType = userDoc.getString("vehicleType") ?: "",
                        vehicleMake = userDoc.getString("vehicleMake") ?: "",
                        vehicleModel = userDoc.getString("vehicleModel") ?: "",
                        vehiclePlate = userDoc.getString("vehiclePlate") ?: "",
                        isVerified = userDoc.getBoolean("isVerified") ?: false,
                        isAvailable = userDoc.getBoolean("isAvailable") ?: false,
                        photoUrl = userDoc.getString("photoUrl") ?: ""
                    )
                    
                    withContext(Dispatchers.Main) {
                    saveUserData(user)
                    }
                    Log.d(TAG, "User data refreshed from Firestore")
                } else {
                    Log.w(TAG, "User document doesn't exist in Firestore")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error refreshing user data", e)
            }
        }
    }
    
    /**
     * Check if user is logged in
     */
    fun isLoggedIn(): Boolean {
        return auth.currentUser != null
    }
    
    /**
     * Get rider's online status
     */
    fun isRiderOnline(): Boolean {
        return sharedPreferences.getBoolean(USER_ONLINE, false)
    }
    
    /**
     * Set rider's online status
     */
    fun setRiderOnline(isOnline: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(USER_ONLINE, isOnline)
        editor.apply()
        
        // Also update in Firestore if user is logged in
        val userId = auth.currentUser?.uid
        if (userId != null) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    db.collection("riders").document(userId)
                        .update(
                            "isOnline", isOnline,
                            "lastOnlineStatusChange", System.currentTimeMillis()
                        )
                        .await()
                    Log.d(TAG, "Rider online status updated in Firestore: $isOnline")
                } catch (e: Exception) {
                    Log.e(TAG, "Error updating rider online status", e)
                }
            }
        }
    }
    
    /**
     * Clear user session data
     */
    fun clearSession() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
        
        // Sign out from Firebase Auth
        auth.signOut()
        Log.d(TAG, "User session cleared")
    }
    
    /**
     * Save Firebase token
     */
    fun saveFirebaseToken(token: String) {
        val editor = sharedPreferences.edit()
        editor.putString(FIREBASE_TOKEN, token)
        editor.apply()
    }
    
    /**
     * Get Firebase token
     */
    fun getFirebaseToken(): String? {
        return sharedPreferences.getString(FIREBASE_TOKEN, null)
    }
} 