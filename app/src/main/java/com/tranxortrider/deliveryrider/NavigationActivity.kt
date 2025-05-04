package com.tranxortrider.deliveryrider

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.tranxortrider.deliveryrider.api.DirectionsResponse
import com.tranxortrider.deliveryrider.viewmodels.NavigationViewModel
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import java.text.DecimalFormat
import kotlin.math.roundToInt

/**
 * Navigation activity using OpenRouteService and OSMDroid
 */
class NavigationActivity : AppCompatActivity() {
    
    // UI Components
    private lateinit var backButton: MaterialButton
    private lateinit var destinationPanel: CardView
    private lateinit var destinationTitle: TextView
    private lateinit var destinationAddress: TextView
    private lateinit var timeRemainingValue: TextView
    private lateinit var distanceValue: TextView
    private lateinit var instructionIcon: ImageView
    private lateinit var instructionText: TextView
    private lateinit var btnCall: Button
    private lateinit var btnArrived: Button
    private lateinit var loadingOverlay: FrameLayout
    private lateinit var mapView: MapView
    private lateinit var btnRecalculate: FloatingActionButton
    
    // Navigation data
    private var destinationLat: Double = 0.0
    private var destinationLng: Double = 0.0
    private var destinationName: String = ""
    private var destinationAddressText: String = ""
    private var customerPhone: String = ""
    private var restaurantPhone: String = ""
    private var orderStatus: String = ""
    private var zoomLevelDouble: Double = 15.0  // Default zoom level
    
    // ViewModel
    private val viewModel: NavigationViewModel by viewModels()
    
    // Map overlays
    private var routeOverlay: Polyline? = null
    private var destinationMarker: Marker? = null
    private var userLocationMarker: Marker? = null
    
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1001
        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        Log.d("NavigationActivity", "onCreate started")
        
