package com.tranxortrider.deliveryrider.adapters;

/**
 * Adapter for displaying pending orders in a RecyclerView
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0001\u001cBO\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\u0002\u0010\u000bJ\b\u0010\u0011\u001a\u00020\u0012H\u0016J\u001c\u0010\u0013\u001a\u00020\b2\n\u0010\u0014\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0015\u001a\u00020\u0012H\u0016J\u001c\u0010\u0016\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0012H\u0016J\u0014\u0010\u001a\u001a\u00020\b2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004R\u0016\u0010\f\u001a\n \u000e*\u0004\u0018\u00010\r0\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001d"}, d2 = {"Lcom/tranxortrider/deliveryrider/adapters/PendingOrdersAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/tranxortrider/deliveryrider/adapters/PendingOrdersAdapter$OrderViewHolder;", "orders", "", "Lcom/tranxortrider/deliveryrider/models/Order;", "onAcceptClick", "Lkotlin/Function1;", "", "onRejectClick", "onItemClick", "(Ljava/util/List;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "currencyFormatter", "Ljava/text/NumberFormat;", "kotlin.jvm.PlatformType", "dateFormatter", "Ljava/text/SimpleDateFormat;", "getItemCount", "", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "updateOrders", "newOrders", "OrderViewHolder", "app_debug"})
public final class PendingOrdersAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.tranxortrider.deliveryrider.adapters.PendingOrdersAdapter.OrderViewHolder> {
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.tranxortrider.deliveryrider.models.Order> orders;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function1<com.tranxortrider.deliveryrider.models.Order, kotlin.Unit> onAcceptClick = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function1<com.tranxortrider.deliveryrider.models.Order, kotlin.Unit> onRejectClick = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function1<com.tranxortrider.deliveryrider.models.Order, kotlin.Unit> onItemClick = null;
    private final java.text.NumberFormat currencyFormatter = null;
    @org.jetbrains.annotations.NotNull()
    private final java.text.SimpleDateFormat dateFormatter = null;
    
    public PendingOrdersAdapter(@org.jetbrains.annotations.NotNull()
    java.util.List<com.tranxortrider.deliveryrider.models.Order> orders, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.tranxortrider.deliveryrider.models.Order, kotlin.Unit> onAcceptClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.tranxortrider.deliveryrider.models.Order, kotlin.Unit> onRejectClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.tranxortrider.deliveryrider.models.Order, kotlin.Unit> onItemClick) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public com.tranxortrider.deliveryrider.adapters.PendingOrdersAdapter.OrderViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.tranxortrider.deliveryrider.adapters.PendingOrdersAdapter.OrderViewHolder holder, int position) {
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    /**
     * Updates the orders list and refreshes the adapter
     */
    public final void updateOrders(@org.jetbrains.annotations.NotNull()
    java.util.List<com.tranxortrider.deliveryrider.models.Order> newOrders) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/tranxortrider/deliveryrider/adapters/PendingOrdersAdapter$OrderViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Lcom/tranxortrider/deliveryrider/adapters/PendingOrdersAdapter;Landroid/view/View;)V", "customerAddressText", "Landroid/widget/TextView;", "deliveryFeeText", "distanceText", "estimatedTimeText", "orderAmountText", "orderIdText", "restaurantAddressText", "restaurantNameText", "timeText", "bind", "", "order", "Lcom/tranxortrider/deliveryrider/models/Order;", "app_debug"})
    public final class OrderViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView orderIdText = null;
        @org.jetbrains.annotations.Nullable()
        private final android.widget.TextView restaurantNameText = null;
        @org.jetbrains.annotations.Nullable()
        private final android.widget.TextView restaurantAddressText = null;
        @org.jetbrains.annotations.Nullable()
        private final android.widget.TextView customerAddressText = null;
        @org.jetbrains.annotations.Nullable()
        private final android.widget.TextView orderAmountText = null;
        @org.jetbrains.annotations.Nullable()
        private final android.widget.TextView deliveryFeeText = null;
        @org.jetbrains.annotations.Nullable()
        private final android.widget.TextView distanceText = null;
        @org.jetbrains.annotations.Nullable()
        private final android.widget.TextView estimatedTimeText = null;
        @org.jetbrains.annotations.Nullable()
        private final android.widget.TextView timeText = null;
        
        public OrderViewHolder(@org.jetbrains.annotations.NotNull()
        android.view.View itemView) {
            super(null);
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull()
        com.tranxortrider.deliveryrider.models.Order order) {
        }
    }
}