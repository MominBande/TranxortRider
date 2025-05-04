package com.tranxortrider.deliveryrider.repositories;

/**
 * Repository for handling notification data
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J,\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\u0018\u0010\u000b\u001a\u0014\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\b0\fJ(\u0010\u000e\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\n2\u0018\u0010\u000b\u001a\u0014\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\b0\fJ\u000e\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u0002J:\u0010\u0013\u001a\u00020\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2&\u0010\u000b\u001a\"\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\n\u0012\f\u0012\n\u0012\u0004\u0012\u00020\u0012\u0018\u00010\u0011\u0012\u0004\u0012\u00020\b0\u0014J&\u0010\u0015\u001a\u00020\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\b0\u0016J(\u0010\u0018\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\n2\u0018\u0010\u000b\u001a\u0014\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\b0\fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/tranxortrider/deliveryrider/repositories/NotificationRepository;", "", "()V", "db", "Lcom/google/firebase/firestore/FirebaseFirestore;", "notificationsCollection", "Lcom/google/firebase/firestore/CollectionReference;", "clearAllNotifications", "", "userId", "", "callback", "Lkotlin/Function2;", "", "deleteNotification", "notificationId", "getMockNotifications", "", "Lcom/tranxortrider/deliveryrider/models/Notification;", "getNotifications", "Lkotlin/Function3;", "getUnreadCount", "Lkotlin/Function1;", "", "markAsRead", "app_debug"})
public final class NotificationRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.firestore.FirebaseFirestore db = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.firestore.CollectionReference notificationsCollection = null;
    
    public NotificationRepository() {
        super();
    }
    
    /**
     * Get all notifications for the current user
     *
     * @param callback Callback with success status, message, and notifications list
     */
    public final void getNotifications(@org.jetbrains.annotations.Nullable()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function3<? super java.lang.Boolean, ? super java.lang.String, ? super java.util.List<com.tranxortrider.deliveryrider.models.Notification>, kotlin.Unit> callback) {
    }
    
    /**
     * Mark a notification as read
     *
     * @param notificationId ID of the notification to mark as read
     * @param callback Callback with success status and message
     */
    public final void markAsRead(@org.jetbrains.annotations.NotNull()
    java.lang.String notificationId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.Boolean, ? super java.lang.String, kotlin.Unit> callback) {
    }
    
    /**
     * Delete a notification
     *
     * @param notificationId ID of the notification to delete
     * @param callback Callback with success status and message
     */
    public final void deleteNotification(@org.jetbrains.annotations.NotNull()
    java.lang.String notificationId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.Boolean, ? super java.lang.String, kotlin.Unit> callback) {
    }
    
    /**
     * Clear all notifications for the current user
     *
     * @param callback Callback with success status and message
     */
    public final void clearAllNotifications(@org.jetbrains.annotations.Nullable()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.Boolean, ? super java.lang.String, kotlin.Unit> callback) {
    }
    
    /**
     * Get unread notification count for the current user
     *
     * @param callback Callback with the count of unread notifications
     */
    public final void getUnreadCount(@org.jetbrains.annotations.Nullable()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> callback) {
    }
    
    /**
     * Mock data for testing - used as fallback only
     */
    private final java.util.List<com.tranxortrider.deliveryrider.models.Notification> getMockNotifications() {
        return null;
    }
}