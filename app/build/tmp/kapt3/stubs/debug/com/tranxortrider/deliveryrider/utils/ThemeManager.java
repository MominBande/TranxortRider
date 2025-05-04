package com.tranxortrider.deliveryrider.utils;

/**
 * Utility class to manage app theme settings
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u000b\u0018\u0000 \u001a2\u00020\u0001:\u0001\u001aB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0007\u001a\u00020\bJ\u0006\u0010\t\u001a\u00020\nJ\u0006\u0010\u000b\u001a\u00020\nJ\u0006\u0010\f\u001a\u00020\rJ\u0006\u0010\u000e\u001a\u00020\nJ\b\u0010\u000f\u001a\u00020\u0010H\u0007J\u0006\u0010\u0011\u001a\u00020\u0010J\u000e\u0010\u0012\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\nJ\u0010\u0010\u0014\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\u0010H\u0007J\u000e\u0010\u0016\u001a\u00020\b2\u0006\u0010\u0017\u001a\u00020\rJ\u000e\u0010\u0018\u001a\u00020\b2\u0006\u0010\u0019\u001a\u00020\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001b"}, d2 = {"Lcom/tranxortrider/deliveryrider/utils/ThemeManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "sharedPreferences", "Landroid/content/SharedPreferences;", "applyTheme", "", "getAccentColorIndex", "", "getAccentColorResId", "getFontScale", "", "getThemeMode", "isDarkModeEnabled", "", "isNightMode", "setAccentColorIndex", "index", "setDarkMode", "enabled", "setFontScale", "scale", "setThemeMode", "mode", "Companion", "app_debug"})
public final class ThemeManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.SharedPreferences sharedPreferences = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String PREFS_NAME = "theme_prefs";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_THEME_MODE = "theme_mode";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_DARK_MODE = "dark_mode";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_FONT_SCALE = "font_scale";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_ACCENT_COLOR = "accent_color";
    public static final int MODE_SYSTEM = 0;
    public static final int MODE_LIGHT = 1;
    public static final int MODE_DARK = 2;
    @org.jetbrains.annotations.NotNull()
    public static final com.tranxortrider.deliveryrider.utils.ThemeManager.Companion Companion = null;
    
    public ThemeManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    /**
     * Get current theme mode
     * @return Theme mode constant (MODE_SYSTEM, MODE_LIGHT, MODE_DARK)
     */
    public final int getThemeMode() {
        return 0;
    }
    
    /**
     * Set theme mode
     * @param mode The theme mode to set (MODE_SYSTEM, MODE_LIGHT, MODE_DARK)
     */
    public final void setThemeMode(int mode) {
    }
    
    /**
     * Check if dark mode is enabled
     * @deprecated Use getThemeMode() instead
     */
    @java.lang.Deprecated()
    public final boolean isDarkModeEnabled() {
        return false;
    }
    
    /**
     * Set dark mode preference
     * @deprecated Use setThemeMode() instead
     */
    @java.lang.Deprecated()
    public final void setDarkMode(boolean enabled) {
    }
    
    /**
     * Get current font scale (as percentage value)
     */
    public final float getFontScale() {
        return 0.0F;
    }
    
    /**
     * Set font scale
     */
    public final void setFontScale(float scale) {
    }
    
    /**
     * Get current accent color index
     */
    public final int getAccentColorIndex() {
        return 0;
    }
    
    /**
     * Set accent color index
     */
    public final void setAccentColorIndex(int index) {
    }
    
    /**
     * Get the actual color resource ID for the current accent color
     */
    public final int getAccentColorResId() {
        return 0;
    }
    
    /**
     * Apply saved theme settings
     */
    public final void applyTheme() {
    }
    
    /**
     * Checks if the current theme is in dark mode
     */
    public final boolean isNightMode() {
        return false;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\tX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/tranxortrider/deliveryrider/utils/ThemeManager$Companion;", "", "()V", "KEY_ACCENT_COLOR", "", "KEY_DARK_MODE", "KEY_FONT_SCALE", "KEY_THEME_MODE", "MODE_DARK", "", "MODE_LIGHT", "MODE_SYSTEM", "PREFS_NAME", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}