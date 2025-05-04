package com.tranxortrider.deliveryrider.utils;

/**
 * Session manager class for handling user session with Firebase
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\f\u0018\u0000  2\u00020\u0001:\u0001 B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u000f\u001a\u00020\u0010J\b\u0010\u0011\u001a\u0004\u0018\u00010\u0006J\u0012\u0010\u0012\u001a\u0004\u0018\u00010\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u0015J\u0006\u0010\u0016\u001a\u00020\u0015J\u0006\u0010\u0017\u001a\u00020\u0015J\u0010\u0010\u0018\u001a\u00020\u00102\u0006\u0010\u0019\u001a\u00020\u0006H\u0002J\u000e\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u0006J\u000e\u0010\u001c\u001a\u00020\u00102\u0006\u0010\u001d\u001a\u00020\u0013J\u000e\u0010\u001e\u001a\u00020\u00102\u0006\u0010\u001f\u001a\u00020\u0015R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006!"}, d2 = {"Lcom/tranxortrider/deliveryrider/utils/SessionManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "TAG", "", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "db", "Lcom/google/firebase/firestore/FirebaseFirestore;", "gson", "Lcom/google/gson/Gson;", "sharedPreferences", "Landroid/content/SharedPreferences;", "clearSession", "", "getFirebaseToken", "getUser", "Lcom/tranxortrider/deliveryrider/models/User;", "forceRefresh", "", "isLoggedIn", "isRiderOnline", "refreshUserData", "userId", "saveFirebaseToken", "token", "saveUserData", "user", "setRiderOnline", "isOnline", "Companion", "app_debug"})
public final class SessionManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.SharedPreferences sharedPreferences = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.gson.Gson gson = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.auth.FirebaseAuth auth = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.firestore.FirebaseFirestore db = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String TAG = "SessionManager";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String PREF_NAME = "TranxortRiderPrefs";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String USER_KEY = "user_data";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String USER_ONLINE = "is_rider_online";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String LAST_SYNC_TIME = "last_sync_time";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String USER_ID = "user_id";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String USER_EMAIL = "user_email";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String USER_NAME = "user_name";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String USER_PHONE = "user_phone";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String USER_PROFILE_PIC = "user_profile_pic";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String USER_IS_VERIFIED = "user_is_verified";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String USER_TOKEN = "user_token";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String FIREBASE_TOKEN = "firebase_token";
    @org.jetbrains.annotations.NotNull()
    public static final com.tranxortrider.deliveryrider.utils.SessionManager.Companion Companion = null;
    
    public SessionManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    /**
     * Save user data to shared preferences
     */
    public final void saveUserData(@org.jetbrains.annotations.NotNull()
    com.tranxortrider.deliveryrider.models.User user) {
    }
    
    /**
     * Get user data from shared preferences, with an option to refresh from Firestore
     */
    @org.jetbrains.annotations.Nullable()
    public final com.tranxortrider.deliveryrider.models.User getUser(boolean forceRefresh) {
        return null;
    }
    
    /**
     * Refresh user data from Firestore
     */
    private final void refreshUserData(java.lang.String userId) {
    }
    
    /**
     * Check if user is logged in
     */
    public final boolean isLoggedIn() {
        return false;
    }
    
    /**
     * Get rider's online status
     */
    public final boolean isRiderOnline() {
        return false;
    }
    
    /**
     * Set rider's online status
     */
    public final void setRiderOnline(boolean isOnline) {
    }
    
    /**
     * Clear user session data
     */
    public final void clearSession() {
    }
    
    /**
     * Save Firebase token
     */
    public final void saveFirebaseToken(@org.jetbrains.annotations.NotNull()
    java.lang.String token) {
    }
    
    /**
     * Get Firebase token
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getFirebaseToken() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\f\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lcom/tranxortrider/deliveryrider/utils/SessionManager$Companion;", "", "()V", "FIREBASE_TOKEN", "", "LAST_SYNC_TIME", "PREF_NAME", "USER_EMAIL", "USER_ID", "USER_IS_VERIFIED", "USER_KEY", "USER_NAME", "USER_ONLINE", "USER_PHONE", "USER_PROFILE_PIC", "USER_TOKEN", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}