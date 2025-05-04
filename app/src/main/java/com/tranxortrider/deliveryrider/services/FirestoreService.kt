package com.tranxortrider.deliveryrider.services

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tranxortrider.deliveryrider.models.Order
import com.tranxortrider.deliveryrider.models.OrderItem
import com.tranxortrider.deliveryrider.models.User
import com.tranxortrider.deliveryrider.utils.SharedPreferencesManager
import kotlinx.coroutines.tasks.await
import java.util.Date
import com.tranxortrider.deliveryrider.models.OrderStatus
import com.tranxortrider.deliveryrider.batch
import com.tranxortrider.deliveryrider.models.DeliveryHistoryItem
import java.util.Calendar
import com.tranxortrider.deliveryrider.models.Earning
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.HashMap
import kotlin.collections.Map
import kotlin.collections.MutableMap
import kotlin.collections.hashMapOf
import kotlin.collections.mapOf
import kotlin.collections.mutableMapOf
import com.tranxortrider.deliveryrider.api.DocumentType

class FirestoreService {
    private val db: FirebaseFirestore = Firebase.firestore
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val TAG = "FirestoreService"

    // Collection references
    private val usersCollection = db.collection("users")
    private val ordersCollection = db.collection("orders")
    private val ridersCollection = db.collection("riders")
    private val batchesCollection = db.collection("batches")
    private val earningsCollection = db.collection("earnings")

    /**
     * Helper method to convert Firestore document to Order object
     */
    fun documentToOrder(doc: DocumentSnapshot): Order? {
        try {
            if (!doc.exists()) return null
            
            val id = doc.id
            val orderId = doc.getString("orderId") ?: return null
            val customerName = doc.getString("customerName") ?: return null
            val customerAddress = doc.getString("customerAddress") ?: return null
            val customerPhone = doc.getString("customerPhone") ?: ""
            val restaurantName = doc.getString("restaurantName") ?: return null
            val restaurantAddress = doc.getString("restaurantAddress") ?: return null
            val restaurantPhone = doc.getString("restaurantPhone") ?: ""
            
            // Extract items - handle potentially missing or invalid data
            val itemsMap = doc.get("items") as? List<Map<String, Any>> ?: emptyList()
            val items = itemsMap.mapNotNull { itemData ->
                try {
                    val itemName = itemData["name"] as? String ?: return@mapNotNull null
                    val itemPrice = (itemData["price"] as? Number)?.toDouble() ?: 0.0
                    val itemQuantity = (itemData["quantity"] as? Number)?.toInt() ?: 1
                    val itemNote = itemData["note"] as? String ?: ""
                    
                    OrderItem(
                        name = itemName,
                        price = itemPrice,
                        quantity = itemQuantity,
                        notes = itemNote
                    )
                } catch (e: Exception) {
                    Log.e(TAG, "Error parsing order item", e)
                    null
                }
            }
            
            if (items.isEmpty()) {
                Log.w(TAG, "Order has no valid items: $orderId")
                // Continue anyway as the order might be in a special state
            }
            
            val totalAmount = (doc.getDouble("totalAmount") ?: 0.0)
            val deliveryFee = (doc.getDouble("deliveryFee") ?: 0.0)
            val status = doc.getString("status") ?: return null
            val paymentMethod = doc.getString("paymentMethod") ?: "CASH"
            val specialInstructions = doc.getString("specialInstructions")
            val distance = doc.getDouble("distance") ?: 0.0
            val estimatedDeliveryTime = (doc.getLong("estimatedDeliveryTime")?.toInt() ?: 30)
            
            val createdAt = doc.getDate("createdAt") ?: Date()
            val acceptedAt = doc.getDate("acceptedAt")
            val pickedUpAt = doc.getDate("pickedUpAt")
            val deliveredAt = doc.getDate("deliveredAt")
            val canceledAt = doc.getDate("canceledAt")
            val cancelReason = doc.getString("cancelReason")
            
            // Get batch ID if available
            val batchId = doc.getString("batchId")
            
            // Location data - safely extract coordinates
            val locationMap = doc.get("riderLocation") as? Map<*, *>
            val latitude = locationMap?.get("latitude") as? Double
            val longitude = locationMap?.get("longitude") as? Double
            
            val customerLocationMap = doc.get("customerLocation") as? Map<*, *>
            val customerLatitude = customerLocationMap?.get("latitude") as? Double
            val customerLongitude = customerLocationMap?.get("longitude") as? Double
            
            val restaurantLocationMap = doc.get("restaurantLocation") as? Map<*, *>
            val restaurantLatitude = restaurantLocationMap?.get("latitude") as? Double
            val restaurantLongitude = restaurantLocationMap?.get("longitude") as? Double
            
            Log.d(TAG, "Converting document to Order - ID: $id, OrderID: $orderId, Status: $status, AssignedTo: ${doc.getString("assignedRider")}")
            
            return Order(
                id = id,
                orderId = orderId,
                customerName = customerName,
                customerAddress = customerAddress,
                customerPhone = customerPhone,
                restaurantName = restaurantName,
                restaurantAddress = restaurantAddress,
                restaurantPhone = restaurantPhone,
                items = items,
                totalAmount = totalAmount,
                deliveryFee = deliveryFee,
                status = OrderStatus.valueOf(status),
                paymentMethod = paymentMethod,
                specialInstructions = specialInstructions,
                distance = distance,
                estimatedDeliveryTime = estimatedDeliveryTime,
                createdAt = createdAt,
                acceptedAt = acceptedAt,
                pickedUpAt = pickedUpAt,
                deliveredAt = deliveredAt,
                canceledAt = canceledAt,
                cancelReason = cancelReason,
                latitude = latitude,
                longitude = longitude,
                customerLatitude = customerLatitude,
                customerLongitude = customerLongitude,
                restaurantLatitude = restaurantLatitude,
                restaurantLongitude = restaurantLongitude,
                batchId = batchId
            )
        } catch (e: Exception) {
            Log.e(TAG, "Error converting document to order", e)
            return null
        }
    }

    // Add this utility method at the top of the class
    /**
     * Helper method to cast maps to the expected type
     */
    private fun <K, V> Map<K, V>.toFirestoreMap(): Map<String, Any> {
        @Suppress("UNCHECKED_CAST")
        return this as Map<String, Any>
    }

    // User operations
    suspend fun createUserProfile(user: User): Result<User> {
        return try {
            val currentUser = auth.currentUser ?: return Result.failure(Exception("No authenticated user"))
            val userWithId = user.copy(id = currentUser.uid)
            
            // Check if user document already exists
            val userDoc = usersCollection.document(currentUser.uid).get().await()
            
            if (userDoc.exists()) {
                // Update existing user document
                usersCollection.document(currentUser.uid).update(
                    mapOf(
                        "name" to user.name,
                        "phone" to user.phone,
                        "email" to (currentUser.email ?: ""),
                        "address" to (user.address ?: ""),
                        "vehicleType" to (user.vehicleType ?: ""),
                        "vehicleMake" to (user.vehicleMake ?: ""),
                        "vehicleModel" to (user.vehicleModel ?: ""),
                        "vehiclePlate" to (user.vehiclePlate ?: ""),
                        "updatedAt" to Date()
                    )
                ).await()
            } else {
                // Create new user document
                usersCollection.document(currentUser.uid).set(userWithId).await()
            }
            
            // Check if rider document already exists
            val riderDoc = ridersCollection.document(currentUser.uid).get().await()
            
            if (riderDoc.exists()) {
                // Update existing rider document
                ridersCollection.document(currentUser.uid).update(
                    mapOf(
                        "name" to user.name,
                        "phone" to user.phone,
                        "email" to (currentUser.email ?: ""),
                        "updatedAt" to Date()
                    )
                ).await()
            } else {
                // Create new rider document
                val riderData = hashMapOf(
                    "userId" to currentUser.uid,
                    "email" to (currentUser.email ?: ""),
                    "name" to user.name,
                    "phone" to user.phone,
                    "status" to "pending",
                    "applicationStatus" to "pending",
                    "isOnline" to false,
                    "createdAt" to Date(),
                    "documents" to hashMapOf<String, Any>()
                )
                ridersCollection.document(currentUser.uid).set(riderData).await()
            }
            
            Result.success(userWithId)
        } catch (e: Exception) {
            Log.e(TAG, "Error creating/updating user profile", e)
            Result.failure(e)
        }
    }

