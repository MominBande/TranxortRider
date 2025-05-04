package com.tranxortrider.deliveryrider;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u0000 \u001f2\u00020\u00012\u00020\u0002:\u0001\u001fB\u0005\u00a2\u0006\u0002\u0010\u0003J\u0006\u0010\u000e\u001a\u00020\u000fJ\u0006\u0010\u0010\u001a\u00020\rJ\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0010\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u0014H\u0016J\b\u0010\u0017\u001a\u00020\u000fH\u0016J\u0010\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u0012H\u0016J\b\u0010\u001a\u001a\u00020\u000fH\u0016J\b\u0010\u001b\u001a\u00020\u000fH\u0002J\b\u0010\u001c\u001a\u00020\u000fH\u0002J\b\u0010\u001d\u001a\u00020\u000fH\u0002J\b\u0010\u001e\u001a\u00020\u000fH\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006 "}, d2 = {"Lcom/tranxortrider/deliveryrider/TranxortRiderApplication;", "Landroid/app/Application;", "Lcom/tranxortrider/deliveryrider/utils/ConnectivityReceiver$ConnectivityReceiverListener;", "()V", "applicationScope", "Lkotlinx/coroutines/CoroutineScope;", "connectivityReceiver", "Lcom/tranxortrider/deliveryrider/utils/ConnectivityReceiver;", "offlineRepository", "Lcom/tranxortrider/deliveryrider/repositories/OfflineRepository;", "sharedPreferencesManager", "Lcom/tranxortrider/deliveryrider/utils/SharedPreferencesManager;", "themeManager", "Lcom/tranxortrider/deliveryrider/utils/ThemeManager;", "applyAppTheme", "", "getThemeManager", "isNightModeActive", "", "configuration", "Landroid/content/res/Configuration;", "onConfigurationChanged", "newConfig", "onCreate", "onNetworkConnectionChanged", "isConnected", "onTerminate", "registerConnectivityReceiver", "startLocationService", "subscribeToNotificationTopics", "syncDataWhenOnline", "Companion", "app_debug"})
public final class TranxortRiderApplication extends android.app.Application implements com.tranxortrider.deliveryrider.utils.ConnectivityReceiver.ConnectivityReceiverListener {
    private com.tranxortrider.deliveryrider.utils.ThemeManager themeManager;
    private com.tranxortrider.deliveryrider.utils.SharedPreferencesManager sharedPreferencesManager;
    private com.tranxortrider.deliveryrider.repositories.OfflineRepository offlineRepository;
    private com.tranxortrider.deliveryrider.utils.ConnectivityReceiver connectivityReceiver;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope applicationScope = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "TranxortRiderApp";
    private static com.tranxortrider.deliveryrider.TranxortRiderApplication instance;
    @org.jetbrains.annotations.NotNull()
    public static final com.tranxortrider.deliveryrider.TranxortRiderApplication.Companion Companion = null;
    
    public TranxortRiderApplication() {
        super();
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    /**
     * Apply the app theme based on saved settings
     */
    public final void applyAppTheme() {
    }
    
    /**
     * Get the theme manager instance
     */
    @org.jetbrains.annotations.NotNull()
    public final com.tranxortrider.deliveryrider.utils.ThemeManager getThemeManager() {
        return null;
    }
    
    @java.lang.Override()
    public void onConfigurationChanged(@org.jetbrains.annotations.NotNull()
    android.content.res.Configuration newConfig) {
    }
    
    private final boolean isNightModeActive(android.content.res.Configuration configuration) {
        return false;
    }
    
    private final void registerConnectivityReceiver() {
    }
    
    private final void subscribeToNotificationTopics() {
    }
    
    @java.lang.Override()
    public void onNetworkConnectionChanged(boolean isConnected) {
    }
    
    private final void syncDataWhenOnline() {
    }
    
    private final void startLocationService() {
    }
    
    @java.lang.Override()
    public void onTerminate() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0007\u001a\u00020\u0006R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/tranxortrider/deliveryrider/TranxortRiderApplication$Companion;", "", "()V", "TAG", "", "instance", "Lcom/tranxortrider/deliveryrider/TranxortRiderApplication;", "getInstance", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.tranxortrider.deliveryrider.TranxortRiderApplication getInstance() {
            return null;
        }
    }
}