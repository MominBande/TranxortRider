package com.tranxortrider.deliveryrider.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingEvent
import com.tranxortrider.deliveryrider.services.NotificationService

/**
 * Broadcast receiver for handling geofence transitions
 */
class GeofenceBroadcastReceiver : BroadcastReceiver() {
    private val TAG = "GeofenceBroadcastReceiver"
    
    override fun onReceive(context: Context, intent: Intent) {
        val geofencingEvent = GeofencingEvent.fromIntent(intent) ?: return
        
        if (geofencingEvent.hasError()) {
            val errorMessage = GeofenceStatusCodes.getStatusCodeString(geofencingEvent.errorCode)
            Log.e(TAG, "Geofence error: $errorMessage")
            return
        }
        
        // Get the transition type
        val geofenceTransition = geofencingEvent.geofenceTransition
        
        // Get the geofences that triggered this event
        val triggeringGeofences = geofencingEvent.triggeringGeofences ?: return
        
        // Get the location that triggered the geofence transition
        val triggeringLocation = geofencingEvent.triggeringLocation
        
        val notificationService = NotificationService(context)
        
        when (geofenceTransition) {
            Geofence.GEOFENCE_TRANSITION_ENTER -> {
                // The rider entered a delivery area
                val areaNames = triggeringGeofences.joinToString(", ") { it.requestId }
                Log.d(TAG, "Entered delivery areas: $areaNames")
                
                // Show notification
                notificationService.showDeliveryAreaNotification(
                    "Entered Delivery Area",
                    "You've entered the delivery area: $areaNames",
                    "delivery_area_channel"
                )
                
                // Broadcast to any listening activities
                val broadcastIntent = Intent("com.tranxortrider.GEOFENCE_TRANSITION")
                broadcastIntent.putExtra("transition_type", "ENTER")
                broadcastIntent.putExtra("area_ids", triggeringGeofences.map { it.requestId }.toTypedArray())
                if (triggeringLocation != null) {
                    broadcastIntent.putExtra("latitude", triggeringLocation.latitude)
                    broadcastIntent.putExtra("longitude", triggeringLocation.longitude)
                }
                context.sendBroadcast(broadcastIntent)
            }
            
            Geofence.GEOFENCE_TRANSITION_EXIT -> {
                // The rider exited a delivery area
                val areaNames = triggeringGeofences.joinToString(", ") { it.requestId }
                Log.d(TAG, "Exited delivery areas: $areaNames")
                
                // Show notification
                notificationService.showDeliveryAreaNotification(
                    "Left Delivery Area",
                    "You've left the delivery area: $areaNames",
                    "delivery_area_channel"
                )
                
                // Broadcast to any listening activities
                val broadcastIntent = Intent("com.tranxortrider.GEOFENCE_TRANSITION")
                broadcastIntent.putExtra("transition_type", "EXIT")
                broadcastIntent.putExtra("area_ids", triggeringGeofences.map { it.requestId }.toTypedArray())
                if (triggeringLocation != null) {
                    broadcastIntent.putExtra("latitude", triggeringLocation.latitude)
                    broadcastIntent.putExtra("longitude", triggeringLocation.longitude)
                }
                context.sendBroadcast(broadcastIntent)
            }
        }
    }
} 