    /**
     * Get user profile for the current user
     */
    suspend fun getUserProfile(): Result<User?> {
        return try {
            val currentUser = auth.currentUser ?: return Result.failure(Exception("No authenticated user"))
            val docSnapshot = usersCollection.document(currentUser.uid).get().await()
            
            if (docSnapshot.exists()) {
                val user = docSnapshot.toObject(User::class.java)
                Result.success(user)
            } else {
                Result.success(null)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting user profile", e)
            Result.failure(e)
        }
    }

    /**
     * Get user profile for a specific user ID
     */
    suspend fun getUserProfile(userId: String): Result<User?> {
        return try {
            val docSnapshot = usersCollection.document(userId).get().await()
            
            if (docSnapshot.exists()) {
                val user = docSnapshot.toObject(User::class.java)
                Result.success(user)
            } else {
                Result.success(null)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting user profile by ID", e)
            Result.failure(e)
        }
    }

    /**
     * Get user profile for the current user (non-suspend version with callback)
     */
    fun getUserProfile(callback: (User?) -> Unit) {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            callback(null)
            return
        }
        
        usersCollection.document(currentUser.uid).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val user = document.toObject(User::class.java)
                    callback(user)
                } else {
                    callback(null)
                }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error getting user profile", exception)
                callback(null)
            }
    }

    /**
     * Get user profile for a specific user ID (non-suspend version with callback)
     */
    fun getUserProfile(userId: String, callback: (User?) -> Unit) {
        usersCollection.document(userId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val user = document.toObject(User::class.java)
                    callback(user)
                } else {
                    callback(null)
                }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error getting user profile by ID", exception)
                callback(null)
            }
    }

    /**
     * Update user profile with a User object
     */
    suspend fun updateUserProfile(user: User): Result<Boolean> {
        return try {
            val currentUser = auth.currentUser ?: return Result.failure(Exception("No authenticated user"))
            
            // Convert User object to a Map
            val userData = mapOf(
                "name" to user.name,
                "email" to user.email,
                "phone" to user.phone,
                "address" to user.address,
                "vehicleType" to user.vehicleType,
                "vehicleMake" to user.vehicleMake,
                "vehicleModel" to user.vehicleModel,
                "vehiclePlate" to user.vehiclePlate,
                "isAvailable" to user.isAvailable,
                "photoUrl" to (user.photoUrl ?: ""),
                "updatedAt" to Date()
            )
            
            usersCollection.document(currentUser.uid).update(userData.toFirestoreMap()).await()
            Result.success(true)
        } catch (e: Exception) {
            Log.e(TAG, "Error updating user profile", e)
            Result.failure(e)
        }
    }

    /**
     * Update user profile with a User object (non-suspend version with callback)
     */
    fun updateUserProfile(user: User, callback: (Boolean, String) -> Unit) {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            callback(false, "No authenticated user")
            return
        }
        
        // Convert User object to a Map
        val userData = mapOf(
            "name" to user.name,
            "email" to user.email,
            "phone" to user.phone,
            "address" to user.address,
            "vehicleType" to user.vehicleType,
            "vehicleMake" to user.vehicleMake,
            "vehicleModel" to user.vehicleModel,
            "vehiclePlate" to user.vehiclePlate,
            "isAvailable" to user.isAvailable,
            "photoUrl" to (user.photoUrl ?: ""),
            "updatedAt" to Date()
        )
        
        usersCollection.document(currentUser.uid).update(userData.toFirestoreMap())
            .addOnSuccessListener {
                callback(true, "Profile updated successfully")
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error updating user profile", exception)
                callback(false, "Failed to update profile: ${exception.message}")
            }
    }

    // Order operations
    suspend fun getPendingOrders(): Result<List<Order>> {
        return try {
            val currentUser = auth.currentUser ?: return Result.failure(Exception("No authenticated user"))
            
            // Get rider's profile to check if they're online and available
            val riderDoc = db.collection("riders")
                .document(currentUser.uid)
                .get()
                .await()
            
            if (!riderDoc.exists()) {
                return Result.failure(Exception("Rider profile not found"))
            }
            
            val isOnline = riderDoc.getBoolean("isOnline") ?: false
            if (!isOnline) {
                return Result.failure(Exception("Rider is offline"))
            }
            
            // Get pending orders that are not assigned to any rider
            val querySnapshot = ordersCollection
                .whereEqualTo("status", "PENDING")
                .whereEqualTo("assignedRider", null)
                .get()
                .await()
            
            val orders = querySnapshot.documents.mapNotNull { doc ->
                documentToOrder(doc)
            }
            
            Result.success(orders)
        } catch (e: Exception) {
            Log.e(TAG, "Error getting pending orders", e)
            Result.failure(e)
        }
    }

    suspend fun getAssignedOrders(): Result<List<Order>> {
        return try {
            val currentUser = auth.currentUser ?: return Result.failure(Exception("No authenticated user"))
            val querySnapshot = ordersCollection
                .whereEqualTo("assignedRider", currentUser.uid)
                .whereIn("status", listOf("ASSIGNED", "ACCEPTED", "PICKED_UP", "IN_TRANSIT"))
                .get()
                .await()
            
            val orders = querySnapshot.documents.mapNotNull { doc ->
                documentToOrder(doc)
            }
            
            Result.success(orders)
        } catch (e: Exception) {
            Log.e(TAG, "Error getting assigned orders", e)
            Result.failure(e)
        }
    }

    suspend fun getCompletedOrders(limit: Long = 10, lastVisible: DocumentSnapshot? = null): Result<Pair<List<Order>, DocumentSnapshot?>> {
        return try {
            val currentUser = auth.currentUser ?: return Result.failure(Exception("No authenticated user"))
            
            var query = ordersCollection
                .whereEqualTo("assignedRider", currentUser.uid)
                .whereEqualTo("status", "COMPLETED")
                .orderBy("completedAt", Query.Direction.DESCENDING)
                .limit(limit)
            
            if (lastVisible != null) {
                query = query.startAfter(lastVisible)
            }
            
            val querySnapshot = query.get().await()
            
            val orders = querySnapshot.documents.mapNotNull { doc ->
                documentToOrder(doc)
            }
            
            val lastDoc = if (querySnapshot.documents.isNotEmpty()) {
                querySnapshot.documents.last()
            } else {
                null
            }
            
            Result.success(Pair(orders, lastDoc))
        } catch (e: Exception) {
            Log.e(TAG, "Error getting completed orders", e)
            Result.failure(e)
        }
    }

    suspend fun getFailedOrders(limit: Long = 10, lastVisible: DocumentSnapshot? = null): Result<Pair<List<Order>, DocumentSnapshot?>> {
        return try {
            val currentUser = auth.currentUser ?: return Result.failure(Exception("No authenticated user"))
            
            var query = ordersCollection
                .whereEqualTo("assignedRider", currentUser.uid)
                .whereEqualTo("status", "FAILED")
                .orderBy("updatedAt", Query.Direction.DESCENDING)
                .limit(limit)
            
            if (lastVisible != null) {
                query = query.startAfter(lastVisible)
            }
            
            val querySnapshot = query.get().await()
            
            val orders = querySnapshot.documents.mapNotNull { doc ->
                documentToOrder(doc)
            }
            
            val lastDoc = if (querySnapshot.documents.isNotEmpty()) {
                querySnapshot.documents.last()
            } else {
                null
            }
            
            Result.success(Pair(orders, lastDoc))
        } catch (e: Exception) {
            Log.e(TAG, "Error getting failed orders", e)
            Result.failure(e)
        }
    }

    suspend fun getOrderById(orderId: String): Result<Order?> {
        return try {
            val docSnapshot = ordersCollection.document(orderId).get().await()
            
            if (docSnapshot.exists()) {
                val order = documentToOrder(docSnapshot)
                Result.success(order)
            } else {
                Result.success(null)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting order by ID", e)
            Result.failure(e)
        }
    }

    suspend fun acceptOrder(orderId: String): Result<Boolean> {
        return try {
            val currentUser = auth.currentUser ?: return Result.failure(Exception("No authenticated user"))
            
            // First, check if the order is still available
            val orderSnapshot = ordersCollection.document(orderId).get().await()
            if (!orderSnapshot.exists()) {
                return Result.failure(Exception("Order not found"))
            }
            
            val currentStatus = orderSnapshot.getString("status")
            val currentRider = orderSnapshot.getString("assignedRider")
            
            if (currentStatus != "PENDING" || currentRider != null) {
                return Result.failure(Exception("Order is no longer available"))
            }
            
            // Update the order
            ordersCollection.document(orderId).update(
                "status", "ACCEPTED",
                "assignedRider", currentUser.uid,
                "acceptedAt", Date(),
                "updatedAt", Date()
            ).await()
            
            Result.success(true)
        } catch (e: Exception) {
            Log.e(TAG, "Error accepting order", e)
            Result.failure(e)
        }
    }

    suspend fun pickupOrder(orderId: String): Result<Boolean> {
        return try {
            val currentUser = auth.currentUser ?: return Result.failure(Exception("No authenticated user"))
            
            // First, get the order to verify ownership
            val orderSnapshot = ordersCollection.document(orderId).get().await()
            if (!orderSnapshot.exists()) {
                return Result.failure(Exception("Order not found"))
            }
            
            val assignedRider = orderSnapshot.getString("assignedRider")
            if (assignedRider != currentUser.uid) {
                return Result.failure(Exception("You are not assigned to this order"))
            }
            
            // Update the order
            ordersCollection.document(orderId).update(
                "status", "PICKED_UP",
                "pickedUpAt", Date(),
                "updatedAt", Date()
            ).await()
            
            Result.success(true)
        } catch (e: Exception) {
            Log.e(TAG, "Error marking order as picked up", e)
            Result.failure(e)
        }
    }

    suspend fun deliverOrder(orderId: String, deliveryNotes: String? = null): Result<Boolean> {
        return try {
            val currentUser = auth.currentUser ?: return Result.failure(Exception("No authenticated user"))
            
            // First, get the order to verify ownership
            val orderSnapshot = ordersCollection.document(orderId).get().await()
            if (!orderSnapshot.exists()) {
                return Result.failure(Exception("Order not found"))
            }
            
            val assignedRider = orderSnapshot.getString("assignedRider")
            if (assignedRider != currentUser.uid) {
                return Result.failure(Exception("You are not assigned to this order"))
            }
            
            // Update the order
            val updateMap = hashMapOf<String, Any>(
                "status" to "COMPLETED",
                "deliveredAt" to Date(),
                "updatedAt" to Date(),
                "completedAt" to Date()
            )
            
            if (!deliveryNotes.isNullOrBlank()) {
                updateMap["deliveryNotes"] = deliveryNotes
            }
            
            ordersCollection.document(orderId).update(updateMap).await()
            Result.success(true)
        } catch (e: Exception) {
            Log.e(TAG, "Error marking order as delivered", e)
            Result.failure(e)
        }
    }

    suspend fun failOrder(orderId: String, reason: String): Result<Boolean> {
        return try {
            val currentUser = auth.currentUser ?: return Result.failure(Exception("No authenticated user"))
            
            // First, get the order to verify ownership
            val orderSnapshot = ordersCollection.document(orderId).get().await()
            if (!orderSnapshot.exists()) {
                return Result.failure(Exception("Order not found"))
            }
            
            val assignedRider = orderSnapshot.getString("assignedRider")
            if (assignedRider != currentUser.uid) {
                return Result.failure(Exception("You are not assigned to this order"))
            }
            
            // Update the order
            ordersCollection.document(orderId).update(
                "status", "FAILED",
                "failReason", reason,
                "failedAt", Date(),
                "updatedAt", Date()
            ).await()
            
            Result.success(true)
        } catch (e: Exception) {
            Log.e(TAG, "Error marking order as failed", e)
            Result.failure(e)
        }
    }

    suspend fun rejectOrder(orderId: String, reason: String): Result<Boolean> {
        return try {
            val currentUser = auth.currentUser ?: return Result.failure(Exception("No authenticated user"))
            
            // First, get the order to check if it's still available
            val orderSnapshot = ordersCollection.document(orderId).get().await()
            if (!orderSnapshot.exists()) {
                return Result.failure(Exception("Order not found"))
            }
            
            // Add to rejected riders array
            ordersCollection.document(orderId).update(
                "rejectedRiders", com.google.firebase.firestore.FieldValue.arrayUnion(currentUser.uid),
                "rejectionReasons.${currentUser.uid}", reason
            ).await()
            
            Result.success(true)
        } catch (e: Exception) {
            Log.e(TAG, "Error rejecting order", e)
            Result.failure(e)
        }
    }

    suspend fun updateRiderLocation(locationData: Map<String, Any>): Result<Boolean> {
        return try {
            val currentUser = auth.currentUser ?: return Result.failure(Exception("No authenticated user"))
            
            // First check if the document exists
            val riderDoc = ridersCollection.document(currentUser.uid).get().await()
            
            if (riderDoc.exists()) {
                // Update existing document
                riderDoc.reference.update(
                    "location.latitude", locationData["latitude"],
                    "location.longitude", locationData["longitude"],
                    "location.timestamp", locationData["timestamp"],
                    "lastLocationUpdate", Date()
                ).await()
            } else {
                // Create a new rider document with location data
                val riderData = hashMapOf(
                    "userId" to currentUser.uid,
                    "email" to (currentUser.email ?: ""),
                    "name" to (currentUser.displayName ?: "Rider"),
                    "phone" to (currentUser.phoneNumber ?: ""),
                    "status" to "pending",
                    "isOnline" to true,
                    "location" to locationData,
                    "createdAt" to Date(),
                    "lastLocationUpdate" to Date(),
                    "totalEarnings" to 0.0
                )
                ridersCollection.document(currentUser.uid).set(riderData).await()
                Log.d(TAG, "Created new rider document with location for user: ${currentUser.uid}")
            }
            
            // Also update location in rider_locations collection for tracking history
            db.collection("rider_locations")
                .add(
                    hashMapOf(
                        "riderId" to currentUser.uid,
                        "latitude" to locationData["latitude"],
                        "longitude" to locationData["longitude"],
                        "accuracy" to locationData["accuracy"],
                        "speed" to locationData["speed"],
                        "bearing" to locationData["bearing"],
                        "timestamp" to locationData["timestamp"],
                        "createdAt" to Date()
                    )
                )
                .await()
            
            Log.d(TAG, "Rider location updated successfully")
            Result.success(true)
        } catch (e: Exception) {
            Log.e(TAG, "Error updating rider location", e)
            Result.failure(e)
        }
    }

    // Update rider status
    suspend fun updateRiderOnlineStatus(isOnline: Boolean): Result<Boolean> {
        return try {
            val currentUser = auth.currentUser ?: return Result.failure(Exception("No authenticated user"))
            
            // First check if the document exists
            val riderDoc = ridersCollection.document(currentUser.uid).get().await()
            
            if (riderDoc.exists()) {
                // Update existing document
                riderDoc.reference.update(
                    "isOnline", isOnline,
                    "lastStatusUpdate", Date()
                ).await()
                Result.success(true)
            } else {
                // Create a new rider document
                val riderData = hashMapOf<String, Any>(
                    "userId" to currentUser.uid,
                    "email" to (currentUser.email ?: ""),
                    "name" to (currentUser.displayName ?: "Rider"),
                    "phone" to (currentUser.phoneNumber ?: ""),
                    "status" to "pending",
                    "isOnline" to isOnline,
                    "createdAt" to Date(),
                    "lastStatusUpdate" to Date()
                )
                
                ridersCollection.document(currentUser.uid).set(riderData).await()
                Result.success(true)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error updating rider online status", e)
            Result.failure(e)
        }
    }

    /**
     * Get active batch for the current rider
     */
    suspend fun getActiveBatch(): Result<batch.BatchDetails?> {
        return try {
            val currentUser = auth.currentUser ?: return Result.failure(Exception("No authenticated user"))
            
            val querySnapshot = batchesCollection
                .whereEqualTo("riderId", currentUser.uid)
                .whereEqualTo("isActive", true)
                .limit(1)
                .get()
                .await()
            
            if (querySnapshot.isEmpty) {
                Result.success(null)
            } else {
                val batchDoc = querySnapshot.documents.first()
                val batchId = batchDoc.id
                val estimatedEarnings = batchDoc.getDouble("estimatedEarnings") ?: 0.0
                val estimatedTimeMinutes = batchDoc.getLong("estimatedTimeMinutes")?.toInt() ?: 0
                
                val batchDetails = batch.BatchDetails(
                    batchId = batchId,
                    estimatedEarnings = estimatedEarnings,
                    estimatedTimeMinutes = estimatedTimeMinutes
                )
                
                Result.success(batchDetails)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting active batch", e)
            Result.failure(e)
        }
    }
    
    /**
     * Create a new batch
     */
    suspend fun createBatch(): Result<String?> {
        return try {
            val currentUser = auth.currentUser ?: return Result.failure(Exception("No authenticated user"))
            
            // Check if there's already an active batch
            val existingBatchResult = getActiveBatch()
            if (existingBatchResult.isSuccess && existingBatchResult.getOrNull() != null) {
                return Result.success(existingBatchResult.getOrNull()?.batchId)
            }
            
            // Create new batch document
            val batchData = hashMapOf(
                "riderId" to currentUser.uid,
                "isActive" to true,
                "estimatedEarnings" to 0.0,
                "estimatedTimeMinutes" to 0,
                "createdAt" to Date(),
                "updatedAt" to Date()
            )
            
            val newBatchRef = batchesCollection.add(batchData).await()
            Result.success(newBatchRef.id)
        } catch (e: Exception) {
            Log.e(TAG, "Error creating batch", e)
            Result.failure(e)
        }
    }
    
    /**
     * Add an order to a batch
     */
    suspend fun addOrderToBatch(batchId: String, orderId: String): Result<Boolean> {
        return try {
            val currentUser = auth.currentUser ?: return Result.failure(Exception("No authenticated user"))
            
            // First get the batch to verify ownership
            val batchDoc = batchesCollection.document(batchId).get().await()
            if (!batchDoc.exists()) {
                return Result.failure(Exception("Batch not found"))
            }
            
            val riderId = batchDoc.getString("riderId")
            if (riderId != currentUser.uid) {
                return Result.failure(Exception("You don't own this batch"))
            }
            
            // Get the order to verify it can be added
            val orderDoc = ordersCollection.document(orderId).get().await()
            if (!orderDoc.exists()) {
                return Result.failure(Exception("Order not found"))
            }
            
            // Add order to batch
            val batch = db.batch()
            
            // Update order with batch ID
            batch.update(ordersCollection.document(orderId), 
                "batchId", batchId,
                "updatedAt", Date()
            )
            
            // Update batch orders list
            val batchOrdersCollection = batchesCollection.document(batchId).collection("orders")
            val orderData = hashMapOf(
                "orderId" to orderId,
                "addedAt" to Date(),
                "sequence" to 0  // Will be updated during optimization
            )
            batch.set(batchOrdersCollection.document(orderId), orderData)
            
            // Commit the batch operation
            batch.commit().await()
            
            // Update batch estimated data (would typically be done by a cloud function)
            updateBatchEstimates(batchId)
            
            Result.success(true)
        } catch (e: Exception) {
            Log.e(TAG, "Error adding order to batch", e)
            Result.failure(e)
        }
    }
    
    /**
     * Update batch estimated earnings and time
     */
    private suspend fun updateBatchEstimates(batchId: String): Result<Boolean> {
        return try {
            // Get all orders in the batch
            val batchOrdersSnap = batchesCollection.document(batchId)
                .collection("orders")
                .get()
                .await()
            
            if (batchOrdersSnap.isEmpty) {
                return Result.success(true)  // No orders to calculate
            }
            
            val orderIds = batchOrdersSnap.documents.map { it.getString("orderId") ?: "" }
                .filter { it.isNotEmpty() }
            
            if (orderIds.isEmpty()) {
                return Result.success(true)
            }
            
            // Get order details for calculation
            val orderDocs = orderIds.map { orderId ->
                ordersCollection.document(orderId).get().await()
            }
            
            var totalEarnings = 0.0
            var totalMinutes = 0
            
            orderDocs.forEach { doc ->
                if (doc.exists()) {
                    val deliveryFee = doc.getDouble("deliveryFee") ?: 0.0
                    val estimatedTime = doc.getLong("estimatedDeliveryTime")?.toInt() ?: 0
                    
                    totalEarnings += deliveryFee
                    totalMinutes += estimatedTime
                }
            }
            
            // Update batch with calculated values
            batchesCollection.document(batchId).update(
                "estimatedEarnings", totalEarnings,
                "estimatedTimeMinutes", totalMinutes,
                "updatedAt", Date()
            ).await()
            
            Result.success(true)
        } catch (e: Exception) {
            Log.e(TAG, "Error updating batch estimates", e)
            Result.failure(e)
        }
    }
    
    /**
     * Optimize the route for a batch of orders
     */
    suspend fun optimizeBatchRoute(batchId: String): Result<List<Order>> {
        return try {
            val currentUser = auth.currentUser ?: return Result.failure(Exception("No authenticated user"))
            
            // 1. Get all orders in the batch
            val batchOrdersSnap = batchesCollection.document(batchId)
                .collection("orders")
                .get()
                .await()
            
            if (batchOrdersSnap.isEmpty) {
                return Result.success(emptyList())
            }
            
            val orderIds = batchOrdersSnap.documents.mapNotNull { it.getString("orderId") ?: "" }
                .filter { it.isNotEmpty() }
            
            if (orderIds.isEmpty()) {
                return Result.success(emptyList())
            }
            
            // 2. Get order details for optimization
            val orders = orderIds.mapNotNull { orderId ->
                val doc = ordersCollection.document(orderId).get().await()
                documentToOrder(doc)
            }
            
            if (orders.size <= 1) {
                return Result.success(orders)  // No need to optimize a single order
            }
            
            // 3. Get rider's current location
            val riderDoc = ridersCollection.document(currentUser.uid).get().await()
            val riderLat = riderDoc.getDouble("latitude") ?: 0.0
            val riderLng = riderDoc.getDouble("longitude") ?: 0.0
            
            // 4. Perform optimization using nearest neighbor algorithm
            // This is a more sophisticated version that considers both pickup and delivery
            val optimizedOrders = optimizeWithNearestNeighbor(orders, riderLat, riderLng)
            
            // 5. Update order sequence in batch
            val batch = db.batch()
            optimizedOrders.forEachIndexed { index, order ->
                val orderRef = batchesCollection.document(batchId)
                    .collection("orders")
                    .document(order.id)
                
                batch.update(orderRef, "sequence", index)
            }
            batch.commit().await()
            
            Result.success(optimizedOrders)
        } catch (e: Exception) {
            Log.e(TAG, "Error optimizing batch route", e)
            Result.failure(e)
        }
    }
    
    /**
     * Optimize route using nearest neighbor algorithm
     * This is a more sophisticated version that considers both pickup and delivery locations
     */
    private fun optimizeWithNearestNeighbor(orders: List<Order>, startLat: Double, startLng: Double): List<Order> {
        // If no orders or only one order, return as is
        if (orders.size <= 1) return orders
        
        // Create a mutable list to track unvisited orders
        val unvisitedOrders = orders.toMutableList()
        val optimizedRoute = mutableListOf<Order>()
        
        // Start with rider's current location
        var currentLat = startLat
        var currentLng = startLng
        
        // Process orders until all are visited
        while (unvisitedOrders.isNotEmpty()) {
            // Find the nearest unvisited order
            var nearestOrderIndex = -1
            var shortestDistance = Double.MAX_VALUE
            
            unvisitedOrders.forEachIndexed { index, order ->
                // Calculate distance to restaurant (pickup location)
                val restaurantLat = order.restaurantLatitude ?: 0.0
                val restaurantLng = order.restaurantLongitude ?: 0.0
                
                // For orders that are already picked up, use customer location instead
                val targetLat = if (order.status == OrderStatus.PICKED_UP) {
                    order.customerLatitude ?: restaurantLat
                } else {
                    restaurantLat
                }
                
                val targetLng = if (order.status == OrderStatus.PICKED_UP) {
                    order.customerLongitude ?: restaurantLng
                } else {
                    restaurantLng
                }
                
                val distance = calculateHaversineDistance(currentLat, currentLng, targetLat, targetLng)
                
                if (distance < shortestDistance) {
                    shortestDistance = distance
                    nearestOrderIndex = index
                }
            }
            
            // Add the nearest order to the optimized route
            if (nearestOrderIndex >= 0) {
                val nearestOrder = unvisitedOrders.removeAt(nearestOrderIndex)
                optimizedRoute.add(nearestOrder)
                
                // Update current location to the location we just visited
                if (nearestOrder.status == OrderStatus.PICKED_UP) {
                    // If order is picked up, update to customer location
                    currentLat = nearestOrder.customerLatitude ?: currentLat
                    currentLng = nearestOrder.customerLongitude ?: currentLng
                } else {
                    // Otherwise update to restaurant location
                    currentLat = nearestOrder.restaurantLatitude ?: currentLat
                    currentLng = nearestOrder.restaurantLongitude ?: currentLng
                }
            }
        }
        
        // Apply some additional optimization:
        // Group orders by restaurant to minimize back-and-forth
        val groupedByRestaurant = optimizedRoute.groupBy { 
            "${it.restaurantLatitude ?: 0.0},${it.restaurantLongitude ?: 0.0}" 
        }
        
        // If we have multiple orders from the same restaurant, ensure they're sequential
        if (groupedByRestaurant.any { it.value.size > 1 }) {
            val finalRoute = mutableListOf<Order>()
            val processedRestaurants = mutableSetOf<String>()
            
            for (order in optimizedRoute) {
                val restaurantKey = "${order.restaurantLatitude ?: 0.0},${order.restaurantLongitude ?: 0.0}"
                
                // If we haven't processed this restaurant yet and it has multiple orders
                if (!processedRestaurants.contains(restaurantKey) && 
                    groupedByRestaurant[restaurantKey]?.size ?: 0 > 1) {
                    
                    // Add all orders from this restaurant
                    finalRoute.addAll(groupedByRestaurant[restaurantKey] ?: emptyList())
                    processedRestaurants.add(restaurantKey)
                } else if (!processedRestaurants.contains(restaurantKey)) {
                    // Add just this order if it's the only one from this restaurant
                    finalRoute.add(order)
                    processedRestaurants.add(restaurantKey)
                }
                // Skip if we've already processed this restaurant
            }
            
            return finalRoute
        }
        
        return optimizedRoute
    }
    
    /**
     * Calculate distance between two points using Haversine formula
     * This gives a more accurate distance calculation over Earth's surface
     */
    private fun calculateHaversineDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val R = 6371.0 // Earth's radius in km
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLon / 2) * Math.sin(dLon / 2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        return R * c
    }
    
    /**
     * Cancel an order
     */
    suspend fun cancelOrder(orderId: String, reason: String): Result<Boolean> {
        return try {
            val currentUser = auth.currentUser ?: return Result.failure(Exception("No authenticated user"))
            
            // First, get the order to verify ownership
            val orderSnapshot = ordersCollection.document(orderId).get().await()
            if (!orderSnapshot.exists()) {
                return Result.failure(Exception("Order not found"))
            }
            
            val assignedRider = orderSnapshot.getString("assignedRider")
            if (assignedRider != currentUser.uid) {
                return Result.failure(Exception("You are not assigned to this order"))
            }
            
            // Update the order
            ordersCollection.document(orderId).update(
                "status", "CANCELLED",
                "cancelReason", reason,
                "canceledAt", Date(),
                "updatedAt", Date()
            ).await()
            
            Result.success(true)
        } catch (e: Exception) {
            Log.e(TAG, "Error cancelling order", e)
            Result.failure(e)
        }
    }

    /**
     * Get batch orders with their sequence numbers
     */
    suspend fun getBatchOrdersWithSequence(batchId: String): Result<List<Pair<Order, Int>>> {
        return try {
            val currentUser = auth.currentUser ?: return Result.failure(Exception("No authenticated user"))
            
            // 1. Get batch to verify ownership
            val batchDoc = batchesCollection.document(batchId).get().await()
            if (!batchDoc.exists()) {
                return Result.failure(Exception("Batch not found"))
            }
            
            val riderId = batchDoc.getString("riderId")
            if (riderId != currentUser.uid) {
                return Result.failure(Exception("You don't own this batch"))
            }
            
            // 2. Get all orders in the batch with their sequence numbers
            val batchOrdersSnap = batchesCollection.document(batchId)
                .collection("orders")
                .get()
                .await()
            
            if (batchOrdersSnap.isEmpty) {
                return Result.success(emptyList())
            }
            
            // 3. Create a map of orderId to sequence
            val orderSequences = batchOrdersSnap.documents.mapNotNull { doc ->
                val orderId = doc.getString("orderId") ?: return@mapNotNull null
                val sequence = doc.getLong("sequence")?.toInt() ?: 0
                orderId to sequence
            }.toMap()
            
            // 4. Get all the order details
            val orderIds = orderSequences.keys.toList()
            val orders = mutableListOf<Pair<Order, Int>>()
            
            for (orderId in orderIds) {
                val doc = ordersCollection.document(orderId).get().await()
                val order = documentToOrder(doc)
                
                // Skip null orders
                if (order != null) {
                    // Add order with its sequence
                    orders.add(Pair(order, orderSequences[orderId] ?: 0))
                }
            }
            
            // 5. Sort by sequence and return
            val sortedOrders = orders.sortedBy { it.second }
            Result.success(sortedOrders)
        } catch (e: Exception) {
            Log.e(TAG, "Error getting batch orders with sequence", e)
            Result.failure(e)
        }
    }
    
    /**
     * Get batch orders
     */
    suspend fun getBatchOrders(batchId: String): Result<List<Order>> {
        return try {
            val result = getBatchOrdersWithSequence(batchId)
            
            result.map { sequencedOrders ->
                // Extract just the orders, already sorted by sequence
                sequencedOrders.map { it.first }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting batch orders", e)
            Result.failure(e)
        }
    }

    /**
     * Complete a batch
     */
    suspend fun completeBatch(batchId: String): Result<Boolean> {
        return try {
            val currentUser = auth.currentUser ?: return Result.failure(Exception("No authenticated user"))
            
            // 1. Get batch to verify ownership
            val batchDoc = batchesCollection.document(batchId).get().await()
            if (!batchDoc.exists()) {
                return Result.failure(Exception("Batch not found"))
            }
            
            val riderId = batchDoc.getString("riderId")
            if (riderId != currentUser.uid) {
                return Result.failure(Exception("You don't own this batch"))
            }
            
            // 2. Update batch status
            batchesCollection.document(batchId).update(
                "isActive", false,
                "isCompleted", true,
                "completedAt", Date(),
                "updatedAt", Date()
            ).await()
            
            // 3. Update any remaining active orders to completed
            val batchOrdersSnap = batchesCollection.document(batchId)
                .collection("orders")
                .get()
                .await()
            
            val batch = db.batch()
            
            for (doc in batchOrdersSnap.documents) {
                val orderId = doc.getString("orderId") ?: continue
                val orderDoc = ordersCollection.document(orderId).get().await()
                
                // Skip already completed or failed orders
                val status = orderDoc.getString("status") ?: continue
                if (status in listOf("COMPLETED", "FAILED", "CANCELLED")) {
                    continue
                }
                
                // Mark as completed
                batch.update(ordersCollection.document(orderId),
                    "status", "COMPLETED",
                    "completedAt", Date(),
                    "updatedAt", Date()
                )
            }
            
            // Commit all updates
            batch.commit().await()
            
            Result.success(true)
        } catch (e: Exception) {
            Log.e(TAG, "Error completing batch", e)
            Result.failure(e)
        }
    }

    suspend fun updateFcmToken(token: String): Result<Boolean> {
        return try {
            val currentUser = auth.currentUser ?: return Result.failure(Exception("No authenticated user"))
            
            // Update token in both user and rider collections
            usersCollection.document(currentUser.uid)
                .update(
                    "fcmToken", token,
                    "tokenUpdatedAt", Date()
                )
                .await()
            
            ridersCollection.document(currentUser.uid)
                .update(
                    "fcmToken", token,
                    "tokenUpdatedAt", Date()
                )
                .await()
            
            Log.d(TAG, "FCM token updated successfully")
            Result.success(true)
        } catch (e: Exception) {
            Log.e(TAG, "Error updating FCM token", e)
            Result.failure(e)
        }
    }

    suspend fun refreshPendingOrders() {
        try {
            // This method is specifically for refreshing the pending orders cache when a notification is received
            val pendingOrdersResult = getPendingOrders()
            Log.d(TAG, "Refreshed pending orders after notification: ${pendingOrdersResult.isSuccess}")
        } catch (e: Exception) {
            Log.e(TAG, "Error refreshing pending orders", e)
        }
    }

    suspend fun bulkUpdateRiderLocations(locationDataList: List<SharedPreferencesManager.LocationData>): Result<Boolean> {
        return try {
            val currentUser = auth.currentUser ?: return Result.failure(Exception("No authenticated user"))
            
            // First check if rider document exists
            val riderDocRef = ridersCollection.document(currentUser.uid)
            val riderDoc = riderDocRef.get().await()
            
            // Use a batch write to add multiple location points at once
            val batch = db.batch()
            
            locationDataList.forEach { locationData ->
                val newLocationRef = db.collection("rider_locations").document()
                batch.set(
                    newLocationRef,
                    hashMapOf(
                        "riderId" to currentUser.uid,
                        "latitude" to locationData.latitude,
                        "longitude" to locationData.longitude,
                        "timestamp" to locationData.timestamp,
                        "createdAt" to Date(),
                        "syncedFromCache" to true
                    )
                )
            }
            
            // Also update current location with the most recent one
            if (locationDataList.isNotEmpty()) {
                val mostRecent = locationDataList.maxByOrNull { it.timestamp }
                if (mostRecent != null) {
                    if (riderDoc.exists()) {
                        // Update existing document
                        batch.update(
                            riderDocRef,
                            "location.latitude", mostRecent.latitude,
                            "location.longitude", mostRecent.longitude,
                            "location.timestamp", mostRecent.timestamp,
                            "lastLocationUpdate", Date()
                        )
                    } else {
                        // Create new rider document
                        val locationMap = hashMapOf(
                            "latitude" to mostRecent.latitude,
                            "longitude" to mostRecent.longitude,
                            "timestamp" to mostRecent.timestamp
                        )
                        
                        batch.set(
                            riderDocRef,
                            hashMapOf(
                                "userId" to currentUser.uid,
                                "email" to (currentUser.email ?: ""),
                                "name" to (currentUser.displayName ?: "Rider"),
                                "phone" to (currentUser.phoneNumber ?: ""),
                                "status" to "pending",
                                "isOnline" to true,
                                "location" to locationMap,
                                "createdAt" to Date(),
                                "lastLocationUpdate" to Date(),
                                "totalEarnings" to 0.0
                            )
                        )
                        Log.d(TAG, "Creating new rider document during bulk location update")
                    }
                }
            }
            
            // Execute the batch
            batch.commit().await()
            
            Log.d(TAG, "Bulk rider locations update successful")
            Result.success(true)
        } catch (e: Exception) {
            Log.e(TAG, "Error performing bulk location update", e)
            Result.failure(e)
        }
    }

    /**
     * Get delivery history for a rider
     */
    suspend fun getDeliveryHistory(riderId: String): Result<List<DeliveryHistoryItem>> {
        return try {
            val currentUser = auth.currentUser ?: return Result.failure(Exception("No authenticated user"))
            
            // Query orders collection for completed, cancelled, and failed orders
            val ordersQuery = ordersCollection
                .whereEqualTo("assignedRider", currentUser.uid)
                .whereIn("status", listOf("COMPLETED", "CANCELLED", "FAILED"))
                .orderBy("updatedAt", Query.Direction.DESCENDING)
                .limit(100)
            
            val ordersSnapshot = ordersQuery.get().await()
            
            // Convert to delivery history items
            val deliveryHistory = ordersSnapshot.documents.mapNotNull { doc ->
                try {
                    val orderId = doc.getString("orderId") ?: return@mapNotNull null
                    val restaurantName = doc.getString("restaurantName") ?: return@mapNotNull null
                    val customerName = doc.getString("customerName") ?: return@mapNotNull null
                    val totalAmount = doc.getDouble("totalAmount") ?: 0.0
                    val deliveryFee = doc.getDouble("deliveryFee") ?: 0.0
                    val status = doc.getString("status") ?: return@mapNotNull null
                    val date = doc.getDate("updatedAt") ?: Date()
                    
                    DeliveryHistoryItem(
                        id = doc.id,
                        orderId = orderId,
                        restaurantName = restaurantName,
                        customerName = customerName,
                        amount = totalAmount,
                        earning = deliveryFee, // Rider earns the delivery fee
                        status = OrderStatus.valueOf(status),
                        date = date
                    )
                } catch (e: Exception) {
                    Log.e(TAG, "Error parsing delivery history document", e)
                    null
                }
            }
            
            Result.success(deliveryHistory)
        } catch (e: Exception) {
            Log.e(TAG, "Error getting delivery history", e)
            Result.failure(e)
        }
    }

    /**
     * Get the rider's weekly earnings
     */
    suspend fun getWeeklyEarnings(): Result<Map<String, Double>> {
        return try {
            val currentUser = auth.currentUser ?: return Result.failure(Exception("No authenticated user"))
            
            // Get the start and end dates for the current week
            val calendar = Calendar.getInstance()
            val endDate = calendar.time
            
            // Move to the beginning of the week (Sunday or Monday depending on locale)
            calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            val startDate = calendar.time
            
            // Query earnings collection - simplify query to avoid index requirement
            val earningsQuery = earningsCollection
                .whereEqualTo("riderId", currentUser.uid)
            
            val earningsSnapshot = earningsQuery.get().await()
            
            // Group earnings by date
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val weeklyEarnings = mutableMapOf<String, Double>()
            
            earningsSnapshot.documents.forEach { doc ->
                val amount = doc.getDouble("amount") ?: 0.0
                val date = doc.getDate("date")
                
                if (date != null) {
                    // Only include earnings from the current week
                    if (date >= startDate && date <= endDate) {
                        val dateKey = dateFormat.format(date)
                        val currentAmount = weeklyEarnings.getOrDefault(dateKey, 0.0)
                        weeklyEarnings[dateKey] = currentAmount + amount
                    }
                }
            }
            
            Result.success(weeklyEarnings)
        } catch (e: Exception) {
            Log.e(TAG, "Error getting weekly earnings", e)
            Result.failure(e)
        }
    }
    
    /**
     * Get the rider's total earnings (all time)
     */
    suspend fun getTotalEarnings(): Result<Double> {
        return try {
            val currentUser = auth.currentUser ?: return Result.failure(Exception("No authenticated user"))
            
            // Get rider document
            val riderDoc = ridersCollection.document(currentUser.uid).get().await()
            
            if (riderDoc.exists()) {
                val totalEarnings = riderDoc.getDouble("totalEarnings") ?: 0.0
                Result.success(totalEarnings)
            } else {
                Result.success(0.0)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting total earnings", e)
            Result.failure(e)
        }
    }
    
    /**
     * Get the rider's earnings for today
     */
    suspend fun getTodayEarnings(): Result<Double> {
        return try {
            val currentUser = auth.currentUser ?: return Result.failure(Exception("No authenticated user"))
            
            // Get the start and end dates for today
            val calendar = Calendar.getInstance()
            val endDate = calendar.time
            
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            val startDate = calendar.time
            
            // Query earnings collection - simplify query to avoid index requirement
            val earningsQuery = earningsCollection
                .whereEqualTo("riderId", currentUser.uid)
            
            val earningsSnapshot = earningsQuery.get().await()
            
            // Sum up the earnings for today
            val todayEarnings = earningsSnapshot.documents.sumOf { doc ->
                val date = doc.getDate("date")
                if (date != null && date >= startDate && date <= endDate) {
                    doc.getDouble("amount") ?: 0.0
                } else {
                    0.0
                }
            }
            
            Result.success(todayEarnings)
        } catch (e: Exception) {
            Log.e(TAG, "Error getting today's earnings", e)
            Result.failure(e)
        }
    }
    
    /**
     * Get the rider's pending earnings
     */
    suspend fun getPendingEarnings(): Result<Double> {
        return try {
            val currentUser = auth.currentUser ?: return Result.failure(Exception("No authenticated user"))
            
            // Query orders that are assigned but not completed yet
            val ordersQuery = ordersCollection
                .whereEqualTo("assignedRider", currentUser.uid)
                .whereIn("status", listOf("ACCEPTED", "PICKED_UP"))
            
            val ordersSnapshot = ordersQuery.get().await()
            
            // Sum up the potential earnings (delivery fees)
            val pendingEarnings = ordersSnapshot.documents.sumOf { doc ->
                doc.getDouble("deliveryFee") ?: 0.0
            }
            
            Result.success(pendingEarnings)
        } catch (e: Exception) {
            Log.e(TAG, "Error getting pending earnings", e)
            Result.failure(e)
        }
    }
    
    /**
     * Get the rider's earnings history
     */
    suspend fun getEarningsHistory(limit: Int = 20): Result<List<Earning>> {
        return try {
            val currentUser = auth.currentUser ?: return Result.failure(Exception("No authenticated user"))
            
            // Query earnings collection - simplify query to avoid index requirement
            // Just filter by riderId without ordering
            val earningsQuery = earningsCollection
                .whereEqualTo("riderId", currentUser.uid)
                .limit(limit.toLong() * 2) // Fetch more to account for sorting and filtering
            
            val earningsSnapshot = earningsQuery.get().await()
            
            // Convert to earnings objects
            val earnings = earningsSnapshot.documents.mapNotNull { doc ->
                try {
                    val id = doc.id
                    val orderId = doc.getString("orderId") ?: return@mapNotNull null
                    val amount = doc.getDouble("amount") ?: 0.0
                    val date = doc.getDate("date") ?: Date()
                    val description = doc.getString("description") ?: "Delivery"
                    val orderReference = doc.getString("orderReference") ?: ""
                    
                    Earning(
                        id = id,
                        orderId = orderId,
                        amount = amount,
                        date = date,
                        description = description,
                        orderReference = orderReference
                    )
                } catch (e: Exception) {
                    Log.e(TAG, "Error parsing earning document", e)
                    null
                }
            }
            
            // Sort in memory by date (newest first)
            val sortedEarnings = earnings.sortedByDescending { it.date }
                .take(limit) // Limit to requested number
            
            Result.success(sortedEarnings)
        } catch (e: Exception) {
            Log.e(TAG, "Error getting earnings history", e)
            Result.failure(e)
        }
    }

    /**
     * Get available orders near rider's location
     */
    suspend fun getAvailableOrdersNearby(maxDistance: Double? = null): Result<List<Order>> {
        return try {
            val currentUser = auth.currentUser ?: return Result.failure(Exception("No authenticated user"))
            
            // Get rider's location
            val riderDoc = db.collection("riders")
                .document(currentUser.uid)
                .get()
                .await()
            
            if (!riderDoc.exists()) {
                return Result.failure(Exception("Rider profile not found"))
            }
            
            val riderLat = riderDoc.getDouble("latitude") ?: 0.0
            val riderLng = riderDoc.getDouble("longitude") ?: 0.0
            
            // Get pending orders
            val pendingOrders = getPendingOrders()
            
            if (pendingOrders.isFailure) {
                return pendingOrders
            }
            
            val orders = pendingOrders.getOrNull() ?: emptyList()
            
            // If maxDistance is provided, filter orders by distance
            val filteredOrders = if (maxDistance != null) {
                orders.filter { order ->
                    val restaurantLat = order.restaurantLatitude ?: 0.0
                    val restaurantLng = order.restaurantLongitude ?: 0.0
                    
                    // Calculate distance using Haversine formula
                    val distance = calculateHaversineDistance(
                        riderLat, riderLng, 
                        restaurantLat, restaurantLng
                    )
                    
                    distance <= maxDistance
                }
            } else {
                orders
            }
            
            Result.success(filteredOrders)
        } catch (e: Exception) {
            Log.e(TAG, "Error getting nearby orders", e)
            Result.failure(e)
        }
    }
    
    /**
     * Request assignment for an order
     */
    suspend fun requestOrderAssignment(orderId: String): Result<Boolean> {
        return try {
            val currentUser = auth.currentUser ?: return Result.failure(Exception("No authenticated user"))
            
            // First, check if the order is still available
            val orderDoc = ordersCollection.document(orderId).get().await()
            
            if (!orderDoc.exists()) {
                return Result.failure(Exception("Order not found"))
            }
            
            val status = orderDoc.getString("status")
            val assignedRider = orderDoc.getString("assignedRider")
            
            if (status != "PENDING" || assignedRider != null) {
                return Result.failure(Exception("Order is no longer available"))
            }
            
            // Create an assignment request
            val requestData = hashMapOf(
                "riderId" to currentUser.uid,
                "orderId" to orderId,
                "requestedAt" to Date(),
                "status" to "PENDING"
            )
            
            // Add to assignment requests collection
            db.collection("orderAssignmentRequests")
                .add(requestData)
                .await()
            
            Result.success(true)
        } catch (e: Exception) {
            Log.e(TAG, "Error requesting order assignment", e)
            Result.failure(e)
        }
    }

    /**
     * Send real-time location update to admin panel
     * This updates both the rider document and adds an entry to the rider_locations collection
     */
    suspend fun sendLocationUpdateToAdmin(latitude: Double, longitude: Double, speed: Float? = null, bearing: Float? = null): Result<Boolean> {
        return try {
            val currentUser = auth.currentUser ?: return Result.failure(Exception("No authenticated user"))
            
            // Create location data map
            val locationData = hashMapOf(
                "latitude" to latitude,
                "longitude" to longitude,
                "timestamp" to System.currentTimeMillis(),
                "updatedAt" to Date()
            )
            
            // Add optional fields if available
            if (speed != null) {
                locationData["speed"] = speed
            }
            
            if (bearing != null) {
                locationData["bearing"] = bearing
            }
            
            // Update the rider document with latest location
            ridersCollection.document(currentUser.uid)
                .update(
                    "location", locationData,
                    "lastLocationUpdate", Date()
                )
                .await()
            
            // Add to real-time tracking collection for admin panel
            val trackingData = hashMapOf(
                "riderId" to currentUser.uid,
                "latitude" to latitude,
                "longitude" to longitude,
                "timestamp" to System.currentTimeMillis(),
                "createdAt" to Date()
            )
            
            // Add optional fields if available
            if (speed != null) {
                trackingData["speed"] = speed
            }
            
            if (bearing != null) {
                trackingData["bearing"] = bearing
            }
            
            // Add to admin tracking collection
            db.collection("admin_rider_tracking")
                .add(trackingData)
                .await()
            
            // Also add to rider_locations for history
            db.collection("rider_locations")
                .add(trackingData)
                .await()
            
            Log.d(TAG, "Location update sent to admin panel")
            Result.success(true)
        } catch (e: Exception) {
            Log.e(TAG, "Error sending location update to admin", e)
            Result.failure(e)
        }
    }

    /**
     * Admin method: Assign order to a specific rider
     */
    suspend fun adminAssignOrderToRider(orderId: String, riderId: String, adminNotes: String? = null): Result<Boolean> {
        return try {
            val currentUser = auth.currentUser ?: return Result.failure(Exception("No authenticated user"))
            
            // Check if current user is an admin
            val adminDoc = db.collection("admins")
                .document(currentUser.uid)
                .get()
                .await()
            
            if (!adminDoc.exists()) {
                return Result.failure(Exception("User is not an admin"))
            }
            
            // Check if admin has permission to assign orders
            val canAssignOrders = adminDoc.getBoolean("canAssignOrders") ?: false
            if (!canAssignOrders) {
                return Result.failure(Exception("Admin does not have permission to assign orders"))
            }
            
            // Check if order exists and is not already assigned
            val orderDoc = ordersCollection.document(orderId).get().await()
            if (!orderDoc.exists()) {
                return Result.failure(Exception("Order not found"))
            }
            
            val orderStatus = orderDoc.getString("status")
            val currentAssignedRider = orderDoc.getString("assignedRider")
            
            if (orderStatus != "PENDING" || currentAssignedRider != null) {
                return Result.failure(Exception("Order is not available for assignment"))
            }
            
            // Check if rider exists and is approved
            val riderDoc = ridersCollection.document(riderId).get().await()
            if (!riderDoc.exists()) {
                return Result.failure(Exception("Rider not found"))
            }
            
            val riderStatus = riderDoc.getString("applicationStatus")
            if (riderStatus != "approved") {
                return Result.failure(Exception("Rider is not approved"))
            }
            
            // Update the order with assignment details
            if (!adminNotes.isNullOrBlank()) {
                ordersCollection.document(orderId).update(
                    "status", "ASSIGNED", // New status for admin-assigned orders
                    "assignedRider", riderId,
                    "assignedBy", currentUser.uid,
                    "assignmentMethod", "manual",
                    "assignmentTimestamp", Date(),
                    "updatedAt", Date(),
                    "adminNotes", adminNotes
                ).await()
            } else {
                ordersCollection.document(orderId).update(
                    "status", "ASSIGNED", // New status for admin-assigned orders
                    "assignedRider", riderId,
                    "assignedBy", currentUser.uid,
                    "assignmentMethod", "manual",
                    "assignmentTimestamp", Date(),
                    "updatedAt", Date()
                ).await()
            }
            
            // Log admin activity
            val logData = createAdminLogData(
                adminId = currentUser.uid,
                action = "order_assigned",
                targetId = orderId,
                details = mapOf(
                    "riderId" to riderId,
                    "orderStatus" to "ASSIGNED",
                    "assignmentMethod" to "manual"
                )
            )
            
            db.collection("admin_logs").add(logData.toFirestoreMap()).await()
            
            // Send notification to rider about the assignment
            notifyRiderAboutAssignment(riderId, orderId)
            
            Result.success(true)
        } catch (e: Exception) {
            Log.e(TAG, "Error in admin approving rider", e)
            Result.failure(e)
        }
    }
    
    /**
     * Admin method: Approve rider application
     */
    suspend fun adminApproveRider(riderId: String, notes: String? = null): Result<Boolean> {
        return try {
            val currentUser = auth.currentUser ?: return Result.failure(Exception("No authenticated user"))
            
            // Check if current user is an admin
            val adminDoc = db.collection("admins")
                .document(currentUser.uid)
                .get()
                .await()
            
            if (!adminDoc.exists()) {
                return Result.failure(Exception("User is not an admin"))
            }
            
            // Check if admin has permission to approve riders
            val canApproveRiders = adminDoc.getBoolean("canApproveRiders") ?: false
            if (!canApproveRiders) {
                return Result.failure(Exception("Admin does not have permission to approve riders"))
            }
            
            // Check if rider exists and is pending
            val riderDoc = ridersCollection.document(riderId).get().await()
            if (!riderDoc.exists()) {
                return Result.failure(Exception("Rider not found"))
            }
            
            val applicationStatus = riderDoc.getString("applicationStatus") ?: "pending"
            if (applicationStatus != "pending") {
                return Result.failure(Exception("Rider application is not pending"))
            }
            
            // Update rider approval status
            if (!notes.isNullOrBlank()) {
                ridersCollection.document(riderId).update(
                    "applicationStatus", "approved",
                    "approvedBy", currentUser.uid,
                    "approvalDate", Date(),
                    "updatedAt", Date(),
                    "approvalNotes", notes
                ).await()
            } else {
                ridersCollection.document(riderId).update(
                    "applicationStatus", "approved",
                    "approvedBy", currentUser.uid,
                    "approvalDate", Date(),
                    "updatedAt", Date()
                ).await()
            }
            
            // Log admin activity
            val logData = hashMapOf<String, Any>(
                "adminId" to currentUser.uid,
                "action" to "rider_approved",
                "targetId" to riderId,
                "details" to mapOf<String, Any>(
                    "approvalDate" to Date(),
                    "notes" to (notes ?: "")
                ),
                "timestamp" to Date()
            )
            
            db.collection("admin_logs").add(logData.toFirestoreMap()).await()
            
            Result.success(true)
        } catch (e: Exception) {
            Log.e(TAG, "Error in admin approving rider", e)
            Result.failure(e)
        }
    }
    
    /**
     * Admin method: Reject rider application
     */
    suspend fun adminRejectRider(riderId: String, reason: String): Result<Boolean> {
        return try {
            val currentUser = auth.currentUser ?: return Result.failure(Exception("No authenticated user"))
            
            // Check if current user is an admin
            val adminDoc = db.collection("admins")
                .document(currentUser.uid)
                .get()
                .await()
            
            if (!adminDoc.exists()) {
                return Result.failure(Exception("User is not an admin"))
            }
            
            // Check if admin has permission to approve riders
            val canApproveRiders = adminDoc.getBoolean("canApproveRiders") ?: false
            if (!canApproveRiders) {
                return Result.failure(Exception("Admin does not have permission to approve/reject riders"))
            }
            
            // Check if rider exists and is pending
            val riderDoc = ridersCollection.document(riderId).get().await()
            if (!riderDoc.exists()) {
                return Result.failure(Exception("Rider not found"))
            }
            
            val applicationStatus = riderDoc.getString("applicationStatus") ?: "pending"
            if (applicationStatus != "pending") {
                return Result.failure(Exception("Rider application is not pending"))
            }
            
            // Update rider rejection status
            ridersCollection.document(riderId).update(
                "applicationStatus", "rejected",
                "rejectedBy", currentUser.uid,
                "rejectionDate", Date(),
                "rejectionReason", reason,
                "updatedAt", Date()
            ).await()
            
            // Log admin activity
            val logData = hashMapOf<String, Any>(
                "adminId" to currentUser.uid,
                "action" to "rider_rejected",
                "targetId" to riderId,
                "details" to mapOf<String, Any>(
                    "rejectionDate" to Date(),
                    "rejectionReason" to reason
                ),
                "timestamp" to Date()
            )
            
            db.collection("admin_logs").add(logData.toFirestoreMap()).await()
            
            Result.success(true)
        } catch (e: Exception) {
            Log.e(TAG, "Error in admin rejecting rider", e)
            Result.failure(e)
        }
    }
    
    /**
     * Admin method: Unassign order from rider
     */
    suspend fun adminUnassignOrder(orderId: String, reason: String): Result<Boolean> {
        return try {
            val currentUser = auth.currentUser ?: return Result.failure(Exception("No authenticated user"))
            
            // Check if current user is an admin
            val adminDoc = db.collection("admins")
                .document(currentUser.uid)
                .get()
                .await()
            
            if (!adminDoc.exists()) {
                return Result.failure(Exception("User is not an admin"))
            }
            
            // Check if admin has permission to assign orders
            val canAssignOrders = adminDoc.getBoolean("canAssignOrders") ?: false
            if (!canAssignOrders) {
                return Result.failure(Exception("Admin does not have permission to assign orders"))
            }
            
            // Check if order exists and is assigned to a rider
            val orderDoc = ordersCollection.document(orderId).get().await()
            if (!orderDoc.exists()) {
                return Result.failure(Exception("Order not found"))
            }
            
            val orderStatus = orderDoc.getString("status")
            val currentAssignedRider = orderDoc.getString("assignedRider")
            
            if (orderStatus != "ASSIGNED" && orderStatus != "ACCEPTED" || currentAssignedRider == null) {
                return Result.failure(Exception("Order is not assigned to any rider"))
            }
            
            // Update the order by removing the rider assignment
            ordersCollection.document(orderId).update(
                "status", "PENDING",
                "assignedRider", null,
                "assignedBy", null,
                "assignmentMethod", null,
                "assignmentTimestamp", null,
                "unassignedBy", currentUser.uid,
                "unassignmentReason", reason,
                "unassignmentTimestamp", Date(),
                "updatedAt", Date()
            ).await()
            
            // Log admin activity
            val logData = hashMapOf<String, Any>(
                "adminId" to currentUser.uid,
                "action" to "order_unassigned",
                "targetId" to orderId,
                "details" to mapOf<String, Any>(
                    "previousRiderId" to currentAssignedRider,
                    "reason" to reason
                ),
                "timestamp" to Date()
            )
            
            db.collection("admin_logs").add(logData.toFirestoreMap()).await()
            
            Result.success(true)
        } catch (e: Exception) {
            Log.e(TAG, "Error in admin unassigning order", e)
            Result.failure(e)
        }
    }
    
    /**
     * Admin method: Get pending riders for approval
     */
    suspend fun adminGetPendingRiders(): Result<List<Map<String, Any>>> {
        return try {
            val currentUser = auth.currentUser ?: return Result.failure(Exception("No authenticated user"))
            
            // Check if current user is an admin
            val adminDoc = db.collection("admins")
                .document(currentUser.uid)
                .get()
                .await()
            
            if (!adminDoc.exists()) {
                return Result.failure(Exception("User is not an admin"))
            }
            
            // Get all riders with pending status
            val querySnapshot = ridersCollection
                .whereEqualTo("applicationStatus", "pending")
                .get()
                .await()
            
            val pendingRiders = querySnapshot.documents.map { doc ->
                val data = doc.data ?: mapOf<String, Any>()
                data.toMutableMap().apply { put("id", doc.id) }
                data
            }
            
            Result.success(pendingRiders)
        } catch (e: Exception) {
            Log.e(TAG, "Error getting pending riders", e)
            Result.failure(e)
        }
    }
    
    /**
     * Notifies a rider about an order assignment
     */
    private suspend fun notifyRiderAboutAssignment(riderId: String, orderId: String) {
        try {
            val riderDoc = ridersCollection.document(riderId).get().await()
            val fcmToken = riderDoc.getString("fcmToken")
            
            if (fcmToken != null) {
                // In a real app, this would send a FCM notification
                // For now, we'll just create a notification in the rider's notifications collection
                
                val notificationData = hashMapOf<String, Any>(
                    "riderId" to riderId,
                    "type" to "order_assigned",
                    "title" to "New Order Assigned",
                    "message" to "You have been assigned a new order by an admin.",
                    "orderId" to orderId,
                    "read" to false,
                    "createdAt" to Date()
                )
                
                db.collection("rider_notifications").add(notificationData.toFirestoreMap()).await()
            }
            
            // Also add notification to the database for the rider to see when they log in
            val orderDoc = ordersCollection.document(orderId).get().await()
            val restaurantName = orderDoc.getString("restaurantName") ?: "Restaurant"
            val orderNumber = orderDoc.getString("orderId") ?: "Unknown"
            
            val dbNotificationData = hashMapOf<String, Any>(
                "userId" to riderId,
                "title" to "New Order Assigned",
                "message" to "You have been assigned order #$orderNumber from $restaurantName by admin.",
                "type" to "order_assigned",
                "orderId" to orderId,
                "isRead" to false,
                "timestamp" to Date()
            )
            
            db.collection("notifications").add(dbNotificationData.toFirestoreMap()).await()
            
        } catch (e: Exception) {
            Log.e(TAG, "Error notifying rider about assignment", e)
            // Don't throw, just log the error
        }
    }
    
    /**
     * Rider method: Accept an admin-assigned order
     */
    suspend fun acceptAdminAssignedOrder(orderId: String): Result<Boolean> {
        return try {
            val currentUser = auth.currentUser ?: return Result.failure(Exception("No authenticated user"))
            
            // Get the order document
            val orderDoc = ordersCollection.document(orderId).get().await()
            if (!orderDoc.exists()) {
                return Result.failure(Exception("Order not found"))
            }
            
            // Verify the order is assigned to this rider
            val assignedRider = orderDoc.getString("assignedRider")
            if (assignedRider != currentUser.uid) {
                return Result.failure(Exception("Order is not assigned to you"))
            }
            
            // Verify the order is in ASSIGNED status
            val status = orderDoc.getString("status")
            if (status != "ASSIGNED") {
                return Result.failure(Exception("Order is not in ASSIGNED status"))
            }
            
            // Update the order status to ACCEPTED
            ordersCollection.document(orderId).update(
                "status", "ACCEPTED",
                "acceptedAt", Date(),
                "updatedAt", Date()
            ).await()
            
            Result.success(true)
        } catch (e: Exception) {
            Log.e(TAG, "Error accepting admin-assigned order", e)
            Result.failure(e)
        }
    }
    
    /**
     * Rider method: Reject an admin-assigned order
     */
    suspend fun rejectAdminAssignedOrder(orderId: String, reason: String): Result<Boolean> {
        return try {
            val currentUser = auth.currentUser ?: return Result.failure(Exception("No authenticated user"))
            
            // Get the order document
            val orderDoc = ordersCollection.document(orderId).get().await()
            if (!orderDoc.exists()) {
                return Result.failure(Exception("Order not found"))
            }
            
            // Verify the order is assigned to this rider
            val assignedRider = orderDoc.getString("assignedRider")
            if (assignedRider != currentUser.uid) {
                return Result.failure(Exception("Order is not assigned to you"))
            }
            
            // Verify the order is in ASSIGNED status
            val status = orderDoc.getString("status")
            if (status != "ASSIGNED") {
                return Result.failure(Exception("Order is not in ASSIGNED status"))
            }
            
            // Update the order to remove the rider assignment and add rejection details
            ordersCollection.document(orderId).update(
                "status", "PENDING",
                "assignedRider", null,
                "assignedBy", null,
                "assignmentMethod", null,
                "assignmentTimestamp", null,
                "rejectedBy", currentUser.uid,
                "rejectionReason", reason,
                "rejectionTimestamp", Date(),
                "updatedAt", Date()
            ).await()
            
            Result.success(true)
        } catch (e: Exception) {
            Log.e(TAG, "Error rejecting admin-assigned order", e)
            Result.failure(e)
        }
    }

    /**
     * Filter orders by distance and sort them
     */
    private fun filterAndSortOrdersByDistance(orders: List<Order>, riderLat: Double, riderLng: Double): List<Order> {
        return orders.map { order ->
            val restaurantLat = order.restaurantLatitude ?: 0.0
            val restaurantLng = order.restaurantLongitude ?: 0.0
            
            // Calculate distance using Haversine formula
            val distance = calculateHaversineDistance(
                riderLat, riderLng, 
                restaurantLat, restaurantLng
            )
            
            // Return order with its distance
            Pair(order, distance)
        }.sortedBy { (_, distance): Pair<Order, Double> ->
            // Sort by distance (ascending)
            distance
        }.map { (order, _): Pair<Order, Double> ->
            // Return just the order
            order
        }
    }
    
    /**
     * Compare two orders by distance
     */
    private fun compareOrdersByDistance(
        order1: Order, order2: Order,
        riderLat: Double, riderLng: Double
    ): Int {
        val distance1 = calculateHaversineDistance(
            riderLat, riderLng,
            order1.restaurantLatitude ?: 0.0,
            order1.restaurantLongitude ?: 0.0
        )
        
        val distance2 = calculateHaversineDistance(
            riderLat, riderLng,
            order2.restaurantLatitude ?: 0.0,
            order2.restaurantLongitude ?: 0.0
        )
        
        // Instead of using compareTo, use direct comparison
        return when {
            distance1 < distance2 -> -1
            distance1 > distance2 -> 1
            else -> 0
        }
    }

    // Add this helper method at the end of the class
    /**
     * Helper method to convert a HashMap to Map<String, Any> to avoid Java type mismatch errors
     */
    private fun createAdminLogData(
        adminId: String,
        action: String,
        targetId: String,
        details: Map<String, Any>
    ): Map<String, Any> {
        return hashMapOf(
            "adminId" to adminId,
            "action" to action,
            "targetId" to targetId,
            "details" to details,
            "timestamp" to Date()
        )
    }

    /**
     * Admin method to approve a rider document
     * Updates both the userDocuments collection and the rider's documents field
     */
    suspend fun approveRiderDocument(documentId: String, adminId: String, notes: String? = null): Result<Boolean> {
        return try {
            // First get the document details
            val documentSnapshot = db.collection("userDocuments").document(documentId).get().await()
            
            if (!documentSnapshot.exists()) {
                return Result.failure(Exception("Document not found"))
            }
            
            val userId = documentSnapshot.getString("userId") ?: ""
            val documentType = documentSnapshot.getString("documentType") ?: ""
            
            if (userId.isEmpty() || documentType.isEmpty()) {
                return Result.failure(Exception("Invalid document data"))
            }
            
            // Update the document status in userDocuments
            db.collection("userDocuments").document(documentId).update(
                mapOf(
                    "status" to "approved",
                    "reviewedBy" to adminId,
                    "reviewedAt" to Date(),
                    "reviewNotes" to (notes ?: "")
                )
            ).await()
            
            // Update the document status in the rider's profile
            val fieldName = getDocumentFieldName(documentType)
            
            db.collection("riders").document(userId).update(
                mapOf(
                    "documents.$fieldName.status" to "approved",
                    "documents.$fieldName.reviewedBy" to adminId,
                    "documents.$fieldName.reviewDate" to Date(),
                    "documents.$fieldName.reviewNotes" to (notes ?: "")
                )
            ).await()
            
            // Log the admin action
            val logData = hashMapOf(
                "adminId" to adminId,
                "action" to "document_approved",
                "targetId" to documentId,
                "details" to mapOf(
                    "userId" to userId,
                    "documentType" to documentType,
                    "notes" to (notes ?: "")
                ),
                "timestamp" to Date()
            )
            
            db.collection("admin_logs").add(logData).await()
            
            // Create notification for the rider
            val notificationData = hashMapOf(
                "riderId" to userId,
                "type" to "document_approved",
                "title" to "Document Approved",
                "message" to "Your $documentType has been approved.",
                "read" to false,
                "createdAt" to Date()
            )
            
            db.collection("rider_notifications").add(notificationData).await()
            
            // Check if all required documents are approved, and if so, update rider status
            checkAndUpdateRiderVerificationStatus(userId)
            
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Admin method to reject a rider document
     * Updates both the userDocuments collection and the rider's documents field
     */
    suspend fun rejectRiderDocument(documentId: String, adminId: String, rejectionReason: String): Result<Boolean> {
        return try {
            if (rejectionReason.isBlank()) {
                return Result.failure(Exception("Rejection reason cannot be empty"))
            }
            
            // First get the document details
            val documentSnapshot = db.collection("userDocuments").document(documentId).get().await()
            
            if (!documentSnapshot.exists()) {
                return Result.failure(Exception("Document not found"))
            }
            
            val userId = documentSnapshot.getString("userId") ?: ""
            val documentType = documentSnapshot.getString("documentType") ?: ""
            
            if (userId.isEmpty() || documentType.isEmpty()) {
                return Result.failure(Exception("Invalid document data"))
            }
            
            // Update the document status in userDocuments
            db.collection("userDocuments").document(documentId).update(
                mapOf(
                    "status" to "rejected",
                    "reviewedBy" to adminId,
                    "reviewedAt" to Date(),
                    "rejectionReason" to rejectionReason
                )
            ).await()
            
            // Update the document status in the rider's profile
            val fieldName = getDocumentFieldName(documentType)
            
            db.collection("riders").document(userId).update(
                mapOf(
                    "documents.$fieldName.status" to "rejected",
                    "documents.$fieldName.reviewedBy" to adminId,
                    "documents.$fieldName.reviewDate" to Date(),
                    "documents.$fieldName.rejectionReason" to rejectionReason
                )
            ).await()
            
            // Log the admin action
            val logData = hashMapOf(
                "adminId" to adminId,
                "action" to "document_rejected",
                "targetId" to documentId,
                "details" to mapOf(
                    "userId" to userId,
                    "documentType" to documentType,
                    "rejectionReason" to rejectionReason
                ),
                "timestamp" to Date()
            )
            
            db.collection("admin_logs").add(logData).await()
            
            // Create notification for the rider
            val notificationData = hashMapOf(
                "riderId" to userId,
                "type" to "document_rejected",
                "title" to "Document Rejected",
                "message" to "Your $documentType has been rejected: $rejectionReason",
                "read" to false,
                "createdAt" to Date()
            )
            
            db.collection("rider_notifications").add(notificationData).await()
            
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Admin method to approve a rider application
     */
    suspend fun approveRiderApplication(riderId: String, adminId: String, notes: String? = null): Result<Boolean> {
        return try {
            // First check if all required documents are approved
            val riderSnapshot = db.collection("riders").document(riderId).get().await()
            
            if (!riderSnapshot.exists()) {
                return Result.failure(Exception("Rider not found"))
            }
            
            val documents = riderSnapshot.get("documents") as? Map<*, *>
            
            if (documents == null) {
                return Result.failure(Exception("Rider has no documents uploaded"))
            }
            
            // Check required documents (adjust as needed for your requirements)
            val requiredDocuments = listOf("driverLicense", "governmentId", "vehicleRegistration")
            
            for (docType in requiredDocuments) {
                val doc = documents[docType] as? Map<*, *>
                if (doc == null || doc["status"] != "approved") {
                    return Result.failure(Exception("Not all required documents are approved"))
                }
            }
            
            // Update rider application status
            db.collection("riders").document(riderId).update(
                mapOf(
                    "applicationStatus" to "approved",
                    "approvedBy" to adminId,
                    "approvalDate" to Date(),
                    "approvalNotes" to (notes ?: "")
                )
            ).await()
            
            // Update user verification status
            db.collection("users").document(riderId).update(
                mapOf(
                    "isVerified" to true,
                    "verificationStatus" to "verified"
                )
            ).await()
            
            // Log the admin action
            val logData = hashMapOf(
                "adminId" to adminId,
                "action" to "rider_approved",
                "targetId" to riderId,
                "details" to mapOf(
                    "notes" to (notes ?: ""),
                    "previousStatus" to "pending",
                    "newStatus" to "approved"
                ),
                "timestamp" to Date()
            )
            
            db.collection("admin_logs").add(logData).await()
            
            // Create notification for the rider
            val notificationData = hashMapOf(
                "riderId" to riderId,
                "type" to "application_approved",
                "title" to "Application Approved",
                "message" to "Your rider application has been approved! You can now accept orders.",
                "read" to false,
                "createdAt" to Date()
            )
            
            db.collection("rider_notifications").add(notificationData).await()
            
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Admin method to reject a rider application
     */
    suspend fun rejectRiderApplication(riderId: String, adminId: String, rejectionReason: String): Result<Boolean> {
        return try {
            if (rejectionReason.isBlank()) {
                return Result.failure(Exception("Rejection reason cannot be empty"))
            }
            
            // Update rider application status
            db.collection("riders").document(riderId).update(
                mapOf(
                    "applicationStatus" to "rejected",
                    "rejectedBy" to adminId,
                    "rejectionDate" to Date(),
                    "rejectionReason" to rejectionReason
                )
            ).await()
            
            // Update user verification status
            db.collection("users").document(riderId).update(
                mapOf(
                    "isVerified" to false,
                    "verificationStatus" to "rejected"
                )
            ).await()
            
            // Log the admin action
            val logData = hashMapOf(
                "adminId" to adminId,
                "action" to "rider_rejected",
                "targetId" to riderId,
                "details" to mapOf(
                    "rejectionReason" to rejectionReason,
                    "previousStatus" to "pending",
                    "newStatus" to "rejected"
                ),
                "timestamp" to Date()
            )
            
            db.collection("admin_logs").add(logData).await()
            
            // Create notification for the rider
            val notificationData = hashMapOf(
                "riderId" to riderId,
                "type" to "application_rejected",
                "title" to "Application Rejected",
                "message" to "Your rider application has been rejected: $rejectionReason",
                "read" to false,
                "createdAt" to Date()
            )
            
            db.collection("rider_notifications").add(notificationData).await()
            
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Check if all required documents are approved and update rider verification status if needed
     */
    private suspend fun checkAndUpdateRiderVerificationStatus(riderId: String) {
        try {
            val riderSnapshot = db.collection("riders").document(riderId).get().await()
            
            if (!riderSnapshot.exists()) {
                return
            }
            
            val documents = riderSnapshot.get("documents") as? Map<*, *> ?: return
            
            // Check required documents (adjust as needed for your requirements)
            val requiredDocuments = listOf("driverLicense", "governmentId", "vehicleRegistration")
            var allApproved = true
            
            for (docType in requiredDocuments) {
                val doc = documents[docType] as? Map<*, *>
                if (doc == null || doc["status"] != "approved") {
                    allApproved = false
                    break
                }
            }
            
            // If application is still pending and all documents are approved, update status to "ready_for_review"
            if (allApproved && riderSnapshot.getString("applicationStatus") == "pending") {
                db.collection("riders").document(riderId).update(
                    mapOf("applicationStatus" to "ready_for_review")
                ).await()
                
                // Create notification for admins
                val riderName = riderSnapshot.getString("name") ?: "A rider"
                
                val adminNotificationData = hashMapOf(
                    "type" to "rider_ready_for_review",
                    "title" to "Rider Ready for Review",
                    "message" to "$riderName has all required documents approved and is ready for final review.",
                    "relatedId" to riderId,
                    "read" to false,
                    "createdAt" to Date()
                )
                
                // Get all admins with approval permission
                val admins = db.collection("admins")
                    .whereEqualTo("permissions.canApproveRiders", true)
                    .get()
                    .await()
                
                // Create a notification for each admin
                for (admin in admins.documents) {
                    val adminId = admin.id
                    db.collection("admin_notifications").add(
                        adminNotificationData + mapOf("adminId" to adminId)
                    ).await()
                }
            }
        } catch (e: Exception) {
            // Log error but don't throw - this is a background operation
            Log.e("FirestoreService", "Error checking rider verification status", e)
        }
    }

    /**
     * Get the field name for a document type in the rider's documents map
     */
    private fun getDocumentFieldName(documentType: String): String {
        return try {
            when (DocumentType.valueOf(documentType)) {
                DocumentType.DRIVER_LICENSE -> "driverLicense"
                DocumentType.GOVERNMENT_ID -> "governmentId"
                DocumentType.VEHICLE_REGISTRATION -> "vehicleRegistration"
                DocumentType.VEHICLE_INSURANCE -> "vehicleInsurance"
                DocumentType.WORK_AUTHORIZATION -> "workAuthorization"
                else -> documentType.lowercase().replace("_", "")
            }
        } catch (e: Exception) {
            // Fallback to lowercase if the enum parsing fails
            documentType.lowercase().replace("_", "")
        }
    }
} 