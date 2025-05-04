package com.tranxortrider.deliveryrider.services

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.MetadataChanges
import com.tranxortrider.deliveryrider.models.Order
import com.tranxortrider.deliveryrider.models.OrderStatus

/**
 * Service for handling Firestore real-time listeners
 */
class FirestoreListenerService {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val TAG = "FirestoreListenerService"
    
    // Listener registrations
    private var pendingOrdersListener: ListenerRegistration? = null
    private var assignedOrdersListener: ListenerRegistration? = null
    private var userProfileListener: ListenerRegistration? = null
    private var specificOrderListener: ListenerRegistration? = null
    private var availableOrdersListener: ListenerRegistration? = null
    private var orderAssignmentListener: ListenerRegistration? = null
    private var batchOrdersListener: ListenerRegistration? = null
    
    // Firestore service for document conversion
    private val firestoreService = FirestoreService()
    
    /**
     * Start listening for pending orders in real-time
     */
    fun listenForPendingOrders(onUpdate: (List<Order>) -> Unit) {
        val currentUser = auth.currentUser ?: return
        
        // Remove any existing listener
        pendingOrdersListener?.remove()
        
        // Set up the new listener
        pendingOrdersListener = db.collection("orders")
            .whereEqualTo("status", "PENDING")
            .whereEqualTo("assignedRider", null)
            .addSnapshotListener(MetadataChanges.INCLUDE) { snapshot, error ->
                if (error != null) {
                    Log.e(TAG, "Error listening for pending orders", error)
                    return@addSnapshotListener
                }
                
                if (snapshot != null) {
                    val orders = snapshot.documents.mapNotNull { doc ->
                        firestoreService.documentToOrder(doc)
                    }
                    onUpdate(orders)
                }
            }
    }
    
    /**
     * Start listening for assigned orders in real-time
     */
    fun listenForAssignedOrders(onUpdate: (List<Order>) -> Unit) {
        val currentUser = auth.currentUser ?: return
        
        // Remove any existing listener
        assignedOrdersListener?.remove()
        
        // Set up the new listener
        assignedOrdersListener = db.collection("orders")
            .whereEqualTo("riderId", currentUser.uid)
            .whereIn("status", listOf("ASSIGNED", "ACCEPTED", "PICKED_UP", "IN_TRANSIT"))
            .addSnapshotListener(MetadataChanges.INCLUDE) { snapshot, error ->
                if (error != null) {
                    Log.e(TAG, "Error listening for assigned orders", error)
                    return@addSnapshotListener
                }
                
                if (snapshot != null) {
                    val orders = snapshot.documents.mapNotNull { doc ->
                        firestoreService.documentToOrder(doc)
                    }
                    Log.d(TAG, "Assigned orders listener received ${orders.size} orders: ${orders.map { "${it.id}: ${it.status}" }}")
                    onUpdate(orders)
                }
            }
    }
    
