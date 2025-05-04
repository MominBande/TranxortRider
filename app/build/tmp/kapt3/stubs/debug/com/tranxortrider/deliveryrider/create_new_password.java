package com.tranxortrider.deliveryrider;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0014\u001a\u00020\u0015H\u0002J\u0018\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u0006H\u0002J\b\u0010\u0019\u001a\u00020\u0015H\u0002J \u0010\u001a\u001a\u00020\u00152\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u0006H\u0002J\u0012\u0010\u001b\u001a\u00020\u00152\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0014J\b\u0010\u001e\u001a\u00020\u0015H\u0002J\b\u0010\u001f\u001a\u00020\u0015H\u0002J\b\u0010 \u001a\u00020\u0015H\u0002J\u0010\u0010!\u001a\u00020\u00152\u0006\u0010\"\u001a\u00020#H\u0002J\u0010\u0010$\u001a\u00020\u00152\u0006\u0010%\u001a\u00020\u0006H\u0002J\u0018\u0010&\u001a\u00020#2\u0006\u0010%\u001a\u00020\u00062\u0006\u0010\'\u001a\u00020\u0006H\u0002J\b\u0010(\u001a\u00020#H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006)"}, d2 = {"Lcom/tranxortrider/deliveryrider/create_new_password;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "authRepository", "Lcom/tranxortrider/deliveryrider/repositories/AuthRepository;", "code", "", "confirmPasswordEditText", "Lcom/google/android/material/textfield/TextInputEditText;", "confirmPasswordInputLayout", "Lcom/google/android/material/textfield/TextInputLayout;", "email", "passwordEditText", "passwordInputLayout", "passwordStrengthIndicator", "Lcom/google/android/material/progressindicator/LinearProgressIndicator;", "passwordStrengthText", "Landroid/widget/TextView;", "resetButton", "Lcom/google/android/material/button/MaterialButton;", "initializeUI", "", "navigateToCreateNewPasswordError", "title", "message", "navigateToForgotPassword", "navigateToSignIn", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "resetPassword", "setupClickListeners", "setupTextChangeListeners", "showLoading", "isLoading", "", "updatePasswordStrengthIndicator", "password", "validateInput", "confirmPassword", "validatePasswordsMatch", "app_debug"})
public final class create_new_password extends androidx.appcompat.app.AppCompatActivity {
    private com.google.android.material.textfield.TextInputEditText passwordEditText;
    private com.google.android.material.textfield.TextInputEditText confirmPasswordEditText;
    private com.google.android.material.textfield.TextInputLayout passwordInputLayout;
    private com.google.android.material.textfield.TextInputLayout confirmPasswordInputLayout;
    private com.google.android.material.progressindicator.LinearProgressIndicator passwordStrengthIndicator;
    private android.widget.TextView passwordStrengthText;
    private com.google.android.material.button.MaterialButton resetButton;
    private com.tranxortrider.deliveryrider.repositories.AuthRepository authRepository;
    private java.lang.String email;
    private java.lang.String code;
    
    public create_new_password() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void initializeUI() {
    }
    
    private final void setupClickListeners() {
    }
    
    private final void setupTextChangeListeners() {
    }
    
    private final void updatePasswordStrengthIndicator(java.lang.String password) {
    }
    
    private final boolean validatePasswordsMatch() {
        return false;
    }
    
    private final void resetPassword() {
    }
    
    private final boolean validateInput(java.lang.String password, java.lang.String confirmPassword) {
        return false;
    }
    
    private final void showLoading(boolean isLoading) {
    }
    
    private final void navigateToForgotPassword() {
    }
    
    private final void navigateToSignIn(java.lang.String title, java.lang.String message) {
    }
    
    private final void navigateToCreateNewPasswordError(java.lang.String title, java.lang.String message) {
    }
}