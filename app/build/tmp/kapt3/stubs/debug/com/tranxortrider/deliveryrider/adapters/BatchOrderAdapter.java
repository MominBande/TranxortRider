package com.tranxortrider.deliveryrider.adapters;

/**
 * Adapter for displaying orders in a batch
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0001\u0019B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\u0002\u0010\u0006J\b\u0010\u000b\u001a\u00020\fH\u0016J\u001c\u0010\r\u001a\u00020\t2\n\u0010\u000e\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u000f\u001a\u00020\fH\u0016J\u001c\u0010\u0010\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\fH\u0016J\u001a\u0010\u0014\u001a\u00020\t2\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\t0\bJ\u001a\u0010\u0016\u001a\u00020\t2\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\t0\bJ\u0014\u0010\u0017\u001a\u00020\t2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004R\u001c\u0010\u0007\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\t\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010\n\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\t\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001a"}, d2 = {"Lcom/tranxortrider/deliveryrider/adapters/BatchOrderAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/tranxortrider/deliveryrider/adapters/BatchOrderAdapter$BatchOrderViewHolder;", "orders", "", "Lcom/tranxortrider/deliveryrider/models/Order;", "(Ljava/util/List;)V", "onItemClickListener", "Lkotlin/Function1;", "", "onRemoveClickListener", "getItemCount", "", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "setOnItemClickListener", "listener", "setOnRemoveClickListener", "updateOrders", "newOrders", "BatchOrderViewHolder", "app_debug"})
public final class BatchOrderAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.tranxortrider.deliveryrider.adapters.BatchOrderAdapter.BatchOrderViewHolder> {
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.tranxortrider.deliveryrider.models.Order> orders;
    @org.jetbrains.annotations.Nullable()
    private kotlin.jvm.functions.Function1<? super com.tranxortrider.deliveryrider.models.Order, kotlin.Unit> onItemClickListener;
    @org.jetbrains.annotations.Nullable()
    private kotlin.jvm.functions.Function1<? super com.tranxortrider.deliveryrider.models.Order, kotlin.Unit> onRemoveClickListener;
    
    public BatchOrderAdapter(@org.jetbrains.annotations.NotNull()
    java.util.List<com.tranxortrider.deliveryrider.models.Order> orders) {
        super();
    }
    
    /**
     * Update the orders list and refresh the adapter
     */
    public final void updateOrders(@org.jetbrains.annotations.NotNull()
    java.util.List<com.tranxortrider.deliveryrider.models.Order> newOrders) {
    }
    
    /**
     * Set click listener for item clicks
     */
    public final void setOnItemClickListener(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.tranxortrider.deliveryrider.models.Order, kotlin.Unit> listener) {
    }
    
    /**
     * Set click listener for remove button clicks
     */
    public final void setOnRemoveClickListener(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.tranxortrider.deliveryrider.models.Order, kotlin.Unit> listener) {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public com.tranxortrider.deliveryrider.adapters.BatchOrderAdapter.BatchOrderViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.tranxortrider.deliveryrider.adapters.BatchOrderAdapter.BatchOrderViewHolder holder, int position) {
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/tranxortrider/deliveryrider/adapters/BatchOrderAdapter$BatchOrderViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Lcom/tranxortrider/deliveryrider/adapters/BatchOrderAdapter;Landroid/view/View;)V", "btnRemove", "Landroid/widget/ImageButton;", "orderCard", "Lcom/google/android/material/card/MaterialCardView;", "tvOrderInfo", "Landroid/widget/TextView;", "tvOrderNumber", "tvRestaurantName", "tvSequence", "bind", "", "order", "Lcom/tranxortrider/deliveryrider/models/Order;", "position", "", "app_debug"})
    public final class BatchOrderViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private final com.google.android.material.card.MaterialCardView orderCard = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView tvOrderNumber = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView tvRestaurantName = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView tvOrderInfo = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView tvSequence = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.ImageButton btnRemove = null;
        
        public BatchOrderViewHolder(@org.jetbrains.annotations.NotNull()
        android.view.View itemView) {
            super(null);
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull()
        com.tranxortrider.deliveryrider.models.Order order, int position) {
        }
    }
}