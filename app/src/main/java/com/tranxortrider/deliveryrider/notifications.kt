package com.tranxortrider.deliveryrider

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tranxortrider.deliveryrider.adapters.NotificationsAdapter
import com.tranxortrider.deliveryrider.models.Notification
import com.tranxortrider.deliveryrider.repositories.NotificationRepository
import com.tranxortrider.deliveryrider.utils.NetworkUtils
import com.tranxortrider.deliveryrider.utils.SessionManager
import com.tranxortrider.deliveryrider.utils.UIUtils

class notifications : AppCompatActivity() {
    
    // UI Components
    private lateinit var toolbarView: View // Use View instead of Toolbar to avoid crashes if not found
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyStateContainer: View
    private lateinit var loadingView: View
    private lateinit var clearAllButton: MaterialButton
    private lateinit var backButton: MaterialButton
    private lateinit var emptyStateTitle: TextView
    private lateinit var emptyStateMessage: TextView
    
    // Optional components that might not exist in the layout
    private var bottomNavigation: BottomNavigationView? = null
    
    // Dependencies
    private lateinit var sessionManager: SessionManager
    private lateinit var notificationRepository: NotificationRepository
    
    // Data
    private lateinit var notificationsAdapter: NotificationsAdapter
    private var notifications = listOf<Notification>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_notifications)
        
        // Initialize dependencies
        sessionManager = SessionManager(this)
        notificationRepository = NotificationRepository()
        
        // Check if user is logged in
        if (sessionManager.getUser() == null) {
            navigateToSignIn()
            return
        }
        
        // Initialize UI components
        initializeUI()
        
        // Set up click listeners
        setupClickListeners()
        
        // Load notifications
        loadNotifications()
    }
    
    private fun initializeUI() {
        // Find views
        toolbarView = findViewById(R.id.topBar)
        recyclerView = findViewById(R.id.recyclerView)
        emptyStateContainer = findViewById(R.id.emptyStateContainer)
        loadingView = findViewById(R.id.loadingView) ?: View(this).apply { visibility = View.GONE }
        clearAllButton = findViewById(R.id.clearAllButton)
        backButton = findViewById(R.id.btnBack)
        emptyStateTitle = findViewById(R.id.emptyStateTitle)
        emptyStateMessage = findViewById(R.id.emptyStateMessage)
        
        // Try to find optional components
        bottomNavigation = findViewById(R.id.bottomNavigation)
        
        // Set up RecyclerView
        notificationsAdapter = NotificationsAdapter(emptyList())
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@notifications)
            adapter = notificationsAdapter
        }
        
        // Set up bottom navigation if it exists
        setupBottomNavigation()
    }
    
    private fun setupBottomNavigation() {
        // Skip setup if bottomNavigation is not in the layout
        bottomNavigation?.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, home_screen::class.java))
                    true
                }
                R.id.nav_orders -> {
                    startActivity(Intent(this, pending_orders::class.java))
                    true
                }
                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
    
    private fun setupClickListeners() {
        // Clear all button
        clearAllButton.setOnClickListener {
            showClearAllDialog()
        }
        
        // Back button
        backButton.setOnClickListener {
            onBackPressed()
        }
        
        // Notification item click
        notificationsAdapter.setOnItemClickListener { notification ->
            handleNotificationClick(notification)
        }
        
        // Mark as read click - handle possible null listener
        notificationsAdapter.setOnMarkReadClickListener { notification, position ->
            markNotificationAsRead(notification, position)
        }
        
        // Delete click - handle possible null listener
        notificationsAdapter.setOnDeleteClickListener { notification, position ->
            deleteNotification(notification, position)
        }
    }
    
    private fun loadNotifications() {
        showLoading()
        
        if (!NetworkUtils.isNetworkAvailable(this)) {
            UIUtils.showSnackbar(findViewById(android.R.id.content), "No internet connection")
            hideLoading()
            showEmptyState()
            return
        }
        
        val userId = sessionManager.getUser()?.id
        if (userId == null) {
            UIUtils.showSnackbar(findViewById(android.R.id.content), "User not logged in")
            hideLoading()
            showEmptyState()
            return
        }
        
        notificationRepository.getNotifications(userId) { success, message, notificationsList ->
            runOnUiThread {
                hideLoading()
                
                if (success && notificationsList != null) {
                    notifications = notificationsList
                    updateNotificationsUI()
                } else {
                    UIUtils.showSnackbar(findViewById(android.R.id.content), message)
                    showEmptyState()
                }
            }
        }
    }
    
    private fun updateNotificationsUI() {
        if (notifications.isEmpty()) {
            showEmptyState()
            clearAllButton.visibility = View.GONE
        } else {
            emptyStateContainer.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            notificationsAdapter.updateNotifications(notifications)
            clearAllButton.visibility = View.VISIBLE
        }
    }
    
    private fun showEmptyState() {
        recyclerView.visibility = View.GONE
        emptyStateContainer.visibility = View.VISIBLE
        
        emptyStateTitle.text = "No Notifications"
        emptyStateMessage.text = "You'll receive notifications here for order updates, earnings, and important announcements"
    }
    
    private fun showLoading() {
        if (::loadingView.isInitialized) {
            loadingView.visibility = View.VISIBLE
        }
        emptyStateContainer.visibility = View.GONE
        recyclerView.visibility = View.GONE
    }
    
    private fun hideLoading() {
        if (::loadingView.isInitialized) {
            loadingView.visibility = View.GONE
        }
    }
    
    private fun handleNotificationClick(notification: Notification) {
        // Mark notification as read first
        if (!notification.isRead) {
            markNotificationAsRead(notification, notifications.indexOf(notification))
        }
        
        try {
            // Navigate based on notification type
            when (notification.type) {
                Notification.Type.NEW_ORDER -> {
                    if (!notification.relatedId.isNullOrEmpty()) {
                        val intent = Intent(this, order_details::class.java)
                        intent.putExtra("ORDER_ID", notification.relatedId)
                        startActivity(intent)
                    } else {
                        UIUtils.showToast(this, "Order details not available")
                    }
                }
                Notification.Type.ORDER_STATUS_CHANGED -> {
                    if (!notification.relatedId.isNullOrEmpty()) {
                        val intent = Intent(this, order_details::class.java)
                        intent.putExtra("ORDER_ID", notification.relatedId)
                        startActivity(intent)
                    } else {
                        UIUtils.showToast(this, "Order details not available")
                    }
                }
                Notification.Type.EARNINGS -> {
                    try {
                        startActivity(Intent(this, EarningsActivity::class.java))
                    } catch (e: Exception) {
                        UIUtils.showToast(this, "Earnings screen not available")
                    }
                }
                Notification.Type.ANNOUNCEMENT -> {
                    showAnnouncementDialog(notification)
                }
                Notification.Type.BATCH_ASSIGNMENT -> {
                    if (!notification.relatedId.isNullOrEmpty()) {
                        try {
                            val intent = Intent(this, batch::class.java)
                            intent.putExtra("BATCH_ID", notification.relatedId)
                            startActivity(intent)
                        } catch (e: Exception) {
                            UIUtils.showToast(this, "Batch details not available")
                        }
                    } else {
                        UIUtils.showToast(this, "Batch details not available")
                    }
                }
            }
        } catch (e: Exception) {
            UIUtils.showToast(this, "Could not process notification")
        }
    }
    
    private fun markNotificationAsRead(notification: Notification, position: Int) {
        if (notification.isRead) return
        
        notificationRepository.markAsRead(notification.id) { success, message ->
            runOnUiThread {
                if (success) {
                    // Update local data
                    try {
                        val updatedNotification = notification.copy(isRead = true)
                        val updatedList = notifications.toMutableList()
                        updatedList[position] = updatedNotification
                        notifications = updatedList
                        
                        // Update UI
                        notificationsAdapter.updateNotifications(notifications)
                    } catch (e: Exception) {
                        // Handle potential index issues safely
                        loadNotifications() // Reload all notifications instead
                    }
                } else {
                    UIUtils.showSnackbar(findViewById(android.R.id.content), message)
                }
            }
        }
    }
    
    private fun deleteNotification(notification: Notification, position: Int) {
        notificationRepository.deleteNotification(notification.id) { success, message ->
            runOnUiThread {
                if (success) {
                    // Remove from local list
                    try {
                        val updatedList = notifications.toMutableList()
                        updatedList.removeAt(position)
                        notifications = updatedList
                        
                        // Update UI
                        updateNotificationsUI()
                        
                        UIUtils.showSnackbar(findViewById(android.R.id.content), "Notification deleted")
                    } catch (e: Exception) {
                        // Handle potential index issues safely
                        loadNotifications() // Reload all notifications instead
                    }
                } else {
                    UIUtils.showSnackbar(findViewById(android.R.id.content), message)
                }
            }
        }
    }
    
    private fun showClearAllDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Clear All Notifications")
            .setMessage("Are you sure you want to delete all notifications? This action cannot be undone.")
            .setPositiveButton("Clear All") { _, _ ->
                clearAllNotifications()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun clearAllNotifications() {
        showLoading()
        
        val userId = sessionManager.getUser()?.id
        if (userId == null) {
            UIUtils.showSnackbar(findViewById(android.R.id.content), "User not logged in")
            hideLoading()
            return
        }
        
        notificationRepository.clearAllNotifications(userId) { success, message ->
            runOnUiThread {
                hideLoading()
                
                if (success) {
                    // Clear local list
                    notifications = emptyList()
                    
                    // Update UI
                    updateNotificationsUI()
                    
                    UIUtils.showSnackbar(findViewById(android.R.id.content), "All notifications cleared")
                } else {
                    UIUtils.showSnackbar(findViewById(android.R.id.content), message)
                }
            }
        }
    }
    
    private fun showAnnouncementDialog(notification: Notification) {
        MaterialAlertDialogBuilder(this)
            .setTitle(notification.title)
            .setMessage(notification.message)
            .setPositiveButton("OK", null)
            .show()
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