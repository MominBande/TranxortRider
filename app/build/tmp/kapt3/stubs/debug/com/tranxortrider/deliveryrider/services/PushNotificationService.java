package com.tranxortrider.deliveryrider.services;

/**
 * Service for handling Firebase Cloud Messaging (FCM) notifications
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001c\u0010\u0007\u001a\u00020\b2\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\nH\u0002J\u001c\u0010\u000b\u001a\u00020\b2\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\nH\u0002J\u001c\u0010\f\u001a\u00020\b2\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\nH\u0002J\u001c\u0010\r\u001a\u00020\b2\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\nH\u0002J\u001c\u0010\u000e\u001a\u00020\b2\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\nH\u0002J\u0010\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\u0004H\u0016J\u0010\u0010\u0014\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/tranxortrider/deliveryrider/services/PushNotificationService;", "Lcom/google/firebase/messaging/FirebaseMessagingService;", "()V", "TAG", "", "firestoreService", "Lcom/tranxortrider/deliveryrider/services/FirestoreService;", "handleDocumentStatusNotification", "", "data", "", "handleNewOrderNotification", "handleOrderAssignmentNotification", "handleOrderCancellationNotification", "handleVerificationStatusNotification", "onMessageReceived", "remoteMessage", "Lcom/google/firebase/messaging/RemoteMessage;", "onNewToken", "token", "showGenericNotification", "app_debug"})
public final class PushNotificationService extends com.google.firebase.messaging.FirebaseMessagingService {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String TAG = "PushNotificationService";
    @org.jetbrains.annotations.NotNull()
    private final com.tranxortrider.deliveryrider.services.FirestoreService firestoreService = null;
    
    public PushNotificationService() {
        super();
    }
    
    @java.lang.Override()
    public void onMessageReceived(@org.jetbrains.annotations.NotNull()
    com.google.firebase.messaging.RemoteMessage remoteMessage) {
    }
    
    /**
     * Called when a new token is generated
     */
    @java.lang.Override()
    public void onNewToken(@org.jetbrains.annotations.NotNull()
    java.lang.String token) {
    }
    
    /**
     * Handle verification status notifications
     */
    private final void handleVerificationStatusNotification(java.util.Map<java.lang.String, java.lang.String> data) {
    }
    
    /**
     * Handle document status notifications
     */
    private final void handleDocumentStatusNotification(java.util.Map<java.lang.String, java.lang.String> data) {
    }
    
    /**
     * Handle new order notifications
     */
    private final void handleNewOrderNotification(java.util.Map<java.lang.String, java.lang.String> data) {
    }
    
    /**
     * Handle order assignment notifications
     */
    private final void handleOrderAssignmentNotification(java.util.Map<java.lang.String, java.lang.String> data) {
    }
    
    /**
     * Handle order cancellation notifications
     */
    private final void handleOrderCancellationNotification(java.util.Map<java.lang.String, java.lang.String> data) {
    }
    
    /**
     * Show a generic notification for messages that don't have a specific handler
     */
    private final void showGenericNotification(com.google.firebase.messaging.RemoteMessage remoteMessage) {
    }
}