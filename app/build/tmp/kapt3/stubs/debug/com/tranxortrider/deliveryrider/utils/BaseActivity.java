package com.tranxortrider.deliveryrider.utils;

/**
 * Base activity that all activities should extend to handle theming automatically
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u00020\nH\u0014J\u0010\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\rH\u0014J\b\u0010\u000e\u001a\u00020\u000fH\u0004J\b\u0010\u0010\u001a\u00020\u000fH\u0004J\b\u0010\u0011\u001a\u00020\u0012H\u0004J\u0010\u0010\u0013\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0012\u0010\u0016\u001a\u00020\n2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0014J\b\u0010\u0019\u001a\u00020\nH\u0014R\u001a\u0010\u0003\u001a\u00020\u0004X\u0084.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u00a8\u0006\u001a"}, d2 = {"Lcom/tranxortrider/deliveryrider/utils/BaseActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "themeManager", "Lcom/tranxortrider/deliveryrider/utils/ThemeManager;", "getThemeManager", "()Lcom/tranxortrider/deliveryrider/utils/ThemeManager;", "setThemeManager", "(Lcom/tranxortrider/deliveryrider/utils/ThemeManager;)V", "applyAccentColor", "", "attachBaseContext", "newBase", "Landroid/content/Context;", "getAccentColor", "", "getAccentColorResId", "isDarkMode", "", "onConfigurationChanged", "newConfig", "Landroid/content/res/Configuration;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onStart", "app_debug"})
public class BaseActivity extends androidx.appcompat.app.AppCompatActivity {
    protected com.tranxortrider.deliveryrider.utils.ThemeManager themeManager;
    
    public BaseActivity() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    protected final com.tranxortrider.deliveryrider.utils.ThemeManager getThemeManager() {
        return null;
    }
    
    protected final void setThemeManager(@org.jetbrains.annotations.NotNull()
    com.tranxortrider.deliveryrider.utils.ThemeManager p0) {
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    protected void onStart() {
    }
    
    @java.lang.Override()
    protected void attachBaseContext(@org.jetbrains.annotations.NotNull()
    android.content.Context newBase) {
    }
    
    @java.lang.Override()
    public void onConfigurationChanged(@org.jetbrains.annotations.NotNull()
    android.content.res.Configuration newConfig) {
    }
    
    /**
     * Apply the selected accent color to UI elements
     */
    protected void applyAccentColor() {
    }
    
    /**
     * Get the current accent color resource ID
     */
    protected final int getAccentColorResId() {
        return 0;
    }
    
    /**
     * Get the current accent color
     */
    protected final int getAccentColor() {
        return 0;
    }
    
    /**
     * Check if dark mode is currently active
     */
    protected final boolean isDarkMode() {
        return false;
    }
}