# TranxortRider App - Migration Guide

This guide helps developers migrate these features into existing Android applications.

## Prerequisites

Before implementing these features, ensure your project has:

1. Firebase integration (FCM, Firestore, Authentication)
2. Kotlin coroutines support
3. Android min SDK 24+ (Android 7.0)
4. Google Play Services (Location)

## Push Notification Service

### Step 1: Add Dependencies

```gradle
// Firebase Cloud Messaging
implementation 'com.google.firebase:firebase-messaging:X.Y.Z'
// Firebase Firestore (for token storage)
implementation 'com.google.firebase:firebase-firestore:X.Y.Z'
```

### Step 2: Implement FCM Service

Create a class extending FirebaseMessagingService:

```kotlin
class PushNotificationService : FirebaseMessagingService() {
    
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Process notification
        remoteMessage.notification?.let {
            sendNotification(it.title, it.body)
        }
        
        // Process data payload
        if (remoteMessage.data.isNotEmpty()) {
            handleDataPayload(remoteMessage.data)
        }
    }
    
    override fun onNewToken(token: String) {
        // Save token to your backend
        storeTokenInFirestore(token)
    }
    
    private fun sendNotification(title: String?, body: String?) {
        // Create and show notification
    }
    
    private fun handleDataPayload(data: Map<String, String>) {
        // Handle data based on type
    }
    
    private fun storeTokenInFirestore(token: String) {
        // Store FCM token in Firestore for the current user
    }
}
```

### Step 3: Register Service in Manifest

```xml
<service
    android:name=".services.PushNotificationService"
    android:exported="false">
    <intent-filter>
        <action android:name="com.google.firebase.MESSAGING_EVENT" />
    </intent-filter>
</service>

<meta-data
    android:name="com.google.firebase.messaging.default_notification_icon"
    android:resource="@drawable/ic_notification" />
<meta-data
    android:name="com.google.firebase.messaging.default_notification_color"
    android:resource="@color/notification_color" />
```

## Background Location Service

### Step 1: Add Dependencies

```gradle
// Google Play Services Location
implementation 'com.google.android.gms:play-services-location:X.Y.Z'
```

### Step 2: Implement Location Service

Create a foreground service for location tracking:

```kotlin
class LocationService : Service() {
    
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    
    override fun onCreate() {
        super.onCreate()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        setupLocationCallback()
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(NOTIFICATION_ID, createNotification())
        requestLocationUpdates()
        return START_STICKY
    }
    
    private fun setupLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { location ->
                    handleLocationUpdate(location)
                }
            }
        }
    }
    
    private fun requestLocationUpdates() {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000)
            .setWaitForAccurateLocation(false)
            .setMinUpdateIntervalMillis(5000)
            .build()
        
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }
    
    private fun handleLocationUpdate(location: Location) {
        // Save locally and sync with backend
    }
    
    override fun onBind(intent: Intent?): IBinder? = null
    
    override fun onDestroy() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
        super.onDestroy()
    }
}
```

### Step 3: Add Permissions in Manifest

```xml
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_BACKGROUND_LOCATION" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />

<service
    android:name=".services.LocationService"
    android:enabled="true"
    android:exported="false"
    android:foregroundServiceType="location" />
```

## Offline Support

### Step 1: Create SharedPreferencesManager

```kotlin
class SharedPreferencesManager(private val context: Context) {
    
    companion object {
        private const val PREFS_NAME = "app_prefs"
        private const val LAST_SYNC_TIMESTAMP = "last_sync_timestamp"
        private const val CACHED_DATA = "cached_data"
        private const val SYNC_QUEUE = "sync_queue"
    }
    
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()
    
    fun cacheData(key: String, data: Any) {
        val json = gson.toJson(data)
        prefs.edit().putString(key, json).apply()
        saveLastSyncTimestamp()
    }
    
    fun <T> getCachedData(key: String, typeToken: TypeToken<T>): T? {
        val json = prefs.getString(key, null) ?: return null
        return gson.fromJson(json, typeToken.type)
    }
    
    fun addToSyncQueue(item: Any) {
        val queue = getSyncQueue<List<Any>>().toMutableList()
        queue.add(item)
        val json = gson.toJson(queue)
        prefs.edit().putString(SYNC_QUEUE, json).apply()
    }
    
    fun <T> getSyncQueue(): T = gson.fromJson(prefs.getString(SYNC_QUEUE, "[]"), object : TypeToken<T>() {}.type)
    
    fun clearSyncQueue() {
        prefs.edit().remove(SYNC_QUEUE).apply()
    }
    
    private fun saveLastSyncTimestamp() {
        prefs.edit().putLong(LAST_SYNC_TIMESTAMP, System.currentTimeMillis()).apply()
    }
}
```

### Step 2: Implement Network State Monitoring

```kotlin
class ConnectivityReceiver : BroadcastReceiver() {
    
    interface ConnectivityReceiverListener {
        fun onNetworkConnectionChanged(isConnected: Boolean)
    }
    
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ConnectivityManager.CONNECTIVITY_ACTION) {
            val isConnected = isNetworkAvailable(context)
            listener?.onNetworkConnectionChanged(isConnected)
        }
    }
    
    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
    
    companion object {
        var listener: ConnectivityReceiverListener? = null
    }
}
```

### Step 3: Register Receiver in Manifest

```xml
<receiver
    android:name=".utils.ConnectivityReceiver"
    android:enabled="true"
    android:exported="false">
    <intent-filter>
        <action android:name="android.net.conn.CONNECTIVITY_CHANGE" />
    </intent-filter>
</receiver>
```

### Step 4: Create Repository with Offline Support

```kotlin
class OfflineAwareRepository(
    private val context: Context,
    private val api: ApiService,
    private val sharedPreferencesManager: SharedPreferencesManager
) {
    
    suspend fun getData(): Flow<Result<Data>> = flow {
        if (NetworkUtils.isNetworkAvailable(context)) {
            try {
                val result = api.fetchData()
                if (result.isSuccess) {
                    // Cache successful response
                    sharedPreferencesManager.cacheData(CACHE_KEY, result.getOrNull()!!)
                    emit(result)
                } else {
                    // Fallback to cache
                    emit(getCachedData())
                }
            } catch (e: Exception) {
                emit(getCachedData())
            }
        } else {
            emit(getCachedData())
        }
    }
    
    private fun getCachedData(): Result<Data> {
        val cachedData = sharedPreferencesManager.getCachedData<Data>(CACHE_KEY, 
            object : TypeToken<Data>() {})
        return if (cachedData != null) {
            Result.success(cachedData)
        } else {
            Result.failure(Exception("No cached data available"))
        }
    }
}
```

## Integration Tips

1. **Application Class**:
   - Initialize Firebase in onCreate()
   - Register ConnectivityReceiver
   - Set up automatic sync when connectivity changes

2. **Permission Handling**:
   - Request location permissions at runtime
   - Explain to users why background location is needed
   - Handle permission denial gracefully

3. **Battery Optimization**:
   - Advise users to disable battery optimization for location service
   - Adjust location parameters based on activity
   - Consider throttling when battery is low

4. **Service Lifecycle**:
   - Start location service when user logs in or goes online
   - Stop service when user logs out or goes offline
   - Handle service restarts after device reboot

5. **Testing**:
   - Test with airplane mode toggling
   - Test with different network conditions
   - Test with app in background/foreground
   - Test with device reboots 