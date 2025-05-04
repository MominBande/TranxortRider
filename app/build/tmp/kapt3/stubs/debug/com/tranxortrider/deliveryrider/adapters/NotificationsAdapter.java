package com.tranxortrider.deliveryrider.adapters;

/**
 * Adapter for displaying notifications in a RecyclerView
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0001\u001eB\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\u0002\u0010\u0006J\b\u0010\u0010\u001a\u00020\u000bH\u0016J\u001c\u0010\u0011\u001a\u00020\f2\n\u0010\u0012\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0013\u001a\u00020\u000bH\u0016J\u001c\u0010\u0014\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u000bH\u0016J \u0010\u0018\u001a\u00020\f2\u0018\u0010\u0019\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\nJ\u001a\u0010\u001a\u001a\u00020\f2\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\f0\u000eJ \u0010\u001b\u001a\u00020\f2\u0018\u0010\u0019\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\nJ\u0014\u0010\u001c\u001a\u00020\f2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\"\u0010\t\u001a\u0016\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010\r\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\f\u0018\u00010\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\"\u0010\u000f\u001a\u0016\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001f"}, d2 = {"Lcom/tranxortrider/deliveryrider/adapters/NotificationsAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/tranxortrider/deliveryrider/adapters/NotificationsAdapter$NotificationViewHolder;", "notifications", "", "Lcom/tranxortrider/deliveryrider/models/Notification;", "(Ljava/util/List;)V", "dateFormatter", "Ljava/text/SimpleDateFormat;", "onDeleteClickListener", "Lkotlin/Function2;", "", "", "onItemClickListener", "Lkotlin/Function1;", "onMarkReadClickListener", "getItemCount", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "setOnDeleteClickListener", "listener", "setOnItemClickListener", "setOnMarkReadClickListener", "updateNotifications", "newNotifications", "NotificationViewHolder", "app_debug"})
public final class NotificationsAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.tranxortrider.deliveryrider.adapters.NotificationsAdapter.NotificationViewHolder> {
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.tranxortrider.deliveryrider.models.Notification> notifications;
    @org.jetbrains.annotations.Nullable()
    private kotlin.jvm.functions.Function1<? super com.tranxortrider.deliveryrider.models.Notification, kotlin.Unit> onItemClickListener;
    @org.jetbrains.annotations.Nullable()
    private kotlin.jvm.functions.Function2<? super com.tranxortrider.deliveryrider.models.Notification, ? super java.lang.Integer, kotlin.Unit> onMarkReadClickListener;
    @org.jetbrains.annotations.Nullable()
    private kotlin.jvm.functions.Function2<? super com.tranxortrider.deliveryrider.models.Notification, ? super java.lang.Integer, kotlin.Unit> onDeleteClickListener;
    @org.jetbrains.annotations.NotNull()
    private final java.text.SimpleDateFormat dateFormatter = null;
    
    public NotificationsAdapter(@org.jetbrains.annotations.NotNull()
    java.util.List<com.tranxortrider.deliveryrider.models.Notification> notifications) {
        super();
    }
    
    /**
     * Update the notifications list and refresh the adapter
     */
    public final void updateNotifications(@org.jetbrains.annotations.NotNull()
    java.util.List<com.tranxortrider.deliveryrider.models.Notification> newNotifications) {
    }
    
    /**
     * Set click listener for item clicks
     */
    public final void setOnItemClickListener(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.tranxortrider.deliveryrider.models.Notification, kotlin.Unit> listener) {
    }
    
    /**
     * Set click listener for mark as read button
     */
    public final void setOnMarkReadClickListener(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super com.tranxortrider.deliveryrider.models.Notification, ? super java.lang.Integer, kotlin.Unit> listener) {
    }
    
    /**
     * Set click listener for delete button
     */
    public final void setOnDeleteClickListener(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super com.tranxortrider.deliveryrider.models.Notification, ? super java.lang.Integer, kotlin.Unit> listener) {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public com.tranxortrider.deliveryrider.adapters.NotificationsAdapter.NotificationViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.tranxortrider.deliveryrider.adapters.NotificationsAdapter.NotificationViewHolder holder, int position) {
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/tranxortrider/deliveryrider/adapters/NotificationsAdapter$NotificationViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Lcom/tranxortrider/deliveryrider/adapters/NotificationsAdapter;Landroid/view/View;)V", "cardView", "Landroidx/cardview/widget/CardView;", "deleteButton", "Landroid/widget/ImageButton;", "iconView", "Landroid/widget/ImageView;", "markReadButton", "messageText", "Landroid/widget/TextView;", "timeText", "titleText", "unreadIndicator", "bind", "", "notification", "Lcom/tranxortrider/deliveryrider/models/Notification;", "position", "", "app_debug"})
    public final class NotificationViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private final androidx.cardview.widget.CardView cardView = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.ImageView iconView = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView titleText = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView messageText = null;
        @org.jetbrains.annotations.Nullable()
        private final android.widget.TextView timeText = null;
        @org.jetbrains.annotations.Nullable()
        private final android.view.View unreadIndicator = null;
        @org.jetbrains.annotations.Nullable()
        private final android.widget.ImageButton markReadButton = null;
        @org.jetbrains.annotations.Nullable()
        private final android.widget.ImageButton deleteButton = null;
        
        public NotificationViewHolder(@org.jetbrains.annotations.NotNull()
        android.view.View itemView) {
            super(null);
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull()
        com.tranxortrider.deliveryrider.models.Notification notification, int position) {
        }
    }
}