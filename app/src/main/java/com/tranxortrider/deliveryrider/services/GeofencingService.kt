package com.tranxortrider.deliveryrider.services

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.tranxortrider.deliveryrider.receivers.GeofenceBroadcastReceiver
import kotlinx.coroutines.tasks.await

/**
 * Service for handling geofencing for delivery areas
 */
class GeofencingService(private val context: Context) {
    private val TAG = "GeofencingService"
    private val geofencingClient: GeofencingClient = LocationServices.getGeofencingClient(context)
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    
    // Pending intent for geofence transitions
    private val geofencePendingIntent: PendingIntent by lazy {
        val intent = Intent(context, GeofenceBroadcastReceiver::class.java)
        PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )
    }
    
    /**
     * Add geofences for all active delivery areas
     */
    suspend fun setupDeliveryAreaGeofences(): Result<Boolean> {
        return try {
            // Fetch delivery areas from Firestore
            val deliveryAreas = fetchDeliveryAreas()
            
            if (deliveryAreas.isEmpty()) {
                Log.d(TAG, "No delivery areas found")
                return Result.success(false)
            }
            
            // Create geofences from delivery areas
            val geofenceList = deliveryAreas.map { area ->
                Geofence.Builder()
                    .setRequestId(area.id)
                    .setCircularRegion(
                        area.latitude,
                        area.longitude,
                        area.radius
                    )
                    .setExpirationDuration(Geofence.NEVER_EXPIRE)
                    .setTransitionTypes(
                        Geofence.GEOFENCE_TRANSITION_ENTER or
                        Geofence.GEOFENCE_TRANSITION_EXIT
                    )
                    .build()
            }
            
            // Create geofencing request
            val geofencingRequest = GeofencingRequest.Builder()
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                .addGeofences(geofenceList)
                .build()
            
            // Add geofences
            try {
                geofencingClient.removeGeofences(geofencePendingIntent).await()
                geofencingClient.addGeofences(geofencingRequest, geofencePendingIntent).await()
                Log.d(TAG, "Successfully added ${geofenceList.size} geofences")
                Result.success(true)
            } catch (securityException: SecurityException) {
                Log.e(TAG, "Missing location permission", securityException)
                Result.failure(securityException)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error setting up geofences", e)
            Result.failure(e)
        }
    }
    
    /**
     * Remove all geofences
     */
    suspend fun removeAllGeofences(): Result<Boolean> {
        return try {
            geofencingClient.removeGeofences(geofencePendingIntent).await()
            Log.d(TAG, "All geofences removed")
            Result.success(true)
        } catch (e: Exception) {
            Log.e(TAG, "Error removing geofences", e)
            Result.failure(e)
        }
    }
    
    /**
     * Fetch delivery areas from Firestore
     */
    private suspend fun fetchDeliveryAreas(): List<DeliveryArea> {
        return try {
            val snapshot = db.collection("delivery_areas")
                .whereEqualTo("isActive", true)
                .get()
                .await()
            
            snapshot.documents.mapNotNull { doc ->
                try {
                    val id = doc.id
                    val name = doc.getString("name") ?: return@mapNotNull null
                    val location = doc.getGeoPoint("center") ?: return@mapNotNull null
                    val radius = doc.getDouble("radiusMeters")?.toFloat() ?: 1000f
                    
                    DeliveryArea(
                        id = id,
                        name = name,
                        latitude = location.latitude,
                        longitude = location.longitude,
                        radius = radius
                    )
                } catch (e: Exception) {
                    Log.e(TAG, "Error parsing delivery area", e)
                    null
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching delivery areas", e)
            emptyList()
        }
    }
    
    /**
     * Check if a location is within any active delivery area
     */
    suspend fun isLocationInDeliveryArea(latitude: Double, longitude: Double): Result<Boolean> {
        return try {
            val deliveryAreas = fetchDeliveryAreas()
            
            if (deliveryAreas.isEmpty()) {
                return Result.success(true) // If no areas defined, consider everywhere valid
            }
            
            // Check if the location is within any delivery area
            val isInArea = deliveryAreas.any { area ->
                val distance = calculateDistance(
                    latitude, longitude,
                    area.latitude, area.longitude
                )
                distance <= area.radius
            }
            
            Result.success(isInArea)
        } catch (e: Exception) {
            Log.e(TAG, "Error checking if location is in delivery area", e)
            Result.failure(e)
        }
    }
    
    /**
     * Calculate distance between two points using Haversine formula
     */
    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Float {
        val R = 6371000.0 // Earth's radius in meters
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLon / 2) * Math.sin(dLon / 2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        return (R * c).toFloat()
    }
    
    /**
     * Data class for delivery area
     */
    data class DeliveryArea(
        val id: String,
        val name: String,
        val latitude: Double,
        val longitude: Double,
        val radius: Float
    )
} 