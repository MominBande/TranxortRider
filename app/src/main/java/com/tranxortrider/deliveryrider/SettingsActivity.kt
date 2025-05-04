package com.tranxortrider.deliveryrider

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.slider.Slider
import com.google.firebase.auth.FirebaseAuth
import com.tranxortrider.deliveryrider.utils.BaseActivity
import com.tranxortrider.deliveryrider.utils.SessionManager
import com.tranxortrider.deliveryrider.utils.ThemeManager
import com.tranxortrider.deliveryrider.utils.UIUtils

class SettingsActivity : BaseActivity() {
    
    // UI Components
    private lateinit var backButton: MaterialButton
    private lateinit var themeRadioGroup: RadioGroup
    private lateinit var systemDefaultRadio: RadioButton
    private lateinit var lightModeRadio: RadioButton
    private lateinit var darkModeRadio: RadioButton
    private lateinit var fontSizeSlider: Slider
    private lateinit var accentColorCard1: MaterialCardView
    private lateinit var accentColorCard2: MaterialCardView
    private lateinit var accentColorCard3: MaterialCardView
    private lateinit var accentColorCard4: MaterialCardView
    private lateinit var accentColorCard5: MaterialCardView
    private lateinit var fontSizeValue: TextView
    private lateinit var notificationsCard: CardView
    private lateinit var profileCard: CardView
    private lateinit var helpCenterCard: CardView
    private lateinit var emergencySOSCard: CardView
    private lateinit var fontPreviewText: TextView
    private lateinit var logoutButton: Button
    
    // Services
    private lateinit var sessionManager: SessionManager
    private lateinit var auth: FirebaseAuth
    
    // Store the source activity to go back to
    private var sourceActivity: String? = null
    
    // Flag to prevent recreation loops
    private var isManuallyChangingTheme = false
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        
        // Get the source activity if available
        sourceActivity = intent.getStringExtra("source_activity")
        
        // Initialize services
        sessionManager = SessionManager(this)
        auth = FirebaseAuth.getInstance()
        
        // Initialize UI
        initViews()
        setupListeners()
        
        // Load current settings
        loadCurrentSettings()
        
