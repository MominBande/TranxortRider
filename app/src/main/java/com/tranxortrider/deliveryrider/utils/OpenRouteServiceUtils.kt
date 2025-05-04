package com.tranxortrider.deliveryrider.utils

import android.content.Context
import android.location.Location
import android.util.Log
import com.tranxortrider.deliveryrider.api.DirectionsRequest
import com.tranxortrider.deliveryrider.api.DirectionsResponse
import com.tranxortrider.deliveryrider.api.OpenRouteServiceApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.TimeUnit

/**
 * Utility class for OpenRouteService API operations
 */
object OpenRouteServiceUtils {
    
    // The provided API key
    private const val API_KEY = "5b3ce3597851110001cf6248ca8eef01f075b8fa16456c4894ef11eb75d1999dcaae2db3680e5ce4"
    
    // Base URL for OpenRouteService
    private const val BASE_URL = "https://api.openrouteservice.org/"
    
    // Initialize OkHttpClient
    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                
                // Android 15 needs clearer headers for network security
                val request = originalRequest.newBuilder()
                    .header("User-Agent", "TranxortRider-AndroidApp")
                    .header("Accept", "application/json")
                    .build()
                    
                Log.d("OpenRouteServiceUtils", "Sending request: ${request.url}")
                Log.d("OpenRouteServiceUtils", "Request headers: ${request.headers}")
                
                val response = chain.proceed(request)
                Log.d("OpenRouteServiceUtils", "Received response: ${response.code} for ${request.url}")
                if (!response.isSuccessful) {
                    Log.e("OpenRouteServiceUtils", "Error response: ${response.code} - ${response.message}")
                    try {
                        val errorBody = response.peekBody(Long.MAX_VALUE).string()
                        Log.e("OpenRouteServiceUtils", "Error body: $errorBody")
                    } catch (e: Exception) {
                        Log.e("OpenRouteServiceUtils", "Could not read error body", e)
                    }
                }
                
