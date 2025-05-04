package com.tranxortrider.deliveryrider;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0002J\b\u0010\u0013\u001a\u00020\u0012H\u0002J\b\u0010\u0014\u001a\u00020\u0012H\u0002J\u0012\u0010\u0015\u001a\u00020\u00122\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0014J\b\u0010\u0018\u001a\u00020\u0012H\u0002J\b\u0010\u0019\u001a\u00020\u0012H\u0002J\u0010\u0010\u001a\u001a\u00020\u00122\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\b\u0010\u001d\u001a\u00020\u0012H\u0002J\b\u0010\u001e\u001a\u00020\u0012H\u0002J(\u0010\u001f\u001a\u00020\u001c2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020!2\u0006\u0010#\u001a\u00020!2\u0006\u0010$\u001a\u00020!H\u0002R\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006%"}, d2 = {"Lcom/tranxortrider/deliveryrider/setup_profile;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "calendar", "Ljava/util/Calendar;", "kotlin.jvm.PlatformType", "dateOfBirthEditText", "Lcom/google/android/material/textfield/TextInputEditText;", "firstNameEditText", "lastNameEditText", "mobileNumberEditText", "nextButton", "Lcom/google/android/material/button/MaterialButton;", "onboardingRepository", "Lcom/tranxortrider/deliveryrider/repositories/OnboardingRepository;", "sessionManager", "Lcom/tranxortrider/deliveryrider/utils/SessionManager;", "initializeUI", "", "navigateToSignIn", "navigateToUploadDocument", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "setupClickListeners", "setupDatePicker", "showLoading", "isLoading", "", "submitProfile", "updateDateInView", "validateInput", "firstName", "", "lastName", "mobileNumber", "dateOfBirth", "app_debug"})
public final class setup_profile extends androidx.appcompat.app.AppCompatActivity {
    private com.google.android.material.textfield.TextInputEditText firstNameEditText;
    private com.google.android.material.textfield.TextInputEditText lastNameEditText;
    private com.google.android.material.textfield.TextInputEditText mobileNumberEditText;
    private com.google.android.material.textfield.TextInputEditText dateOfBirthEditText;
    private com.google.android.material.button.MaterialButton nextButton;
    private com.tranxortrider.deliveryrider.repositories.OnboardingRepository onboardingRepository;
    private com.tranxortrider.deliveryrider.utils.SessionManager sessionManager;
    private final java.util.Calendar calendar = null;
    
    public setup_profile() {
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
    
    private final void setupDatePicker() {
    }
    
    private final void updateDateInView() {
    }
    
    private final void submitProfile() {
    }
    
    private final boolean validateInput(java.lang.String firstName, java.lang.String lastName, java.lang.String mobileNumber, java.lang.String dateOfBirth) {
        return false;
    }
    
    private final void showLoading(boolean isLoading) {
    }
    
    private final void navigateToUploadDocument() {
    }
    
    private final void navigateToSignIn() {
    }
}