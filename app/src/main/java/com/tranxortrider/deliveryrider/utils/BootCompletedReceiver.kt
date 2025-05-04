package com.tranxortrider.deliveryrider.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.tranxortrider.deliveryrider.services.LocationService

/**
 * BroadcastReceiver to handle device boot completed
 */
class BootCompletedReceiver : BroadcastReceiver() {
    
    private val TAG = "BootCompletedReceiver"
    
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED ||
            intent.action == "android.intent.action.QUICKBOOT_POWERON") {
            
            Log.d(TAG, "Device boot completed, starting services")
            
            // Get shared preferences to check if location service should be started
            val sharedPreferencesManager = SharedPreferencesManager(context)
            
            // Start location service if enabled
            if (sharedPreferencesManager.getBackgroundLocationPreference()) {
                Log.d(TAG, "Starting location service after boot")
                startLocationService(context)
            }
        }
    }
    
    private fun startLocationService(context: Context) {
        val serviceIntent = Intent(context, LocationService::class.java)
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(serviceIntent)
        } else {
            context.startService(serviceIntent)
        }
    }
} 