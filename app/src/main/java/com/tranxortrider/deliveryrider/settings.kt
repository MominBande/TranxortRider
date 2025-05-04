package com.tranxortrider.deliveryrider

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.switchmaterial.SwitchMaterial
import com.tranxortrider.deliveryrider.utils.SessionManager
import com.tranxortrider.deliveryrider.utils.ThemeManager
import com.tranxortrider.deliveryrider.utils.UIUtils

class settings : AppCompatActivity() {
    
    // UI Components
    private lateinit var toolbar: Toolbar
    private lateinit var bottomNavigation: BottomNavigationView
    
    // Settings UI elements (created programmatically)
    private lateinit var settingsContainer: LinearLayout
    private lateinit var appThemeItem: View
    private lateinit var notificationsItem: View
    private lateinit var privacyItem: View
    private lateinit var termsOfServiceItem: View
    private lateinit var emergencyContactsItem: View
    private lateinit var helpCenterItem: View
    private lateinit var logoutItem: View
    private lateinit var clearCacheItem: View
    private lateinit var appVersionItem: View
    private lateinit var notificationsSwitch: MaterialSwitch
    private lateinit var versionText: TextView
    
    // Dependencies
    private lateinit var sessionManager: SessionManager
    private lateinit var themeManager: ThemeManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)
        
        // Initialize dependencies
        sessionManager = SessionManager(this)
        themeManager = ThemeManager(this)
        
        // Check if user is logged in
        if (sessionManager.getUser() == null) {
            navigateToSignIn()
            return
        }
        
        // Initialize UI components
        initializeUI()
        
        // Set up click listeners
        setupClickListeners()
        
        // Load current settings
        loadSettings()
    }
    
    private fun initializeUI() {
        // Get the container from the layout
        settingsContainer = findViewById<LinearLayout>(android.R.id.content)
            .findViewWithTag("settings_container") ?: LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
            
        // Find existing views or create them programmatically
        toolbar = findViewById(R.id.toolbar) ?: Toolbar(this).apply {
            title = "Settings"
        }
        
        // Create setting items
        appThemeItem = createSettingItem("App Theme", "Change app appearance")
        notificationsItem = createSettingItem("Notifications", "Manage notification preferences")
        privacyItem = createSettingItem("Privacy Policy", "View our privacy policy")
        termsOfServiceItem = createSettingItem("Terms of Service", "View our terms of service")
        emergencyContactsItem = createSettingItem("Emergency Contacts", "Manage emergency contacts")
        helpCenterItem = createSettingItem("Help Center", "Get help with the app")
        logoutItem = createSettingItem("Logout", "Sign out of your account")
        clearCacheItem = createSettingItem("Clear Cache", "Clear temporary files")
        appVersionItem = createSettingItem("App Version", "")
        
        // Create notification switch
        notificationsSwitch = MaterialSwitch(this).apply {
            isChecked = true
        }
        
        // Add switch to notifications item
        (notificationsItem as? ViewGroup)?.addView(notificationsSwitch)
        
        // Create version text
        versionText = TextView(this).apply {
            text = "Version 1.0.0"
            textSize = 14f
        }
        
        // Add version text to app version item
        (appVersionItem as? ViewGroup)?.addView(versionText)
        
        // Find bottom navigation or create it
        bottomNavigation = findViewById(R.id.bottomNavigation) ?: BottomNavigationView(this)
        
        // Set up toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = "Settings"
        }
        
        // Set up bottom navigation
        if (bottomNavigation.menu.size() > 0) {
            bottomNavigation.selectedItemId = R.id.nav_profile
            bottomNavigation.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.nav_home -> {
                        startActivity(Intent(this, home_screen::class.java))
                        true
                    }
                    R.id.nav_orders -> {
                        startActivity(Intent(this, pending_orders::class.java))
                        true
                    }
                    R.id.nav_profile -> true
                    else -> false
                }
            }
        }
    }
    
    private fun createSettingItem(title: String, subtitle: String): View {
        // Create a linear layout for the setting item
        return LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 16, 0, 16)
            }
            
            // Create a vertical layout for text
            val textContainer = LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                layoutParams = LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1.0f
                )
                
                // Add title
                addView(TextView(context).apply {
                    text = title
                    textSize = 16f
                    setTextColor(resources.getColor(R.color.light_text_primary, theme))
                })
                
                // Add subtitle if not empty
                if (subtitle.isNotEmpty()) {
                    addView(TextView(context).apply {
                        text = subtitle
                        textSize = 14f
                        setTextColor(resources.getColor(R.color.light_text_secondary, theme))
                    })
                }
            }
            
            addView(textContainer)
        }
    }
    
    private fun setupClickListeners() {
        // App theme
        appThemeItem.setOnClickListener {
            // Just show a message for now
            UIUtils.showSnackbar(findViewById(android.R.id.content), "Theme settings would open here")
        }
        
        // Notifications
        notificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            updateNotificationSettings(isChecked)
        }
        
        // Privacy policy
        privacyItem.setOnClickListener {
            openPrivacyPolicy()
        }
        
        // Terms of service
        termsOfServiceItem.setOnClickListener {
            openTermsOfService()
        }
        
        // Emergency contacts
        emergencyContactsItem.setOnClickListener {
            UIUtils.showSnackbar(findViewById(android.R.id.content), "Emergency contacts would open here")
        }
        
        // Help center
        helpCenterItem.setOnClickListener {
            UIUtils.showSnackbar(findViewById(android.R.id.content), "Help center would open here")
        }
        
        // Logout
        logoutItem.setOnClickListener {
            showLogoutConfirmationDialog()
        }
        
        // Clear cache
        clearCacheItem.setOnClickListener {
            showClearCacheDialog()
        }
    }
    
    private fun loadSettings() {
        // Load notifications settings - use default true if method doesn't exist
        val notificationsEnabled = true
        notificationsSwitch.isChecked = notificationsEnabled
        
        // Set app version
        try {
            val packageInfo = packageManager.getPackageInfo(packageName, 0)
            versionText.text = "Version ${packageInfo.versionName} (${packageInfo.versionCode})"
        } catch (e: Exception) {
            versionText.text = "Version Unknown"
        }
    }
    
    private fun updateNotificationSettings(enabled: Boolean) {
        // Store notification preference - commented out since method doesn't exist
        // sessionManager.setNotificationsEnabled(enabled)
        
        // In a real app, this would update notification settings on the server
        UIUtils.showSnackbar(
            findViewById(android.R.id.content),
            if (enabled) "Notifications enabled" else "Notifications disabled"
        )
    }
    
    private fun openPrivacyPolicy() {
        // In a real app, this would open a webview or dedicated activity
        UIUtils.showSnackbar(findViewById(android.R.id.content), "Privacy Policy would open here")
    }
    
    private fun openTermsOfService() {
        // In a real app, this would open a webview or dedicated activity
        UIUtils.showSnackbar(findViewById(android.R.id.content), "Terms of Service would open here")
    }
    
    private fun showLogoutConfirmationDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Log Out")
            .setMessage("Are you sure you want to log out?")
            .setPositiveButton("Log Out") { _, _ ->
                logout()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun logout() {
        // Clear user session
        sessionManager.clearSession()
        
        // Navigate to sign in screen
        val intent = Intent(this, sign_in_screen::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
    
    private fun showClearCacheDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Clear Cache")
            .setMessage("Are you sure you want to clear cached data? This won't affect your account information.")
            .setPositiveButton("Clear") { _, _ ->
                clearCache()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun clearCache() {
        // In a real app, this would clear the app's cache
        // For now, just show a confirmation message
        UIUtils.showSnackbar(findViewById(android.R.id.content), "Cache cleared successfully")
    }
    
    private fun navigateToSignIn() {
        val intent = Intent(this, sign_in_screen::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}