    /**
     * Start listening for a specific order in real-time
     */
    fun listenForOrder(orderId: String, onUpdate: (Order?) -> Unit) {
        // Remove any existing listener
        specificOrderListener?.remove()
        
        // Set up the new listener
        specificOrderListener = db.collection("orders")
            .document(orderId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e(TAG, "Error listening for order", error)
                    return@addSnapshotListener
                }
                
                if (snapshot != null && snapshot.exists()) {
                    val order = firestoreService.documentToOrder(snapshot)
                    onUpdate(order)
                } else {
                    onUpdate(null)
                }
            }
    }
    
    /**
     * Start listening for user profile changes in real-time
     */
    fun listenForUserProfile(onUpdate: (Map<String, Any>?) -> Unit) {
        val currentUser = auth.currentUser ?: return
        
        // Remove any existing listener
        userProfileListener?.remove()
        
        // Set up the new listener
        userProfileListener = db.collection("users")
            .document(currentUser.uid)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e(TAG, "Error listening for user profile", error)
                    return@addSnapshotListener
                }
                
                if (snapshot != null && snapshot.exists()) {
                    onUpdate(snapshot.data)
                } else {
                    onUpdate(null)
                }
            }
    }
    
    /**
     * Start listening for order status changes that might require batch updates
     */
    fun startOrderStatusChangeListener(onOrderStatusChanged: (Order, String) -> Unit) {
        val currentUser = auth.currentUser ?: return
        
        // Remove any existing listener
        specificOrderListener?.remove()
        
        // Set up a listener for orders assigned to this rider
        specificOrderListener = db.collection("orders")
            .whereEqualTo("assignedRider", currentUser.uid)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e(TAG, "Error listening for order status changes", error)
                    return@addSnapshotListener
                }
                
                if (snapshot != null) {
                    for (docChange in snapshot.documentChanges) {
                        val order = firestoreService.documentToOrder(docChange.document) ?: continue
                        
                        // Check if this is a status change
                        val oldStatus = docChange.document.getString("oldStatus")
                        if (oldStatus != null && oldStatus != order.status.toString()) {
                            // Status has changed
                            onOrderStatusChanged(order, oldStatus)
                        }
                    }
                }
            }
    }
    
    /**
     * Start listening for available orders that can be assigned to the rider
     */
    fun listenForAvailableOrders(
        maxDistance: Double? = null,
        maxOrders: Int = 10,
        onUpdate: (List<Order>) -> Unit
    ) {
        val currentUser = auth.currentUser ?: return
        
        // Remove any existing listener
        availableOrdersListener?.remove()
        
        // Get the rider's current location
        db.collection("riders")
            .document(currentUser.uid)
            .get()
            .addOnSuccessListener { riderDoc ->
                val riderLat = riderDoc.getDouble("latitude") ?: 0.0
                val riderLng = riderDoc.getDouble("longitude") ?: 0.0
                
                // Set up the new listener for available orders
                availableOrdersListener = db.collection("orders")
                    .whereEqualTo("status", "PENDING")
                    .whereEqualTo("assignedRider", null)
                    .limit(maxOrders.toLong())
                    .addSnapshotListener(MetadataChanges.INCLUDE) { snapshot, error ->
                        if (error != null) {
                            Log.e(TAG, "Error listening for available orders", error)
                            return@addSnapshotListener
                        }
                        
                        if (snapshot != null) {
                            val orders = snapshot.documents.mapNotNull { doc ->
                                firestoreService.documentToOrder(doc)
                            }
                            
                            // Filter orders by distance if maxDistance is provided
                            val filteredOrders = if (maxDistance != null) {
                                orders.filter { order ->
                                    val restaurantLat = order.restaurantLatitude ?: 0.0
                                    val restaurantLng = order.restaurantLongitude ?: 0.0
                                    
                                    // Calculate distance
                                    val distance = calculateDistance(riderLat, riderLng, restaurantLat, restaurantLng)
                                    distance <= maxDistance
                                }
                            } else {
                                orders
                            }
                            
                            onUpdate(filteredOrders)
                        }
                    }
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error getting rider location", e)
                // Fall back to unfiltered orders
                availableOrdersListener = db.collection("orders")
                    .whereEqualTo("status", "PENDING")
                    .whereEqualTo("assignedRider", null)
                    .limit(maxOrders.toLong())
                    .addSnapshotListener(MetadataChanges.INCLUDE) { snapshot, error ->
                        if (error != null) {
                            Log.e(TAG, "Error listening for available orders", error)
                            return@addSnapshotListener
                        }
                        
                        if (snapshot != null) {
                            val orders = snapshot.documents.mapNotNull { doc ->
                                firestoreService.documentToOrder(doc)
                            }
                            onUpdate(orders)
                        }
                    }
            }
    }
    
    /**
     * Start listening for order assignments specifically for this rider
     */
    fun listenForOrderAssignments(onOrderAssigned: (Order) -> Unit) {
        val currentUser = auth.currentUser ?: return
        
        // Remove any existing listener
        orderAssignmentListener?.remove()
        
        // Set up the new listener
        orderAssignmentListener = db.collection("orders")
            .whereEqualTo("assignedRider", currentUser.uid)
            .whereEqualTo("status", "ASSIGNED")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e(TAG, "Error listening for order assignments", error)
                    return@addSnapshotListener
                }
                
                if (snapshot != null) {
                    for (docChange in snapshot.documentChanges) {
                        val order = firestoreService.documentToOrder(docChange.document)
                        if (order != null) {
                            onOrderAssigned(order)
                        }
                    }
                }
            }
    }
    
    /**
     * Start listening for batch order updates in real-time
     */
    fun listenForBatchOrders(batchId: String, onUpdate: (List<Order>) -> Unit) {
        val currentUser = auth.currentUser ?: return

        // Remove any existing listener
        batchOrdersListener?.remove()

        // First verify batch ownership
        db.collection("batches")
            .document(batchId)
            .get()
            .addOnSuccessListener { batchDoc ->
                if (!batchDoc.exists()) {
                    Log.e(TAG, "Batch not found: $batchId")
                    onUpdate(emptyList())
                    return@addOnSuccessListener
                }
                
                val riderId = batchDoc.getString("riderId")
                if (riderId != currentUser.uid) {
                    Log.e(TAG, "You don't own this batch: $batchId")
                    onUpdate(emptyList())
                    return@addOnSuccessListener
                }
                
                // Setup listener for batch orders collection
                batchOrdersListener = db.collection("batches")
                    .document(batchId)
                    .collection("orders")
                    .addSnapshotListener { snapshot, error ->
                        if (error != null) {
                            Log.e(TAG, "Error listening for batch orders", error)
                            return@addSnapshotListener
                        }
                        
                        if (snapshot != null) {
                            // Get order IDs with their sequence numbers
                            val orderSequences = snapshot.documents.mapNotNull { doc ->
                                val orderId = doc.getString("orderId") ?: return@mapNotNull null
                                val sequence = doc.getLong("sequence")?.toInt() ?: 0
                                orderId to sequence
                            }.sortedBy { it.second }

                            // Fetch all order details
                            if (orderSequences.isNotEmpty()) {
                                val orderIds = orderSequences.map { it.first }
                                db.collection("orders")
                                    .whereIn("orderId", orderIds)
                                    .get()
                                    .addOnSuccessListener { ordersSnapshot ->
                                        val orders = ordersSnapshot.documents.mapNotNull { doc ->
                                            firestoreService.documentToOrder(doc)
                                        }

                                        // Sort orders according to sequence
                                        val sequencedOrders = orders.sortedWith { order1, order2 ->
                                            val seq1 = orderSequences.find { it.first == order1.orderId }?.second ?: Int.MAX_VALUE
                                            val seq2 = orderSequences.find { it.first == order2.orderId }?.second ?: Int.MAX_VALUE
                                            seq1.compareTo(seq2)
                                        }

                                        onUpdate(sequencedOrders)
                                    }
                                    .addOnFailureListener { e ->
                                        Log.e(TAG, "Error fetching batch order details", e)
                                        onUpdate(emptyList())
                                    }
                            } else {
                                onUpdate(emptyList())
                            }
                        }
                    }
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error verifying batch ownership", e)
                onUpdate(emptyList())
            }
    }
    
    /**
     * Calculate distance between two coordinates (simplified)
     */
    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
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
     * Remove all listeners
     */
    fun removeAllListeners() {
        pendingOrdersListener?.remove()
        assignedOrdersListener?.remove()
        userProfileListener?.remove()
        specificOrderListener?.remove()
        availableOrdersListener?.remove()
        orderAssignmentListener?.remove()
        batchOrdersListener?.remove()
        
        pendingOrdersListener = null
        assignedOrdersListener = null
        userProfileListener = null
        specificOrderListener = null
        availableOrdersListener = null
        orderAssignmentListener = null
        batchOrdersListener = null
    }
} 