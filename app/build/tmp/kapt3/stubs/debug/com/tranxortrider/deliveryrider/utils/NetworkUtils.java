package com.tranxortrider.deliveryrider.utils;

/**
 * Utility class for network operations
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\t"}, d2 = {"Lcom/tranxortrider/deliveryrider/utils/NetworkUtils;", "", "()V", "isCellularConnected", "", "context", "Landroid/content/Context;", "isNetworkAvailable", "isWifiConnected", "app_debug"})
public final class NetworkUtils {
    @org.jetbrains.annotations.NotNull()
    public static final com.tranxortrider.deliveryrider.utils.NetworkUtils INSTANCE = null;
    
    private NetworkUtils() {
        super();
    }
    
    /**
     * Check if the device is connected to the internet
     *
     * @param context The application context
     * @return True if the device is connected to the internet, false otherwise
     */
    public final boolean isNetworkAvailable(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return false;
    }
    
    /**
     * Check if the device is connected to WiFi
     *
     * @param context The application context
     * @return True if the device is connected to WiFi, false otherwise
     */
    public final boolean isWifiConnected(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return false;
    }
    
    /**
     * Check if the device is connected to a cellular network
     */
    public final boolean isCellularConnected(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return false;
    }
}