package com.tranxortrider.deliveryrider;

/**
 * Navigation activity using OpenRouteService and OSMDroid
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u00ae\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u000f\u0018\u0000 U2\u00020\u0001:\u0001UB\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010-\u001a\u00020.H\u0002J\u0016\u0010/\u001a\b\u0012\u0004\u0012\u000201002\u0006\u00102\u001a\u00020\u000bH\u0002J\u0010\u00103\u001a\u00020.2\u0006\u00104\u001a\u000205H\u0002J\n\u00106\u001a\u0004\u0018\u000107H\u0002J\b\u00108\u001a\u000209H\u0002J\b\u0010:\u001a\u00020.H\u0002J\b\u0010;\u001a\u00020.H\u0002J\u0012\u0010<\u001a\u00020.2\b\u0010=\u001a\u0004\u0018\u00010>H\u0014J\b\u0010?\u001a\u00020.H\u0014J\b\u0010@\u001a\u00020.H\u0014J-\u0010A\u001a\u00020.2\u0006\u0010B\u001a\u00020C2\u000e\u0010D\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000b0E2\u0006\u0010F\u001a\u00020GH\u0016\u00a2\u0006\u0002\u0010HJ\b\u0010I\u001a\u00020.H\u0014J\b\u0010J\u001a\u00020.H\u0014J\b\u0010K\u001a\u00020.H\u0014J\b\u0010L\u001a\u00020.H\u0002J\b\u0010M\u001a\u00020.H\u0002J\b\u0010N\u001a\u00020.H\u0002J\b\u0010O\u001a\u00020.H\u0002J\u0010\u0010P\u001a\u00020.2\u0006\u0010Q\u001a\u00020\u000bH\u0002J\u0018\u0010R\u001a\u00020.2\u0006\u0010S\u001a\u00020\u00102\u0006\u0010T\u001a\u00020\u0010H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\"\u001a\u0004\u0018\u00010#X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010%\u001a\u0004\u0018\u00010\u0013X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001b\u0010&\u001a\u00020\'8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b*\u0010+\u001a\u0004\b(\u0010)R\u000e\u0010,\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006V"}, d2 = {"Lcom/tranxortrider/deliveryrider/NavigationActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "backButton", "Lcom/google/android/material/button/MaterialButton;", "btnArrived", "Landroid/widget/Button;", "btnCall", "btnRecalculate", "Lcom/google/android/material/floatingactionbutton/FloatingActionButton;", "customerPhone", "", "destinationAddress", "Landroid/widget/TextView;", "destinationAddressText", "destinationLat", "", "destinationLng", "destinationMarker", "Lorg/osmdroid/views/overlay/Marker;", "destinationName", "destinationPanel", "Landroidx/cardview/widget/CardView;", "destinationTitle", "distanceValue", "instructionIcon", "Landroid/widget/ImageView;", "instructionText", "loadingOverlay", "Landroid/widget/FrameLayout;", "mapView", "Lorg/osmdroid/views/MapView;", "orderStatus", "restaurantPhone", "routeOverlay", "Lorg/osmdroid/views/overlay/Polyline;", "timeRemainingValue", "userLocationMarker", "viewModel", "Lcom/tranxortrider/deliveryrider/viewmodels/NavigationViewModel;", "getViewModel", "()Lcom/tranxortrider/deliveryrider/viewmodels/NavigationViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "zoomLevelDouble", "addDestinationMarker", "", "decodePolyline", "", "Lorg/osmdroid/util/GeoPoint;", "encodedPolyline", "drawRoute", "directionsResponse", "Lcom/tranxortrider/deliveryrider/api/DirectionsResponse;", "getInitialLocation", "Landroid/location/Location;", "hasRequiredPermissions", "", "initViews", "initializeMap", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onPause", "onRequestPermissionsResult", "requestCode", "", "permissions", "", "grantResults", "", "(I[Ljava/lang/String;[I)V", "onResume", "onStart", "onStop", "requestPermissions", "setupListeners", "setupObservers", "showAlternativeNavigationOptions", "showErrorAndExit", "message", "updateUserLocationOnMap", "latitude", "longitude", "Companion", "app_debug"})
public final class NavigationActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.google.android.material.button.MaterialButton backButton;
    private androidx.cardview.widget.CardView destinationPanel;
    private android.widget.TextView destinationTitle;
    private android.widget.TextView destinationAddress;
    private android.widget.TextView timeRemainingValue;
    private android.widget.TextView distanceValue;
    private android.widget.ImageView instructionIcon;
    private android.widget.TextView instructionText;
    private android.widget.Button btnCall;
    private android.widget.Button btnArrived;
    private android.widget.FrameLayout loadingOverlay;
    private org.osmdroid.views.MapView mapView;
    private com.google.android.material.floatingactionbutton.FloatingActionButton btnRecalculate;
    private double destinationLat = 0.0;
    private double destinationLng = 0.0;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String destinationName = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String destinationAddressText = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String customerPhone = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String restaurantPhone = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String orderStatus = "";
    private double zoomLevelDouble = 15.0;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewModel$delegate = null;
    @org.jetbrains.annotations.Nullable()
    private org.osmdroid.views.overlay.Polyline routeOverlay;
    @org.jetbrains.annotations.Nullable()
    private org.osmdroid.views.overlay.Marker destinationMarker;
    @org.jetbrains.annotations.Nullable()
    private org.osmdroid.views.overlay.Marker userLocationMarker;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String[] REQUIRED_PERMISSIONS = {"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION", "android.permission.INTERNET", "android.permission.WRITE_EXTERNAL_STORAGE"};
    @org.jetbrains.annotations.NotNull()
    public static final com.tranxortrider.deliveryrider.NavigationActivity.Companion Companion = null;
    
    public NavigationActivity() {
        super();
    }
    
    private final com.tranxortrider.deliveryrider.viewmodels.NavigationViewModel getViewModel() {
        return null;
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void initViews() {
    }
    
    private final void setupListeners() {
    }
    
    private final void setupObservers() {
    }
    
    private final void initializeMap() {
    }
    
    /**
     * Try to get an initial location before receiving location updates
     */
    private final android.location.Location getInitialLocation() {
        return null;
    }
    
    private final void addDestinationMarker() {
    }
    
    private final void updateUserLocationOnMap(double latitude, double longitude) {
    }
    
    private final void drawRoute(com.tranxortrider.deliveryrider.api.DirectionsResponse directionsResponse) {
    }
    
    /**
     * Decodes a polyline string into a list of GeoPoints
     */
    private final java.util.List<org.osmdroid.util.GeoPoint> decodePolyline(java.lang.String encodedPolyline) {
        return null;
    }
    
    private final boolean hasRequiredPermissions() {
        return false;
    }
    
    private final void requestPermissions() {
    }
    
    @java.lang.Override()
    public void onRequestPermissionsResult(int requestCode, @org.jetbrains.annotations.NotNull()
    java.lang.String[] permissions, @org.jetbrains.annotations.NotNull()
    int[] grantResults) {
    }
    
    /**
     * Shows alternative navigation options using external apps
     * Only called when permissions are denied or explicitly requested
     */
    private final void showAlternativeNavigationOptions() {
    }
    
    private final void showErrorAndExit(java.lang.String message) {
    }
    
    @java.lang.Override()
    protected void onStart() {
    }
    
    @java.lang.Override()
    protected void onResume() {
    }
    
    @java.lang.Override()
    protected void onPause() {
    }
    
    @java.lang.Override()
    protected void onStop() {
    }
    
    @java.lang.Override()
    protected void onDestroy() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\b\u00a8\u0006\t"}, d2 = {"Lcom/tranxortrider/deliveryrider/NavigationActivity$Companion;", "", "()V", "LOCATION_PERMISSION_REQUEST_CODE", "", "REQUIRED_PERMISSIONS", "", "", "[Ljava/lang/String;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}