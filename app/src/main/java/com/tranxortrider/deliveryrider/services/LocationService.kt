package com.tranxortrider.deliveryrider.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.location.Location
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.*
import com.google.firebase.auth.FirebaseAuth
import com.tranxortrider.deliveryrider.R
import com.tranxortrider.deliveryrider.home_screen
import com.tranxortrider.deliveryrider.utils.SharedPreferencesManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class LocationService : Service() {
    
    private val TAG = "LocationService"
    private val NOTIFICATION_ID = 12345
    private val CHANNEL_ID = "location_service_channel"
    
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var firestoreService: FirestoreService
    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    private lateinit var geofencingService: GeofencingService
    
    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    
    // Configuration parameters
    private val UPDATE_INTERVAL = TimeUnit.SECONDS.toMillis(30) // 30 seconds
    private val FASTEST_INTERVAL = TimeUnit.SECONDS.toMillis(15) // 15 seconds
    private val DISPLACEMENT = 20f // 20 meters
    
    // Admin panel update parameters
    private val ADMIN_UPDATE_INTERVAL = TimeUnit.SECONDS.toMillis(60) // 60 seconds
    private var lastAdminUpdateTime = 0L
    
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Location service created")
        
        firestoreService = FirestoreService()
        sharedPreferencesManager = SharedPreferencesManager(this)
        geofencingService = GeofencingService(this)
        
        // Initialize location client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        
        // Setup location callback
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { location ->
                    handleLocationUpdate(location)
                }
            }
        }
        
        // Setup geofences for delivery areas
        setupGeofences()
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "Location service started")
        
        // Create and show the notification
        val notification = createNotification()
        
        try {
            // Start as a foreground service with notification
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                startForeground(NOTIFICATION_ID, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION)
            } else {
                startForeground(NOTIFICATION_ID, notification)
            }
            
            // Request location updates
            requestLocationUpdates()
        } catch (e: SecurityException) {
            Log.e(TAG, "Security exception when starting service", e)
            stopSelf()
        } catch (e: Exception) {
            Log.e(TAG, "Error starting location service", e)
            stopSelf()
        }
        
        // If the service gets killed, restart it, but don't automatically recreate 
        // it might cause the same exception when the system tries to recreate it
        return START_NOT_STICKY
    }
    
    private fun requestLocationUpdates() {
        try {
            val locationRequest = LocationRequest.create().apply {
                interval = UPDATE_INTERVAL
                fastestInterval = FASTEST_INTERVAL
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                smallestDisplacement = DISPLACEMENT
            }
            
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
            
            Log.d(TAG, "Location updates requested")
        } catch (e: SecurityException) {
            Log.e(TAG, "Location permission not granted", e)
        } catch (e: Exception) {
            Log.e(TAG, "Error requesting location updates", e)
        }
    }
    
    private fun setupGeofences() {
        serviceScope.launch {
            try {
                val result = geofencingService.setupDeliveryAreaGeofences()
                if (result.isSuccess) {
                    Log.d(TAG, "Geofences setup successfully")
                } else {
                    Log.e(TAG, "Failed to setup geofences: ${result.exceptionOrNull()?.message}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error setting up geofences", e)
            }
        }
    }
    
    private fun handleLocationUpdate(location: Location) {
        Log.d(TAG, "New location: ${location.latitude}, ${location.longitude}")
        
        // Save location to preferences for offline access
        saveLocationLocally(location)
        
        // Update location in Firebase
        updateLocationInFirebase(location)
        
        // Send updates to admin panel (but not too frequently)
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastAdminUpdateTime >= ADMIN_UPDATE_INTERVAL) {
            sendLocationToAdminPanel(location)
            lastAdminUpdateTime = currentTime
        }
        
        // Check if location is within delivery area
        checkDeliveryAreaStatus(location)
    }
    
    private fun saveLocationLocally(location: Location) {
        sharedPreferencesManager.saveLastLocation(location.latitude, location.longitude)
        sharedPreferencesManager.saveLastLocationTimestamp(System.currentTimeMillis())
    }
    
    private fun updateLocationInFirebase(location: Location) {
        val currentUser = FirebaseAuth.getInstance().currentUser ?: return
        
        serviceScope.launch {
            try {
                val locationData = hashMapOf(
                    "latitude" to location.latitude,
                    "longitude" to location.longitude,
                    "accuracy" to location.accuracy,
                    "speed" to location.speed,
                    "bearing" to location.bearing,
                    "timestamp" to System.currentTimeMillis(),
                    "isOnline" to true
                )
                
                firestoreService.updateRiderLocation(locationData)
                
                // Also check if there are any cached locations that need to be synced
                syncCachedLocations()
            } catch (e: Exception) {
                Log.e(TAG, "Failed to update location in Firebase", e)
                // Cache this location for later sync
                sharedPreferencesManager.addLocationToSyncQueue(
                    location.latitude, 
                    location.longitude,
                    System.currentTimeMillis()
                )
            }
        }
    }
    
    private fun syncCachedLocations() {
        val locationQueue = sharedPreferencesManager.getLocationSyncQueue()
        if (locationQueue.isEmpty()) return
        
        serviceScope.launch {
            try {
                firestoreService.bulkUpdateRiderLocations(locationQueue)
                // Clear the queue if successful
                sharedPreferencesManager.clearLocationSyncQueue()
            } catch (e: Exception) {
                Log.e(TAG, "Failed to sync cached locations", e)
            }
        }
    }
    
    private fun sendLocationToAdminPanel(location: Location) {
        serviceScope.launch {
            try {
                val result = firestoreService.sendLocationUpdateToAdmin(
                    location.latitude, 
                    location.longitude,
                    location.speed,
                    location.bearing
                )
                
                if (result.isSuccess) {
                    Log.d(TAG, "Location sent to admin panel successfully")
                } else {
                    Log.e(TAG, "Failed to send location to admin panel: ${result.exceptionOrNull()?.message}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error sending location to admin panel", e)
            }
        }
    }
    
    private fun checkDeliveryAreaStatus(location: Location) {
        serviceScope.launch {
            try {
                val result = geofencingService.isLocationInDeliveryArea(location.latitude, location.longitude)
                
                if (result.isSuccess) {
                    val isInDeliveryArea = result.getOrNull() ?: false
                    
                    // Store the status for the UI to use
                    sharedPreferencesManager.setIsInDeliveryArea(isInDeliveryArea)
                    
                    // If outside delivery area and we have active orders, notify the user
                    if (!isInDeliveryArea) {
                        // This would typically check if the rider has active orders
                        // and show a more prominent warning if they do
                        Log.d(TAG, "Rider is outside of any delivery area")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error checking delivery area status", e)
            }
        }
    }
    
    private fun createNotification() = NotificationCompat.Builder(this, CHANNEL_ID)
        .setContentTitle("TranxortRider Location Service")
        .setContentText("Tracking your location for deliveries")
        .setSmallIcon(R.drawable.ic_location)
        .setContentIntent(
            PendingIntent.getActivity(
                this,
                0,
                Intent(this, home_screen::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
        .setOngoing(true)
        .build().also {
            createNotificationChannel()
        }
    
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Location Service Channel",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Used for tracking rider location during deliveries"
            }
            
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
    
    override fun onBind(intent: Intent?): IBinder? = null
    
    override fun onDestroy() {
        super.onDestroy()
        
        // Stop location updates
        fusedLocationClient.removeLocationUpdates(locationCallback)
        
        // Update rider status to offline
        serviceScope.launch {
            try {
                val currentUser = FirebaseAuth.getInstance().currentUser
                if (currentUser != null) {
                    firestoreService.updateRiderOnlineStatus(false)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to update rider status to offline", e)
            }
            
            // Cancel all coroutines
            serviceScope.cancel()
        }
        
        Log.d(TAG, "Location service destroyed")
    }
} 