        // Apply accent colors after views are initialized
        applyAccentColor()
    }
    
    private fun initViews() {
        // Find UI components
        backButton = findViewById(R.id.btnBack)
        themeRadioGroup = findViewById(R.id.themeRadioGroup)
        systemDefaultRadio = findViewById(R.id.systemDefaultRadio)
        lightModeRadio = findViewById(R.id.lightModeRadio)
        darkModeRadio = findViewById(R.id.darkModeRadio)
        fontSizeSlider = findViewById(R.id.fontSizeSlider)
        fontSizeValue = findViewById(R.id.fontSizeValue)
        fontPreviewText = findViewById(R.id.fontPreviewText)
        
        accentColorCard1 = findViewById(R.id.accentColorCard1)
        accentColorCard2 = findViewById(R.id.accentColorCard2)
        accentColorCard3 = findViewById(R.id.accentColorCard3)
        accentColorCard4 = findViewById(R.id.accentColorCard4)
        accentColorCard5 = findViewById(R.id.accentColorCard5)
        
        notificationsCard = findViewById(R.id.notificationsCard)
        profileCard = findViewById(R.id.profileCard)
        helpCenterCard = findViewById(R.id.helpCenterCard)
        emergencySOSCard = findViewById(R.id.emergencySOSCard)
        
        logoutButton = findViewById(R.id.logoutButton)
    }
    
    private fun setupListeners() {
        // Back button - go back to previous screen
        backButton.setOnClickListener {
            navigateBack()
        }
        
        // Theme Radio Group
        themeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.systemDefaultRadio -> applyThemeMode(ThemeManager.MODE_SYSTEM)
                R.id.lightModeRadio -> applyThemeMode(ThemeManager.MODE_LIGHT)
                R.id.darkModeRadio -> applyThemeMode(ThemeManager.MODE_DARK)
            }
        }
        
        // Font Size Slider
        fontSizeSlider.addOnChangeListener { _, value, _ ->
            val percentage = value.toInt()
            fontSizeValue.text = "$percentage%"
            applyFontSize(percentage)
        }
        
        // Accent Color Cards
        accentColorCard1.setOnClickListener { selectAccentColor(1) }
        accentColorCard2.setOnClickListener { selectAccentColor(2) }
        accentColorCard3.setOnClickListener { selectAccentColor(3) }
        accentColorCard4.setOnClickListener { selectAccentColor(4) }
        accentColorCard5.setOnClickListener { selectAccentColor(5) }
        
        // Navigation cards
        notificationsCard.setOnClickListener {
            // In a real app, this would navigate to notification settings
            UIUtils.showToast(this, "Notification settings")
        }
        
        profileCard.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        
        helpCenterCard.setOnClickListener {
            startActivity(Intent(this, HelpCenterActivity::class.java))
        }
        
        emergencySOSCard.setOnClickListener {
            startActivity(Intent(this, EmergencySOSActivity::class.java))
        }
        
        // Logout Button - show confirmation dialog
        logoutButton.setOnClickListener {
            showLogoutConfirmationDialog()
        }
    }
    
    private fun navigateBack() {
        try {
            if (sourceActivity != null) {
                // Navigate back to the source activity
                val activityClass = Class.forName(sourceActivity!!)
                val intent = Intent(this, activityClass)
                startActivity(intent)
                finish()
            } else if (isTaskRoot) {
                // If this is the root task and no source specified, go to home
                startActivity(Intent(this, home_screen::class.java))
                finish()
            } else {
                // Just finish if we have a parent activity in the stack
                super.onBackPressed()
            }
        } catch (e: Exception) {
            // If anything goes wrong, go to home screen
            startActivity(Intent(this, home_screen::class.java))
            finish()
        }
    }
    
    // Handle system back button the same way
    override fun onBackPressed() {
        navigateBack()
    }
    
    private fun loadCurrentSettings() {
        // Set flag to prevent accidental recreation
        isManuallyChangingTheme = true
        
        try {
            // Theme mode setting
            when (themeManager.getThemeMode()) {
                ThemeManager.MODE_SYSTEM -> systemDefaultRadio.isChecked = true
                ThemeManager.MODE_LIGHT -> lightModeRadio.isChecked = true
                ThemeManager.MODE_DARK -> darkModeRadio.isChecked = true
            }
            
            // Font size
            val fontSizePercentage = themeManager.getFontScale()
            fontSizeSlider.value = fontSizePercentage
            fontSizeValue.text = "${fontSizePercentage.toInt()}%"
            
            // Accent color
            val accentColorIndex = themeManager.getAccentColorIndex()
            highlightSelectedAccentColor(accentColorIndex)
            
            // Update UI components to reflect current theme
            updateUIForCurrentTheme()
        } finally {
            isManuallyChangingTheme = false
        }
    }
    
    private fun updateUIForCurrentTheme() {
        val isDarkMode = isDarkMode()
        
        // Apply any theme-specific UI tweaks here
        if (isDarkMode) {
            // For dark theme
            accentColorCard5.strokeColor = ContextCompat.getColor(this, R.color.dark_border)
        } else {
            // For light theme
            accentColorCard5.strokeColor = ContextCompat.getColor(this, R.color.light_border)
        }
    }
    
    private fun applyThemeMode(mode: Int) {
        // Check if we're actually changing the theme
        if (themeManager.getThemeMode() == mode) {
            return
        }
        
        // Set flag to avoid infinite recreate loops
        isManuallyChangingTheme = true
        
        // Save the new theme mode
        themeManager.setThemeMode(mode)
        
        // Apply the theme change
        val nightMode = when (mode) {
            ThemeManager.MODE_LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
            ThemeManager.MODE_DARK -> AppCompatDelegate.MODE_NIGHT_YES
            else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
        
        // Apply the theme change immediately
        AppCompatDelegate.setDefaultNightMode(nightMode)
        
        // Show feedback to user
        UIUtils.showToast(this, "Theme applied")
        
        // Don't need to recreate manually - the system will do it automatically 
        // when night mode changes
    }
    
    private fun applyFontSize(percentage: Int) {
        val fontScale = percentage / 100f
        themeManager.setFontScale(fontScale)
        
        // Update preview text
        fontPreviewText.textSize = 16f * fontScale
        
        // To actually apply the font scale across the app,
        // we would need to recreate the activity or restart the app
    }
    
    private fun selectAccentColor(colorIndex: Int) {
        themeManager.setAccentColorIndex(colorIndex)
        highlightSelectedAccentColor(colorIndex)
        
        // Apply the accent color immediately to some UI elements
        applyAccentColor()
        
        UIUtils.showSnackbar(
            findViewById(android.R.id.content),
            "Accent color applied"
        )
    }
    
    override fun applyAccentColor() {
        super.applyAccentColor()
        
        // Get the accent color
        val accentColor = getAccentColor()
        
        // Apply the color to relevant UI elements as needed
        // For demonstration, we'll apply it to buttons and some elements
        logoutButton.backgroundTintList = ContextCompat.getColorStateList(this, R.color.red) // Keep logout red
        
        // For other screens, the accent color will be applied when they're created
    }
    
    private fun highlightSelectedAccentColor(colorIndex: Int) {
        // Reset all cards
        accentColorCard1.strokeWidth = 0
        accentColorCard2.strokeWidth = 0
        accentColorCard3.strokeWidth = 0
        accentColorCard4.strokeWidth = 0
        accentColorCard5.strokeWidth = 1
        
        // Highlight selected card
        when (colorIndex) {
            1 -> accentColorCard1.strokeWidth = 4
            2 -> accentColorCard2.strokeWidth = 4
            3 -> accentColorCard3.strokeWidth = 4
            4 -> accentColorCard4.strokeWidth = 4
            5 -> accentColorCard5.strokeWidth = 4
        }
    }
    
    private fun showLogoutConfirmationDialog() {
        // Create a custom dialog view that matches the UI in the screenshot
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_logout_confirmation, null)
        
        // Create the dialog
        val dialog = MaterialAlertDialogBuilder(this, R.style.RoundedDialogStyle)
            .setView(dialogView)
            .setCancelable(true)
            .create()
        
        // Set up click listeners for the buttons
        dialogView.findViewById<Button>(R.id.btnCancel).setOnClickListener {
            dialog.dismiss()
        }
        
        dialogView.findViewById<Button>(R.id.btnLogout).setOnClickListener {
            dialog.dismiss()
            performLogout()
        }
        
        // Show the dialog
        dialog.show()
    }
    
    private fun performLogout() {
        // Sign out from Firebase
        auth.signOut()
        
        // Clear session
        sessionManager.clearSession()
        
        // Navigate to sign in screen
        val intent = Intent(this, sign_in_screen::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
    
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        
        // Skip updating if we're in the middle of manually changing theme
        if (isManuallyChangingTheme) {
            return
        }
        
        // Handle configuration changes
        updateUIForCurrentTheme()
        
        // Reset the flag after a delay to ensure it doesn't interfere with normal operation
        backButton.postDelayed({
            isManuallyChangingTheme = false
        }, 500)
    }
} 