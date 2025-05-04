package com.tranxortrider.deliveryrider.utils;

/**
 * BroadcastReceiver to handle connectivity changes
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \f2\u00020\u0001:\u0002\f\rB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u000e\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000b\u00a8\u0006\u000e"}, d2 = {"Lcom/tranxortrider/deliveryrider/utils/ConnectivityReceiver;", "Landroid/content/BroadcastReceiver;", "()V", "onReceive", "", "context", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "setConnectivityListener", "listener", "Lcom/tranxortrider/deliveryrider/utils/ConnectivityReceiver$ConnectivityReceiverListener;", "Companion", "ConnectivityReceiverListener", "app_debug"})
public final class ConnectivityReceiver extends android.content.BroadcastReceiver {
    @org.jetbrains.annotations.Nullable()
    private static com.tranxortrider.deliveryrider.utils.ConnectivityReceiver.ConnectivityReceiverListener connectivityReceiverListener;
    @org.jetbrains.annotations.NotNull()
    public static final com.tranxortrider.deliveryrider.utils.ConnectivityReceiver.Companion Companion = null;
    
    public ConnectivityReceiver() {
        super();
    }
    
    /**
     * Set the listener to be notified of connectivity changes
     */
    public final void setConnectivityListener(@org.jetbrains.annotations.NotNull()
    com.tranxortrider.deliveryrider.utils.ConnectivityReceiver.ConnectivityReceiverListener listener) {
    }
    
    @java.lang.Override()
    public void onReceive(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    android.content.Intent intent) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/tranxortrider/deliveryrider/utils/ConnectivityReceiver$Companion;", "", "()V", "connectivityReceiverListener", "Lcom/tranxortrider/deliveryrider/utils/ConnectivityReceiver$ConnectivityReceiverListener;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
    
    /**
     * Interface to notify about network connectivity changes
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&\u00a8\u0006\u0006"}, d2 = {"Lcom/tranxortrider/deliveryrider/utils/ConnectivityReceiver$ConnectivityReceiverListener;", "", "onNetworkConnectionChanged", "", "isConnected", "", "app_debug"})
    public static abstract interface ConnectivityReceiverListener {
        
        public abstract void onNetworkConnectionChanged(boolean isConnected);
    }
}