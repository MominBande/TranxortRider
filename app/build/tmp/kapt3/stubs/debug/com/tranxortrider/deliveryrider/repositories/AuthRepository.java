package com.tranxortrider.deliveryrider.repositories;

/**
 * Repository for authentication related operations
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002JF\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f26\u0010\r\u001a2\u0012\u0013\u0012\u00110\u000f\u00a2\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0012\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0013\u0012\u0004\u0012\u00020\n0\u000eJF\u0010\u0014\u001a\u00020\n2\u0006\u0010\u0015\u001a\u00020\u000426\u0010\r\u001a2\u0012\u0013\u0012\u00110\u000f\u00a2\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0012\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0013\u0012\u0004\u0012\u00020\n0\u000eJ+\u0010\u0016\u001a\u00020\n2#\u0010\r\u001a\u001f\u0012\u0015\u0012\u0013\u0018\u00010\f\u00a2\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u000b\u0012\u0004\u0012\u00020\n0\u0017J\u0006\u0010\u0018\u001a\u00020\u000fJe\u0010\u0019\u001a\u00020\n2\u0006\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u00042M\u0010\r\u001aI\u0012\u0013\u0012\u00110\u000f\u00a2\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0012\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0013\u0012\u0015\u0012\u0013\u0018\u00010\f\u00a2\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u000b\u0012\u0004\u0012\u00020\n0\u001bJ_\u0010\u001c\u001a\u00020\n2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e2M\u0010\r\u001aI\u0012\u0013\u0012\u00110\u000f\u00a2\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0012\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0013\u0012\u0015\u0012\u0013\u0018\u00010\f\u00a2\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u000b\u0012\u0004\u0012\u00020\n0\u001bJ\u0006\u0010\u001f\u001a\u00020\nJV\u0010 \u001a\u00020\n2\u0006\u0010\u0015\u001a\u00020\u00042\u0006\u0010!\u001a\u00020\u00042\u0006\u0010\"\u001a\u00020\u000426\u0010\r\u001a2\u0012\u0013\u0012\u00110\u000f\u00a2\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0012\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0013\u0012\u0004\u0012\u00020\n0\u000eJN\u0010#\u001a\u00020\n2\u0006\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u000426\u0010\r\u001a2\u0012\u0013\u0012\u00110\u000f\u00a2\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0012\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0013\u0012\u0004\u0012\u00020\n0\u000eR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006$"}, d2 = {"Lcom/tranxortrider/deliveryrider/repositories/AuthRepository;", "", "()V", "TAG", "", "authService", "Lcom/tranxortrider/deliveryrider/services/FirebaseAuthService;", "firestoreService", "Lcom/tranxortrider/deliveryrider/services/FirestoreService;", "createUserProfile", "", "user", "Lcom/tranxortrider/deliveryrider/models/User;", "callback", "Lkotlin/Function2;", "", "Lkotlin/ParameterName;", "name", "success", "message", "forgotPassword", "email", "getCurrentUserProfile", "Lkotlin/Function1;", "isUserLoggedIn", "login", "password", "Lkotlin/Function3;", "loginWithGoogle", "data", "Landroid/content/Intent;", "logout", "resetPassword", "code", "newPassword", "signup", "app_debug"})
public final class AuthRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.tranxortrider.deliveryrider.services.FirebaseAuthService authService = null;
    @org.jetbrains.annotations.NotNull()
    private final com.tranxortrider.deliveryrider.services.FirestoreService firestoreService = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String TAG = "AuthRepository";
    
    public AuthRepository() {
        super();
    }
    
    /**
     * Sign in a user with email and password
     */
    public final void login(@org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    java.lang.String password, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function3<? super java.lang.Boolean, ? super java.lang.String, ? super com.tranxortrider.deliveryrider.models.User, kotlin.Unit> callback) {
    }
    
    /**
     * Sign in with Google
     */
    public final void loginWithGoogle(@org.jetbrains.annotations.Nullable()
    android.content.Intent data, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function3<? super java.lang.Boolean, ? super java.lang.String, ? super com.tranxortrider.deliveryrider.models.User, kotlin.Unit> callback) {
    }
    
    /**
     * Register a new user with email and password
     */
    public final void signup(@org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    java.lang.String password, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.Boolean, ? super java.lang.String, kotlin.Unit> callback) {
    }
    
    /**
     * Create user profile after registration
     */
    public final void createUserProfile(@org.jetbrains.annotations.NotNull()
    com.tranxortrider.deliveryrider.models.User user, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.Boolean, ? super java.lang.String, kotlin.Unit> callback) {
    }
    
    /**
     * Send password reset email
     */
    public final void forgotPassword(@org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.Boolean, ? super java.lang.String, kotlin.Unit> callback) {
    }
    
    /**
     * Sign out the current user
     */
    public final void logout() {
    }
    
    /**
     * Check if user is logged in
     */
    public final boolean isUserLoggedIn() {
        return false;
    }
    
    /**
     * Get the current user's profile
     */
    public final void getCurrentUserProfile(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.tranxortrider.deliveryrider.models.User, kotlin.Unit> callback) {
    }
    
    /**
     * Reset password with verification code
     */
    public final void resetPassword(@org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    java.lang.String code, @org.jetbrains.annotations.NotNull()
    java.lang.String newPassword, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.Boolean, ? super java.lang.String, kotlin.Unit> callback) {
    }
}