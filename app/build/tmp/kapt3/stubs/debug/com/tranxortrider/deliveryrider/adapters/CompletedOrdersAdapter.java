package com.tranxortrider.deliveryrider.adapters;

/**
 * Adapter for displaying completed orders in a RecyclerView
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0001\u0018B\'\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\u0002\u0010\tJ\b\u0010\u000f\u001a\u00020\u0010H\u0016J\u001c\u0010\u0011\u001a\u00020\b2\n\u0010\u0012\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0013\u001a\u00020\u0010H\u0016J\u001c\u0010\u0014\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0010H\u0016R\u0016\u0010\n\u001a\n \f*\u0004\u0018\u00010\u000b0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/tranxortrider/deliveryrider/adapters/CompletedOrdersAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/tranxortrider/deliveryrider/adapters/CompletedOrdersAdapter$OrderViewHolder;", "orders", "", "Lcom/tranxortrider/deliveryrider/models/Order;", "onItemClick", "Lkotlin/Function1;", "", "(Ljava/util/List;Lkotlin/jvm/functions/Function1;)V", "currencyFormatter", "Ljava/text/NumberFormat;", "kotlin.jvm.PlatformType", "dateFormatter", "Ljava/text/SimpleDateFormat;", "getItemCount", "", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "OrderViewHolder", "app_debug"})
public final class CompletedOrdersAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.tranxortrider.deliveryrider.adapters.CompletedOrdersAdapter.OrderViewHolder> {
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.tranxortrider.deliveryrider.models.Order> orders = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function1<com.tranxortrider.deliveryrider.models.Order, kotlin.Unit> onItemClick = null;
    private final java.text.NumberFormat currencyFormatter = null;
    @org.jetbrains.annotations.NotNull()
    private final java.text.SimpleDateFormat dateFormatter = null;
    
    public CompletedOrdersAdapter(@org.jetbrains.annotations.NotNull()
    java.util.List<com.tranxortrider.deliveryrider.models.Order> orders, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.tranxortrider.deliveryrider.models.Order, kotlin.Unit> onItemClick) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public com.tranxortrider.deliveryrider.adapters.CompletedOrdersAdapter.OrderViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.tranxortrider.deliveryrider.adapters.CompletedOrdersAdapter.OrderViewHolder holder, int position) {
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lcom/tranxortrider/deliveryrider/adapters/CompletedOrdersAdapter$OrderViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Lcom/tranxortrider/deliveryrider/adapters/CompletedOrdersAdapter;Landroid/view/View;)V", "completedTimeText", "Landroid/widget/TextView;", "customerNameText", "deliveryAddressText", "earnedAmountText", "orderIdText", "restaurantNameText", "statusBadge", "totalAmountText", "bind", "", "order", "Lcom/tranxortrider/deliveryrider/models/Order;", "app_debug"})
    public final class OrderViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView orderIdText = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView customerNameText = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView restaurantNameText = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView deliveryAddressText = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView totalAmountText = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView completedTimeText = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView earnedAmountText = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView statusBadge = null;
        
        public OrderViewHolder(@org.jetbrains.annotations.NotNull()
        android.view.View itemView) {
            super(null);
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull()
        com.tranxortrider.deliveryrider.models.Order order) {
        }
    }
}