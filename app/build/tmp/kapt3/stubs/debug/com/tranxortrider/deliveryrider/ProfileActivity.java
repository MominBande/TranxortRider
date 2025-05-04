package com.tranxortrider.deliveryrider;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0090\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010+\u001a\u00020,H\u0002J\b\u0010-\u001a\u00020,H\u0002J\b\u0010.\u001a\u00020,H\u0002J\u0012\u0010/\u001a\u00020,2\b\u00100\u001a\u0004\u0018\u000101H\u0014J\b\u00102\u001a\u00020,H\u0002J\u0010\u00103\u001a\u00020,2\u0006\u0010$\u001a\u00020%H\u0002J\u0010\u00104\u001a\u00020,2\u0006\u00105\u001a\u00020\u0013H\u0002J\b\u00106\u001a\u00020,H\u0002J\b\u00107\u001a\u00020,H\u0002J\b\u00108\u001a\u00020,H\u0002J\u0010\u00109\u001a\u00020,2\u0006\u0010:\u001a\u00020\u0013H\u0002J\u0010\u0010;\u001a\u00020,2\u0006\u0010<\u001a\u00020=H\u0002J\u0010\u0010>\u001a\u00020,2\u0006\u00105\u001a\u00020\u0013H\u0002J\b\u0010?\u001a\u00020,H\u0002J\b\u0010@\u001a\u00020\u0013H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0019X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u000eX\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001e\u001a\u0004\u0018\u00010\u001fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020#X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010$\u001a\u0004\u0018\u00010%X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\'\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020*X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006A"}, d2 = {"Lcom/tranxortrider/deliveryrider/ProfileActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "addressEditText", "Landroid/widget/EditText;", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "availabilityStatusText", "Landroid/widget/TextView;", "availabilitySwitch", "Lcom/google/android/material/switchmaterial/SwitchMaterial;", "backButton", "Lcom/google/android/material/button/MaterialButton;", "changePhotoButton", "Landroid/widget/Button;", "emailEditText", "firestoreService", "Lcom/tranxortrider/deliveryrider/services/FirestoreService;", "isPhotoChanged", "", "loadingView", "Landroid/view/View;", "nameEditText", "phoneEditText", "pickImageLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "profileImageView", "Landroid/widget/ImageView;", "saveButton", "selectedImageUri", "Landroid/net/Uri;", "sessionManager", "Lcom/tranxortrider/deliveryrider/utils/SessionManager;", "storageService", "Lcom/tranxortrider/deliveryrider/services/StorageService;", "user", "Lcom/tranxortrider/deliveryrider/models/User;", "vehicleMakeEditText", "vehicleModelEditText", "vehiclePlateEditText", "vehicleTypeSpinner", "Landroid/widget/Spinner;", "initViews", "", "loadUserProfile", "navigateToLogin", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "openImagePicker", "populateUserData", "sendAvailabilityBroadcast", "isAvailable", "setupBottomNavigation", "setupListeners", "setupVehicleTypeSpinner", "showLoading", "isLoading", "showSnackbar", "message", "", "updateAvailabilityUI", "updateProfile", "validateInputs", "app_debug"})
public final class ProfileActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.google.android.material.button.MaterialButton backButton;
    private android.widget.Button saveButton;
    private android.widget.ImageView profileImageView;
    private android.widget.Button changePhotoButton;
    private android.widget.EditText nameEditText;
    private android.widget.EditText phoneEditText;
    private android.widget.EditText emailEditText;
    private android.widget.EditText addressEditText;
    private android.widget.Spinner vehicleTypeSpinner;
    private android.widget.EditText vehicleMakeEditText;
    private android.widget.EditText vehicleModelEditText;
    private android.widget.EditText vehiclePlateEditText;
    private com.google.android.material.switchmaterial.SwitchMaterial availabilitySwitch;
    private android.widget.TextView availabilityStatusText;
    private android.view.View loadingView;
    private com.tranxortrider.deliveryrider.services.FirestoreService firestoreService;
    private com.tranxortrider.deliveryrider.services.StorageService storageService;
    private com.tranxortrider.deliveryrider.utils.SessionManager sessionManager;
    private com.google.firebase.auth.FirebaseAuth auth;
    @org.jetbrains.annotations.Nullable()
    private com.tranxortrider.deliveryrider.models.User user;
    @org.jetbrains.annotations.Nullable()
    private android.net.Uri selectedImageUri;
    private boolean isPhotoChanged = false;
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<android.content.Intent> pickImageLauncher = null;
    
    public ProfileActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void initViews() {
    }
    
    private final void setupBottomNavigation() {
    }
    
    private final void setupListeners() {
    }
    
    private final void setupVehicleTypeSpinner() {
    }
    
    private final void loadUserProfile() {
    }
    
    private final void populateUserData(com.tranxortrider.deliveryrider.models.User user) {
    }
    
    private final void updateAvailabilityUI(boolean isAvailable) {
    }
    
    private final boolean validateInputs() {
        return false;
    }
    
    private final void updateProfile() {
    }
    
    private final void openImagePicker() {
    }
    
    private final void navigateToLogin() {
    }
    
    private final void sendAvailabilityBroadcast(boolean isAvailable) {
    }
    
    private final void showLoading(boolean isLoading) {
    }
    
    private final void showSnackbar(java.lang.String message) {
    }
}