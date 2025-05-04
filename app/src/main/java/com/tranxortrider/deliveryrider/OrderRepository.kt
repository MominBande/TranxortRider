package com.tranxortrider.deliveryrider

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.util.Log
import com.tranxortrider.deliveryrider.services.FirestoreService

class OrderRepository(private val firestoreService: FirestoreService) {
    private val TAG = "OrderRepository"

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
    // ... existing code ...
} 