                response
            }
            .build()
    }
    
    // Initialize Retrofit
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    
    // Create API service
    private val apiService by lazy {
        retrofit.create(OpenRouteServiceApi::class.java)
    }
    
    /**
     * Test connectivity to the OpenRouteService API
     * 
     * @return true if the API is reachable, false otherwise
     */
    suspend fun testApiConnectivity(): Boolean = withContext(Dispatchers.IO) {
        try {
            Log.d("OpenRouteServiceUtils", "Testing API connectivity")
            
            // Try the simplest approach first - direct OkHttp call to the API's base URL
            try {
                val request = Request.Builder()
                    .url(BASE_URL)
                    .build()
                
                val response = okHttpClient.newCall(request).execute()
                val isSuccess = response.isSuccessful
                
                Log.d("OpenRouteServiceUtils", "OkHttp connectivity test result: $isSuccess (${response.code})")
                if (isSuccess) {
                    return@withContext true
                }
            } catch (e: Exception) {
                Log.e("OpenRouteServiceUtils", "OkHttp connectivity test failed", e)
                // Continue to try other methods
            }
            
            // Fallback to simpler HttpURLConnection
            try {
                val url = URL(BASE_URL)
                val connection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 10000
                connection.readTimeout = 10000
                connection.requestMethod = "GET"
                
                val responseCode = connection.responseCode
                val isSuccess = responseCode in 200..299
                
                Log.d("OpenRouteServiceUtils", "URLConnection connectivity test result: $isSuccess ($responseCode)")
                return@withContext isSuccess
            } catch (e: IOException) {
                Log.e("OpenRouteServiceUtils", "URLConnection connectivity test failed", e)
            }
            
            Log.e("OpenRouteServiceUtils", "All connectivity tests failed")
            return@withContext false
        } catch (e: Exception) {
            Log.e("OpenRouteServiceUtils", "Unexpected error in connectivity test", e)
            return@withContext false
        }
    }
    
    /**
     * Get directions between two points
     * 
     * @param startLat Starting point latitude
     * @param startLng Starting point longitude
     * @param endLat Destination latitude
     * @param endLng Destination longitude
     * @return DirectionsResponse with route information
     */
    suspend fun getDirections(
        startLat: Double,
        startLng: Double,
        endLat: Double,
        endLng: Double
    ): Result<DirectionsResponse> = withContext(Dispatchers.IO) {
        try {
            Log.d("OpenRouteServiceUtils", "Getting directions from ($startLat, $startLng) to ($endLat, $endLng)")
            
            // Validate input coordinates
            if (!isValidCoordinate(startLat, startLng)) {
                Log.e("OpenRouteServiceUtils", "Invalid start coordinates ($startLat, $startLng)")
                return@withContext Result.failure(IllegalArgumentException("Invalid start coordinates ($startLat, $startLng)"))
            }
            
            if (!isValidCoordinate(endLat, endLng)) {
                Log.e("OpenRouteServiceUtils", "Invalid end coordinates ($endLat, $endLng)")
                return@withContext Result.failure(IllegalArgumentException("Invalid end coordinates ($endLat, $endLng)"))
            }
            
            val coordinates = listOf(
                listOf(startLng, startLat), // Note: OpenRouteService uses [longitude, latitude] order
                listOf(endLng, endLat)
            )
            
            val request = DirectionsRequest(coordinates)
            Log.d("OpenRouteServiceUtils", "Sending request with coordinates: $coordinates")
            Log.d("OpenRouteServiceUtils", "API URL: ${BASE_URL}v2/directions/driving-car/json")
            
            // Try with Authorization header first (more reliable)
            try {
                Log.d("OpenRouteServiceUtils", "Trying API request with Authorization header")
                val response = apiService.getDirections("Bearer $API_KEY", request)
                
                Log.d("OpenRouteServiceUtils", "Response code: ${response.code()}")
                Log.d("OpenRouteServiceUtils", "Response message: ${response.message()}")
                
                if (response.isSuccessful && response.body() != null) {
                    val directionsResponse = response.body()!!
                    Log.d("OpenRouteServiceUtils", "Got successful response with ${directionsResponse.routes.size} routes")
                    
                    // Check if we have a valid route
                    val hasValidRoute = directionsResponse.routes.isNotEmpty()
                    
                    if (hasValidRoute) {
                        Log.d("OpenRouteServiceUtils", "Route has a valid path")
                        Log.d("OpenRouteServiceUtils", "Distance: ${directionsResponse.routes.first().summary.distance}m, Duration: ${directionsResponse.routes.first().summary.duration}s")
                        return@withContext Result.success(directionsResponse)
                    } else {
                        Log.e("OpenRouteServiceUtils", "Response successful but no valid route found")
                        return@withContext Result.failure(Exception("No valid route found in response"))
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    Log.e("OpenRouteServiceUtils", "Failed with authorization header: $errorBody, code: ${response.code()}")
                    
                    // Log specific errors but don't return yet - try the other method
                    if (response.code() == 401 || response.code() == 403) {
                        Log.e("OpenRouteServiceUtils", "API key authentication failed with authorization header")
                    } else if (response.code() == 429) {
                        Log.e("OpenRouteServiceUtils", "Rate limited by OpenRouteService API")
                    }
                }
            } catch (e: Exception) {
                Log.e("OpenRouteServiceUtils", "Exception using authorization header", e)
                // Don't return, try the next method
            }
            
            // Try with API key as query parameter next
            try {
                Log.d("OpenRouteServiceUtils", "Trying API request with query parameter")
                val response = apiService.getDirectionsWithApiKey(API_KEY, request)
                
                Log.d("OpenRouteServiceUtils", "Response code: ${response.code()}")
                Log.d("OpenRouteServiceUtils", "Response message: ${response.message()}")
                
                if (response.isSuccessful && response.body() != null) {
                    val directionsResponse = response.body()!!
                    Log.d("OpenRouteServiceUtils", "Got successful response with ${directionsResponse.routes.size} routes")
                    
                    // Check if we have a valid route
                    val hasValidRoute = directionsResponse.routes.isNotEmpty()
                    
                    if (hasValidRoute) {
                        Log.d("OpenRouteServiceUtils", "Route has a valid path")
                        Log.d("OpenRouteServiceUtils", "Distance: ${directionsResponse.routes.first().summary.distance}m, Duration: ${directionsResponse.routes.first().summary.duration}s")
                        return@withContext Result.success(directionsResponse)
                    } else {
                        Log.e("OpenRouteServiceUtils", "Response successful but no valid route found")
                        // Continue to fallback approach
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    Log.e("OpenRouteServiceUtils", "Failed with query parameter: $errorBody, code: ${response.code()}")
                    
                    // Log specific errors
                    if (response.code() == 401 || response.code() == 403) {
                        Log.e("OpenRouteServiceUtils", "API key authentication failed with query parameter")
                    } else if (response.code() == 429) {
                        Log.e("OpenRouteServiceUtils", "Rate limited by OpenRouteService API")
                    }
                }
            } catch (e: Exception) {
                Log.e("OpenRouteServiceUtils", "Exception using query parameter", e)
            }
            
            // Direct fallback using HttpURLConnection as a last resort
            try {
                Log.d("OpenRouteServiceUtils", "Trying direct HttpURLConnection fallback")
                val url = URL("${BASE_URL}v2/directions/driving-car/json")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.connectTimeout = 30000
                connection.readTimeout = 30000
                connection.doOutput = true
                connection.doInput = true
                connection.setRequestProperty("Content-Type", "application/json")
                connection.setRequestProperty("Authorization", "Bearer $API_KEY")
                connection.setRequestProperty("Accept", "application/json")
                connection.setRequestProperty("User-Agent", "TranxortRider-AndroidApp")
                
                // Create JSON request manually - removed format parameter
                val jsonBody = """
                    {"coordinates":${coordinates}}
                """.trimIndent()
                
                Log.d("OpenRouteServiceUtils", "Sending direct request with body: $jsonBody")
                
                val outputStream = connection.outputStream
                outputStream.write(jsonBody.toByteArray())
                outputStream.close()
                
                val responseCode = connection.responseCode
                Log.d("OpenRouteServiceUtils", "Direct connection response code: $responseCode")
                
                if (responseCode in 200..299) {
                    val inputStream = connection.inputStream
                    val response = inputStream.bufferedReader().use { it.readText() }
                    Log.d("OpenRouteServiceUtils", "Direct connection successful response")
                    
                    // Parse the response manually using Gson
                    val gson = com.google.gson.Gson()
                    val directionsResponse = gson.fromJson(response, DirectionsResponse::class.java)
                    
                    val hasValidRoute = directionsResponse.routes.isNotEmpty()
                    
                    if (hasValidRoute) {
                        Log.d("OpenRouteServiceUtils", "Direct connection route has a valid path")
                        return@withContext Result.success(directionsResponse)
                    }
                } else {
                    val errorStream = connection.errorStream
                    val errorResponse = errorStream?.bufferedReader()?.use { it.readText() } ?: "Unknown error"
                    Log.e("OpenRouteServiceUtils", "Direct connection failed: $errorResponse, code: $responseCode")
                }
            } catch (e: Exception) {
                Log.e("OpenRouteServiceUtils", "Exception with direct connection fallback", e)
            }
            
            // If we get here, all methods failed
            Log.e("OpenRouteServiceUtils", "All API request methods failed")
            return@withContext Result.failure(Exception("Could not calculate route: All API request methods failed"))
            
        } catch (e: Exception) {
            Log.e("OpenRouteServiceUtils", "Exception getting directions", e)
            return@withContext Result.failure(e)
        }
    }
    
    /**
     * Check if a coordinate is valid (within ranges and not 0,0)
     */
    private fun isValidCoordinate(lat: Double, lng: Double): Boolean {
        // Check if coordinates are in valid ranges
        val isInRange = lat >= -90.0 && lat <= 90.0 && lng >= -180.0 && lng <= 180.0
        
        // Check if coordinates are not exactly 0,0 (which is often a default value)
        val isNotOrigin = !(lat == 0.0 && lng == 0.0)
        
        return isInRange && isNotOrigin
    }
    
    /**
     * Calculate estimated time of arrival in minutes
     * 
     * @param directionsResponse The response from the directions API
     * @return Estimated time in minutes
     */
    fun calculateEta(directionsResponse: DirectionsResponse): Double {
        val durationInSeconds = directionsResponse.routes.firstOrNull()?.summary?.duration ?: 0.0
        return durationInSeconds / 60.0 // Convert to minutes
    }
    
    /**
     * Get the total distance of the route in kilometers
     * 
     * @param directionsResponse The response from the directions API
     * @return Distance in kilometers
     */
    fun calculateDistance(directionsResponse: DirectionsResponse): Double {
        val distanceInMeters = directionsResponse.routes.firstOrNull()?.summary?.distance ?: 0.0
        return distanceInMeters / 1000.0 // Convert to kilometers
    }
    
    /**
     * Get turn-by-turn navigation instructions
     * 
     * @param directionsResponse The response from the directions API
     * @return List of navigation instructions
     */
    fun getNavigationInstructions(directionsResponse: DirectionsResponse): List<String> {
        return directionsResponse.routes.firstOrNull()?.segments?.flatMap { segment ->
            segment.steps.map { step -> step.instruction }
        } ?: emptyList()
    }
    
    /**
     * Get the next navigation instruction based on current location
     * 
     * @param directionsResponse The response from the directions API
     * @param currentLocation Current user location
     * @return The next navigation instruction
     */
    fun getNextInstruction(directionsResponse: DirectionsResponse, currentLocation: Location): String {
        val instructions = getNavigationInstructions(directionsResponse)
        return instructions.firstOrNull() ?: "Proceed to destination"
    }
} 