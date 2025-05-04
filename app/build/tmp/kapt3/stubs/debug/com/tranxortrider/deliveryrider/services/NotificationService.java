package com.tranxortrider.deliveryrider.services;

/**
 * Service for handling notifications
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0002J\u000e\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tJ \u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\t2\b\b\u0002\u0010\r\u001a\u00020\tJ\u0016\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\tJ2\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\u00122\b\b\u0002\u0010\r\u001a\u00020\t2\b\b\u0002\u0010\u0013\u001a\u00020\u0014J\u001e\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/tranxortrider/deliveryrider/services/NotificationService;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "createNotificationChannels", "", "showAccountVerificationNotification", "status", "", "showDeliveryAreaNotification", "title", "message", "channelId", "showDocumentVerificationNotification", "documentType", "showOrderNotification", "intent", "Landroid/content/Intent;", "alertUser", "", "showVerificationStatusNotification", "Companion", "app_debug"})
public final class NotificationService {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String CHANNEL_ID = "tranxort_rider_channel";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String CHANNEL_NAME = "TranxortRider Notifications";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String CHANNEL_DESCRIPTION = "Notifications for TranxortRider app";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String ORDER_CHANNEL_ID = "tranxort_rider_order_channel";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String ORDER_CHANNEL_NAME = "Order Notifications";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String ORDER_CHANNEL_DESCRIPTION = "Notifications for orders";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String ORDER_ASSIGNMENT_CHANNEL_ID = "tranxort_rider_assignment_channel";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String ORDER_ASSIGNMENT_CHANNEL_NAME = "Order Assignment Notifications";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String ORDER_ASSIGNMENT_CHANNEL_DESCRIPTION = "Notifications for order assignments";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String DELIVERY_AREA_CHANNEL_ID = "tranxort_rider_delivery_area_channel";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String DELIVERY_AREA_CHANNEL_NAME = "Delivery Area Notifications";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String DELIVERY_AREA_CHANNEL_DESCRIPTION = "Notifications for delivery area transitions";
    public static final int NOTIFICATION_VERIFICATION_ID = 1001;
    public static final int NOTIFICATION_NEW_ORDER_ID = 1002;
    public static final int NOTIFICATION_ORDER_UPDATE_ID = 1003;
    public static final int NOTIFICATION_ORDER_ASSIGNMENT_ID = 1004;
    public static final int NOTIFICATION_DELIVERY_AREA_ID = 1005;
    @org.jetbrains.annotations.NotNull()
    public static final com.tranxortrider.deliveryrider.services.NotificationService.Companion Companion = null;
    
    public NotificationService(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    /**
     * Create the notification channels
     */
    private final void createNotificationChannels() {
    }
    
    /**
     * Show a verification status notification
     *
     * @param title The notification title
     * @param message The notification message
     * @param status The verification status
     */
    public final void showVerificationStatusNotification(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String message, @org.jetbrains.annotations.NotNull()
    java.lang.String status) {
    }
    
    /**
     * Show a document verification notification
     *
     * @param documentType The type of document
     * @param status The verification status
     */
    public final void showDocumentVerificationNotification(@org.jetbrains.annotations.NotNull()
    java.lang.String documentType, @org.jetbrains.annotations.NotNull()
    java.lang.String status) {
    }
    
    /**
     * Show an account verification notification
     *
     * @param status The verification status
     */
    public final void showAccountVerificationNotification(@org.jetbrains.annotations.NotNull()
    java.lang.String status) {
    }
    
    /**
     * Show an order notification
     *
     * @param title The notification title
     * @param message The notification message
     * @param intent The intent to launch when the notification is tapped
     * @param channelId The channel ID to use
     * @param alertUser Whether to play sound and vibrate
     */
    public final void showOrderNotification(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String message, @org.jetbrains.annotations.NotNull()
    android.content.Intent intent, @org.jetbrains.annotations.NotNull()
    java.lang.String channelId, boolean alertUser) {
    }
    
    /**
     * Show a delivery area notification
     */
    public final void showDeliveryAreaNotification(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String message, @org.jetbrains.annotations.NotNull()
    java.lang.String channelId) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u000b\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/tranxortrider/deliveryrider/services/NotificationService$Companion;", "", "()V", "CHANNEL_DESCRIPTION", "", "CHANNEL_ID", "CHANNEL_NAME", "DELIVERY_AREA_CHANNEL_DESCRIPTION", "DELIVERY_AREA_CHANNEL_ID", "DELIVERY_AREA_CHANNEL_NAME", "NOTIFICATION_DELIVERY_AREA_ID", "", "NOTIFICATION_NEW_ORDER_ID", "NOTIFICATION_ORDER_ASSIGNMENT_ID", "NOTIFICATION_ORDER_UPDATE_ID", "NOTIFICATION_VERIFICATION_ID", "ORDER_ASSIGNMENT_CHANNEL_DESCRIPTION", "ORDER_ASSIGNMENT_CHANNEL_ID", "ORDER_ASSIGNMENT_CHANNEL_NAME", "ORDER_CHANNEL_DESCRIPTION", "ORDER_CHANNEL_ID", "ORDER_CHANNEL_NAME", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}