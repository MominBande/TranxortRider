package com.tranxortrider.deliveryrider.utils

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.tranxortrider.deliveryrider.R
import com.tranxortrider.deliveryrider.TranxortRiderApplication

/**
 * Base activity that all activities should extend to handle theming automatically
 */
open class BaseActivity : AppCompatActivity() {
    
    protected lateinit var themeManager: ThemeManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        // Apply theme manager before calling super.onCreate
        themeManager = (application as TranxortRiderApplication).getThemeManager()
        
        super.onCreate(savedInstanceState)
        
        // Note: Don't call applyAccentColor here anymore, 
        // each child class should call it in their onStart or after views are initialized
    }
    
    override fun onStart() {
        super.onStart()
        // Child classes can override this method and call applyAccentColor()
        // after they have initialized their views
    }
    
    override fun attachBaseContext(newBase: Context) {
        // Apply font scale to all text
        val themeManager = ThemeManager(newBase)
        val fontScale = themeManager.getFontScale() / 100f
        
        val configuration = Configuration(newBase.resources.configuration)
        configuration.fontScale = fontScale
        
        val context = newBase.createConfigurationContext(configuration)
        super.attachBaseContext(context)
    }
    
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Handle theme changes if needed
    }
    
    /**
     * Apply the selected accent color to UI elements
     */
    protected open fun applyAccentColor() {
        // Get the current accent color index
        val colorIndex = themeManager.getAccentColorIndex()
        
        // No need to do anything here in the base class
        // Each activity that needs accent color should override this
    }
    
    /**
     * Get the current accent color resource ID
     */
    protected fun getAccentColorResId(): Int {
        return themeManager.getAccentColorResId()
    }
    
    /**
     * Get the current accent color
     */
    protected fun getAccentColor(): Int {
        return ContextCompat.getColor(this, getAccentColorResId())
    }
    
    /**
     * Check if dark mode is currently active
     */
    protected fun isDarkMode(): Boolean {
        return themeManager.isNightMode()
    }
} 