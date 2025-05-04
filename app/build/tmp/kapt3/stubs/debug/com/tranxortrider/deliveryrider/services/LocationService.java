package com.tranxortrider.deliveryrider.services;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J\b\u0010\u001f\u001a\u00020 H\u0002J\b\u0010!\u001a\u00020\u001cH\u0002J\u0010\u0010\"\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J\u0014\u0010#\u001a\u0004\u0018\u00010$2\b\u0010%\u001a\u0004\u0018\u00010&H\u0016J\b\u0010\'\u001a\u00020\u001cH\u0016J\b\u0010(\u001a\u00020\u001cH\u0016J\"\u0010)\u001a\u00020\u000b2\b\u0010%\u001a\u0004\u0018\u00010&2\u0006\u0010*\u001a\u00020\u000b2\u0006\u0010+\u001a\u00020\u000bH\u0016J\b\u0010,\u001a\u00020\u001cH\u0002J\u0010\u0010-\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J\u0010\u0010.\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J\b\u0010/\u001a\u00020\u001cH\u0002J\b\u00100\u001a\u00020\u001cH\u0002J\u0010\u00101\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u00062"}, d2 = {"Lcom/tranxortrider/deliveryrider/services/LocationService;", "Landroid/app/Service;", "()V", "ADMIN_UPDATE_INTERVAL", "", "CHANNEL_ID", "", "DISPLACEMENT", "", "FASTEST_INTERVAL", "NOTIFICATION_ID", "", "TAG", "UPDATE_INTERVAL", "firestoreService", "Lcom/tranxortrider/deliveryrider/services/FirestoreService;", "fusedLocationClient", "Lcom/google/android/gms/location/FusedLocationProviderClient;", "geofencingService", "Lcom/tranxortrider/deliveryrider/services/GeofencingService;", "lastAdminUpdateTime", "locationCallback", "Lcom/google/android/gms/location/LocationCallback;", "serviceScope", "Lkotlinx/coroutines/CoroutineScope;", "sharedPreferencesManager", "Lcom/tranxortrider/deliveryrider/utils/SharedPreferencesManager;", "checkDeliveryAreaStatus", "", "location", "Landroid/location/Location;", "createNotification", "Landroid/app/Notification;", "createNotificationChannel", "handleLocationUpdate", "onBind", "Landroid/os/IBinder;", "intent", "Landroid/content/Intent;", "onCreate", "onDestroy", "onStartCommand", "flags", "startId", "requestLocationUpdates", "saveLocationLocally", "sendLocationToAdminPanel", "setupGeofences", "syncCachedLocations", "updateLocationInFirebase", "app_debug"})
public final class LocationService extends android.app.Service {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String TAG = "LocationService";
    private final int NOTIFICATION_ID = 12345;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String CHANNEL_ID = "location_service_channel";
    private com.google.android.gms.location.FusedLocationProviderClient fusedLocationClient;
    private com.google.android.gms.location.LocationCallback locationCallback;
    private com.tranxortrider.deliveryrider.services.FirestoreService firestoreService;
    private com.tranxortrider.deliveryrider.utils.SharedPreferencesManager sharedPreferencesManager;
    private com.tranxortrider.deliveryrider.services.GeofencingService geofencingService;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope serviceScope = null;
    private final long UPDATE_INTERVAL = 0L;
    private final long FASTEST_INTERVAL = 0L;
    private final float DISPLACEMENT = 20.0F;
    private final long ADMIN_UPDATE_INTERVAL = 0L;
    private long lastAdminUpdateTime = 0L;
    
    public LocationService() {
        super();
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    @java.lang.Override()
    public int onStartCommand(@org.jetbrains.annotations.Nullable()
    android.content.Intent intent, int flags, int startId) {
        return 0;
    }
    
    private final void requestLocationUpdates() {
    }
    
    private final void setupGeofences() {
    }
    
    private final void handleLocationUpdate(android.location.Location location) {
    }
    
    private final void saveLocationLocally(android.location.Location location) {
    }
    
    private final void updateLocationInFirebase(android.location.Location location) {
    }
    
    private final void syncCachedLocations() {
    }
    
    private final void sendLocationToAdminPanel(android.location.Location location) {
    }
    
    private final void checkDeliveryAreaStatus(android.location.Location location) {
    }
    
    private final android.app.Notification createNotification() {
        return null;
    }
    
    private final void createNotificationChannel() {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public android.os.IBinder onBind(@org.jetbrains.annotations.Nullable()
    android.content.Intent intent) {
        return null;
    }
    
    @java.lang.Override()
    public void onDestroy() {
    }
}