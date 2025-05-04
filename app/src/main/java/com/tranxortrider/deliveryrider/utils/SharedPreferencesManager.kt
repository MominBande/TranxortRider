package com.tranxortrider.deliveryrider.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class SharedPreferencesManager(private val context: Context) {
    
    companion object {
        private const val PREFS_NAME = "tranxort_rider_prefs"
        private const val LAST_LATITUDE = "last_latitude"
        private const val LAST_LONGITUDE = "last_longitude"
        private const val LAST_LOCATION_TIMESTAMP = "last_location_timestamp"
        private const val LOCATION_SYNC_QUEUE = "location_sync_queue"
        private const val CACHED_ORDERS = "cached_orders"
        private const val CACHED_USER_PROFILE = "cached_user_profile"
        private const val THEME_PREFERENCE = "theme_preference"
        private const val NOTIFICATION_PREFERENCE = "notification_preference"
        private const val BACKGROUND_LOCATION_PREFERENCE = "background_location_preference"
        private const val LAST_SYNC_TIMESTAMP = "last_sync_timestamp"
        private const val APP_VERSION = "app_version"
        private const val PENDING_LOCATION_SERVICE_START = "pending_location_service_start"
        private const val IS_IN_DELIVERY_AREA = "is_in_delivery_area"
        private const val CURRENT_DELIVERY_AREA_ID = "current_delivery_area_id"
        private const val CURRENT_DELIVERY_AREA_NAME = "current_delivery_area_name"
    }
    
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()
    
    // Location caching
    
    fun saveLastLocation(latitude: Double, longitude: Double) {
        prefs.edit()
            .putString(LAST_LATITUDE, latitude.toString())
            .putString(LAST_LONGITUDE, longitude.toString())
            .apply()
    }
    
    fun getLastLocation(): Pair<Double, Double>? {
        val lat = prefs.getString(LAST_LATITUDE, null)
        val lng = prefs.getString(LAST_LONGITUDE, null)
        
        return if (lat != null && lng != null) {
            Pair(lat.toDouble(), lng.toDouble())
        } else {
            null
        }
    }
    
    fun saveLastLocationTimestamp(timestamp: Long) {
        prefs.edit().putLong(LAST_LOCATION_TIMESTAMP, timestamp).apply()
    }
    
    fun getLastLocationTimestamp(): Long {
        return prefs.getLong(LAST_LOCATION_TIMESTAMP, 0L)
    }
    
    fun addLocationToSyncQueue(latitude: Double, longitude: Double, timestamp: Long) {
        val queue = getLocationSyncQueue().toMutableList()
        queue.add(LocationData(latitude, longitude, timestamp))
        
        val json = gson.toJson(queue)
        prefs.edit().putString(LOCATION_SYNC_QUEUE, json).apply()
    }
    
    fun getLocationSyncQueue(): List<LocationData> {
        val json = prefs.getString(LOCATION_SYNC_QUEUE, null) ?: return emptyList()
        val type: Type = object : TypeToken<List<LocationData>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }
    
    fun clearLocationSyncQueue() {
        prefs.edit().remove(LOCATION_SYNC_QUEUE).apply()
    }
    
    // Order caching
    
    fun cacheOrders(ordersJson: String) {
        prefs.edit().putString(CACHED_ORDERS, ordersJson).apply()
        saveLastSyncTimestamp()
    }
    
    fun getCachedOrders(): String? {
        return prefs.getString(CACHED_ORDERS, null)
    }
    
    // User profile caching
    
    fun cacheUserProfile(profileJson: String) {
        prefs.edit().putString(CACHED_USER_PROFILE, profileJson).apply()
    }
    
    fun getCachedUserProfile(): String? {
        return prefs.getString(CACHED_USER_PROFILE, null)
    }
    
    // Sync tracking
    
    fun saveLastSyncTimestamp() {
        prefs.edit().putLong(LAST_SYNC_TIMESTAMP, System.currentTimeMillis()).apply()
    }
    
    fun getLastSyncTimestamp(): Long {
        return prefs.getLong(LAST_SYNC_TIMESTAMP, 0L)
    }
    
    fun isSyncNeeded(maxAgeMs: Long = 5 * 60 * 1000): Boolean { // Default 5 minutes
        val lastSync = getLastSyncTimestamp()
        val currentTime = System.currentTimeMillis()
        return (currentTime - lastSync) > maxAgeMs
    }
    
    // App settings
    
    fun saveThemePreference(darkMode: Boolean) {
        prefs.edit().putBoolean(THEME_PREFERENCE, darkMode).apply()
    }
    
    fun getThemePreference(): Boolean {
        return prefs.getBoolean(THEME_PREFERENCE, false) // Default to light mode
    }
    
    fun saveNotificationPreference(enabled: Boolean) {
        prefs.edit().putBoolean(NOTIFICATION_PREFERENCE, enabled).apply()
    }
    
    fun getNotificationPreference(): Boolean {
        return prefs.getBoolean(NOTIFICATION_PREFERENCE, true) // Default to enabled
    }
    
    fun saveBackgroundLocationPreference(enabled: Boolean) {
        prefs.edit().putBoolean(BACKGROUND_LOCATION_PREFERENCE, enabled).apply()
    }
    
    fun getBackgroundLocationPreference(): Boolean {
        return prefs.getBoolean(BACKGROUND_LOCATION_PREFERENCE, true) // Default to enabled
    }
    
    fun saveAppVersion(versionCode: Int) {
        prefs.edit().putInt(APP_VERSION, versionCode).apply()
    }
    
    fun getAppVersion(): Int {
        return prefs.getInt(APP_VERSION, 0)
    }
    
    // Clear all stored data (for logout)
    fun clearAll() {
        prefs.edit().clear().apply()
    }
    
    // Pending location service start tracking
    fun setPendingLocationServiceStart(pending: Boolean) {
        prefs.edit().putBoolean(PENDING_LOCATION_SERVICE_START, pending).apply()
    }
    
    fun isPendingLocationServiceStart(): Boolean {
        return prefs.getBoolean(PENDING_LOCATION_SERVICE_START, false)
    }
    
    // Delivery area status
    
    fun setIsInDeliveryArea(isInArea: Boolean) {
        prefs.edit().putBoolean(IS_IN_DELIVERY_AREA, isInArea).apply()
    }
    
    fun isInDeliveryArea(): Boolean {
        return prefs.getBoolean(IS_IN_DELIVERY_AREA, true) // Default to true to avoid restrictions
    }
    
    fun setCurrentDeliveryArea(areaId: String?, areaName: String?) {
        val editor = prefs.edit()
        if (areaId != null && areaName != null) {
            editor.putString(CURRENT_DELIVERY_AREA_ID, areaId)
            editor.putString(CURRENT_DELIVERY_AREA_NAME, areaName)
        } else {
            editor.remove(CURRENT_DELIVERY_AREA_ID)
            editor.remove(CURRENT_DELIVERY_AREA_NAME)
        }
        editor.apply()
    }
    
    fun getCurrentDeliveryArea(): Pair<String, String>? {
        val areaId = prefs.getString(CURRENT_DELIVERY_AREA_ID, null)
        val areaName = prefs.getString(CURRENT_DELIVERY_AREA_NAME, null)
        
        return if (areaId != null && areaName != null) {
            Pair(areaId, areaName)
        } else {
            null
        }
    }
    
    // Data class for cached location
    data class LocationData(
        val latitude: Double,
        val longitude: Double,
        val timestamp: Long
    )
} 