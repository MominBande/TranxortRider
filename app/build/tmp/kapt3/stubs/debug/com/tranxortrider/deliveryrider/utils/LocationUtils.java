package com.tranxortrider.deliveryrider.utils;

/**
 * Utility class for location-related operations
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J&\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\tJ\u0016\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010\u0011J\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00132\u0006\u0010\u000f\u001a\u00020\u0010J$\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u00150\u0019H\u0002J(\u0010\u001a\u001a\u00020\u00152\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001f"}, d2 = {"Lcom/tranxortrider/deliveryrider/utils/LocationUtils;", "", "()V", "FASTEST_LOCATION_INTERVAL", "", "LOCATION_UPDATE_INTERVAL", "calculateDistance", "", "startLat", "", "startLng", "endLat", "endLng", "getCurrentLocation", "Landroid/location/Location;", "context", "Landroid/content/Context;", "(Landroid/content/Context;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getLocationUpdates", "Lkotlinx/coroutines/flow/Flow;", "requestSingleUpdate", "", "fusedLocationClient", "Lcom/google/android/gms/location/FusedLocationProviderClient;", "onResult", "Lkotlin/Function1;", "tryAlternativeLocationMethod", "locationCallback", "Lcom/google/android/gms/location/LocationCallback;", "locationRequest", "Lcom/google/android/gms/location/LocationRequest;", "app_debug"})
public final class LocationUtils {
    private static final long LOCATION_UPDATE_INTERVAL = 5000L;
    private static final long FASTEST_LOCATION_INTERVAL = 2000L;
    @org.jetbrains.annotations.NotNull()
    public static final com.tranxortrider.deliveryrider.utils.LocationUtils INSTANCE = null;
    
    private LocationUtils() {
        super();
    }
    
    /**
     * Get the current location using FusedLocationProvider
     *
     * @param context Application context
     * @return Location object or null if location couldn't be obtained
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getCurrentLocation(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super android.location.Location> $completion) {
        return null;
    }
    
    /**
     * Request a single location update
     */
    private final void requestSingleUpdate(com.google.android.gms.location.FusedLocationProviderClient fusedLocationClient, kotlin.jvm.functions.Function1<? super android.location.Location, kotlin.Unit> onResult) {
    }
    
    /**
     * Get continuous location updates as a Flow
     *
     * @param context Application context
     * @return Flow of Location objects
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<android.location.Location> getLocationUpdates(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    /**
     * Try alternative method to get locations on Android 15
     */
    private final void tryAlternativeLocationMethod(android.content.Context context, com.google.android.gms.location.FusedLocationProviderClient fusedLocationClient, com.google.android.gms.location.LocationCallback locationCallback, com.google.android.gms.location.LocationRequest locationRequest) {
    }
    
    /**
     * Calculate distance between two points in meters
     */
    public final float calculateDistance(double startLat, double startLng, double endLat, double endLng) {
        return 0.0F;
    }
}