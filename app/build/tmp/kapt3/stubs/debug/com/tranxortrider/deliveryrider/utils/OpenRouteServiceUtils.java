package com.tranxortrider.deliveryrider.utils;

/**
 * Utility class for OpenRouteService API operations
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aJ\u000e\u0010\u001b\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aJ<\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001a0\u001d2\u0006\u0010\u001e\u001a\u00020\u00182\u0006\u0010\u001f\u001a\u00020\u00182\u0006\u0010 \u001a\u00020\u00182\u0006\u0010!\u001a\u00020\u0018H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\"\u0010#J\u0014\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00040%2\u0006\u0010\u0019\u001a\u00020\u001aJ\u0016\u0010&\u001a\u00020\u00042\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\'\u001a\u00020(J\u0018\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020\u00182\u0006\u0010,\u001a\u00020\u0018H\u0002J\u000e\u0010-\u001a\u00020*H\u0086@\u00a2\u0006\u0002\u0010.R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R#\u0010\u0006\u001a\n \b*\u0004\u0018\u00010\u00070\u00078BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\nR\u001b\u0010\r\u001a\u00020\u000e8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0011\u0010\f\u001a\u0004\b\u000f\u0010\u0010R#\u0010\u0012\u001a\n \b*\u0004\u0018\u00010\u00130\u00138BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0016\u0010\f\u001a\u0004\b\u0014\u0010\u0015\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006/"}, d2 = {"Lcom/tranxortrider/deliveryrider/utils/OpenRouteServiceUtils;", "", "()V", "API_KEY", "", "BASE_URL", "apiService", "Lcom/tranxortrider/deliveryrider/api/OpenRouteServiceApi;", "kotlin.jvm.PlatformType", "getApiService", "()Lcom/tranxortrider/deliveryrider/api/OpenRouteServiceApi;", "apiService$delegate", "Lkotlin/Lazy;", "okHttpClient", "Lokhttp3/OkHttpClient;", "getOkHttpClient", "()Lokhttp3/OkHttpClient;", "okHttpClient$delegate", "retrofit", "Lretrofit2/Retrofit;", "getRetrofit", "()Lretrofit2/Retrofit;", "retrofit$delegate", "calculateDistance", "", "directionsResponse", "Lcom/tranxortrider/deliveryrider/api/DirectionsResponse;", "calculateEta", "getDirections", "Lkotlin/Result;", "startLat", "startLng", "endLat", "endLng", "getDirections-yxL6bBk", "(DDDDLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getNavigationInstructions", "", "getNextInstruction", "currentLocation", "Landroid/location/Location;", "isValidCoordinate", "", "lat", "lng", "testApiConnectivity", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class OpenRouteServiceUtils {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String API_KEY = "5b3ce3597851110001cf6248ca8eef01f075b8fa16456c4894ef11eb75d1999dcaae2db3680e5ce4";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String BASE_URL = "https://api.openrouteservice.org/";
    @org.jetbrains.annotations.NotNull()
    private static final kotlin.Lazy okHttpClient$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private static final kotlin.Lazy retrofit$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private static final kotlin.Lazy apiService$delegate = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.tranxortrider.deliveryrider.utils.OpenRouteServiceUtils INSTANCE = null;
    
    private OpenRouteServiceUtils() {
        super();
    }
    
    private final okhttp3.OkHttpClient getOkHttpClient() {
        return null;
    }
    
    private final retrofit2.Retrofit getRetrofit() {
        return null;
    }
    
    private final com.tranxortrider.deliveryrider.api.OpenRouteServiceApi getApiService() {
        return null;
    }
    
    /**
     * Test connectivity to the OpenRouteService API
     *
     * @return true if the API is reachable, false otherwise
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object testApiConnectivity(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    /**
     * Check if a coordinate is valid (within ranges and not 0,0)
     */
    private final boolean isValidCoordinate(double lat, double lng) {
        return false;
    }
    
    /**
     * Calculate estimated time of arrival in minutes
     *
     * @param directionsResponse The response from the directions API
     * @return Estimated time in minutes
     */
    public final double calculateEta(@org.jetbrains.annotations.NotNull()
    com.tranxortrider.deliveryrider.api.DirectionsResponse directionsResponse) {
        return 0.0;
    }
    
    /**
     * Get the total distance of the route in kilometers
     *
     * @param directionsResponse The response from the directions API
     * @return Distance in kilometers
     */
    public final double calculateDistance(@org.jetbrains.annotations.NotNull()
    com.tranxortrider.deliveryrider.api.DirectionsResponse directionsResponse) {
        return 0.0;
    }
    
    /**
     * Get turn-by-turn navigation instructions
     *
     * @param directionsResponse The response from the directions API
     * @return List of navigation instructions
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getNavigationInstructions(@org.jetbrains.annotations.NotNull()
    com.tranxortrider.deliveryrider.api.DirectionsResponse directionsResponse) {
        return null;
    }
    
    /**
     * Get the next navigation instruction based on current location
     *
     * @param directionsResponse The response from the directions API
     * @param currentLocation Current user location
     * @return The next navigation instruction
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getNextInstruction(@org.jetbrains.annotations.NotNull()
    com.tranxortrider.deliveryrider.api.DirectionsResponse directionsResponse, @org.jetbrains.annotations.NotNull()
    android.location.Location currentLocation) {
        return null;
    }
}