        try {
            // Configure OSMDroid - wrap this in try-catch as it's a common source of errors
            try {
                Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))
                Log.d("NavigationActivity", "OSMDroid configuration loaded")
            } catch (e: Exception) {
                Log.e("NavigationActivity", "Error configuring OSMDroid", e)
                // Continue anyway, we'll handle this later
            }
            
            setContentView(R.layout.activity_navigation)
            Log.d("NavigationActivity", "Content view set")
            
            // Log intent info
            if (intent == null) {
                Log.e("NavigationActivity", "Intent is null")
                showErrorAndExit("No navigation data provided - intent is null")
                return
            }
            
            // Get navigation data from intent
            Log.d("NavigationActivity", "Extracting extras from intent")
            Log.d("NavigationActivity", "Intent action: ${intent.action}, data: ${intent.data}")
            Log.d("NavigationActivity", "Has extras: ${intent.extras != null}")
            
            intent.extras?.let {
                Log.d("NavigationActivity", "Intent extras found: ${it.keySet().joinToString()}")
                
                destinationLat = it.getDouble("destination_lat", 0.0)
                destinationLng = it.getDouble("destination_lng", 0.0)
                destinationName = it.getString("destination_name", "Destination")
                destinationAddressText = it.getString("destination_address", "")
                customerPhone = it.getString("customer_phone", "")
                restaurantPhone = it.getString("restaurant_phone", "")
                orderStatus = it.getString("order_status", "")
                
                Log.d("NavigationActivity", "Received coordinates: lat=$destinationLat, lng=$destinationLng")
                Log.d("NavigationActivity", "Destination info: name=$destinationName, address=$destinationAddressText")
                Log.d("NavigationActivity", "Order status: $orderStatus")
                
                // Debug all extras
                for (key in it.keySet()) {
                    Log.d("NavigationActivity", "Extra: $key = ${it.get(key)}")
                }
            } ?: run {
                Log.e("NavigationActivity", "No extras found in intent")
                showErrorAndExit("No navigation data provided")
                return
            }
            
            // Validate coordinates
            if (destinationLat < -90.0 || destinationLat > 90.0 || 
                destinationLng < -180.0 || destinationLng > 180.0) {
                Log.e("NavigationActivity", "Invalid coordinate range: lat=$destinationLat, lng=$destinationLng")
                showErrorAndExit("Invalid coordinate range")
                return
            }
            
            if (destinationLat == 0.0 && destinationLng == 0.0) {
                Log.e("NavigationActivity", "Invalid destination coordinates (0,0)")
                showErrorAndExit("Invalid destination coordinates")
                return
            }
            
            Log.d("NavigationActivity", "Coordinates validated successfully")
            
            // Initialize UI
            initViews()
            setupListeners()
            setupObservers()
            
            // Check permissions
            if (hasRequiredPermissions()) {
                Log.d("NavigationActivity", "Required permissions granted, initializing map")
                initializeMap()
            } else {
                Log.d("NavigationActivity", "Requesting permissions")
                requestPermissions()
            }
        } catch (e: Exception) {
            Log.e("NavigationActivity", "Error initializing navigation", e)
            showErrorAndExit("Error initializing navigation: ${e.message}")
        }
    }
    
    private fun initViews() {
        backButton = findViewById(R.id.btnBack)
        destinationPanel = findViewById(R.id.destinationPanel)
        destinationTitle = findViewById(R.id.destinationTitle)
        destinationAddress = findViewById(R.id.destinationAddress)
        timeRemainingValue = findViewById(R.id.timeRemainingValue)
        distanceValue = findViewById(R.id.distanceValue)
        instructionIcon = findViewById(R.id.instructionIcon)
        instructionText = findViewById(R.id.instructionText)
        btnCall = findViewById(R.id.btnCall)
        btnArrived = findViewById(R.id.btnArrived)
        loadingOverlay = findViewById(R.id.loadingOverlay)
        mapView = findViewById(R.id.mapView)
        btnRecalculate = findViewById(R.id.btnRecalculate)
        
        // Set destination information
        destinationTitle.text = destinationName
        destinationAddress.text = destinationAddressText
        
        // Show loading overlay initially
        loadingOverlay.visibility = View.VISIBLE
        
        // Set call button text to just "Call"
        btnCall.text = "Call"
        
        // Ensure buttons have same width
        btnCall.layoutParams.width = 0
        btnArrived.layoutParams.width = 0
        (btnCall.layoutParams as LinearLayout.LayoutParams).weight = 1f
        (btnArrived.layoutParams as LinearLayout.LayoutParams).weight = 1f
    }
    
    private fun setupListeners() {
        // Back button
        backButton.setOnClickListener {
            onBackPressed()
        }
        
        // Call button - handles both restaurant and customer calls based on order status
        btnCall.setOnClickListener {
            // Determine which phone number to call based on order status
            val phoneNumber = when {
                // If going to restaurant (ACCEPTED or ASSIGNED status), call restaurant
                orderStatus.equals("ACCEPTED", ignoreCase = true) || 
                orderStatus.equals("ASSIGNED", ignoreCase = true) -> {
                    Log.d("NavigationActivity", "Calling restaurant: $restaurantPhone")
                    restaurantPhone
                }
                // If going to customer (PICKED_UP status), call customer
                orderStatus.equals("PICKED_UP", ignoreCase = true) -> {
                    Log.d("NavigationActivity", "Calling customer: $customerPhone")
                    customerPhone
                }
                // Default to customer phone
                else -> {
                    Log.d("NavigationActivity", "Default to calling customer: $customerPhone")
                    customerPhone
                }
            }
            
            if (phoneNumber.isNotEmpty()) {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:$phoneNumber")
                startActivity(intent)
            } else {
                // Determine the error message based on which number we were trying to call
                val errorMsg = when {
                    orderStatus.equals("ACCEPTED", ignoreCase = true) || 
                    orderStatus.equals("ASSIGNED", ignoreCase = true) -> 
                        "Restaurant phone number not available"
                    else -> 
                        "Customer phone number not available"
                }
                
                Snackbar.make(
                    findViewById(android.R.id.content),
                    errorMsg,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
        
        // Arrived button
        btnArrived.setOnClickListener {
            // Show confirmation that user has arrived
            Snackbar.make(
                findViewById(android.R.id.content),
                "You have arrived at the destination",
                Snackbar.LENGTH_SHORT
            ).show()
            
            // Go back to the previous screen after a short delay
            Handler(Looper.getMainLooper()).postDelayed({
                finish()
            }, 1000)
        }
        
        // Recalculate route button
        btnRecalculate.setOnClickListener {
            viewModel.recalculateRoute()
        }
        
        // Center on current location button
        findViewById<FloatingActionButton>(R.id.btnCenterLocation).setOnClickListener {
            viewModel.currentLocation.value?.let { location ->
                val userGeoPoint = GeoPoint(location.latitude, location.longitude)
                mapView.controller.animateTo(userGeoPoint)
                mapView.controller.setZoom(18.0)
            }
        }
    }
    
    private fun setupObservers() {
        // Observe loading state
        viewModel.loadingRoute.observe(this, Observer { isLoading ->
            loadingOverlay.visibility = if (isLoading) View.VISIBLE else View.GONE
        })
        
        // Observe error messages
        viewModel.errorMessage.observe(this, Observer { message ->
            message?.let {
                Snackbar.make(findViewById(android.R.id.content), it, Snackbar.LENGTH_LONG).show()
            }
        })
        
        // Observe distance to destination
        viewModel.distanceToDestination.observe(this, Observer { distance ->
            val formattedDistance = when {
                distance < 1.0 -> "${(distance * 1000).roundToInt()} m"
                else -> "%.1f km".format(distance)
            }
            distanceValue.text = formattedDistance
        })
        
        // Observe time to destination
        viewModel.timeToDestination.observe(this, Observer { time ->
            val hours = time.toInt() / 60
            val minutes = time.toInt() % 60
            
            timeRemainingValue.text = when {
                hours > 0 -> "$hours h $minutes min"
                else -> "$minutes min"
            }
        })
        
        // Observe current instruction
        viewModel.currentInstruction.observe(this, Observer { instruction ->
            instructionText.text = instruction
        })
        
        // Observe current location
        viewModel.currentLocation.observe(this, Observer { location ->
            updateUserLocationOnMap(location.latitude, location.longitude)
        })
        
        // Observe route response for drawing the route
        viewModel.routeResponse.observe(this, Observer { response ->
            response?.let { drawRoute(it) }
        })
    }
    
    private fun initializeMap() {
        try {
            Log.d("NavigationActivity", "Initializing map")
            
            // Force proceed with initialization even if permission check fails
            // This is a workaround for Android 15 where permission checks might fail
            // even when permissions are actually granted
            
            // Configure map
            mapView.setTileSource(TileSourceFactory.MAPNIK)
            mapView.setMultiTouchControls(true)
            mapView.controller.setZoom(zoomLevelDouble)
            
            // Add destination marker directly
            addDestinationMarker()
            
            // Check if location services are enabled
            val locationManager = getSystemService(LOCATION_SERVICE) as android.location.LocationManager
            val isGpsEnabled = locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)
            val isNetworkEnabled = locationManager.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER)
            
            if (!isGpsEnabled && !isNetworkEnabled) {
                Log.e("NavigationActivity", "Location services are disabled")
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Location services are disabled. Please enable GPS or network location.",
                    Snackbar.LENGTH_LONG
                ).setAction("Settings") {
                    // Open location settings
                    val intent = Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(intent)
                }.show()
                // Continue anyway - don't return early
            }
            
            try {
                // Start observing location updates
                viewModel.startLocationUpdates(this)
                
                // Get initial location - attempt to use last known location
                val initialLocation = getInitialLocation()
                if (initialLocation != null) {
                    Log.d("NavigationActivity", "Got initial location: ${initialLocation.latitude}, ${initialLocation.longitude}")
                    
                    // Update user marker with initial location
                    updateUserLocationOnMap(initialLocation.latitude, initialLocation.longitude)
                    
                    // Calculate route immediately with initial location
                    viewModel.setDestinationAndCalculateRoute(destinationLat, destinationLng, initialLocation)
                } else {
                    Log.d("NavigationActivity", "No initial location available, will recalculate when location is available")
                    // If no initial location, tell the ViewModel to calculate route when first location is received
                    viewModel.requestRecalculateOnFirstLocation()
                    viewModel.setDestinationAndCalculateRoute(destinationLat, destinationLng)
                }
            } catch (e: SecurityException) {
                Log.e("NavigationActivity", "Security exception starting location updates", e)
                // Only request permissions if we get an actual security exception
                requestPermissions()
            }
            
            // Set a timeout to show external nav option if route calculation fails
            Handler(Looper.getMainLooper()).postDelayed({
                if (viewModel.routeResponse.value == null) {
                    Log.w("NavigationActivity", "No route response after 20 seconds")
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "Unable to calculate route using in-app navigation",
                        Snackbar.LENGTH_LONG
                    ).setAction("Try External Maps") {
                        showAlternativeNavigationOptions()
                    }.show()
                }
            }, 20000) // Increase timeout to 20 seconds to allow for OpenRouteService API to respond
            
        } catch (e: Exception) {
            Log.e("NavigationActivity", "Error initializing map", e)
            
            // Show error without immediately falling back to external navigation
            try {
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Error initializing map: ${e.message}",
                    Snackbar.LENGTH_LONG
                ).show()
            } catch (e2: Exception) {
                Log.e("NavigationActivity", "Error showing snackbar", e2)
            }
        }
    }
    
    /**
     * Try to get an initial location before receiving location updates
     */
    private fun getInitialLocation(): Location? {
        try {
            val locationManager = getSystemService(LOCATION_SERVICE) as android.location.LocationManager
            
            // Try GPS provider first
            var location = locationManager.getLastKnownLocation(android.location.LocationManager.GPS_PROVIDER)
            
            // If GPS location not available, try network provider
            if (location == null) {
                location = locationManager.getLastKnownLocation(android.location.LocationManager.NETWORK_PROVIDER)
            }
            
            // If location is too old (more than 2 minutes), don't use it
            if (location != null && System.currentTimeMillis() - location.time > 2 * 60 * 1000) {
                Log.d("NavigationActivity", "Last known location is too old, ignoring")
                return null
            }
            
            return location
        } catch (e: SecurityException) {
            Log.e("NavigationActivity", "Security exception getting initial location", e)
            return null
        } catch (e: Exception) {
            Log.e("NavigationActivity", "Error getting initial location", e)
            return null
        }
    }
    
    private fun addDestinationMarker() {
        val destinationPoint = GeoPoint(destinationLat, destinationLng)
        
        destinationMarker = Marker(mapView).apply {
            position = destinationPoint
            title = destinationName
            snippet = destinationAddressText
            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            icon = ContextCompat.getDrawable(this@NavigationActivity, R.drawable.ic_destination_marker)
        }
        
        mapView.overlays.add(destinationMarker)
        mapView.controller.animateTo(destinationPoint)
    }
    
    private fun updateUserLocationOnMap(latitude: Double, longitude: Double) {
        val userLocation = GeoPoint(latitude, longitude)
        
        // Create or update user location marker
        if (userLocationMarker == null) {
            userLocationMarker = Marker(mapView).apply {
                position = userLocation
                title = "Your Location"
                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
                icon = ContextCompat.getDrawable(this@NavigationActivity, R.drawable.ic_user_location)
            }
            mapView.overlays.add(userLocationMarker)
        } else {
            userLocationMarker?.position = userLocation
        }
        
        // Center map on user's location if it's the first update
        if (mapView.zoomLevelDouble < 10) {
            mapView.controller.animateTo(userLocation)
            mapView.controller.setZoom(zoomLevelDouble)
        }
        
        mapView.invalidate()
    }
    
    private fun drawRoute(directionsResponse: DirectionsResponse) {
        // Remove previous route if exists
        routeOverlay?.let {
            mapView.overlays.remove(it)
            Log.d("NavigationActivity", "Removed previous route overlay")
        }
        
        // Get the route geometry
        val route = directionsResponse.routes.firstOrNull()
        
        if (route != null) {
            Log.d("NavigationActivity", "Found route with summary: ${route.summary.distance}m, ${route.summary.duration}s")
            val routePoints = ArrayList<GeoPoint>()
            
            try {
                // The geometry is now an encoded polyline string that needs to be decoded
                val decodedPoints = decodePolyline(route.geometry)
                Log.d("NavigationActivity", "Decoded ${decodedPoints.size} points from polyline")
                
                routePoints.addAll(decodedPoints)
                
                Log.d("NavigationActivity", "Created ${routePoints.size} route points")
                
                // Create the route overlay
                routeOverlay = Polyline().apply {
                    outlinePaint.color = ContextCompat.getColor(this@NavigationActivity, R.color.route_color)
                    outlinePaint.strokeWidth = 10f
                    setPoints(routePoints)
                }
                
                // Add the route to the map
                mapView.overlays.add(routeOverlay)
                
                // Force redraw
                mapView.invalidate()
                Log.d("NavigationActivity", "Added route overlay to map and invalidated view")
                
                // Get the bounding box of the route
                val boundingBox = routeOverlay?.bounds
                
                // Always zoom to fit the entire route when it's first drawn
                boundingBox?.let {
                    // Add padding to the bounding box for better visibility
                    val padding = 100 // pixels of padding around the route
                    mapView.zoomToBoundingBox(it, true, padding)
                    Log.d("NavigationActivity", "Zoomed to route bounding box")
                }
            } catch (e: Exception) {
                Log.e("NavigationActivity", "Error decoding route geometry", e)
            }
        } else {
            Log.e("NavigationActivity", "No routes found in response")
        }
    }
    
    /**
     * Decodes a polyline string into a list of GeoPoints
     */
    private fun decodePolyline(encodedPolyline: String): List<GeoPoint> {
        val poly = ArrayList<GeoPoint>()
        var index = 0
        val len = encodedPolyline.length
        var lat = 0
        var lng = 0
        
        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            
            // Decode latitude
            do {
                b = encodedPolyline[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat
            
            // Decode longitude
            shift = 0
            result = 0
            do {
                b = encodedPolyline[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng
            
            // Convert to GeoPoint
            // Note: The API returns coordinates in the order [longitude, latitude]
            // but for GeoPoint we need [latitude, longitude]
            val point = GeoPoint(lat.toDouble() / 1E5, lng.toDouble() / 1E5)
            poly.add(point)
        }
        
        return poly
    }
    
    private fun hasRequiredPermissions(): Boolean {
        // For Android 15, we've seen cases where the permission check fails
        // even when permissions are actually granted. Let's add a fallback check.
        try {
            val locationManager = getSystemService(LOCATION_SERVICE) as android.location.LocationManager
            val isLocationEnabled = locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER) ||
                                  locationManager.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER)
            
            // Get a last known location as a test
            val location = locationManager.getLastKnownLocation(android.location.LocationManager.GPS_PROVIDER)
            
            // If we can get location info, then we effectively have permission regardless of what checkSelfPermission says
            if (isLocationEnabled && location != null) {
                Log.d("NavigationActivity", "Location available, considering permissions granted despite system report")
                return true
            }
        } catch (e: SecurityException) {
            // If we get a security exception here, we truly don't have permission
            Log.e("NavigationActivity", "Security exception checking location", e)
        } catch (e: Exception) {
            // Other exceptions don't necessarily mean we don't have permission
            Log.e("NavigationActivity", "Error checking location availability", e)
        }
        
        // Fall back to the standard permission check
        val permissionsGranted = REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }
        
        Log.d("NavigationActivity", "Standard permission check result = $permissionsGranted")
        return permissionsGranted
    }
    
    private fun requestPermissions() {
        Log.d("NavigationActivity", "Requesting permissions: ${REQUIRED_PERMISSIONS.joinToString()}")
        
        // Always directly request permissions first to avoid extra steps
        ActivityCompat.requestPermissions(
            this,
            REQUIRED_PERMISSIONS,
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }
    
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            val allGranted = grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }
            Log.d("NavigationActivity", "Permission results received, all granted = $allGranted")
            
            if (allGranted) {
                Log.d("NavigationActivity", "All permissions granted, initializing map")
                // Give a short delay to ensure permissions are fully registered in the system
                Handler(Looper.getMainLooper()).postDelayed({
                    initializeMap()
                }, 500)
            } else {
                Log.d("NavigationActivity", "Some permissions denied, showing message")
                
                // Check if we can show rationale for any of the denied permissions
                val shouldShowRationale = REQUIRED_PERMISSIONS.any {
                    ActivityCompat.shouldShowRequestPermissionRationale(this, it)
                }
                
                if (shouldShowRationale) {
                    // User has denied but not checked "Don't ask again"
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "Navigation requires location permission to show your route",
                        Snackbar.LENGTH_INDEFINITE
                    ).setAction("Grant") {
                        requestPermissions()
                    }.show()
                } else {
                    // User has denied and checked "Don't ask again" or permissions cannot be requested
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "Location permissions denied. Navigation features will be limited.",
                        Snackbar.LENGTH_INDEFINITE
                    ).setAction("Settings") {
                        // Open app settings
                        val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        intent.data = Uri.fromParts("package", packageName, null)
                        startActivity(intent)
                    }.show()
                    
                    // Also show option for external navigation
                    Handler(Looper.getMainLooper()).postDelayed({
                        Snackbar.make(
                            findViewById(android.R.id.content),
                            "Would you like to use external navigation instead?",
                            Snackbar.LENGTH_LONG
                        ).setAction("Yes") {
                            showAlternativeNavigationOptions()
                        }.show()
                    }, 3000)
                }
            }
        }
    }
    
    /**
     * Shows alternative navigation options using external apps
     * Only called when permissions are denied or explicitly requested
     */
    private fun showAlternativeNavigationOptions() {
        try {
            Log.d("NavigationActivity", "User explicitly requested alternative navigation")
            
            if (destinationLat != 0.0 && destinationLng != 0.0) {
                Log.d("NavigationActivity", "Attempting to launch external navigation app")
                
                // Try Google Maps first
                try {
                    val googleMapsUri = Uri.parse("google.navigation:q=$destinationLat,$destinationLng")
                    val googleMapsIntent = Intent(Intent.ACTION_VIEW, googleMapsUri)
                    googleMapsIntent.setPackage("com.google.android.apps.maps")
                    
                    if (googleMapsIntent.resolveActivity(packageManager) != null) {
                        Log.d("NavigationActivity", "Launching Google Maps")
                        startActivity(googleMapsIntent)
                        return
                    }
                } catch (e: Exception) {
                    Log.e("NavigationActivity", "Error launching Google Maps", e)
                }
                
                // Web fallback as last resort
                try {
                    val mapUrl = "https://www.google.com/maps/dir/?api=1&destination=$destinationLat,$destinationLng"
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(mapUrl))
                    
                    Log.d("NavigationActivity", "Launching web browser for navigation")
                    startActivity(browserIntent)
                    return
                } catch (e: Exception) {
                    Log.e("NavigationActivity", "Error launching browser for navigation", e)
                }
            }
        } catch (e: Exception) {
            Log.e("NavigationActivity", "Error in showAlternativeNavigationOptions", e)
        }
    }
    
    private fun showErrorAndExit(message: String) {
        try {
            Log.e("NavigationActivity", "Error encountered: $message")
            
            // Don't immediately exit - try to display the message and continue
            Snackbar.make(
                findViewById(android.R.id.content),
                message,
                Snackbar.LENGTH_LONG
            ).show()
            
            // Try to initialize again if possible
            if (hasRequiredPermissions()) {
                Log.d("NavigationActivity", "Attempting to recover from error...")
                Handler(Looper.getMainLooper()).postDelayed({
                    try {
                        initializeMap()
                    } catch (e: Exception) {
                        Log.e("NavigationActivity", "Recovery attempt failed", e)
                        // Only now offer external navigation as a fallback, giving our app time to recover
                        Snackbar.make(
                            findViewById(android.R.id.content),
                            "Would you like to use external navigation instead?",
                            Snackbar.LENGTH_LONG
                        ).setAction("Yes") {
                            showAlternativeNavigationOptions()
                        }.show()
                    }
                }, 3000) // Give 3 seconds to recover
            }
        } catch (e: Exception) {
            Log.e("NavigationActivity", "Error showing error message", e)
        }
    }
    
    override fun onStart() {
        super.onStart()
        Log.d("NavigationActivity", "onStart called")
    }
    
    override fun onResume() {
        super.onResume()
        Log.d("NavigationActivity", "onResume called")
        viewModel.startLocationUpdates(this)
    }
    
    override fun onPause() {
        super.onPause()
        Log.d("NavigationActivity", "onPause called")
        viewModel.stopLocationUpdates()
    }
    
    override fun onStop() {
        super.onStop()
        Log.d("NavigationActivity", "onStop called")
    }
    
    override fun onDestroy() {
        Log.d("NavigationActivity", "onDestroy called")
        super.onDestroy()
        mapView.onDetach()
    }
} 