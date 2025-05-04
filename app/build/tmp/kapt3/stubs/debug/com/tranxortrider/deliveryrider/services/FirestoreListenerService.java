package com.tranxortrider.deliveryrider.services;

/**
 * Service for handling Firestore real-time listeners
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J(\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u00142\u0006\u0010\u0017\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00020\u0014H\u0002J \u0010\u0019\u001a\u00020\u001a2\u0018\u0010\u001b\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001e0\u001d\u0012\u0004\u0012\u00020\u001a0\u001cJ;\u0010\u001f\u001a\u00020\u001a2\n\b\u0002\u0010 \u001a\u0004\u0018\u00010\u00142\b\b\u0002\u0010!\u001a\u00020\"2\u0018\u0010\u001b\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001e0\u001d\u0012\u0004\u0012\u00020\u001a0\u001c\u00a2\u0006\u0002\u0010#J(\u0010$\u001a\u00020\u001a2\u0006\u0010%\u001a\u00020\u00042\u0018\u0010\u001b\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001e0\u001d\u0012\u0004\u0012\u00020\u001a0\u001cJ$\u0010&\u001a\u00020\u001a2\u0006\u0010\'\u001a\u00020\u00042\u0014\u0010\u001b\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u001e\u0012\u0004\u0012\u00020\u001a0\u001cJ\u001a\u0010(\u001a\u00020\u001a2\u0012\u0010)\u001a\u000e\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u00020\u001a0\u001cJ \u0010*\u001a\u00020\u001a2\u0018\u0010\u001b\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001e0\u001d\u0012\u0004\u0012\u00020\u001a0\u001cJ(\u0010+\u001a\u00020\u001a2 \u0010\u001b\u001a\u001c\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0001\u0018\u00010,\u0012\u0004\u0012\u00020\u001a0\u001cJ\u0006\u0010-\u001a\u00020\u001aJ \u0010.\u001a\u00020\u001a2\u0018\u0010/\u001a\u0014\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u001a00R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u00061"}, d2 = {"Lcom/tranxortrider/deliveryrider/services/FirestoreListenerService;", "", "()V", "TAG", "", "assignedOrdersListener", "Lcom/google/firebase/firestore/ListenerRegistration;", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "availableOrdersListener", "batchOrdersListener", "db", "Lcom/google/firebase/firestore/FirebaseFirestore;", "firestoreService", "Lcom/tranxortrider/deliveryrider/services/FirestoreService;", "orderAssignmentListener", "pendingOrdersListener", "specificOrderListener", "userProfileListener", "calculateDistance", "", "lat1", "lon1", "lat2", "lon2", "listenForAssignedOrders", "", "onUpdate", "Lkotlin/Function1;", "", "Lcom/tranxortrider/deliveryrider/models/Order;", "listenForAvailableOrders", "maxDistance", "maxOrders", "", "(Ljava/lang/Double;ILkotlin/jvm/functions/Function1;)V", "listenForBatchOrders", "batchId", "listenForOrder", "orderId", "listenForOrderAssignments", "onOrderAssigned", "listenForPendingOrders", "listenForUserProfile", "", "removeAllListeners", "startOrderStatusChangeListener", "onOrderStatusChanged", "Lkotlin/Function2;", "app_debug"})
public final class FirestoreListenerService {
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.firestore.FirebaseFirestore db = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.auth.FirebaseAuth auth = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String TAG = "FirestoreListenerService";
    @org.jetbrains.annotations.Nullable()
    private com.google.firebase.firestore.ListenerRegistration pendingOrdersListener;
    @org.jetbrains.annotations.Nullable()
    private com.google.firebase.firestore.ListenerRegistration assignedOrdersListener;
    @org.jetbrains.annotations.Nullable()
    private com.google.firebase.firestore.ListenerRegistration userProfileListener;
    @org.jetbrains.annotations.Nullable()
    private com.google.firebase.firestore.ListenerRegistration specificOrderListener;
    @org.jetbrains.annotations.Nullable()
    private com.google.firebase.firestore.ListenerRegistration availableOrdersListener;
    @org.jetbrains.annotations.Nullable()
    private com.google.firebase.firestore.ListenerRegistration orderAssignmentListener;
    @org.jetbrains.annotations.Nullable()
    private com.google.firebase.firestore.ListenerRegistration batchOrdersListener;
    @org.jetbrains.annotations.NotNull()
    private final com.tranxortrider.deliveryrider.services.FirestoreService firestoreService = null;
    
    public FirestoreListenerService() {
        super();
    }
    
    /**
     * Start listening for pending orders in real-time
     */
    public final void listenForPendingOrders(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.util.List<com.tranxortrider.deliveryrider.models.Order>, kotlin.Unit> onUpdate) {
    }
    
    /**
     * Start listening for assigned orders in real-time
     */
    public final void listenForAssignedOrders(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.util.List<com.tranxortrider.deliveryrider.models.Order>, kotlin.Unit> onUpdate) {
    }
    
    /**
     * Start listening for a specific order in real-time
     */
    public final void listenForOrder(@org.jetbrains.annotations.NotNull()
    java.lang.String orderId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.tranxortrider.deliveryrider.models.Order, kotlin.Unit> onUpdate) {
    }
    
    /**
     * Start listening for user profile changes in real-time
     */
    public final void listenForUserProfile(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.util.Map<java.lang.String, ? extends java.lang.Object>, kotlin.Unit> onUpdate) {
    }
    
    /**
     * Start listening for order status changes that might require batch updates
     */
    public final void startOrderStatusChangeListener(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super com.tranxortrider.deliveryrider.models.Order, ? super java.lang.String, kotlin.Unit> onOrderStatusChanged) {
    }
    
    /**
     * Start listening for available orders that can be assigned to the rider
     */
    public final void listenForAvailableOrders(@org.jetbrains.annotations.Nullable()
    java.lang.Double maxDistance, int maxOrders, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.util.List<com.tranxortrider.deliveryrider.models.Order>, kotlin.Unit> onUpdate) {
    }
    
    /**
     * Start listening for order assignments specifically for this rider
     */
    public final void listenForOrderAssignments(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.tranxortrider.deliveryrider.models.Order, kotlin.Unit> onOrderAssigned) {
    }
    
    /**
     * Start listening for batch order updates in real-time
     */
    public final void listenForBatchOrders(@org.jetbrains.annotations.NotNull()
    java.lang.String batchId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.util.List<com.tranxortrider.deliveryrider.models.Order>, kotlin.Unit> onUpdate) {
    }
    
    /**
     * Calculate distance between two coordinates (simplified)
     */
    private final double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        return 0.0;
    }
    
    /**
     * Remove all listeners
     */
    public final void removeAllListeners() {
    }
}