package com.tranxortrider.deliveryrider

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.os.Build
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.tranxortrider.deliveryrider.repositories.OfflineRepository
import com.tranxortrider.deliveryrider.services.LocationService
import com.tranxortrider.deliveryrider.utils.ConnectivityReceiver
import com.tranxortrider.deliveryrider.utils.SharedPreferencesManager
import com.tranxortrider.deliveryrider.utils.ThemeManager
import com.tranxortrider.deliveryrider.utils.SupabaseClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class TranxortRiderApplication : Application(), ConnectivityReceiver.ConnectivityReceiverListener {
    
    private lateinit var themeManager: ThemeManager
    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    private lateinit var offlineRepository: OfflineRepository
    private lateinit var connectivityReceiver: ConnectivityReceiver
    
    private val applicationScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    
    companion object {
        private const val TAG = "TranxortRiderApp"
        
        // Singleton instance for accessing application-wide
        private lateinit var instance: TranxortRiderApplication
        
        fun getInstance(): TranxortRiderApplication {
            return instance
        }
    }
    
    override fun onCreate() {
        super.onCreate()
        instance = this
        
        // Initialize Firebase
        FirebaseApp.initializeApp(this)
        
        // Configure Firestore for offline persistence
        val settings = com.google.firebase.firestore.FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .setCacheSizeBytes(com.google.firebase.firestore.FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
            .build()
        
        com.google.firebase.firestore.FirebaseFirestore.getInstance().firestoreSettings = settings
        
        // Enable Firestore logging (always in development mode for now)
        try {
            com.google.firebase.firestore.FirebaseFirestore.setLoggingEnabled(true)
        } catch (e: Exception) {
            Log.w(TAG, "Could not enable Firebase logging", e)
        }
        
        // Initialize theme manager and apply theme
        themeManager = ThemeManager(this)
        applyAppTheme()
        
        // Initialize shared preferences manager
        sharedPreferencesManager = SharedPreferencesManager(this)
        
        // Initialize offline repository
        offlineRepository = OfflineRepository(this)
        
        // Register connectivity receiver
        connectivityReceiver = ConnectivityReceiver()
        connectivityReceiver.setConnectivityListener(this)
        registerConnectivityReceiver()
        
        // Subscribe to Firebase topics
        subscribeToNotificationTopics()
        
        // Initialize Supabase (just access the client to trigger initialization)
        try {
            val bucket = SupabaseClient.getDocumentsBucket()
            Log.d(TAG, "Supabase initialized successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Error initializing Supabase", e)
        }
    }
    
    /**
     * Apply the app theme based on saved settings
     */
    fun applyAppTheme() {
        // First apply theme mode (light/dark)
        val mode = themeManager.getThemeMode()
        val nightMode = when (mode) {
            ThemeManager.MODE_LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
            ThemeManager.MODE_DARK -> AppCompatDelegate.MODE_NIGHT_YES
            else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
        AppCompatDelegate.setDefaultNightMode(nightMode)
        
        // For font scale and accent color, each Activity is responsible for applying
        // these during onCreate, as they require UI updates specific to each screen
    }
    
    /**
     * Get the theme manager instance
     */
    fun getThemeManager(): ThemeManager {
        return themeManager
    }
    
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Handle night mode changes
        Log.d(TAG, "Configuration changed: ${if (isNightModeActive(newConfig)) "Night mode" else "Day mode"}")
    }
    
    private fun isNightModeActive(configuration: Configuration): Boolean {
        return configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }
    
    private fun registerConnectivityReceiver() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // For Android 13 and above, we use the registerReceiver that accepts flags
            registerReceiver(
                connectivityReceiver,
                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION),
                Context.RECEIVER_NOT_EXPORTED
            )
        } else {
            // For older versions
            registerReceiver(
                connectivityReceiver,
                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            )
        }
    }
    
    private fun subscribeToNotificationTopics() {
        // Subscribe to general topic
        FirebaseMessaging.getInstance().subscribeToTopic("general")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Subscribed to general topic")
                } else {
                    Log.e(TAG, "Failed to subscribe to general topic")
                }
            }
            
        // Subscribe to updates topic
        FirebaseMessaging.getInstance().subscribeToTopic("updates")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Subscribed to updates topic")
                } else {
                    Log.e(TAG, "Failed to subscribe to updates topic")
                }
            }
    }
    
    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        if (isConnected) {
            Log.d(TAG, "Network is connected")
            // Sync data when coming back online
            syncDataWhenOnline()
            
            // Start location service if enabled in settings
            if (sharedPreferencesManager.getBackgroundLocationPreference()) {
                startLocationService()
            }
        } else {
            Log.d(TAG, "Network is disconnected")
        }
    }
    
    private fun syncDataWhenOnline() {
        applicationScope.launch {
            try {
                offlineRepository.syncPendingChanges()
                Log.d(TAG, "Successfully synced pending changes")
            } catch (e: Exception) {
                Log.e(TAG, "Error syncing data when back online", e)
            }
        }
    }
    
    private fun startLocationService() {
        try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // For Android 8.0 and above, use a regular service that will call startForeground internally
                // This way the service itself will handle the foreground status appropriately
                startService(Intent(this, LocationService::class.java))
        } else {
            startService(Intent(this, LocationService::class.java))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error starting location service", e)
            // Schedule the service to start when the app is next in the foreground
            sharedPreferencesManager.setPendingLocationServiceStart(true)
        }
    }
    
    override fun onTerminate() {
        super.onTerminate()
        // Unregister receiver when app terminates
        unregisterReceiver(connectivityReceiver)
    }
} 