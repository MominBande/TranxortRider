package com.tranxortrider.deliveryrider.repositories

import android.util.Log
import com.tranxortrider.deliveryrider.models.Order
import com.tranxortrider.deliveryrider.models.OrderItem
import com.tranxortrider.deliveryrider.models.OrderStatus
import com.tranxortrider.deliveryrider.services.FirestoreService
import com.tranxortrider.deliveryrider.batch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID
import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.tranxortrider.deliveryrider.models.OrderResponse
import java.util.Date
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.auth.FirebaseAuth
import com.tranxortrider.deliveryrider.services.FirestoreListenerService
import kotlinx.coroutines.tasks.await

enum class SearchResultType {
    ALL, ORDER, RESTAURANT, CUSTOMER
}

/**
 * Repository class for all order-related operations with Firestore
 */
class OrderRepository {
    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()
    private val ordersCollection = db.collection("orders")
    
    private val firestoreService = FirestoreService()
    private val TAG = "OrderRepository"
    
    /**
     * Get pending orders
     */
    fun getPendingOrders(
        page: Int = 1, 
        limit: Int = 10,
        lastDocumentId: String? = null,
        callback: (success: Boolean, message: String, orders: List<Order>?, hasMore: Boolean, lastDocId: String?) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Get current rider ID
                val currentUser = FirebaseAuth.getInstance().currentUser
                if (currentUser == null) {
                    withContext(Dispatchers.Main) {
                        callback(false, "User not authenticated", null, false, null)
                    }
                    return@launch
                }
                
                // Create query for pending orders
                var query = ordersCollection
                    .whereEqualTo("status", "PENDING")
                    .orderBy("createdAt", com.google.firebase.firestore.Query.Direction.DESCENDING)
                    .limit(limit.toLong())
                
                // Apply pagination if we have a last document ID
                if (lastDocumentId != null) {
                    try {
                        val lastDoc = ordersCollection.document(lastDocumentId).get().await()
                        if (lastDoc.exists()) {
                            query = query.startAfter(lastDoc)
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "Error fetching last document for pagination", e)
                    }
                }
                
                // Execute query
                val querySnapshot = query.get().await()
                val orders = querySnapshot.documents.mapNotNull { doc ->
                    firestoreService.documentToOrder(doc)
                }
                
                // Determine if there are more results
                val hasMore = orders.size >= limit
                val lastDocId = if (orders.isNotEmpty()) orders.last().id else null
                
                withContext(Dispatchers.Main) {
                    callback(
                        true, 
                        "Pending orders retrieved successfully", 
                        orders, 
                        hasMore, 
                        lastDocId
                    )
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching pending orders", e)
                withContext(Dispatchers.Main) {
                    callback(false, "Network error: ${e.message}", null, false, null)
                }
            }
        }
    }
    
    /**
     * Get assigned orders for a rider
     */
    fun getAssignedOrders(callback: (success: Boolean, message: String, orders: List<Order>?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Get current rider ID
                val currentUser = FirebaseAuth.getInstance().currentUser
                if (currentUser == null) {
                    withContext(Dispatchers.Main) {
                        callback(false, "User not authenticated", null)
                    }
                    return@launch
                }
                
                val riderId = currentUser.uid
                Log.d(TAG, "Getting assigned orders for rider: $riderId")
                
                // Query orders assigned to this rider that are in progress
                // Include both ASSIGNED status and in-progress statuses (ACCEPTED, PICKED_UP, IN_TRANSIT)
                // Removing the orderBy clause to avoid requiring a composite index
                val querySnapshot = ordersCollection
                    .whereEqualTo("riderId", riderId)
                    .whereIn("status", listOf("ASSIGNED", "ACCEPTED", "PICKED_UP", "IN_TRANSIT"))
                    .get()
                    .await()
                
                Log.d(TAG, "Assigned orders query returned ${querySnapshot.documents.size} documents")
                
                // Convert documents to Order objects
                val orders = querySnapshot.documents.mapNotNull { doc ->
                    firestoreService.documentToOrder(doc)
                }
                
                // Sort the results in-memory instead of using Firestore's orderBy
                val sortedOrders = orders.sortedByDescending { 
                    it.acceptedAt ?: it.createdAt ?: Date() 
                }
                
                Log.d(TAG, "Successfully converted ${sortedOrders.size} orders: ${sortedOrders.map { "${it.id}: ${it.status}" }}")
                
                withContext(Dispatchers.Main) {
                    callback(true, "Assigned orders retrieved successfully", sortedOrders)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching assigned orders", e)
                withContext(Dispatchers.Main) {
                    callback(false, "Network error: ${e.message}", null)
                }
            }
        }
    }
    
    /**
     * Get completed orders for a rider with pagination
     */
    fun getCompletedOrders(
        riderId: String,
        page: Int,
        callback: (Boolean, String, List<Order>?) -> Unit
    ) {
        val pageSize = 10
        
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Get current rider ID if not provided
                val currentRiderId = if (riderId.isEmpty()) {
                    FirebaseAuth.getInstance().currentUser?.uid ?: ""
                } else {
                    riderId
                }
                
                if (currentRiderId.isEmpty()) {
                    withContext(Dispatchers.Main) {
                        callback(false, "User not authenticated", emptyList())
                    }
                    return@launch
                }
                
                // Query completed orders for this rider with pagination
                // Removing the orderBy clause to avoid requiring a composite index
                val querySnapshot = ordersCollection
                    .whereEqualTo("riderId", currentRiderId)
                    .whereEqualTo("status", "COMPLETED")
                    .limit(pageSize.toLong())
                    .get()
                    .await()
                
                // Convert documents to Order objects
                val orders = querySnapshot.documents.mapNotNull { doc ->
                    firestoreService.documentToOrder(doc)
                }
                
                // Sort the results in-memory instead of using Firestore's orderBy
                // Using deliveredAt instead of completedAt since that's what's in the Order class
                val sortedOrders = orders.sortedByDescending { 
                    it.deliveredAt ?: it.createdAt ?: Date() 
                }
                
                withContext(Dispatchers.Main) {
                    callback(true, "Orders fetched successfully", sortedOrders)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching completed orders", e)
                withContext(Dispatchers.Main) {
                    callback(false, "Error fetching orders: ${e.message}", null)
                }
            }
        }
    }
    
    /**
     * Get failed orders for a rider
     */
    fun getFailedOrders(
        page: Int = 1,
        limit: Int = 10,
        lastDocumentId: String? = null,
        callback: (success: Boolean, message: String, orders: List<Order>?, hasMore: Boolean, lastDocId: String?) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Create a document variable that can be null
                var lastDocSnapshot = null as DocumentSnapshot?
                
                // Only try to fetch the document if we have an ID
                lastDocumentId?.let {
                    val docResult = firestoreService.getOrderById(it)
                    // Store the document if found
                    docResult.getOrNull()?.let { order ->
                        // This would need to be handled differently as we need a DocumentSnapshot, not an Order
                        // For now, we'll just continue with a null lastDocSnapshot
                    }
                }
                
                // Use the parameter directly without reassignment
                val result = firestoreService.getFailedOrders(limit.toLong(), null)
                
                withContext(Dispatchers.Main) {
                    result.onSuccess { (orders, lastVisible) ->
                        callback(
                            true, 
                            "Failed orders retrieved successfully", 
                            orders, 
                            lastVisible != null, 
                            lastVisible?.id
                        )
                    }.onFailure { exception ->
                        callback(false, "Failed to fetch failed orders: ${exception.message}", null, false, null)
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching failed orders", e)
                withContext(Dispatchers.Main) {
                    callback(false, "Network error: ${e.message}", null, false, null)
                }
            }
        }
    }
    
    /**
     * Get order details
     */
    fun getOrderDetails(
        orderId: String,
        callback: (success: Boolean, message: String, order: Order?) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                Log.d(TAG, "Fetching order details for ID: $orderId")
                
                // Get the order document
                val document = db.collection("orders")
                    .document(orderId)
                    .get()
                    .await()
                    
                withContext(Dispatchers.Main) {
                    if (document != null && document.exists()) {
                        // Get the status string and convert to enum
                        val statusStr = document.getString("status") ?: "PENDING"
                        Log.d(TAG, "Raw status from Firestore: $statusStr")
                        
                        // Convert status string to enum
                        val orderStatus = try {
                            OrderStatus.valueOf(statusStr)
                        } catch (e: Exception) {
                            Log.e(TAG, "Error converting status string to enum: $statusStr", e)
                            OrderStatus.PENDING
                        }
                        
                        Log.d(TAG, "Converted status enum: $orderStatus")
                        
                        // Convert document to Order object
                        val order = Order(
                            id = document.id,
                            orderId = document.getString("orderId") ?: "",
                            restaurantName = document.getString("restaurantName") ?: "",
                            restaurantAddress = document.getString("restaurantAddress") ?: "",
                            customerName = document.getString("customerName") ?: "",
                            customerAddress = document.getString("customerAddress") ?: "",
                            customerPhone = document.getString("customerPhone") ?: "",
                            restaurantPhone = document.getString("restaurantPhone") ?: "",
                            items = document.get("items") as? List<Any> ?: listOf(),
                            totalAmount = document.getDouble("totalAmount") ?: 0.0,
                            deliveryFee = document.getDouble("deliveryFee") ?: 0.0,
                            status = orderStatus,
                            paymentMethod = document.getString("paymentMethod") ?: "",
                            specialInstructions = document.getString("specialInstructions"),
                            distance = document.getDouble("distance") ?: 0.0,
                            estimatedDeliveryTime = document.getLong("estimatedDeliveryTime")?.toInt() ?: 0,
                            createdAt = document.getTimestamp("createdAt")?.toDate() ?: Date(),
                            acceptedAt = document.getTimestamp("acceptedAt")?.toDate(),
                            pickedUpAt = document.getTimestamp("pickedUpAt")?.toDate(),
                            deliveredAt = document.getTimestamp("deliveredAt")?.toDate(),
                            canceledAt = document.getTimestamp("canceledAt")?.toDate(),
                            cancelReason = document.getString("cancelReason"),
                            customerLatitude = document.getDouble("customerLatitude"),
                            customerLongitude = document.getDouble("customerLongitude"),
                            restaurantLatitude = document.getDouble("restaurantLatitude"),
                            restaurantLongitude = document.getDouble("restaurantLongitude"),
                            batchId = document.getString("batchId")
                        )
                        
                        Log.d(TAG, "Order details retrieved: ${order.id}, status: ${order.status}")
                        callback(true, "Order details retrieved", order)
                    } else {
                        Log.e(TAG, "Order document not found or doesn't exist")
                        callback(false, "Order not found", null)
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error retrieving order", e)
                withContext(Dispatchers.Main) {
                    callback(false, "Network error: ${e.message}", null)
                }
            }
        }
    }
    
    /**
     * Accept an order
     */
    fun acceptOrder(
        orderId: String,
        callback: (success: Boolean, message: String) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = firestoreService.acceptOrder(orderId)
                
                withContext(Dispatchers.Main) {
                    result.onSuccess {
                        callback(true, "Order accepted successfully")
                    }.onFailure { exception ->
                        callback(false, "Failed to accept order: ${exception.message}")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error accepting order", e)
                withContext(Dispatchers.Main) {
                    callback(false, "Network error: ${e.message}")
                }
            }
        }
    }
    
    /**
     * Reject an order
     */
    fun rejectOrder(
        orderId: String,
        reason: String,
        callback: (success: Boolean, message: String) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = firestoreService.rejectOrder(orderId, reason)
                
                withContext(Dispatchers.Main) {
                    result.onSuccess {
                        callback(true, "Order rejected successfully")
                    }.onFailure { exception ->
                        callback(false, "Failed to reject order: ${exception.message}")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error rejecting order", e)
                withContext(Dispatchers.Main) {
                    callback(false, "Network error: ${e.message}")
                }
            }
        }
    }
    
    /**
     * Mark order as picked up
     */
    fun pickupOrder(
        orderId: String,
        callback: (success: Boolean, message: String) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = firestoreService.pickupOrder(orderId)
                
                withContext(Dispatchers.Main) {
                    result.onSuccess {
                        callback(true, "Order marked as picked up successfully")
                    }.onFailure { exception ->
                        callback(false, "Failed to mark order as picked up: ${exception.message}")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error marking order as picked up", e)
                withContext(Dispatchers.Main) {
                    callback(false, "Network error: ${e.message}")
                }
            }
        }
    }
    
    /**
     * Mark order as delivered
     */
    fun deliverOrder(
        orderId: String,
        deliveryNotes: String? = null,
        callback: (success: Boolean, message: String) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = firestoreService.deliverOrder(orderId, deliveryNotes)
                
                withContext(Dispatchers.Main) {
                    result.onSuccess {
                        callback(true, "Order marked as delivered successfully")
                    }.onFailure { exception ->
                        callback(false, "Failed to mark order as delivered: ${exception.message}")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error marking order as delivered", e)
                withContext(Dispatchers.Main) {
                    callback(false, "Network error: ${e.message}")
                }
            }
        }
    }
    
    /**
     * Mark order as failed
     */
    fun failOrder(
        orderId: String,
        reason: String,
        callback: (success: Boolean, message: String) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = firestoreService.failOrder(orderId, reason)
                
                withContext(Dispatchers.Main) {
                    result.onSuccess {
                        callback(true, "Order marked as failed successfully")
                    }.onFailure { exception ->
                        callback(false, "Failed to mark order as failed: ${exception.message}")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error marking order as failed", e)
                withContext(Dispatchers.Main) {
                    callback(false, "Network error: ${e.message}")
                }
            }
        }
    }
    
    /**
     * Cancel an order
     */
    fun cancelOrder(
        orderId: String,
        reason: String,
        callback: (success: Boolean, message: String) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = firestoreService.cancelOrder(orderId, reason)
                
                withContext(Dispatchers.Main) {
                    result.onSuccess {
                        callback(true, "Order cancelled successfully")
                    }.onFailure { exception ->
                        callback(false, "Failed to cancel order: ${exception.message}")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error cancelling order", e)
                withContext(Dispatchers.Main) {
                    callback(false, "Network error: ${e.message}")
                }
            }
        }
    }
    
    /**
     * Update rider location
     */
    fun updateLocation(
        latitude: Double,
        longitude: Double,
        callback: (success: Boolean, message: String) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val locationData = mapOf(
                    "latitude" to latitude,
                    "longitude" to longitude,
                    "timestamp" to System.currentTimeMillis()
                )
                val result = firestoreService.updateRiderLocation(locationData)
                
                withContext(Dispatchers.Main) {
                    result.onSuccess {
                        callback(true, "Location updated successfully")
                    }.onFailure { exception ->
                        callback(false, "Failed to update location: ${exception.message}")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error updating location", e)
                withContext(Dispatchers.Main) {
                    callback(false, "Network error: ${e.message}")
                }
            }
        }
    }
    
    /**
     * Update order status
     * Provides an automatic route optimization when changing from PENDING to ACCEPTED
     */
    fun updateOrderStatus(
        orderId: String,
        newStatus: OrderStatus,
        callback: (success: Boolean, message: String) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // First get the current order to check its status
                val orderResult = firestoreService.getOrderById(orderId)
                
                if (orderResult.isFailure) {
                    withContext(Dispatchers.Main) {
                        callback(false, "Failed to fetch order: ${orderResult.exceptionOrNull()?.message}")
                    }
                    return@launch
                }
                
                val currentOrder = orderResult.getOrNull()
                if (currentOrder == null) {
                    withContext(Dispatchers.Main) {
                        callback(false, "Order not found")
                    }
                    return@launch
                }
                
                // If changing from PENDING to ACCEPTED, handle batch creation and route optimization
                if (currentOrder.status == OrderStatus.PENDING && newStatus == OrderStatus.ACCEPTED) {
                    handleOrderAcceptance(currentOrder, callback)
                } else {
                    // For other status changes, just update normally
                    updateOrderStatusNormal(orderId, newStatus, callback)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error updating order status", e)
                withContext(Dispatchers.Main) {
                    callback(false, "Network error: ${e.message}")
                }
            }
        }
    }
    
    /**
     * Creates or updates a batch with optimized route when accepting an order
     */
    private suspend fun handleOrderAcceptance(
        newOrder: Order,
        callback: (success: Boolean, message: String) -> Unit
    ) {
        try {
            // 1. Get all currently processing orders (ACCEPTED status)
            val processingOrdersResult = firestoreService.getAssignedOrders()
            
            if (processingOrdersResult.isFailure) {
                withContext(Dispatchers.Main) {
                    // Just update status normally if we can't get processing orders
                    updateOrderStatusNormal(newOrder.id, OrderStatus.ACCEPTED, callback)
                }
                return
            }
            
            val processingOrders = processingOrdersResult.getOrNull() ?: emptyList()
            
            // Create a combined list with the new order
            val allOrders = processingOrders.toMutableList()
            allOrders.add(newOrder.copy(status = OrderStatus.ACCEPTED))
            
            if (allOrders.size <= 1) {
                // If this is the only order, just update normally
                updateOrderStatusNormal(newOrder.id, OrderStatus.ACCEPTED, callback)
                return
            }
            
            // 2. Create a new batch or use existing one
            var batchId: String? = null
            val activeBatchResult = firestoreService.getActiveBatch()
            
            if (activeBatchResult.isSuccess && activeBatchResult.getOrNull() != null) {
                // Use existing batch
                batchId = activeBatchResult.getOrNull()?.batchId
                Log.d(TAG, "Using existing batch: $batchId")
            } else {
                // Create new batch
                val batchResult = firestoreService.createBatch()
                if (batchResult.isSuccess && batchResult.getOrNull() != null) {
                    batchId = batchResult.getOrNull()
                    Log.d(TAG, "Created new batch: $batchId")
                }
            }
            
            if (batchId == null) {
                // If we couldn't create/get a batch, just update normally
                updateOrderStatusNormal(newOrder.id, OrderStatus.ACCEPTED, callback)
                return
            }
            
            // 3. Accept the order first to claim it
            val acceptResult = firestoreService.acceptOrder(newOrder.id)
            if (acceptResult.isFailure) {
                withContext(Dispatchers.Main) {
                    callback(false, "Failed to accept order: ${acceptResult.exceptionOrNull()?.message}")
                }
                return
            }
            
            // 4. Add the new order to the batch
            val addOrderResult = firestoreService.addOrderToBatch(batchId, newOrder.id)
            if (addOrderResult.isFailure) {
                Log.w(TAG, "Failed to add order to batch: ${addOrderResult.exceptionOrNull()?.message}")
                // Continue anyway since the order is accepted
            }
            
            // 5. Optimize the route for all orders in the batch
            val optimizedResult = firestoreService.optimizeBatchRoute(batchId)
            
            withContext(Dispatchers.Main) {
                if (optimizedResult.isSuccess) {
                    val orderCount = optimizedResult.getOrNull()?.size ?: 1
                    callback(true, "Order accepted and added to optimized delivery route with $orderCount orders")
                } else {
                    callback(true, "Order accepted successfully")
                }
            }
            
        } catch (e: Exception) {
            Log.e(TAG, "Error handling order acceptance", e)
            withContext(Dispatchers.Main) {
                // Fallback to normal update
                updateOrderStatusNormal(newOrder.id, OrderStatus.ACCEPTED, callback)
            }
        }
    }
    
    /**
     * Standard order status update without batching
     */
    private fun updateOrderStatusNormal(
        orderId: String,
        newStatus: OrderStatus,
        callback: (success: Boolean, message: String) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = when (newStatus) {
                    OrderStatus.ACCEPTED -> firestoreService.acceptOrder(orderId)
                    OrderStatus.PICKED_UP -> firestoreService.pickupOrder(orderId)
                    OrderStatus.COMPLETED -> firestoreService.deliverOrder(orderId, null)
                    OrderStatus.CANCELLED -> firestoreService.cancelOrder(orderId, "Cancelled by rider")
                    OrderStatus.FAILED -> firestoreService.failOrder(orderId, "Delivery failed")
                    else -> Result.failure(Exception("Invalid status change"))
                }
                
                withContext(Dispatchers.Main) {
                    result.onSuccess {
                        callback(true, "Order status updated successfully")
                    }.onFailure { exception ->
                        callback(false, "Failed to update order status: ${exception.message}")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error updating order status", e)
                withContext(Dispatchers.Main) {
                    callback(false, "Network error: ${e.message}")
                }
            }
        }
    }
    
    /**
     * Get orders for a batch with their sequence numbers
     */
    fun getBatchOrdersWithSequence(
        batchId: String,
        callback: (success: Boolean, message: String, orders: List<Pair<Order, Int>>?) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = firestoreService.getBatchOrdersWithSequence(batchId)
                
                withContext(Dispatchers.Main) {
                    result.onSuccess { sequencedOrders ->
                        callback(true, "Batch orders retrieved successfully", sequencedOrders)
                    }.onFailure { exception ->
                        callback(false, "Failed to fetch batch orders: ${exception.message}", null)
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching batch orders with sequence", e)
                withContext(Dispatchers.Main) {
                    callback(false, "Network error: ${e.message}", null)
                }
            }
        }
    }
    
    /**
     * Get orders for a batch
     */
    fun getBatchOrders(
        batchId: String,
        callback: (success: Boolean, message: String, orders: List<Order>?) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = firestoreService.getBatchOrders(batchId)
                
                withContext(Dispatchers.Main) {
                    result.onSuccess { orders ->
                        callback(true, "Batch orders retrieved successfully", orders)
                    }.onFailure { exception ->
                        callback(false, "Failed to fetch batch orders: ${exception.message}", null)
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching batch orders", e)
                withContext(Dispatchers.Main) {
                    callback(false, "Network error: ${e.message}", null)
                }
            }
        }
    }
    
    /**
     * Complete a batch
     */
    fun completeBatch(batchId: String, callback: (success: Boolean, message: String) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Mock implementation - in a real app, this would call the API
                withContext(Dispatchers.Main) {
                    callback(true, "Batch completed successfully")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error completing batch", e)
                withContext(Dispatchers.Main) {
                    callback(false, "Network error: ${e.message}")
                }
            }
        }
    }
    
    /**
     * Get the currently active batch for a rider
     */
    fun getActiveBatch(callback: (success: Boolean, message: String, batchDetails: batch.BatchDetails?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Mock implementation - in a real app, this would call the API
                withContext(Dispatchers.Main) {
                    // Return mock data for now
                    val mockBatch = batch.BatchDetails(
                        batchId = "B" + UUID.randomUUID().toString().substring(0, 8),
                        estimatedEarnings = 25.0,
                        estimatedTimeMinutes = 45
                    )
                    callback(true, "Active batch retrieved", mockBatch)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching active batch", e)
                withContext(Dispatchers.Main) {
                    callback(false, "Network error: ${e.message}", null)
                }
            }
        }
    }
    
    /**
     * Start a batch delivery
     */
    fun startBatch(batchId: String, callback: (success: Boolean, message: String) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Mock implementation - in a real app, this would call the API
                withContext(Dispatchers.Main) {
                    callback(true, "Batch started successfully")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error starting batch", e)
                withContext(Dispatchers.Main) {
                    callback(false, "Network error: ${e.message}")
                }
            }
        }
    }
    
    /**
     * Optimize the route for a batch
     */
    fun optimizeBatchRoute(batchId: String, callback: (success: Boolean, message: String, optimizedOrders: List<Order>?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = firestoreService.optimizeBatchRoute(batchId)
                
                withContext(Dispatchers.Main) {
                    result.onSuccess { orders ->
                        callback(true, "Route optimized successfully", orders)
                    }.onFailure { exception ->
                        callback(false, "Failed to optimize route: ${exception.message}", null)
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error optimizing batch route", e)
                withContext(Dispatchers.Main) {
                    callback(false, "Network error: ${e.message}", null)
                }
            }
        }
    }
    
    /**
     * Remove an order from a batch
     */
    fun removeOrderFromBatch(batchId: String, orderId: String, callback: (success: Boolean, message: String) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Mock implementation - in a real app, this would call the API
                withContext(Dispatchers.Main) {
                    callback(true, "Order removed from batch successfully")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error removing order from batch", e)
                withContext(Dispatchers.Main) {
                    callback(false, "Network error: ${e.message}")
                }
            }
        }
    }
    
    /**
     * Get available orders that can be added to a batch
     */
    fun getAvailableOrdersForBatch(callback: (success: Boolean, message: String, orders: List<Order>?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Get current rider ID
                val currentUser = FirebaseAuth.getInstance().currentUser
                if (currentUser == null) {
                    withContext(Dispatchers.Main) {
                        callback(false, "User not authenticated", null)
                    }
                    return@launch
                }
                
                // Query orders that are pending or accepted but not assigned to a batch
                val querySnapshot = ordersCollection
                    .whereIn("status", listOf("PENDING", "ACCEPTED"))
                    .whereEqualTo("batchId", null)  // Not already in a batch
                    .orderBy("createdAt", com.google.firebase.firestore.Query.Direction.DESCENDING)
                    .limit(10)
                    .get()
                    .await()
                
                // Convert documents to Order objects
                val orders = querySnapshot.documents.mapNotNull { doc ->
                    firestoreService.documentToOrder(doc)
                }
                
                withContext(Dispatchers.Main) {
                    callback(true, "Available orders retrieved successfully", orders)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching available orders for batch", e)
                withContext(Dispatchers.Main) {
                    callback(false, "Network error: ${e.message}", null)
                }
            }
        }
    }
    
    /**
     * Create a new batch
     */
    fun createBatch(callback: (success: Boolean, message: String, batchId: String?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Get current rider ID
                val currentUser = FirebaseAuth.getInstance().currentUser
                if (currentUser == null) {
                    withContext(Dispatchers.Main) {
                        callback(false, "User not authenticated", null)
                    }
                    return@launch
                }
                
                val riderId = currentUser.uid
                
                // Generate a batch ID
                val batchId = "B" + UUID.randomUUID().toString().substring(0, 8)
                
                // Create a new batch document
                val batchData = hashMapOf(
                    "batchId" to batchId,
                    "riderId" to riderId,
                    "status" to "ACTIVE",
                    "createdAt" to Date(),
                    "updatedAt" to Date(),
                    "orderCount" to 0
                )
                
                // Add to batches collection
                db.collection("batches")
                    .document(batchId)
                    .set(batchData)
                    .await()
                
                withContext(Dispatchers.Main) {
                    callback(true, "Batch created successfully", batchId)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error creating batch", e)
                withContext(Dispatchers.Main) {
                    callback(false, "Network error: ${e.message}", null)
                }
            }
        }
    }
    
    /**
     * Add an order to a batch
     */
    fun addOrderToBatch(batchId: String, orderId: String, callback: (success: Boolean, message: String) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Validate inputs
                if (batchId.isEmpty() || orderId.isEmpty()) {
                    withContext(Dispatchers.Main) {
                        callback(false, "Invalid batch ID or order ID")
                    }
                    return@launch
                }
                
                // Get current rider ID
                val currentUser = FirebaseAuth.getInstance().currentUser
                if (currentUser == null) {
                    withContext(Dispatchers.Main) {
                        callback(false, "User not authenticated")
                    }
                    return@launch
                }
                
                // First, check if the batch exists
                val batchDoc = db.collection("batches")
                    .document(batchId)
                    .get()
                    .await()
                
                if (!batchDoc.exists()) {
                    withContext(Dispatchers.Main) {
                        callback(false, "Batch not found")
                    }
                    return@launch
                }
                
                // Check if the order exists
                val orderDoc = db.collection("orders")
                    .document(orderId)
                    .get()
                    .await()
                
                if (!orderDoc.exists()) {
                    withContext(Dispatchers.Main) {
                        callback(false, "Order not found")
                    }
                    return@launch
                }
                
                // Update the order with the batch ID
                db.collection("orders")
                    .document(orderId)
                    .update(
                        "batchId", batchId,
                        "updatedAt", Date()
                    )
                    .await()
                
                // Increment the order count in the batch
                db.collection("batches")
                    .document(batchId)
                    .update(
                        "orderCount", com.google.firebase.firestore.FieldValue.increment(1),
                        "updatedAt", Date()
                    )
                    .await()
                
                withContext(Dispatchers.Main) {
                    callback(true, "Order added to batch successfully")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error adding order to batch", e)
                withContext(Dispatchers.Main) {
                    callback(false, "Network error: ${e.message}")
                }
            }
        }
    }
    
    /**
     * Scan a package barcode
     */
    fun scanPackage(
        barcode: String,
        callback: (success: Boolean, message: String) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Check if barcode is valid
                if (barcode.isBlank()) {
                    withContext(Dispatchers.Main) {
                        callback(false, "Invalid barcode")
                    }
                    return@launch
                }
                
                // Normalize barcode - remove "PKG:" prefix if present
                val normalizedBarcode = if (barcode.startsWith("PKG:")) {
                    barcode.substring(4)
                } else {
                    barcode
                }
                
                // Look for orders with this package code
                val querySnapshot = db.collection("orders")
                    .whereEqualTo("packageCode", normalizedBarcode)
                    .limit(1)
                    .get()
                    .await()
                
                withContext(Dispatchers.Main) {
                    if (querySnapshot.isEmpty) {
                        // No order found with this package code
                        callback(false, "No order found with this package code")
                    } else {
                        // Get the order document
                        val orderDoc = querySnapshot.documents.first()
                        val orderId = orderDoc.id
                        val orderStatus = orderDoc.getString("status") ?: ""
                        
                        when (orderStatus) {
                            "COMPLETED", "DELIVERED" -> {
                                callback(false, "This package has already been delivered")
                            }
                            "CANCELLED" -> {
                                callback(false, "This order has been cancelled")
                            }
                            else -> {
                                // Record the scan
                                val scanData = hashMapOf(
                                    "orderId" to orderId,
                                    "packageCode" to normalizedBarcode,
                                    "scannedAt" to Date(),
                                    "scannedBy" to (FirebaseAuth.getInstance().currentUser?.uid ?: ""),
                                    "location" to null  // Would be set from device location in a real app
                                )
                                
                                // Add to package scans collection
                                db.collection("packageScans")
                                    .add(scanData)
                                    .await()
                                
                                // Get order details for the message
                                val orderNumber = orderDoc.getString("orderId") ?: "Unknown"
                                val customerName = orderDoc.getString("customerName") ?: "Customer"
                                
                                callback(true, "Package for order #$orderNumber scanned successfully")
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error scanning package", e)
                withContext(Dispatchers.Main) {
                    callback(false, "Network error: ${e.message}")
                }
            }
        }
    }
    
    /**
     * Verify delivery code
     */
    fun verifyDeliveryCode(
        orderId: String,
        code: String,
        callback: (success: Boolean, message: String) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Check if code is valid
                if (code.isBlank() || orderId.isBlank()) {
                    withContext(Dispatchers.Main) {
                        callback(false, "Invalid code or order ID")
                    }
                    return@launch
                }
                
                // Get the order document
                val orderDoc = db.collection("orders")
                    .document(orderId)
                    .get()
                    .await()
                
                if (!orderDoc.exists()) {
                    withContext(Dispatchers.Main) {
                        callback(false, "Order not found")
                    }
                    return@launch
                }
                
                // Check if the order is already delivered
                val status = orderDoc.getString("status") ?: ""
                if (status == "COMPLETED" || status == "DELIVERED") {
                    withContext(Dispatchers.Main) {
                        callback(false, "This order has already been delivered")
                    }
                    return@launch
                }
                
                // Check if the order is cancelled
                if (status == "CANCELLED") {
                    withContext(Dispatchers.Main) {
                        callback(false, "This order has been cancelled")
                    }
                    return@launch
                }
                
                // Verify the delivery code
                val deliveryCode = orderDoc.getString("deliveryCode") ?: ""
                val isValid = deliveryCode == code || code == "1234" // Fallback for testing
                
                if (isValid) {
                    // Update the order status to delivered
                    db.collection("orders")
                        .document(orderId)
                        .update(
                            "status", "COMPLETED",
                            "deliveredAt", Date(),
                            "updatedAt", Date()
                        )
                        .await()
                    
                    withContext(Dispatchers.Main) {
                        callback(true, "Delivery verified successfully")
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        callback(false, "Invalid delivery code")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error verifying delivery code", e)
                withContext(Dispatchers.Main) {
                    callback(false, "Network error: ${e.message}")
                }
            }
        }
    }
    
    /**
     * Search for orders, restaurants, customers, etc.
     */
    fun search(
        query: String,
        filter: SearchResultType = SearchResultType.ALL,
        callback: (success: Boolean, message: String, results: List<Any>?) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // In a real app, this would call FirestoreService's search method
                // For now, we'll generate mock results
                val results = generateMockSearchResults(query, filter)
                
                withContext(Dispatchers.Main) {
                    if (results.isNotEmpty()) {
                        callback(true, "Search results found", results)
                    } else {
                        callback(false, "No results found for '$query'", null)
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error performing search", e)
                withContext(Dispatchers.Main) {
                    callback(false, "Network error: ${e.message}", null)
                }
            }
        }
    }
    
    private fun generateMockSearchResults(query: String, filter: SearchResultType): List<Any> {
        // In a real app, this would search in Firestore
        // For now, generate some mock data based on the query and filter
        val results = mutableListOf<Any>()
        
        // If query is empty, return empty results
        if (query.isEmpty()) return results
        
        // Generate mock orders if filter is ALL or ORDER
        if (filter == SearchResultType.ALL || filter == SearchResultType.ORDER) {
            // Create a few sample orders that match the query
            for (i in 1..3) {
                val order = Order(
                    id = "ID$i",
                    orderId = "ORD$i",
                    customerName = "Customer $query $i",
                    customerAddress = "123 Main St, Apt $i",
                    customerPhone = "555-123-456$i",
                    restaurantName = "Restaurant $query $i",
                    restaurantAddress = "456 Food St #$i",
                    restaurantPhone = "555-789-012$i",
                    items = listOf(
                        OrderItem("Item 1", 1, 9.99),
                        OrderItem("Item 2", 2, 12.99)
                    ),
                    totalAmount = 35.97,
                    deliveryFee = 4.99,
                    status = OrderStatus.PENDING,
                    paymentMethod = "CARD",
                    distance = 3.5,
                    estimatedDeliveryTime = 30,
                    createdAt = Date()
                )
                results.add(order)
            }
        }
        
        return results
    }
    
    /**
     * Get available orders near rider's location
     */
    fun getAvailableOrdersNearby(
        maxDistance: Double? = null,
        callback: (success: Boolean, message: String, orders: List<Order>?) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = firestoreService.getAvailableOrdersNearby(maxDistance)
                
                withContext(Dispatchers.Main) {
                    result.onSuccess { orders ->
                        callback(true, "Available orders retrieved successfully", orders)
                    }.onFailure { exception ->
                        callback(false, "Failed to fetch available orders: ${exception.message}", null)
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching available orders", e)
                withContext(Dispatchers.Main) {
                    callback(false, "Network error: ${e.message}", null)
                }
            }
        }
    }
    
    /**
     * Start listening for available orders in real-time
     */
    fun startListeningForAvailableOrders(
        maxDistance: Double? = null,
        maxOrders: Int = 10,
        listener: FirestoreListenerService = FirestoreListenerService(),
        onUpdate: (List<Order>) -> Unit
    ) {
        listener.listenForAvailableOrders(maxDistance, maxOrders, onUpdate)
    }
    
    /**
     * Start listening for order assignments in real-time
     */
    fun startListeningForOrderAssignments(
        listener: FirestoreListenerService = FirestoreListenerService(),
        onOrderAssigned: (Order) -> Unit
    ) {
        listener.listenForOrderAssignments(onOrderAssigned)
    }
    
    /**
     * Request assignment for an order
     */
    fun requestOrderAssignment(
        orderId: String,
        callback: (success: Boolean, message: String) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = firestoreService.requestOrderAssignment(orderId)
                
                withContext(Dispatchers.Main) {
                    result.onSuccess {
                        callback(true, "Order assignment requested successfully")
                    }.onFailure { exception ->
                        callback(false, "Failed to request order assignment: ${exception.message}")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error requesting order assignment", e)
                withContext(Dispatchers.Main) {
                    callback(false, "Network error: ${e.message}")
                }
            }
        }
    }
    
    /**
     * Update rider's online status
     */
    fun updateRiderOnlineStatus(
        isOnline: Boolean,
        callback: (success: Boolean, message: String) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = firestoreService.updateRiderOnlineStatus(isOnline)
                
                withContext(Dispatchers.Main) {
                    result.onSuccess {
                        callback(true, "Rider status updated successfully")
                    }.onFailure { exception ->
                        callback(false, "Failed to update rider status: ${exception.message}")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error updating rider status", e)
                withContext(Dispatchers.Main) {
                    callback(false, "Network error: ${e.message}")
                }
            }
        }
    }
    
    /**
     * Start listening for batch order updates in real-time
     */
    fun startListeningForBatchOrders(
        batchId: String,
        listener: FirestoreListenerService = FirestoreListenerService(),
        onUpdate: (List<Order>) -> Unit
    ) {
        listener.listenForBatchOrders(batchId, onUpdate)
    }
    
    /**
     * Send real-time location update to admin panel
     */
    fun sendLocationUpdateToAdmin(
        latitude: Double,
        longitude: Double,
        speed: Float? = null,
        bearing: Float? = null,
        callback: (success: Boolean, message: String) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = firestoreService.sendLocationUpdateToAdmin(latitude, longitude, speed, bearing)
                
                withContext(Dispatchers.Main) {
                    result.onSuccess {
                        callback(true, "Location update sent successfully")
                    }.onFailure { exception ->
                        callback(false, "Failed to send location update: ${exception.message}")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error sending location update to admin", e)
                withContext(Dispatchers.Main) {
                    callback(false, "Network error: ${e.message}")
                }
            }
        }
    }
    
    /**
     * Admin method: Assign order to a specific rider
     */
    fun adminAssignOrder(
        orderId: String,
        riderId: String,
        adminNotes: String? = null,
        callback: (success: Boolean, message: String) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = firestoreService.adminAssignOrderToRider(orderId, riderId, adminNotes)
                
                withContext(Dispatchers.Main) {
                    result.onSuccess { success: Boolean ->
                        callback(true, "Order assigned to rider successfully")
                    }.onFailure { exception: Throwable ->
                        callback(false, "Failed to assign order: ${exception.message}")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error in admin assigning order", e)
                withContext(Dispatchers.Main) {
                    callback(false, "Network error: ${e.message}")
                }
            }
        }
    }
    
    /**
     * Admin method: Approve a rider application
     */
    fun adminApproveRider(
        riderId: String,
        notes: String? = null,
        callback: (success: Boolean, message: String) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = firestoreService.adminApproveRider(riderId, notes)
                
                withContext(Dispatchers.Main) {
                    result.onSuccess { success: Boolean ->
                        callback(true, "Rider approved successfully")
                    }.onFailure { exception: Throwable ->
                        callback(false, "Failed to approve rider: ${exception.message}")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error in admin approving rider", e)
                withContext(Dispatchers.Main) {
                    callback(false, "Network error: ${e.message}")
                }
            }
        }
    }
    
    /**
     * Admin method: Reject a rider application
     */
    fun adminRejectRider(
        riderId: String,
        reason: String,
        callback: (success: Boolean, message: String) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = firestoreService.adminRejectRider(riderId, reason)
                
                withContext(Dispatchers.Main) {
                    result.onSuccess { success: Boolean ->
                        callback(true, "Rider application rejected successfully")
                    }.onFailure { exception: Throwable ->
                        callback(false, "Failed to reject rider application: ${exception.message}")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error in admin rejecting rider", e)
                withContext(Dispatchers.Main) {
                    callback(false, "Network error: ${e.message}")
                }
            }
        }
    }
    
    /**
     * Admin method: Unassign an order from a rider
     */
    fun adminUnassignOrder(
        orderId: String,
        reason: String,
        callback: (success: Boolean, message: String) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = firestoreService.adminUnassignOrder(orderId, reason)
                
                withContext(Dispatchers.Main) {
                    result.onSuccess { success: Boolean ->
                        callback(true, "Order unassigned successfully")
                    }.onFailure { exception: Throwable ->
                        callback(false, "Failed to unassign order: ${exception.message}")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error in admin unassigning order", e)
                withContext(Dispatchers.Main) {
                    callback(false, "Network error: ${e.message}")
                }
            }
        }
    }
    
    /**
     * Admin method: Get pending riders for approval
     */
    fun adminGetPendingRiders(
        callback: (success: Boolean, message: String, riders: List<Map<String, Any>>?) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = firestoreService.adminGetPendingRiders()
                
                withContext(Dispatchers.Main) {
                    result.onSuccess { riders: List<Map<String, Any>> ->
                        callback(true, "Pending riders retrieved successfully", riders)
                    }.onFailure { exception: Throwable ->
                        callback(false, "Failed to get pending riders: ${exception.message}", null)
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error getting pending riders", e)
                withContext(Dispatchers.Main) {
                    callback(false, "Network error: ${e.message}", null)
                }
            }
        }
    }
    
    /**
     * Rider method: Accept an admin-assigned order
     */
    fun acceptAdminAssignedOrder(
        orderId: String,
        callback: (success: Boolean, message: String) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = firestoreService.acceptAdminAssignedOrder(orderId)
                
                withContext(Dispatchers.Main) {
                    result.onSuccess { success: Boolean ->
                        callback(true, "Order accepted successfully")
                    }.onFailure { exception: Throwable ->
                        callback(false, "Failed to accept order: ${exception.message}")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error accepting admin-assigned order", e)
                withContext(Dispatchers.Main) {
                    callback(false, "Network error: ${e.message}")
                }
            }
        }
    }
    
    /**
     * Rider method: Reject an admin-assigned order
     */
    fun rejectAdminAssignedOrder(
        orderId: String,
        reason: String,
        callback: (success: Boolean, message: String) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = firestoreService.rejectAdminAssignedOrder(orderId, reason)
                
                withContext(Dispatchers.Main) {
                    result.onSuccess { success: Boolean ->
                        callback(true, "Order rejected successfully")
                    }.onFailure { exception: Throwable ->
                        callback(false, "Failed to reject order: ${exception.message}")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error rejecting admin-assigned order", e)
                withContext(Dispatchers.Main) {
                    callback(false, "Network error: ${e.message}")
                }
            }
        }
    }
    
    /**
     * Search orders by query
     */
    fun searchOrders(query: String, filter: SearchResultType): List<Any> {
        // Early return for empty queries
        if (query.isEmpty()) return emptyList()
        
        try {
            val normalizedQuery = query.lowercase().trim()
            val results = mutableListOf<Any>()
            
            // Perform a synchronous query - in a real app, this would be asynchronous
            val querySnapshot = ordersCollection
                .orderBy("createdAt", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .limit(20)
                .get()
                .result
            
            // Filter the results based on the query
            for (doc in querySnapshot.documents) {
                val order = firestoreService.documentToOrder(doc) ?: continue
                
                // Check if the order matches the search criteria
                val matchesQuery = order.orderId.lowercase().contains(normalizedQuery) ||
                                  order.customerName.lowercase().contains(normalizedQuery) ||
                                  order.restaurantName.lowercase().contains(normalizedQuery)
                
                if (matchesQuery) {
                    results.add(order)
                }
            }
            
            return results
        } catch (e: Exception) {
            Log.e(TAG, "Error searching orders", e)
            return emptyList()
        }
    }
} 