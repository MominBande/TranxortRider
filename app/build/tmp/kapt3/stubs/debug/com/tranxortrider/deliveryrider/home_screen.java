package com.tranxortrider.deliveryrider;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u00ba\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\r\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u001a\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010;\u001a\u00020<2\u0006\u0010=\u001a\u00020\u000eH\u0002J\u0010\u0010>\u001a\u00020<2\u0006\u0010?\u001a\u00020\u0005H\u0002J\b\u0010@\u001a\u00020<H\u0002J\b\u0010A\u001a\u00020<H\u0002J\b\u0010B\u001a\u00020<H\u0002J\b\u0010C\u001a\u00020<H\u0002J\b\u0010D\u001a\u00020<H\u0002J\u001e\u0010E\u001a\u00020<2\n\b\u0002\u0010F\u001a\u0004\u0018\u00010\u000e2\b\b\u0002\u0010G\u001a\u00020\u001bH\u0002JL\u0010H\u001a\u00020<2\n\b\u0002\u0010F\u001a\u0004\u0018\u00010\u000e2\b\b\u0002\u0010I\u001a\u00020J2\b\b\u0002\u0010K\u001a\u00020J2\"\u0010L\u001a\u001e\u0012\u0004\u0012\u00020\u001b\u0012\u0006\u0012\u0004\u0018\u00010\u000e\u0012\u0006\u0012\u0004\u0018\u00010N\u0012\u0004\u0012\u00020<0MH\u0002J\b\u0010O\u001a\u00020JH\u0002J\b\u0010P\u001a\u00020<H\u0002J\b\u0010Q\u001a\u00020<H\u0002J\u0010\u0010R\u001a\u00020<2\u0006\u0010?\u001a\u00020\u0005H\u0002J\b\u0010S\u001a\u00020<H\u0002J\b\u0010T\u001a\u00020<H\u0002J\u0012\u0010U\u001a\u00020<2\b\u0010V\u001a\u0004\u0018\u00010WH\u0014J\b\u0010X\u001a\u00020<H\u0014J\b\u0010Y\u001a\u00020<H\u0014J\b\u0010Z\u001a\u00020<H\u0014J\u0018\u0010[\u001a\u00020<2\u0006\u0010=\u001a\u00020\u000e2\u0006\u0010\\\u001a\u00020\u000eH\u0002J\u0018\u0010]\u001a\u00020<2\u0006\u0010=\u001a\u00020\u000e2\u0006\u0010\\\u001a\u00020\u000eH\u0002J\u0018\u0010^\u001a\u00020<2\u0006\u0010?\u001a\u00020\u00052\u0006\u0010\\\u001a\u00020\u000eH\u0002J\b\u0010_\u001a\u00020<H\u0002J\b\u0010`\u001a\u00020<H\u0002J\b\u0010a\u001a\u00020<H\u0002J\b\u0010b\u001a\u00020<H\u0002J\b\u0010c\u001a\u00020<H\u0002J\u0010\u0010G\u001a\u00020<2\u0006\u0010d\u001a\u00020\u001bH\u0002J\u0010\u0010e\u001a\u00020<2\u0006\u0010?\u001a\u00020\u0005H\u0002J\u0010\u0010f\u001a\u00020<2\u0006\u0010=\u001a\u00020\u000eH\u0002J\b\u0010g\u001a\u00020<H\u0002J\b\u0010h\u001a\u00020<H\u0002J\b\u0010i\u001a\u00020<H\u0002J\b\u0010j\u001a\u00020<H\u0002J\b\u0010k\u001a\u00020<H\u0002J\b\u0010l\u001a\u00020<H\u0002J\u0010\u0010m\u001a\u00020<2\u0006\u0010n\u001a\u00020\u000eH\u0002J\b\u0010o\u001a\u00020<H\u0002J\b\u0010p\u001a\u00020<H\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0012X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0012X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0012X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0010X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020#X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020%X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\'X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020)X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020+X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010,\u001a\u0004\u0018\u00010-X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010.\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e000/X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u00101\u001a\b\u0012\u0004\u0012\u00020\u000e00X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u00102R\u000e\u00103\u001a\u000204X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u00105\u001a\u000206X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u00107\u001a\u00020!X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u00108\u001a\u00020!X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u00109\u001a\u00020!X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010:\u001a\u00020!X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006q"}, d2 = {"Lcom/tranxortrider/deliveryrider/home_screen;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "allOrders", "", "Lcom/tranxortrider/deliveryrider/models/Order;", "bottomNavigation", "Lcom/google/android/material/bottomnavigation/BottomNavigationView;", "btnDutyStatus", "Landroidx/appcompat/widget/SwitchCompat;", "btnNotifications", "Lcom/google/android/material/button/MaterialButton;", "btnSearch", "currentFilter", "", "emptyOrdersView", "Landroid/view/View;", "filterButtonAccepted", "Landroid/widget/Button;", "filterButtonAll", "filterButtonCompleted", "filterButtonPending", "firebaseListenerService", "Lcom/tranxortrider/deliveryrider/services/FirestoreListenerService;", "firestoreService", "Lcom/tranxortrider/deliveryrider/services/FirestoreService;", "hasDeliveryStatsViews", "", "hasRecyclerView", "isOnDuty", "isReceiverRegistered", "loadingView", "notificationCount", "Landroid/widget/TextView;", "orderRepository", "Lcom/tranxortrider/deliveryrider/repositories/OrderRepository;", "orderStatusReceiver", "Landroid/content/BroadcastReceiver;", "pendingOrdersAdapter", "Lcom/tranxortrider/deliveryrider/adapters/PendingOrdersAdapter;", "pendingOrdersRecyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "profileImage", "Lcom/google/android/material/imageview/ShapeableImageView;", "refreshTimer", "Ljava/util/Timer;", "requestPermissionLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "", "requiredPermissions", "[Ljava/lang/String;", "sessionManager", "Lcom/tranxortrider/deliveryrider/utils/SessionManager;", "sharedPreferencesManager", "Lcom/tranxortrider/deliveryrider/utils/SharedPreferencesManager;", "tvDeliveryCount", "tvDutyStatus", "tvName", "tvOnTimeRate", "acceptAdminAssignedOrder", "", "orderId", "acceptOrder", "order", "applyCurrentFilter", "checkAndRequestLocationPermission", "checkNotifications", "checkUserVerificationStatus", "fetchDeliveryStats", "fetchOrders", "status", "showLoading", "getOrders", "page", "", "limit", "callback", "Lkotlin/Function3;", "", "getStatusBarHeight", "initializeUI", "loadUserData", "navigateToOrderDetails", "navigateToSignIn", "navigateToVerification", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onPause", "onResume", "rejectAdminAssignedOrder", "reason", "rejectAssignedOrder", "rejectOrder", "resetFilterButtonStates", "setOffDuty", "setupBottomNavigation", "setupClickListeners", "showEmptyState", "show", "showRejectDialog", "showRejectReasonDialog", "startAutoRefresh", "startLocationServiceIfEnabled", "startOrderAssignmentListening", "startOrderRefreshTimer", "stopAutoRefresh", "toggleDutyStatus", "updateFilter", "filter", "updateFilterCounts", "updateUI", "app_debug"})
public final class home_screen extends androidx.appcompat.app.AppCompatActivity {
    private android.widget.TextView tvName;
    private android.widget.TextView tvDeliveryCount;
    private android.widget.TextView tvOnTimeRate;
    private androidx.appcompat.widget.SwitchCompat btnDutyStatus;
    private android.widget.TextView tvDutyStatus;
    private com.google.android.material.button.MaterialButton btnSearch;
    private com.google.android.material.button.MaterialButton btnNotifications;
    private android.widget.TextView notificationCount;
    private com.google.android.material.imageview.ShapeableImageView profileImage;
    private androidx.recyclerview.widget.RecyclerView pendingOrdersRecyclerView;
    private android.view.View emptyOrdersView;
    private android.view.View loadingView;
    private android.widget.Button filterButtonAll;
    private android.widget.Button filterButtonPending;
    private android.widget.Button filterButtonAccepted;
    private android.widget.Button filterButtonCompleted;
    private com.google.android.material.bottomnavigation.BottomNavigationView bottomNavigation;
    private com.tranxortrider.deliveryrider.utils.SessionManager sessionManager;
    private com.tranxortrider.deliveryrider.repositories.OrderRepository orderRepository;
    private com.tranxortrider.deliveryrider.utils.SharedPreferencesManager sharedPreferencesManager;
    private com.tranxortrider.deliveryrider.services.FirestoreService firestoreService;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String[] requiredPermissions = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<java.lang.String[]> requestPermissionLauncher = null;
    private com.tranxortrider.deliveryrider.adapters.PendingOrdersAdapter pendingOrdersAdapter;
    private boolean isOnDuty = true;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String currentFilter = "all";
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.tranxortrider.deliveryrider.models.Order> allOrders;
    private boolean hasRecyclerView = false;
    @org.jetbrains.annotations.NotNull()
    private final android.content.BroadcastReceiver orderStatusReceiver = null;
    @org.jetbrains.annotations.NotNull()
    private com.tranxortrider.deliveryrider.services.FirestoreListenerService firebaseListenerService;
    @org.jetbrains.annotations.Nullable()
    private java.util.Timer refreshTimer;
    private boolean hasDeliveryStatsViews = false;
    private boolean isReceiverRegistered = false;
    
