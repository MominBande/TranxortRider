package com.tranxortrider.deliveryrider.utils;

/**
 * Utility class to handle bottom navigation consistently across activities
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\bH\u0002J\u001e\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eJ\u0016\u0010\u000f\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\fJ\u0016\u0010\u0010\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u0012J\u0018\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0002\u00a8\u0006\u0018"}, d2 = {"Lcom/tranxortrider/deliveryrider/utils/NavigationUtils;", "", "()V", "navigateTo", "", "currentActivity", "Landroid/app/Activity;", "targetActivityClass", "Ljava/lang/Class;", "navigateToOrderAddress", "activity", "order", "Lcom/tranxortrider/deliveryrider/models/Order;", "isRestaurant", "", "navigateWithMultipleStops", "setupBottomNavigation", "bottomNavigationView", "Lcom/google/android/material/bottomnavigation/BottomNavigationView;", "showNavigationError", "context", "Landroid/content/Context;", "message", "", "app_debug"})
public final class NavigationUtils {
    @org.jetbrains.annotations.NotNull()
    public static final com.tranxortrider.deliveryrider.utils.NavigationUtils INSTANCE = null;
    
    private NavigationUtils() {
        super();
    }
    
    /**
     * Set up the bottom navigation with proper item selection and click listeners
     */
    public final void setupBottomNavigation(@org.jetbrains.annotations.NotNull()
    android.app.Activity activity, @org.jetbrains.annotations.NotNull()
    com.google.android.material.bottomnavigation.BottomNavigationView bottomNavigationView) {
    }
    
    /**
     * Navigate to a new activity and finish the current one
     */
    private final void navigateTo(android.app.Activity currentActivity, java.lang.Class<?> targetActivityClass) {
    }
    
    /**
     * Navigate to the restaurant or customer address for an order using any available map app
     *
     * @param activity The activity to use for starting the intent
     * @param order The order to navigate to
     * @param isRestaurant If true, navigate to restaurant; if false, navigate to customer
     */
    public final void navigateToOrderAddress(@org.jetbrains.annotations.NotNull()
    android.app.Activity activity, @org.jetbrains.annotations.NotNull()
    com.tranxortrider.deliveryrider.models.Order order, boolean isRestaurant) {
    }
    
    /**
     * Show a navigation error message
     */
    private final void showNavigationError(android.content.Context context, java.lang.String message) {
    }
    
    /**
     * Navigate to the restaurant for pickup and then to the customer for delivery
     * This attempts to create a multi-stop route using available map apps
     *
     * @param activity The activity to use for starting the intent
     * @param order The order to navigate to
     */
    public final void navigateWithMultipleStops(@org.jetbrains.annotations.NotNull()
    android.app.Activity activity, @org.jetbrains.annotations.NotNull()
    com.tranxortrider.deliveryrider.models.Order order) {
    }
}