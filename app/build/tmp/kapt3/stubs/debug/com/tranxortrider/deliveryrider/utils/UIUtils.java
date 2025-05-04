package com.tranxortrider.deliveryrider.utils;

/**
 * Utility class for UI-related helpers
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0010\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J)\u0010\u0007\u001a\u0004\u0018\u0001H\b\"\b\b\u0000\u0010\b*\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\b\b\u0001\u0010\f\u001a\u00020\r\u00a2\u0006\u0002\u0010\u000eJ\u0010\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u0006\u0010\u0012\u001a\u00020\u0013J\u0010\u0010\u0014\u001a\u00020\u00132\b\u0010\u0015\u001a\u0004\u0018\u00010\tJ\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J:\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u00042\u0006\u0010\u001c\u001a\u00020\u00042\b\b\u0002\u0010\u001d\u001a\u00020\u00042\u0010\b\u0002\u0010\u001e\u001a\n\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u001fJX\u0010 \u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u00042\u0006\u0010\u001c\u001a\u00020\u00042\b\b\u0002\u0010\u001d\u001a\u00020\u00042\u0010\b\u0002\u0010\u001e\u001a\n\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u001f2\n\b\u0002\u0010!\u001a\u0004\u0018\u00010\u00042\u0010\b\u0002\u0010\"\u001a\n\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u001fJR\u0010#\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u00042\u0006\u0010\u001c\u001a\u00020\u00042\b\b\u0002\u0010\u001d\u001a\u00020\u00042\b\b\u0002\u0010!\u001a\u00020\u00042\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00130\u001f2\u0010\b\u0002\u0010\"\u001a\n\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u001fJ\u0016\u0010$\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010%\u001a\u00020\u0011J\u0018\u0010&\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u001a2\b\b\u0002\u0010\u001c\u001a\u00020\u0004J\u0016\u0010\'\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001c\u001a\u00020\u0004J \u0010(\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\t2\u0006\u0010\u001c\u001a\u00020\u00042\b\b\u0002\u0010)\u001a\u00020\rJ6\u0010*\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\t2\u0006\u0010\u001c\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\u00042\f\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00130\u001f2\b\b\u0002\u0010)\u001a\u00020\rJ\u0016\u0010-\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001c\u001a\u00020\u0004J\u0010\u0010.\u001a\u00020\u00132\b\u0010\u0015\u001a\u0004\u0018\u00010\tR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006/"}, d2 = {"Lcom/tranxortrider/deliveryrider/utils/UIUtils;", "", "()V", "TAG", "", "loadingDialog", "Landroid/app/Dialog;", "findViewSafely", "T", "Landroid/view/View;", "activity", "Landroid/app/Activity;", "viewId", "", "(Landroid/app/Activity;I)Landroid/view/View;", "getReadableFileSize", "size", "", "hideLoading", "", "hideViewSafely", "view", "isFileSizeExceeded", "", "showAlert", "context", "Landroid/content/Context;", "title", "message", "positiveButtonText", "positiveAction", "Lkotlin/Function0;", "showAlertDialog", "negativeButtonText", "negativeAction", "showConfirmationDialog", "showFileSizeDialog", "fileSize", "showLoading", "showLongToast", "showSnackbar", "duration", "showSnackbarWithAction", "actionText", "action", "showToast", "showViewSafely", "app_debug"})
public final class UIUtils {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "UIUtils";
    @org.jetbrains.annotations.Nullable()
    private static android.app.Dialog loadingDialog;
    @org.jetbrains.annotations.NotNull()
    public static final com.tranxortrider.deliveryrider.utils.UIUtils INSTANCE = null;
    
    private UIUtils() {
        super();
    }
    
    /**
     * Show a short toast message
     */
    public final void showToast(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String message) {
    }
    
    /**
     * Show a long toast message
     */
    public final void showLongToast(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String message) {
    }
    
    /**
     * Shows a snackbar with the given message
     *
     * @param view The view to find a parent from
     * @param message The message to show
     * @param duration The duration to show the snackbar
     */
    public final void showSnackbar(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.NotNull()
    java.lang.String message, int duration) {
    }
    
    /**
     * Shows a snackbar with the given message and an action
     *
     * @param view The view to find a parent from
     * @param message The message to show
     * @param actionText The text to display for the action
     * @param action The action to perform when clicked
     * @param duration The duration to show the snackbar
     */
    public final void showSnackbarWithAction(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.NotNull()
    java.lang.String message, @org.jetbrains.annotations.NotNull()
    java.lang.String actionText, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> action, int duration) {
    }
    
    /**
     * Show an alert dialog
     */
    public final void showAlertDialog(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String message, @org.jetbrains.annotations.NotNull()
    java.lang.String positiveButtonText, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function0<kotlin.Unit> positiveAction, @org.jetbrains.annotations.Nullable()
    java.lang.String negativeButtonText, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function0<kotlin.Unit> negativeAction) {
    }
    
    /**
     * Find a view with the given ID, or log an error if it doesn't exist
     *
     * @param activity The activity to find the view in
     * @param viewId The ID of the view to find
     * @return The view, or null if it doesn't exist
     */
    @org.jetbrains.annotations.Nullable()
    public final <T extends android.view.View>T findViewSafely(@org.jetbrains.annotations.NotNull()
    android.app.Activity activity, @androidx.annotation.IdRes()
    int viewId) {
        return null;
    }
    
    /**
     * Show a view safely, handling potential null references
     */
    public final void showViewSafely(@org.jetbrains.annotations.Nullable()
    android.view.View view) {
    }
    
    /**
     * Hide a view safely, handling potential null references
     */
    public final void hideViewSafely(@org.jetbrains.annotations.Nullable()
    android.view.View view) {
    }
    
    /**
     * Shows a loading dialog
     *
     * @param context The context to show the dialog in
     * @param message The loading message
     */
    public final void showLoading(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String message) {
    }
    
    /**
     * Hides the loading dialog
     */
    public final void hideLoading() {
    }
    
    /**
     * Show an alert dialog with a title and message
     */
    public final void showAlert(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String message, @org.jetbrains.annotations.NotNull()
    java.lang.String positiveButtonText, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function0<kotlin.Unit> positiveAction) {
    }
    
    /**
     * Show a confirmation dialog with positive and negative buttons
     */
    public final void showConfirmationDialog(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String message, @org.jetbrains.annotations.NotNull()
    java.lang.String positiveButtonText, @org.jetbrains.annotations.NotNull()
    java.lang.String negativeButtonText, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> positiveAction, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function0<kotlin.Unit> negativeAction) {
    }
    
    /**
     * Show file size information dialog
     */
    public final void showFileSizeDialog(@org.jetbrains.annotations.NotNull()
    android.content.Context context, long fileSize) {
    }
    
    /**
     * Convert bytes to human-readable file size
     */
    private final java.lang.String getReadableFileSize(long size) {
        return null;
    }
    
    /**
     * Check if file size exceeds the limit (5MB)
     */
    private final boolean isFileSizeExceeded(long size) {
        return false;
    }
}