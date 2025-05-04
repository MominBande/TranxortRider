# TranxortRider App - Debugging Guide

This guide provides troubleshooting tips for the three main features implemented in the TranxortRider app:

## Push Notification Service

### Common Issues

1. **Notifications not showing**
   - Check Firebase setup in `google-services.json`
   - Verify proper registration of FCM token using logs:
     ```
     adb logcat -s "PushNotificationService"
     ```
   - Ensure notification channels are created (Android 8.0+)
   - Verify notification permissions are granted in app settings

2. **Token not updating**
   - Check for error logs during token refresh process:
     ```kotlin
     override fun onNewToken(token: String) {
         Log.d(TAG, "Refreshed token: $token")
         sendRegistrationToServer(token)
     }
     ```
   - Verify Firestore write permissions
   - Check internet connectivity

### Debug Steps

1. Send test notification from Firebase Console
2. Check for callback in `onMessageReceived()`
3. Check if data payload is correctly parsed
4. Verify notification building and channel creation

## Background Location Service

### Common Issues

1. **Service not starting**
   - Check all location permissions are granted
   - Ensure foreground service permissions are granted
   - Verify proper service declaration in AndroidManifest.xml
   - Check battery optimization settings

2. **Location updates not received**
   - Check location settings on device
   - Verify GPS is enabled
   - Check location request parameters:
     ```kotlin
     val locationRequest = LocationRequest.Builder(
         Priority.PRIORITY_HIGH_ACCURACY,
         UPDATE_INTERVAL
     )
         .setWaitForAccurateLocation(false)
         .setMinUpdateIntervalMillis(FASTEST_INTERVAL)
         .build()
     ```

3. **Location not syncing to Firebase**
   - Check internet connectivity
   - Verify Firebase authentication state
   - Check Firestore write permissions
   - Look for error logs during update

### Debug Steps

1. Enable debug logging in LocationService
2. Monitor service lifecycle:
   ```
   adb logcat -s "LocationService"
   ```
3. Test location permissions:
   ```kotlin
   if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) 
       != PackageManager.PERMISSION_GRANTED) {
       // Handle permission not granted
   }
   ```
4. Check local storage with Device File Explorer

## Offline Support

### Common Issues

1. **Data not available offline**
   - Verify data is properly cached in SharedPreferences
   - Check serialization/deserialization logic
   - Ensure cache is updated when online

2. **Changes not syncing when back online**
   - Verify ConnectivityReceiver is registered properly
   - Check sync queue implementation
   - Ensure onNetworkConnectionChanged() is triggered

3. **Cached data becoming stale**
   - Verify cache refresh policies
   - Check timestamp-based expiration logic
   - Ensure proper cache invalidation

### Debug Steps

1. Monitor connectivity changes:
   ```
   adb logcat -s "ConnectivityReceiver"
   ```
2. Check SharedPreferences content:
   ```
   adb shell run-as com.tranxortrider.deliveryrider cat /data/data/com.tranxortrider.deliveryrider/shared_prefs/tranxort_rider_prefs.xml
   ```
3. Test offline fallback by toggling airplane mode
4. Check sync queue processing:
   ```kotlin
   private fun syncCachedLocations() {
       val locationQueue = sharedPreferencesManager.getLocationSyncQueue()
       if (locationQueue.isEmpty()) return
       
       serviceScope.launch {
           try {
               firestoreService.bulkUpdateRiderLocations(locationQueue)
               // Clear the queue if successful
               sharedPreferencesManager.clearLocationSyncQueue()
           } catch (e: Exception) {
               Log.e(TAG, "Failed to sync cached locations", e)
           }
       }
   }
   ```

## General Debugging Tips

1. **Enable Verbose Logging**
   - Add detailed logging for each service
   - Use distinct TAG values for filtering
   - Log key events and state transitions

2. **Check Android Manifest**
   - Verify all required permissions
   - Ensure services are properly registered
   - Check intent filters for receivers

3. **Monitor Battery and Performance**
   - Use Android Profiler to check CPU/memory usage
   - Monitor battery impact of location service
   - Look for wake locks that might not be released

4. **Test in Different Scenarios**
   - Test with good/poor/no connectivity
   - Check behavior when app is in background or killed
   - Test across different Android versions

5. **Firebase Debug Tools**
   - Use Firebase Debug View in console
   - Check Cloud Messaging logs
   - Verify FCM token registration 