    public home_screen() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void initializeUI() {
    }
    
    private final void setupBottomNavigation() {
    }
    
    private final void setupClickListeners() {
    }
    
    private final void loadUserData() {
    }
    
    private final void checkNotifications() {
    }
    
    private final void fetchDeliveryStats() {
    }
    
    private final void toggleDutyStatus() {
    }
    
    private final void setOffDuty() {
    }
    
    private final void fetchOrders(java.lang.String status, boolean showLoading) {
    }
    
    private final void applyCurrentFilter() {
    }
    
    /**
     * Updates the UI based on orders data
     */
    private final void updateUI() {
    }
    
    private final void showLoading(boolean show) {
    }
    
    private final void updateFilter(java.lang.String filter) {
    }
    
    private final void resetFilterButtonStates() {
    }
    
    /**
     * Update the filter button texts with order counts
     */
    private final void updateFilterCounts() {
    }
    
    private final void showEmptyState() {
    }
    
    private final void startOrderRefreshTimer() {
    }
    
    private final void navigateToVerification() {
    }
    
    private final void navigateToSignIn() {
    }
    
    @java.lang.Override()
    protected void onResume() {
    }
    
    @java.lang.Override()
    protected void onPause() {
    }
    
    /**
     * Check if we have the required permissions, and request them if not
     */
    private final void checkAndRequestLocationPermission() {
    }
    
