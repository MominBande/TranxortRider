package com.tranxortrider.deliveryrider.api

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * MOCK ApiClient for backward compatibility
 * 
 * Note: This class is kept for compatibility with existing code, but the app
 * now uses Firebase Firestore directly for data operations instead of a custom API.
 */
object ApiClient {
    private const val TAG = "MockApiClient"
    
    // Using a fake URL - this service is not actually used
    private const val BASE_URL = "https://mock-api.tranxortrider.com/api/"
    
    private val loggingInterceptor = HttpLoggingInterceptor { message ->
        Log.d(TAG, message)
    }.apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    
    // Configure OkHttpClient
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)
        .build()
    
    // Create Retrofit instance - NOTE: This will never actually be used
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    
    // Create API service instance - API calls will fail fast with error messages
    val apiService: ApiService = retrofit.create(ApiService::class.java)
    
    init {
        Log.w(TAG, "WARNING: Using mock API client. All API calls will be redirected to Firebase.")
    }
} 