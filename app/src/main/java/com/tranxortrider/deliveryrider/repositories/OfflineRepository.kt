package com.tranxortrider.deliveryrider.repositories

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tranxortrider.deliveryrider.models.Order
import com.tranxortrider.deliveryrider.models.OrderStatus
import com.tranxortrider.deliveryrider.models.User
import com.tranxortrider.deliveryrider.services.FirestoreService
import com.tranxortrider.deliveryrider.utils.NetworkUtils
import com.tranxortrider.deliveryrider.utils.SharedPreferencesManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.reflect.Type

/**
 * Repository for managing offline data access and synchronization
 */
class OfflineRepository(
    private val context: Context,
    private val firestoreService: FirestoreService = FirestoreService()
) {
    private val TAG = "OfflineRepository"
    private val sharedPreferencesManager = SharedPreferencesManager(context)
    private val gson = Gson()
    
    // User profile
    
    suspend fun getUserProfile(): Flow<Result<User?>> = flow {
        if (NetworkUtils.isNetworkAvailable(context)) {
            try {
                // Try to get from network first
                val result = firestoreService.getUserProfile()
                
                if (result.isSuccess) {
                    val user = result.getOrNull()
                    if (user != null) {
                        // Cache the user profile
                        val userJson = gson.toJson(user)
                        sharedPreferencesManager.cacheUserProfile(userJson)
                    }
                    emit(result)
                } else {
                    // Network request failed, try cached data
                    emit(getCachedUserProfile())
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error getting user profile", e)
                emit(getCachedUserProfile())
            }
        } else {
            // No network, use cached data
            emit(getCachedUserProfile())
        }
    }
    
    private fun getCachedUserProfile(): Result<User?> {
        return try {
            val cachedJson = sharedPreferencesManager.getCachedUserProfile()
            if (cachedJson != null) {
                val user = gson.fromJson(cachedJson, User::class.java)
                Result.success(user)
            } else {
                Result.success(null)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error parsing cached user profile", e)
            Result.failure(e)
        }
    }
    
    // Orders
    
    suspend fun getOrders(status: OrderStatus? = null): Flow<Result<List<Order>>> = flow {
        if (NetworkUtils.isNetworkAvailable(context)) {
            try {
                // Try to get from network first
                val result = when (status) {
                    OrderStatus.PENDING, OrderStatus.ASSIGNED -> firestoreService.getPendingOrders()
                    OrderStatus.ACCEPTED, OrderStatus.PICKED_UP, OrderStatus.IN_TRANSIT -> firestoreService.getAssignedOrders()
                    OrderStatus.COMPLETED -> firestoreService.getCompletedOrders().map { it.first }
                    OrderStatus.FAILED -> firestoreService.getFailedOrders().map { it.first }
                    OrderStatus.CANCELLED -> firestoreService.getFailedOrders().map { it.first } // Treat canceled as failed for now
                    null -> {
                        val pendingResult = firestoreService.getPendingOrders()
                        val assignedResult = firestoreService.getAssignedOrders()
                        
                        if (pendingResult.isSuccess && assignedResult.isSuccess) {
                            Result.success(pendingResult.getOrNull().orEmpty() + assignedResult.getOrNull().orEmpty())
                        } else {
                            Result.failure(Exception("Failed to fetch orders"))
                        }
                    }
                }
                
                if (result.isSuccess) {
                    val orders = result.getOrNull() ?: emptyList()
                    // Cache the orders
                    val ordersJson = gson.toJson(orders)
                    sharedPreferencesManager.cacheOrders(ordersJson)
                    emit(result)
                } else {
                    // Network request failed, try cached data
                    emit(getCachedOrders(status))
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error getting orders", e)
                emit(getCachedOrders(status))
            }
        } else {
            // No network, use cached data
            emit(getCachedOrders(status))
        }
    }
    
    private fun getCachedOrders(status: OrderStatus? = null): Result<List<Order>> {
        return try {
            val cachedJson = sharedPreferencesManager.getCachedOrders()
            if (cachedJson != null) {
                val type: Type = object : TypeToken<List<Order>>() {}.type
                val orders: List<Order> = gson.fromJson(cachedJson, type)
                
                // Filter by status if needed
                val filteredOrders = if (status != null) {
                    orders.filter { it.status == status }
                } else {
                    orders
                }
                
                Result.success(filteredOrders)
            } else {
                Result.success(emptyList())
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error parsing cached orders", e)
            Result.failure(e)
        }
    }
    
    // Order details
    
    suspend fun getOrderById(orderId: String): Flow<Result<Order?>> = flow {
        if (NetworkUtils.isNetworkAvailable(context)) {
            try {
                // Try to get from network first
                val result = firestoreService.getOrderById(orderId)
                emit(result)
            } catch (e: Exception) {
                Log.e(TAG, "Error getting order by ID", e)
                // Try to find in cached orders
                emit(getCachedOrderById(orderId))
            }
        } else {
            // No network, try to find in cached orders
            emit(getCachedOrderById(orderId))
        }
    }
    
    private fun getCachedOrderById(orderId: String): Result<Order?> {
        return try {
            val cachedJson = sharedPreferencesManager.getCachedOrders()
            if (cachedJson != null) {
                val type: Type = object : TypeToken<List<Order>>() {}.type
                val orders: List<Order> = gson.fromJson(cachedJson, type)
                
                // Find the specific order
                val order = orders.find { it.id == orderId }
                Result.success(order)
            } else {
                Result.success(null)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error finding cached order by ID", e)
            Result.failure(e)
        }
    }
    
    // Sync data when coming back online
    
    suspend fun syncPendingChanges(): Result<Boolean> {
        if (!NetworkUtils.isNetworkAvailable(context)) {
            return Result.failure(Exception("Network not available"))
        }
        
        return try {
            // Sync any cached location updates
            val locationQueue = sharedPreferencesManager.getLocationSyncQueue()
            if (locationQueue.isNotEmpty()) {
                firestoreService.bulkUpdateRiderLocations(locationQueue)
                sharedPreferencesManager.clearLocationSyncQueue()
            }
            
            // Update online status
            firestoreService.updateRiderOnlineStatus(true)
            
            // Refresh data
            firestoreService.getPendingOrders()
            firestoreService.getAssignedOrders()
            
            Result.success(true)
        } catch (e: Exception) {
            Log.e(TAG, "Error syncing pending changes", e)
            Result.failure(e)
        }
    }
    
    // Check if sync is needed
    
    fun isSyncNeeded(): Boolean {
        return sharedPreferencesManager.isSyncNeeded()
    }
} 