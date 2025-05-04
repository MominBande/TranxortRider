package com.tranxortrider.deliveryrider;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0002J\u0012\u0010\u0012\u001a\u00020\u00112\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0014J\b\u0010\u0015\u001a\u00020\u0011H\u0014J\u0010\u0010\u0016\u001a\u00020\u00112\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\b\u0010\u0019\u001a\u00020\u0011H\u0002J\b\u0010\u001a\u001a\u00020\u0011H\u0002J\b\u0010\u001b\u001a\u00020\u0011H\u0002J\u0010\u0010\u001c\u001a\u00020\u00112\u0006\u0010\u001d\u001a\u00020\u0018H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001e"}, d2 = {"Lcom/tranxortrider/deliveryrider/verification_code;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "authRepository", "Lcom/tranxortrider/deliveryrider/repositories/AuthRepository;", "codeEditText", "Lcom/google/android/material/textfield/TextInputEditText;", "countdownTimer", "Landroid/os/CountDownTimer;", "countdownTimerText", "Landroid/widget/TextView;", "resendCodeButton", "Landroid/widget/Button;", "resendTimeInMillis", "", "verifyButton", "hideLoading", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "resendVerificationCode", "email", "", "setupCodeEditTextListener", "showLoading", "startCountdownTimer", "verifyCode", "code", "app_debug"})
public final class verification_code extends androidx.appcompat.app.AppCompatActivity {
    private com.google.android.material.textfield.TextInputEditText codeEditText;
    private android.widget.Button resendCodeButton;
    private android.widget.Button verifyButton;
    private android.widget.TextView countdownTimerText;
    private com.tranxortrider.deliveryrider.repositories.AuthRepository authRepository;
    @org.jetbrains.annotations.Nullable()
    private android.os.CountDownTimer countdownTimer;
    private final long resendTimeInMillis = 60000L;
    
    public verification_code() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupCodeEditTextListener() {
    }
    
    private final void startCountdownTimer() {
    }
    
    private final void resendVerificationCode(java.lang.String email) {
    }
    
    private final void verifyCode(java.lang.String code) {
    }
    
    private final void showLoading() {
    }
    
    private final void hideLoading() {
    }
    
    @java.lang.Override()
    protected void onDestroy() {
    }
}