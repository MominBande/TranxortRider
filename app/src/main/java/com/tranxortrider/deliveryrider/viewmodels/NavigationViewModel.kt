package com.tranxortrider.deliveryrider.viewmodels

import android.content.Context
import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tranxortrider.deliveryrider.api.DirectionsResponse
import com.tranxortrider.deliveryrider.utils.LocationUtils
import com.tranxortrider.deliveryrider.utils.OpenRouteServiceUtils
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * ViewModel for handling navigation logic
 */
class NavigationViewModel : ViewModel() {
    
    // Current location of the user
    private val _currentLocation = MutableLiveData<Location>()
    val currentLocation: LiveData<Location> = _currentLocation
    
    // Navigation instructions from the route
    private val _navigationInstructions = MutableLiveData<List<String>>(emptyList())
    val navigationInstructions: LiveData<List<String>> = _navigationInstructions
    
    // Current navigation instruction
    private val _currentInstruction = MutableLiveData<String>("")
    val currentInstruction: LiveData<String> = _currentInstruction
    
    // Distance to destination in kilometers
    private val _distanceToDestination = MutableLiveData<Double>(0.0)
    val distanceToDestination: LiveData<Double> = _distanceToDestination
    
    // Estimated time to destination in minutes
    private val _timeToDestination = MutableLiveData<Double>(0.0)
    val timeToDestination: LiveData<Double> = _timeToDestination
    
    // Loading state for routes
    private val _loadingRoute = MutableLiveData<Boolean>(false)
    val loadingRoute: LiveData<Boolean> = _loadingRoute
    
    // Error message
    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> = _errorMessage
    
    // Route response for drawing on the map
    private val _routeResponse = MutableLiveData<DirectionsResponse?>(null)
    val routeResponse: LiveData<DirectionsResponse?> = _routeResponse
    
    // Job for location updates
    private var locationUpdatesJob: Job? = null
    
    // Destination coordinates
    private var destinationLat: Double = 0.0
    private var destinationLng: Double = 0.0
    
    // Flag to recalculate route on first location update
    private var recalculateOnFirstLocation = false
    
    /**
     * Request recalculation of route when first location is received
     */
    fun requestRecalculateOnFirstLocation() {
        recalculateOnFirstLocation = true
    }
    
    /**
     * Start location updates
     * 
     * @param context Application context
     */
    fun startLocationUpdates(context: Context) {
        // Cancel any existing job
        locationUpdatesJob?.cancel()
        
        locationUpdatesJob = LocationUtils.getLocationUpdates(context)
            .onEach { location ->
                Log.d("NavigationViewModel", "Location update: lat=${location.latitude}, lng=${location.longitude}")
                
                val isFirstLocation = _currentLocation.value == null
                _currentLocation.value = location
                
                // Update navigation info if we have a route
                _routeResponse.value?.let { response ->
                    updateNavigationInfo(location, response)
                }
                
                // Auto-calculate route in these cases:
                // 1. If this is the first location and recalculateOnFirstLocation flag is set
                // 2. If we have a destination but no route yet (could happen if previous calculation failed)
                if ((isFirstLocation && recalculateOnFirstLocation) || 
                    (_routeResponse.value == null && destinationLat != 0.0 && destinationLng != 0.0 && !_loadingRoute.value!!)) {
                    Log.d("NavigationViewModel", "Auto-calculating route with location update lat=${location.latitude}, lng=${location.longitude}")
                    recalculateOnFirstLocation = false
                    calculateRoute(location.latitude, location.longitude, destinationLat, destinationLng)
                }
            }
            .catch { e ->
                Log.e("NavigationViewModel", "Location update error", e)
                _errorMessage.value = "Location update error: ${e.message}"
            }
            .launchIn(viewModelScope)
    }
    
    /**
     * Stop location updates
     */
    fun stopLocationUpdates() {
        locationUpdatesJob?.cancel()
        locationUpdatesJob = null
    }
    
    /**
     * Set destination and calculate route using OpenRouteService
     */
    fun setDestinationAndCalculateRoute(
        endLat: Double,
        endLng: Double,
        currentLocation: Location? = _currentLocation.value
    ) {
        // Save destination coordinates
        destinationLat = endLat
        destinationLng = endLng
        
        // Calculate route if we have current location
        if (currentLocation != null) {
            calculateRoute(currentLocation.latitude, currentLocation.longitude, endLat, endLng)
        } else {
            _errorMessage.value = "Current location not available. Unable to calculate route."
        }
    }
    
