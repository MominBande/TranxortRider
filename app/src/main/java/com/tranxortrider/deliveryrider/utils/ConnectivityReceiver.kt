package com.tranxortrider.deliveryrider.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager

/**
 * BroadcastReceiver to handle connectivity changes
 */
class ConnectivityReceiver : BroadcastReceiver() {
    
    /**
     * Interface to notify about network connectivity changes
     */
    interface ConnectivityReceiverListener {
        fun onNetworkConnectionChanged(isConnected: Boolean)
    }
    
    companion object {
        private var connectivityReceiverListener: ConnectivityReceiverListener? = null
    }
    
    /**
     * Set the listener to be notified of connectivity changes
     */
    fun setConnectivityListener(listener: ConnectivityReceiverListener) {
        connectivityReceiverListener = listener
    }
    
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ConnectivityManager.CONNECTIVITY_ACTION) {
            // Check current network state
            val isConnected = NetworkUtils.isNetworkAvailable(context)
            
            // Notify the listener
            connectivityReceiverListener?.onNetworkConnectionChanged(isConnected)
        }
    }
} 