package com.tranxortrider.deliveryrider.api

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface OpenRouteServiceApi {
    
    @POST("v2/directions/driving-car/json")
    suspend fun getDirections(
        @Header("Authorization") apiKey: String,
        @Body requestBody: DirectionsRequest
    ): Response<DirectionsResponse>
    
    @POST("v2/directions/driving-car/json")
    suspend fun getDirectionsWithApiKey(
        @Query("api_key") apiKey: String,
        @Body requestBody: DirectionsRequest
    ): Response<DirectionsResponse>
    
    @GET("geocode/search")
    suspend fun geocodeSearch(
        @Header("Authorization") apiKey: String,
        @Query("text") searchText: String,
        @Query("size") size: Int = 5
    ): Response<GeocodingResponse>
}

data class DirectionsRequest(
    val coordinates: List<List<Double>>
)

data class DirectionsResponse(
    val bbox: List<Double>,
    val routes: List<Route>,
    val metadata: Metadata
) {
    data class Route(
        val summary: Summary,
        val segments: List<Segment>,
        val bbox: List<Double>,
        val geometry: String,
        @SerializedName("way_points") val wayPoints: List<Int>
    )
    
    data class Segment(
        val distance: Double,
        val duration: Double,
        val steps: List<Step>
    )
    
    data class Step(
        val distance: Double,
        val duration: Double,
        val type: Int,
        val instruction: String,
        val name: String,
        @SerializedName("way_points") val wayPoints: List<Int>,
        @SerializedName("exit_number") val exitNumber: Int? = null
    )
    
    data class Summary(
        val distance: Double,
        val duration: Double
    )
    
    data class Metadata(
        val attribution: String,
        val service: String,
        val timestamp: Long,
        val query: Query,
        val engine: Engine? = null
    )
    
    data class Query(
        val coordinates: List<List<Double>>,
        val profile: String,
        @SerializedName("profileName") val profileName: String? = null,
        val format: String
    )
    
    data class Engine(
        val version: String,
        @SerializedName("build_date") val buildDate: String,
        @SerializedName("graph_date") val graphDate: String
    )
}

data class GeocodingResponse(
    val geocoding: GeocodingMetadata,
    val type: String,
    val features: List<GeocodingFeature>,
    val bbox: List<Double>
) {
    data class GeocodingMetadata(
        val version: String,
        val attribution: String,
        val query: GeocodingQuery
    )
    
    data class GeocodingQuery(
        val text: String,
        val size: Int,
        val layers: List<String>?,
        @SerializedName("boundary.country") val boundaryCountry: String?
    )
    
    data class GeocodingFeature(
        val type: String,
        val geometry: GeocodingGeometry,
        val properties: GeocodingProperties
    )
    
    data class GeocodingGeometry(
        val type: String,
        val coordinates: List<Double>
    )
    
    data class GeocodingProperties(
        val id: String,
        val gid: String,
        val layer: String,
        val source: String,
        val name: String,
        val housenumber: String?,
        val street: String?,
        val postalcode: String?,
        val confidence: Double,
        val match_type: String,
        val accuracy: String?,
        val country: String,
        val country_a: String?,
        val region: String?,
        val region_a: String?,
        val county: String?,
        val locality: String?,
        val label: String
    )
} 