    /**
     * Calculate route using OpenRouteService
     */
    private fun calculateRoute(
        startLat: Double,
        startLng: Double,
        endLat: Double,
        endLng: Double
    ) {
        viewModelScope.launch {
            _loadingRoute.value = true
            Log.d("NavigationViewModel", "Calculating route from ($startLat, $startLng) to ($endLat, $endLng)")
            
            // Validate input coordinates
            if (startLat == 0.0 && startLng == 0.0) {
                Log.e("NavigationViewModel", "Invalid start coordinates (0,0)")
                _errorMessage.value = "Invalid start coordinates (0,0). Please ensure location services are enabled."
                _loadingRoute.value = false
                return@launch
            }
            
            if (endLat == 0.0 && endLng == 0.0) {
                Log.e("NavigationViewModel", "Invalid destination coordinates (0,0)")
                _errorMessage.value = "Invalid destination coordinates (0,0)."
                _loadingRoute.value = false
                return@launch
            }
            
            try {
                // First test connectivity to the API
                Log.d("NavigationViewModel", "Testing API connectivity")
                val isConnected = OpenRouteServiceUtils.testApiConnectivity()
                
                if (!isConnected) {
                    Log.e("NavigationViewModel", "API connectivity test failed")
                    _errorMessage.value = "Cannot connect to navigation service. Please check your internet connection."
                    _loadingRoute.value = false
                    return@launch
                }
                
                Log.d("NavigationViewModel", "API connectivity confirmed, calculating route")
                val result = OpenRouteServiceUtils.getDirections(
                    startLat, startLng,
                    endLat, endLng
                )
                
                if (result.isSuccess) {
                    val response = result.getOrNull()
                    Log.d("NavigationViewModel", "Route calculation successful, storing response: $response")
                    _routeResponse.value = response
                    
                    response?.let { directions ->
                        Log.d("NavigationViewModel", "Route calculated successfully")
                        // Update navigation information
                        val distance = OpenRouteServiceUtils.calculateDistance(directions)
                        val time = OpenRouteServiceUtils.calculateEta(directions)
                        Log.d("NavigationViewModel", "Route details: distance=$distance km, time=$time min")
                        
                        _distanceToDestination.value = distance
                        _timeToDestination.value = time
                        
                        val instructions = OpenRouteServiceUtils.getNavigationInstructions(directions)
                        Log.d("NavigationViewModel", "Got ${instructions.size} instructions")
                        _navigationInstructions.value = instructions
                        
                        // Update current instruction
                        _currentLocation.value?.let { location ->
                            val instruction = OpenRouteServiceUtils.getNextInstruction(directions, location)
                            Log.d("NavigationViewModel", "Current instruction: $instruction")
                            _currentInstruction.value = instruction
                        }
                        
                        // Clear any error message if route was calculated successfully
                        _errorMessage.value = null
                    }
                } else {
                    val error = result.exceptionOrNull()
                    val errorMsg = error?.message ?: "Unknown error"
                    Log.e("NavigationViewModel", "Failed to calculate route: $errorMsg", error)
                    _errorMessage.value = "Failed to calculate route: $errorMsg"
                    
                    // Provide more helpful message for common errors
                    if (errorMsg.contains("No valid route") || errorMsg.contains("Invalid coordinates")) {
                        _errorMessage.value = "Could not find a driving route between these locations. Please check the coordinates or try a different destination."
                    } else if (errorMsg.contains("API key") || errorMsg.contains("authentication")) {
                        _errorMessage.value = "Authentication error with the navigation service. Please try again later."
                    } else if (errorMsg.contains("timeout") || errorMsg.contains("timed out")) {
                        _errorMessage.value = "Connection timed out. Please check your internet connection and try again."
                    } else if (errorMsg.contains("rate limit") || errorMsg.contains("429")) {
                        _errorMessage.value = "Navigation service rate limit reached. Please try again in a few minutes."
                    }
                }
            } catch (e: Exception) {
                Log.e("NavigationViewModel", "Error calculating route", e)
                _errorMessage.value = "Error calculating route: ${e.message}"
            } finally {
                _loadingRoute.value = false
            }
        }
    }
    
    /**
     * Update navigation information based on current location and route
     */
    private fun updateNavigationInfo(location: Location, directionsResponse: DirectionsResponse) {
        // Calculate remaining distance using the more accurate route data
        val remainingDistance = OpenRouteServiceUtils.calculateDistance(directionsResponse)
        _distanceToDestination.value = remainingDistance
        
        // Update the current instruction
        _currentInstruction.value = OpenRouteServiceUtils.getNextInstruction(directionsResponse, location)
    }
    
    /**
     * Recalculate route with current location
     */
    fun recalculateRoute() {
        val currentLocation = _currentLocation.value
        
        if (currentLocation != null && destinationLat != 0.0 && destinationLng != 0.0) {
            Log.d("NavigationViewModel", "Recalculating route with current location")
            calculateRoute(currentLocation.latitude, currentLocation.longitude, destinationLat, destinationLng)
        } else {
            if (currentLocation == null) {
                Log.e("NavigationViewModel", "Cannot recalculate: Current location is null")
                _errorMessage.value = "Cannot recalculate: Current location unavailable. Please ensure location services are enabled."
            } else {
                Log.e("NavigationViewModel", "Cannot recalculate: No destination set")
                _errorMessage.value = "Cannot recalculate: No destination set"
            }
        }
    }
    
    /**
     * Clear navigation data
     */
    fun clearNavigationData() {
        _distanceToDestination.value = 0.0
        _timeToDestination.value = 0.0
        _navigationInstructions.value = emptyList()
        _currentInstruction.value = ""
        _routeResponse.value = null
        destinationLat = 0.0
        destinationLng = 0.0
    }
    
    override fun onCleared() {
        super.onCleared()
        stopLocationUpdates()
    }
} 