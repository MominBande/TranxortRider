package com.tranxortrider.deliveryrider;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u000fH\u0002J\b\u0010\u0018\u001a\u00020\u0016H\u0002J\u0012\u0010\u0019\u001a\u00020\u00162\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0014J\b\u0010\u001c\u001a\u00020\u0016H\u0002J\b\u0010\u001d\u001a\u00020\u0016H\u0002J\b\u0010\u001e\u001a\u00020\u0016H\u0002J\b\u0010\u001f\u001a\u00020\u0016H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\u00110\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006 "}, d2 = {"Lcom/tranxortrider/deliveryrider/sign_in_screen;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "authService", "Lcom/tranxortrider/deliveryrider/services/FirebaseAuthService;", "emailEditText", "Landroid/widget/EditText;", "foregroundLocationPermissionRequest", "Landroidx/activity/result/ActivityResultLauncher;", "", "forgotPasswordText", "Landroid/widget/TextView;", "googleSignInButton", "Landroid/widget/Button;", "googleSignInLauncher", "Landroid/content/Intent;", "locationPermissionRequest", "", "passwordEditText", "signInButton", "signUpText", "handleGoogleSignIn", "", "data", "navigateToHome", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "requestForegroundLocationPermission", "requestLocationPermissions", "signInWithEmail", "signInWithGoogle", "app_debug"})
public final class sign_in_screen extends androidx.appcompat.app.AppCompatActivity {
    private com.tranxortrider.deliveryrider.services.FirebaseAuthService authService;
    private android.widget.EditText emailEditText;
    private android.widget.EditText passwordEditText;
    private android.widget.Button signInButton;
    private android.widget.Button googleSignInButton;
    private android.widget.TextView forgotPasswordText;
    private android.widget.TextView signUpText;
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<java.lang.String[]> locationPermissionRequest = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<java.lang.String> foregroundLocationPermissionRequest = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<android.content.Intent> googleSignInLauncher = null;
    
    public sign_in_screen() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void requestLocationPermissions() {
    }
    
    private final void requestForegroundLocationPermission() {
    }
    
    private final void signInWithEmail() {
    }
    
    private final void signInWithGoogle() {
    }
    
    private final void handleGoogleSignIn(android.content.Intent data) {
    }
    
    private final void navigateToHome() {
    }
}