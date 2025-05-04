package com.tranxortrider.deliveryrider.repositories;

/**
 * Repository for managing offline data access and synchronization
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J%\u0010\r\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u000e2\u0006\u0010\u0010\u001a\u00020\bH\u0002\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0011\u0010\u0012J-\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u00140\u000e2\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0002\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u001d\u0010\u0019\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001a0\u000eH\u0002\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001b\u0010\u001cJ$\u0010\u001d\u001a\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u000e0\u001e2\u0006\u0010\u0010\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u001fJ,\u0010 \u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u00140\u000e0\u001e2\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0086@\u00a2\u0006\u0002\u0010!J\u001c\u0010\"\u001a\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u001a0\u000e0\u001eH\u0086@\u00a2\u0006\u0002\u0010#J\u0006\u0010$\u001a\u00020%J\u001c\u0010&\u001a\b\u0012\u0004\u0012\u00020%0\u000eH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\'\u0010#R\u000e\u0010\u0007\u001a\u00020\bX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006("}, d2 = {"Lcom/tranxortrider/deliveryrider/repositories/OfflineRepository;", "", "context", "Landroid/content/Context;", "firestoreService", "Lcom/tranxortrider/deliveryrider/services/FirestoreService;", "(Landroid/content/Context;Lcom/tranxortrider/deliveryrider/services/FirestoreService;)V", "TAG", "", "gson", "Lcom/google/gson/Gson;", "sharedPreferencesManager", "Lcom/tranxortrider/deliveryrider/utils/SharedPreferencesManager;", "getCachedOrderById", "Lkotlin/Result;", "Lcom/tranxortrider/deliveryrider/models/Order;", "orderId", "getCachedOrderById-IoAF18A", "(Ljava/lang/String;)Ljava/lang/Object;", "getCachedOrders", "", "status", "Lcom/tranxortrider/deliveryrider/models/OrderStatus;", "getCachedOrders-IoAF18A", "(Lcom/tranxortrider/deliveryrider/models/OrderStatus;)Ljava/lang/Object;", "getCachedUserProfile", "Lcom/tranxortrider/deliveryrider/models/User;", "getCachedUserProfile-d1pmJ48", "()Ljava/lang/Object;", "getOrderById", "Lkotlinx/coroutines/flow/Flow;", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getOrders", "(Lcom/tranxortrider/deliveryrider/models/OrderStatus;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getUserProfile", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "isSyncNeeded", "", "syncPendingChanges", "syncPendingChanges-IoAF18A", "app_debug"})
public final class OfflineRepository {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.tranxortrider.deliveryrider.services.FirestoreService firestoreService = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String TAG = "OfflineRepository";
    @org.jetbrains.annotations.NotNull()
    private final com.tranxortrider.deliveryrider.utils.SharedPreferencesManager sharedPreferencesManager = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.gson.Gson gson = null;
    
    public OfflineRepository(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.tranxortrider.deliveryrider.services.FirestoreService firestoreService) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getUserProfile(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<kotlin.Result<com.tranxortrider.deliveryrider.models.User>>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getOrders(@org.jetbrains.annotations.Nullable()
    com.tranxortrider.deliveryrider.models.OrderStatus status, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends kotlin.Result<? extends java.util.List<com.tranxortrider.deliveryrider.models.Order>>>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getOrderById(@org.jetbrains.annotations.NotNull()
    java.lang.String orderId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<kotlin.Result<com.tranxortrider.deliveryrider.models.Order>>> $completion) {
        return null;
    }
    
    public final boolean isSyncNeeded() {
        return false;
    }
}