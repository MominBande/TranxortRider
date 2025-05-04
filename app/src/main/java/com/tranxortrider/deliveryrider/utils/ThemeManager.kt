package com.tranxortrider.deliveryrider.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.tranxortrider.deliveryrider.R
import android.content.res.Resources.Theme
import android.content.res.Configuration
import android.util.TypedValue

/**
 * Utility class to manage app theme settings
 */
class ThemeManager(private val context: Context) {
    
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        PREFS_NAME, Context.MODE_PRIVATE
    )
    
    /**
     * Get current theme mode
     * @return Theme mode constant (MODE_SYSTEM, MODE_LIGHT, MODE_DARK)
     */
    fun getThemeMode(): Int {
        return sharedPreferences.getInt(KEY_THEME_MODE, MODE_SYSTEM)
    }
    
    /**
     * Set theme mode
     * @param mode The theme mode to set (MODE_SYSTEM, MODE_LIGHT, MODE_DARK)
     */
    fun setThemeMode(mode: Int) {
        sharedPreferences.edit().putInt(KEY_THEME_MODE, mode).apply()
    }
    
    /**
     * Check if dark mode is enabled
     * @deprecated Use getThemeMode() instead
     */
    @Deprecated("Use getThemeMode() instead", ReplaceWith("getThemeMode() == MODE_DARK"))
    fun isDarkModeEnabled(): Boolean {
        return getThemeMode() == MODE_DARK
    }
    
    /**
     * Set dark mode preference
     * @deprecated Use setThemeMode() instead
     */
    @Deprecated("Use setThemeMode() instead", ReplaceWith("setThemeMode(if (enabled) MODE_DARK else MODE_LIGHT)"))
    fun setDarkMode(enabled: Boolean) {
        setThemeMode(if (enabled) MODE_DARK else MODE_LIGHT)
    }
    
    /**
     * Get current font scale (as percentage value)
     */
    fun getFontScale(): Float {
        return sharedPreferences.getFloat(KEY_FONT_SCALE, 100f)
    }
    
    /**
     * Set font scale
     */
    fun setFontScale(scale: Float) {
        sharedPreferences.edit().putFloat(KEY_FONT_SCALE, scale * 100).apply()
    }
    
    /**
     * Get current accent color index
     */
    fun getAccentColorIndex(): Int {
        return sharedPreferences.getInt(KEY_ACCENT_COLOR, 1)
    }
    
    /**
     * Set accent color index
     */
    fun setAccentColorIndex(index: Int) {
        sharedPreferences.edit().putInt(KEY_ACCENT_COLOR, index).apply()
    }
    
    /**
     * Get the actual color resource ID for the current accent color
     */
    fun getAccentColorResId(): Int {
        return when (getAccentColorIndex()) {
            1 -> R.color.primary
            2 -> R.color.blue
            3 -> R.color.purple
            4 -> R.color.orange
            5 -> R.color.white
            else -> R.color.primary
        }
    }
    
    /**
     * Apply saved theme settings
     */
    fun applyTheme() {
        // Apply theme mode
        val nightMode = when (getThemeMode()) {
            MODE_LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
            MODE_DARK -> AppCompatDelegate.MODE_NIGHT_YES
            else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
        AppCompatDelegate.setDefaultNightMode(nightMode)
        
        // Font scale and accent color need to be applied in the respective activities
    }
    
    /**
     * Checks if the current theme is in dark mode
     */
    fun isNightMode(): Boolean {
        val nightMode = AppCompatDelegate.getDefaultNightMode()
        return when (nightMode) {
            AppCompatDelegate.MODE_NIGHT_YES -> true
            AppCompatDelegate.MODE_NIGHT_NO -> false
            else -> {
                // If following system, check if the context is in night mode
                (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
            }
        }
    }
    
    companion object {
        private const val PREFS_NAME = "theme_prefs"
        private const val KEY_THEME_MODE = "theme_mode"
        private const val KEY_DARK_MODE = "dark_mode" // Kept for backward compatibility
        private const val KEY_FONT_SCALE = "font_scale"
        private const val KEY_ACCENT_COLOR = "accent_color"
        
        // Theme mode constants
        const val MODE_SYSTEM = 0
        const val MODE_LIGHT = 1
        const val MODE_DARK = 2
    }
} 