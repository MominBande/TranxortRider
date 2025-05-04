package com.tranxortrider.deliveryrider.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tranxortrider.deliveryrider.EarningsActivity
import com.tranxortrider.deliveryrider.ProfileActivity
import com.tranxortrider.deliveryrider.R
import com.tranxortrider.deliveryrider.SettingsActivity
import com.tranxortrider.deliveryrider.home_screen
import com.tranxortrider.deliveryrider.pending_orders
import com.tranxortrider.deliveryrider.scanner
import com.tranxortrider.deliveryrider.models.Order

/**
 * Utility class to handle bottom navigation consistently across activities
 */
object NavigationUtils {
    
    /**
     * Set up the bottom navigation with proper item selection and click listeners
     */
    fun setupBottomNavigation(activity: Activity, bottomNavigationView: BottomNavigationView) {
        try {
            // Set the selected item based on the current activity
            val selectedItemId = when (activity) {
                is home_screen -> R.id.nav_home
                is pending_orders -> R.id.nav_orders
                is scanner -> R.id.nav_scanner
                is EarningsActivity -> R.id.nav_earnings
                is ProfileActivity -> R.id.nav_profile
                is SettingsActivity -> R.id.nav_profile
                else -> R.id.nav_home
            }
            
            // Set the selected item without triggering the listener
            bottomNavigationView.selectedItemId = selectedItemId
            
            // Set up item click listener
            bottomNavigationView.setOnItemSelectedListener { item ->
                // Don't navigate if we're already on the selected item
                if (item.itemId == bottomNavigationView.selectedItemId) {
                    return@setOnItemSelectedListener true
                }
                
                when (item.itemId) {
                    R.id.nav_home -> {
                        navigateTo(activity, home_screen::class.java)
                    }
                    R.id.nav_orders -> {
                        navigateTo(activity, pending_orders::class.java)
                    }
                    R.id.nav_scanner -> {
                        navigateTo(activity, scanner::class.java)
                    }
                    R.id.nav_earnings -> {
                        navigateTo(activity, EarningsActivity::class.java)
                    }
                    R.id.nav_profile -> {
                        // When navigating to Settings, include the source activity
                        val intent = Intent(activity, SettingsActivity::class.java)
                        intent.putExtra("source_activity", activity.javaClass.name)
                        ContextCompat.startActivity(activity, intent, null)
                        activity.finish()
                    }
                }
                true
            }
            
            // Make the bottom navigation visible
            bottomNavigationView.visibility = android.view.View.VISIBLE
            
        } catch (e: Exception) {
            Log.e("NavigationUtils", "Error setting up bottom navigation", e)
        }
    }
    
    /**
     * Navigate to a new activity and finish the current one
     */
    private fun navigateTo(currentActivity: Activity, targetActivityClass: Class<*>) {
        try {
            // Don't navigate if we're already on the target activity
            if (currentActivity.javaClass == targetActivityClass) {
                return
            }
            
            val intent = Intent(currentActivity, targetActivityClass)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            ContextCompat.startActivity(currentActivity, intent, null)
            
            // Finish the current activity to avoid stacking
            currentActivity.finish()
        } catch (e: Exception) {
            Log.e("NavigationUtils", "Error navigating to ${targetActivityClass.simpleName}", e)
        }
    }
    
    /**
     * Navigate to the restaurant or customer address for an order using any available map app
     * 
     * @param activity The activity to use for starting the intent
     * @param order The order to navigate to
     * @param isRestaurant If true, navigate to restaurant; if false, navigate to customer
     */
    fun navigateToOrderAddress(activity: Activity, order: Order, isRestaurant: Boolean) {
        val latitude: Double
        val longitude: Double
        val locationName: String
        
        if (isRestaurant) {
            // Navigate to restaurant
            latitude = order.restaurantLatitude ?: 0.0
            longitude = order.restaurantLongitude ?: 0.0
            locationName = order.restaurantName
        } else {
            // Navigate to customer
            latitude = order.customerLatitude ?: 0.0
            longitude = order.customerLongitude ?: 0.0
            locationName = order.customerName
        }
        
        // Skip if coordinates are invalid
        if (latitude == 0.0 && longitude == 0.0) {
            showNavigationError(activity, "Cannot navigate: no valid coordinates")
            return
        }
        
        // Try to navigate using any available map app
        try {
            // Create a generic geo URI that any map app can handle
            val uri = Uri.parse("geo:0,0?q=$latitude,$longitude($locationName)")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            
            // Check if there's an app that can handle this intent
            if (intent.resolveActivity(activity.packageManager) != null) {
                activity.startActivity(intent)
                UIUtils.showToast(activity, "Opening navigation to $locationName")
            } else {
                // Fallback to browser-based maps
                val mapUrl = "https://www.google.com/maps/search/?api=1&query=$latitude,$longitude"
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(mapUrl))
                activity.startActivity(browserIntent)
                UIUtils.showToast(activity, "Opening map in browser")
            }
        } catch (e: Exception) {
            Log.e("NavigationUtils", "Navigation error", e)
            showNavigationError(activity, "Navigation error: ${e.message}")
        }
    }
    
    /**
     * Show a navigation error message
     */
    private fun showNavigationError(context: Context, message: String) {
        UIUtils.showToast(context, message)
    }
    
    /**
     * Navigate to the restaurant for pickup and then to the customer for delivery
     * This attempts to create a multi-stop route using available map apps
     * 
     * @param activity The activity to use for starting the intent
     * @param order The order to navigate to
     */
    fun navigateWithMultipleStops(activity: Activity, order: Order) {
        val restaurantLat = order.restaurantLatitude ?: 0.0
        val restaurantLng = order.restaurantLongitude ?: 0.0
        val customerLat = order.customerLatitude ?: 0.0
        val customerLng = order.customerLongitude ?: 0.0
        
        // Skip if coordinates are invalid
        if ((restaurantLat == 0.0 && restaurantLng == 0.0) || 
            (customerLat == 0.0 && customerLng == 0.0)) {
            showNavigationError(activity, "Cannot navigate: no valid coordinates")
            return
        }
        
        try {
            // Try Google Maps first as it supports waypoints well
            val googleMapsUri = Uri.parse(
                "https://www.google.com/maps/dir/?api=1&destination=$customerLat,$customerLng&waypoints=$restaurantLat,$restaurantLng"
            )
            val googleMapsIntent = Intent(Intent.ACTION_VIEW, googleMapsUri)
            googleMapsIntent.setPackage("com.google.android.apps.maps")
            
            // Check if Google Maps is installed
            if (googleMapsIntent.resolveActivity(activity.packageManager) != null) {
                activity.startActivity(googleMapsIntent)
                UIUtils.showToast(activity, "Opening Google Maps with multi-stop navigation")
                return
            }
            
            // Fallback to browser-based Google Maps
            val browserIntent = Intent(Intent.ACTION_VIEW, googleMapsUri)
            if (browserIntent.resolveActivity(activity.packageManager) != null) {
                activity.startActivity(browserIntent)
                UIUtils.showToast(activity, "Opening multi-stop navigation in browser")
                return
            }
            
            // Last resort: just navigate to restaurant first
            UIUtils.showToast(activity, "Multi-stop navigation not available, navigating to restaurant")
            navigateToOrderAddress(activity, order, true)
            
        } catch (e: Exception) {
            Log.e("NavigationUtils", "Error launching navigation", e)
            showNavigationError(activity, "Navigation error: ${e.message}")
            
            // Fallback to simple navigation to restaurant first
            navigateToOrderAddress(activity, order, true)
        }
    }
} 