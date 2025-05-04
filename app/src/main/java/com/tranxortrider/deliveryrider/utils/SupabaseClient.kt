package com.tranxortrider.deliveryrider.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import java.net.InetAddress
import java.net.UnknownHostException

/**
 * Singleton for Supabase client
 */
object SupabaseClient {
    private const val SUPABASE_URL = "https://kwbbgaxwqrfaousxwbtd.supabase.co"
    private const val SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imt3YmJnYXh3cXJmYW91c3h3YnRkIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDY3OTIzODIsImV4cCI6MjA2MjM2ODM4Mn0.j-JOBV0EeupFxMUiDFMiPnfBD7o5dTCsBN2TIe-FZq4"
    private const val BUCKET_NAME = "riders-info"
    
    val client = createSupabaseClient(
        supabaseUrl = SUPABASE_URL,
        supabaseKey = SUPABASE_KEY
    ) {
        install(Storage)
    }
    
    /**
     * Get the storage bucket for rider documents
     */
    fun getDocumentsBucket() = client.storage[BUCKET_NAME]

    /**
     * Check if the device has internet connectivity
     */
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
               capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }

    /**
     * Check if Supabase server is reachable
     */
    fun isSupabaseReachable(): Boolean {
        return try {
            val hostname = SUPABASE_URL.replace("https://", "")
            InetAddress.getByName(hostname).isReachable(5000)
        } catch (e: UnknownHostException) {
            false
        }
    }
} 