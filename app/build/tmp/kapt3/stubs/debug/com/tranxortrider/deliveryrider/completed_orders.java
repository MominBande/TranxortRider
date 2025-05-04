package com.tranxortrider.deliveryrider;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u000bH\u0002J\u0010\u0010\u001a\u001a\u00020\u00182\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\b\u0010\u001d\u001a\u00020\u0018H\u0002J\b\u0010\u001e\u001a\u00020\u0018H\u0002J\u0010\u0010\u001f\u001a\u00020\u00182\u0006\u0010 \u001a\u00020\u001cH\u0002J\b\u0010!\u001a\u00020\u0018H\u0002J\u0012\u0010\"\u001a\u00020\u00182\b\u0010#\u001a\u0004\u0018\u00010$H\u0014J\b\u0010%\u001a\u00020\u000bH\u0016J\b\u0010&\u001a\u00020\u0018H\u0002J\u001e\u0010\'\u001a\u00020\u00182\f\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00050)2\u0006\u0010\u0019\u001a\u00020\u000bH\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006*"}, d2 = {"Lcom/tranxortrider/deliveryrider/completed_orders;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "completedOrders", "", "Lcom/tranxortrider/deliveryrider/models/Order;", "currentPage", "", "emptyStateText", "Landroid/widget/TextView;", "isLoading", "", "orderRepository", "Lcom/tranxortrider/deliveryrider/repositories/OrderRepository;", "ordersAdapter", "Lcom/tranxortrider/deliveryrider/adapters/CompletedOrdersAdapter;", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "sessionManager", "Lcom/tranxortrider/deliveryrider/utils/SessionManager;", "swipeRefreshLayout", "Landroidx/swiperefreshlayout/widget/SwipeRefreshLayout;", "totalPages", "fetchCompletedOrders", "", "clearExisting", "handleError", "message", "", "initializeUI", "loadMoreOrders", "navigateToOrderDetails", "orderId", "navigateToSignIn", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onSupportNavigateUp", "setupRecyclerView", "updateOrders", "orders", "", "app_debug"})
public final class completed_orders extends androidx.appcompat.app.AppCompatActivity {
    private androidx.recyclerview.widget.RecyclerView recyclerView;
    private androidx.swiperefreshlayout.widget.SwipeRefreshLayout swipeRefreshLayout;
    private android.widget.TextView emptyStateText;
    private com.tranxortrider.deliveryrider.adapters.CompletedOrdersAdapter ordersAdapter;
    private com.tranxortrider.deliveryrider.repositories.OrderRepository orderRepository;
    private com.tranxortrider.deliveryrider.utils.SessionManager sessionManager;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.tranxortrider.deliveryrider.models.Order> completedOrders = null;
    private int currentPage = 1;
    private int totalPages = 1;
    private boolean isLoading = false;
    
    public completed_orders() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void initializeUI() {
    }
    
    private final void setupRecyclerView() {
    }
    
    private final void fetchCompletedOrders(boolean clearExisting) {
    }
    
    private final void loadMoreOrders() {
    }
    
    private final void updateOrders(java.util.List<com.tranxortrider.deliveryrider.models.Order> orders, boolean clearExisting) {
    }
    
    private final void handleError(java.lang.String message) {
    }
    
    private final void navigateToOrderDetails(java.lang.String orderId) {
    }
    
    private final void navigateToSignIn() {
    }
    
    @java.lang.Override()
    public boolean onSupportNavigateUp() {
        return false;
    }
}