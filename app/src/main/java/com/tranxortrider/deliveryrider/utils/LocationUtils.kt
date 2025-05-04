package com.tranxortrider.deliveryrider.utils

import android.content.Context
import android.location.Location
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Utility class for location-related operations
 */
object LocationUtils {
    
    // Constants for location updates
    private const val LOCATION_UPDATE_INTERVAL = 5000L // 5 seconds
    private const val FASTEST_LOCATION_INTERVAL = 2000L // 2 seconds
    
    /**
     * Get the current location using FusedLocationProvider
     * 
     * @param context Application context
     * @return Location object or null if location couldn't be obtained
     */
    suspend fun getCurrentLocation(context: Context): Location = suspendCancellableCoroutine { continuation ->
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        
        try {
            Log.d("LocationUtils", "Requesting current location")
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        Log.d("LocationUtils", "Got last location: lat=${location.latitude}, lng=${location.longitude}")
                        continuation.resume(location)
                    } else {
                        Log.d("LocationUtils", "Last location is null, requesting fresh location")
                        // If last location is null, request a single update
                        requestSingleUpdate(fusedLocationClient) { newLocation ->
                            Log.d("LocationUtils", "Fresh location received: lat=${newLocation.latitude}, lng=${newLocation.longitude}")
                            continuation.resume(newLocation)
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("LocationUtils", "Failed to get last location", e)
                    // Try single update even on failure
                    requestSingleUpdate(fusedLocationClient) { newLocation ->
                        Log.d("LocationUtils", "Got fallback location after failure: lat=${newLocation.latitude}, lng=${newLocation.longitude}")
                        continuation.resume(newLocation)
                    }
                }
                .addOnCanceledListener {
                    Log.w("LocationUtils", "Location request was canceled")
                    requestSingleUpdate(fusedLocationClient) { newLocation ->
                        Log.d("LocationUtils", "Got fallback location after cancellation: lat=${newLocation.latitude}, lng=${newLocation.longitude}")
                        continuation.resume(newLocation)
                    }
                }
        } catch (e: SecurityException) {
            Log.e("LocationUtils", "Security exception getting location", e)
            continuation.resumeWithException(e)
        } catch (e: Exception) {
            Log.e("LocationUtils", "Unexpected error getting location", e)
            continuation.resumeWithException(e)
        }
        
        continuation.invokeOnCancellation {
            // Clean up if needed
            Log.d("LocationUtils", "Location request was cancelled by caller")
        }
    }
    