    /**
     * Start the location service if enabled in settings
     */
    private final void startLocationServiceIfEnabled() {
    }
    
    @java.lang.Override()
    protected void onDestroy() {
    }
    
    /**
     * Gets orders from the repository based on the given status
     */
    private final void getOrders(java.lang.String status, int page, int limit, kotlin.jvm.functions.Function3<? super java.lang.Boolean, ? super java.lang.String, java.lang.Object, kotlin.Unit> callback) {
    }
    
    /**
     * Navigates to the order details activity
     */
    private final void navigateToOrderDetails(com.tranxortrider.deliveryrider.models.Order order) {
    }
    
    /**
     * Accepts an order
     */
    private final void acceptOrder(com.tranxortrider.deliveryrider.models.Order order) {
    }
    
    /**
     * Shows a reject dialog
     */
    private final void showRejectDialog(com.tranxortrider.deliveryrider.models.Order order) {
    }
    
    /**
     * Rejects an assigned order
     */
    private final void rejectAssignedOrder(java.lang.String orderId, java.lang.String reason) {
    }
    
    /**
     * Rejects an order
     */
    private final void rejectOrder(com.tranxortrider.deliveryrider.models.Order order, java.lang.String reason) {
    }
    
    /**
     * Starts listening for real-time order assignments from admin
     */
    private final void startOrderAssignmentListening() {
    }
    
    /**
     * Shows a dialog to collect reject reason for admin-assigned orders
     */
    private final void showRejectReasonDialog(java.lang.String orderId) {
    }
    
    /**
     * Accepts an admin-assigned order
     */
    private final void acceptAdminAssignedOrder(java.lang.String orderId) {
    }
    
    /**
     * Rejects an admin-assigned order
     */
    private final void rejectAdminAssignedOrder(java.lang.String orderId, java.lang.String reason) {
    }
    
    /**
     * Sets up a timer to refresh orders every 10 seconds
     */
    private final void startAutoRefresh() {
    }
    
    /**
     * Stops the auto-refresh timer
     */
    private final void stopAutoRefresh() {
    }
    
    private final void checkUserVerificationStatus() {
    }
    
    private final int getStatusBarHeight() {
        return 0;
    }
}