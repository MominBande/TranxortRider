package com.tranxortrider.deliveryrider.viewmodels;

/**
 * ViewModel for handling navigation logic
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J(\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020\t2\u0006\u0010,\u001a\u00020\t2\u0006\u0010-\u001a\u00020\t2\u0006\u0010.\u001a\u00020\tH\u0002J\u0006\u0010/\u001a\u00020*J\b\u00100\u001a\u00020*H\u0014J\u0006\u00101\u001a\u00020*J\u0006\u00102\u001a\u00020*J\"\u00103\u001a\u00020*2\u0006\u0010-\u001a\u00020\t2\u0006\u0010.\u001a\u00020\t2\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0007J\u000e\u00104\u001a\u00020*2\u0006\u00105\u001a\u000206J\u0006\u00107\u001a\u00020*J\u0018\u00108\u001a\u00020*2\u0006\u00109\u001a\u00020\u00072\u0006\u0010:\u001a\u00020\u0010H\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u000e0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u000f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00100\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\t0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00050\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00070\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0015R\u000e\u0010\u0018\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\t0\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0015R\u0019\u0010\u001c\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0015R\u0017\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\f0\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0015R\u0010\u0010 \u001a\u0004\u0018\u00010!X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001d\u0010\"\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u000e0\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0015R\u000e\u0010$\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0019\u0010%\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00100\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u0015R\u0017\u0010\'\u001a\b\u0012\u0004\u0012\u00020\t0\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010\u0015\u00a8\u0006;"}, d2 = {"Lcom/tranxortrider/deliveryrider/viewmodels/NavigationViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "_currentInstruction", "Landroidx/lifecycle/MutableLiveData;", "", "_currentLocation", "Landroid/location/Location;", "_distanceToDestination", "", "_errorMessage", "_loadingRoute", "", "_navigationInstructions", "", "_routeResponse", "Lcom/tranxortrider/deliveryrider/api/DirectionsResponse;", "_timeToDestination", "currentInstruction", "Landroidx/lifecycle/LiveData;", "getCurrentInstruction", "()Landroidx/lifecycle/LiveData;", "currentLocation", "getCurrentLocation", "destinationLat", "destinationLng", "distanceToDestination", "getDistanceToDestination", "errorMessage", "getErrorMessage", "loadingRoute", "getLoadingRoute", "locationUpdatesJob", "Lkotlinx/coroutines/Job;", "navigationInstructions", "getNavigationInstructions", "recalculateOnFirstLocation", "routeResponse", "getRouteResponse", "timeToDestination", "getTimeToDestination", "calculateRoute", "", "startLat", "startLng", "endLat", "endLng", "clearNavigationData", "onCleared", "recalculateRoute", "requestRecalculateOnFirstLocation", "setDestinationAndCalculateRoute", "startLocationUpdates", "context", "Landroid/content/Context;", "stopLocationUpdates", "updateNavigationInfo", "location", "directionsResponse", "app_debug"})
public final class NavigationViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<android.location.Location> _currentLocation = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<android.location.Location> currentLocation = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.util.List<java.lang.String>> _navigationInstructions = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.util.List<java.lang.String>> navigationInstructions = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.lang.String> _currentInstruction = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.lang.String> currentInstruction = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.lang.Double> _distanceToDestination = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.lang.Double> distanceToDestination = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.lang.Double> _timeToDestination = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.lang.Double> timeToDestination = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.lang.Boolean> _loadingRoute = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.lang.Boolean> loadingRoute = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.lang.String> _errorMessage = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.lang.String> errorMessage = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<com.tranxortrider.deliveryrider.api.DirectionsResponse> _routeResponse = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<com.tranxortrider.deliveryrider.api.DirectionsResponse> routeResponse = null;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job locationUpdatesJob;
    private double destinationLat = 0.0;
    private double destinationLng = 0.0;
    private boolean recalculateOnFirstLocation = false;
    
    public NavigationViewModel() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<android.location.Location> getCurrentLocation() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<java.lang.String>> getNavigationInstructions() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.String> getCurrentInstruction() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.Double> getDistanceToDestination() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.Double> getTimeToDestination() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.Boolean> getLoadingRoute() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.String> getErrorMessage() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.tranxortrider.deliveryrider.api.DirectionsResponse> getRouteResponse() {
        return null;
    }
    
    /**
     * Request recalculation of route when first location is received
     */
    public final void requestRecalculateOnFirstLocation() {
    }
    
    /**
     * Start location updates
     *
     * @param context Application context
     */
    public final void startLocationUpdates(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    /**
     * Stop location updates
     */
    public final void stopLocationUpdates() {
    }
    
    /**
     * Set destination and calculate route using OpenRouteService
     */
    public final void setDestinationAndCalculateRoute(double endLat, double endLng, @org.jetbrains.annotations.Nullable()
    android.location.Location currentLocation) {
    }
    
    /**
     * Calculate route using OpenRouteService
     */
    private final void calculateRoute(double startLat, double startLng, double endLat, double endLng) {
    }
    
    /**
     * Update navigation information based on current location and route
     */
    private final void updateNavigationInfo(android.location.Location location, com.tranxortrider.deliveryrider.api.DirectionsResponse directionsResponse) {
    }
    
    /**
     * Recalculate route with current location
     */
    public final void recalculateRoute() {
    }
    
    /**
     * Clear navigation data
     */
    public final void clearNavigationData() {
    }
    
    @java.lang.Override()
    protected void onCleared() {
    }
}