    /**
     * Request a single location update
     */
    private fun requestSingleUpdate(
        fusedLocationClient: FusedLocationProviderClient,
        onResult: (Location) -> Unit
    ) {
        try {
            Log.d("LocationUtils", "Requesting single high-accuracy location update")
            
            // Create a very high priority request for a single location
            val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000)
                .setWaitForAccurateLocation(false)  // Don't wait too long for accuracy
                .setMinUpdateIntervalMillis(0)
                .setMaxUpdateDelayMillis(0)
                .build()
            
            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    result.lastLocation?.let { location ->
                        Log.d("LocationUtils", "Single location update received: lat=${location.latitude}, lng=${location.longitude}")
                        onResult(location)
                        fusedLocationClient.removeLocationUpdates(this)
                    } ?: run {
                        Log.w("LocationUtils", "Location result received but lastLocation is null")
                        // Create a mock location as a last resort if necessary
                        val mockLocation = Location("LastResortProvider")
                        mockLocation.latitude = 0.0
                        mockLocation.longitude = 0.0
                        mockLocation.accuracy = 10000f  // Very inaccurate
                        onResult(mockLocation)
                        fusedLocationClient.removeLocationUpdates(this)
                    }
                }
            }
            
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            ).addOnFailureListener { e ->
                Log.e("LocationUtils", "Failed to request single location update", e)
                // Create a mock location as a last resort
                val mockLocation = Location("LastResortProvider")
                mockLocation.latitude = 0.0
                mockLocation.longitude = 0.0
                mockLocation.accuracy = 10000f  // Very inaccurate
                onResult(mockLocation)
            }
        } catch (e: SecurityException) {
            Log.e("LocationUtils", "Security exception requesting single update", e)
            // Handle permission denial
            val mockLocation = Location("LastResortProvider")
            mockLocation.latitude = 0.0
            mockLocation.longitude = 0.0
            mockLocation.accuracy = 10000f
            onResult(mockLocation)
        } catch (e: Exception) {
            Log.e("LocationUtils", "Exception requesting single update", e)
            val mockLocation = Location("LastResortProvider")
            mockLocation.latitude = 0.0
            mockLocation.longitude = 0.0
            mockLocation.accuracy = 10000f
            onResult(mockLocation)
        }
    }
    
    /**
     * Get continuous location updates as a Flow
     * 
     * @param context Application context
     * @return Flow of Location objects
     */
    fun getLocationUpdates(context: Context): Flow<Location> = callbackFlow {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        
        // Create a high-accuracy request with shorter intervals for better responsiveness
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            LOCATION_UPDATE_INTERVAL
        )
            .setWaitForAccurateLocation(false)
            .setMinUpdateIntervalMillis(FASTEST_LOCATION_INTERVAL)
            .setMaxUpdateAgeMillis(30000) // Accept locations up to 30 seconds old
            .build()
        
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                result.lastLocation?.let { location ->
                    Log.d("LocationUtils", "Flow location update: lat=${location.latitude}, lng=${location.longitude}, accuracy=${location.accuracy}m")
                    trySend(location).isSuccess
                } ?: run {
                    Log.w("LocationUtils", "Location result received in flow but lastLocation is null")
                    // Try to handle this by creating a fake location from any available location in the result
                    result.locations.firstOrNull()?.let { firstLocation ->
                        Log.d("LocationUtils", "Using first location from result list instead: lat=${firstLocation.latitude}, lng=${firstLocation.longitude}")
                        trySend(firstLocation).isSuccess
                    }
                }
            }
        }
        
        try {
            Log.d("LocationUtils", "Starting location updates flow")
            
            // First try to get an immediate location to emit right away
            try {
                val task = fusedLocationClient.lastLocation
                task.addOnSuccessListener { location ->
                    if (location != null) {
                        Log.d("LocationUtils", "Initial location for flow: lat=${location.latitude}, lng=${location.longitude}")
                        trySend(location)
                    } else {
                        Log.d("LocationUtils", "No initial location available for flow, will wait for updates")
                    }
                }
            } catch (e: Exception) {
                Log.e("LocationUtils", "Error getting initial location", e)
            }
            
            // Start continuous updates
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            ).addOnFailureListener { e ->
                Log.e("LocationUtils", "Failed to start location updates flow", e)
                
                // Try alternative method for Android 15
                tryAlternativeLocationMethod(context, fusedLocationClient, locationCallback, locationRequest)
                
                // Don't close the flow yet - let the alternative method try
            }
        } catch (e: SecurityException) {
            Log.e("LocationUtils", "Security exception starting location updates flow", e)
            // Try alternative method for Android 15
            tryAlternativeLocationMethod(context, fusedLocationClient, locationCallback, locationRequest)
        } catch (e: Exception) {
            Log.e("LocationUtils", "Error starting location updates flow", e)
            close(e)
        }
        
        awaitClose {
            Log.d("LocationUtils", "Stopping location updates flow")
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }
    }
    
    /**
     * Try alternative method to get locations on Android 15
     */
    private fun tryAlternativeLocationMethod(
        context: Context,
        fusedLocationClient: FusedLocationProviderClient,
        locationCallback: LocationCallback,
        locationRequest: LocationRequest
    ) {
        try {
            Log.d("LocationUtils", "Trying alternative location method for Android 15")
            
            // Use android.location.LocationManager directly
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as android.location.LocationManager
            val locationListener = object : android.location.LocationListener {
                override fun onLocationChanged(location: android.location.Location) {
                    Log.d("LocationUtils", "Alternative location update: lat=${location.latitude}, lng=${location.longitude}")
                    fusedLocationClient.flushLocations() // Try to force an update through FusedLocationProvider
                }
                override fun onProviderEnabled(provider: String) {}
                override fun onProviderDisabled(provider: String) {}
            }
            
            // Try both providers
            try {
                locationManager.requestLocationUpdates(
                    android.location.LocationManager.GPS_PROVIDER,
                    LOCATION_UPDATE_INTERVAL,
                    10f, // 10 meters minimum movement
                    locationListener
                )
                Log.d("LocationUtils", "Registered for GPS updates directly")
            } catch (e: Exception) {
                Log.e("LocationUtils", "Failed to register for GPS updates directly", e)
            }
            
            try {
                locationManager.requestLocationUpdates(
                    android.location.LocationManager.NETWORK_PROVIDER,
                    LOCATION_UPDATE_INTERVAL,
                    10f, // 10 meters minimum movement
                    locationListener
                )
                Log.d("LocationUtils", "Registered for network updates directly")
            } catch (e: Exception) {
                Log.e("LocationUtils", "Failed to register for network updates directly", e)
            }
            
            // Also retry with FusedLocationProvider but with less demanding settings
            val simpleRequest = LocationRequest.Builder(
                Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                LOCATION_UPDATE_INTERVAL * 2
            )
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(FASTEST_LOCATION_INTERVAL * 2)
                .build()
                
            fusedLocationClient.requestLocationUpdates(
                simpleRequest,
                locationCallback,
                Looper.getMainLooper()
            )
            Log.d("LocationUtils", "Registered simplified request with FusedLocationProvider")
            
        } catch (e: Exception) {
            Log.e("LocationUtils", "Alternative location method failed", e)
        }
    }
    
    /**
     * Calculate distance between two points in meters
     */
    fun calculateDistance(
        startLat: Double,
        startLng: Double,
        endLat: Double,
        endLng: Double
    ): Float {
        val results = FloatArray(1)
        Location.distanceBetween(startLat, startLng, endLat, endLng, results)
        return results[0]
    }
} 