package com.tranxortrider.deliveryrider

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.imageview.ShapeableImageView
import com.tranxortrider.deliveryrider.adapters.PendingOrdersAdapter
import com.tranxortrider.deliveryrider.models.Order
import com.tranxortrider.deliveryrider.models.OrderStatus
import com.tranxortrider.deliveryrider.repositories.OrderRepository
import com.tranxortrider.deliveryrider.utils.NetworkUtils
import com.tranxortrider.deliveryrider.utils.SessionManager
import com.tranxortrider.deliveryrider.utils.UIUtils
import com.tranxortrider.deliveryrider.utils.SharedPreferencesManager
import com.tranxortrider.deliveryrider.services.LocationService
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.tranxortrider.deliveryrider.services.FirestoreService
import com.tranxortrider.deliveryrider.services.FirestoreListenerService
import java.util.*
import com.google.firebase.firestore.FirebaseFirestore

class home_screen : AppCompatActivity() {
    
    // UI Components
    private lateinit var tvName: TextView
    private lateinit var tvDeliveryCount: TextView
    private lateinit var tvOnTimeRate: TextView
    private lateinit var btnDutyStatus: androidx.appcompat.widget.SwitchCompat
    private lateinit var tvDutyStatus: TextView
    private lateinit var btnSearch: MaterialButton
    private lateinit var btnNotifications: MaterialButton
    private lateinit var notificationCount: TextView
    private lateinit var profileImage: ShapeableImageView
    private lateinit var pendingOrdersRecyclerView: RecyclerView
    private lateinit var emptyOrdersView: View
    private lateinit var loadingView: View
    private lateinit var filterButtonAll: Button
    private lateinit var filterButtonPending: Button
    private lateinit var filterButtonAccepted: Button
    private lateinit var filterButtonCompleted: Button
    private lateinit var bottomNavigation: BottomNavigationView
    
    // Dependencies
    private lateinit var sessionManager: SessionManager
    private lateinit var orderRepository: OrderRepository
    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    private lateinit var firestoreService: FirestoreService
    
    // Permission related
    private val requiredPermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.POST_NOTIFICATIONS,
            Manifest.permission.FOREGROUND_SERVICE_LOCATION
        )
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.FOREGROUND_SERVICE_LOCATION
        )
    } else {
        arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }
    
    // Permission callback
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        var locationPermissionGranted = false
        var allRequiredPermissionsGranted = true
        
        permissions.entries.forEach { entry ->
            val permission = entry.key
            val isGranted = entry.value
            
            // Check if any of the location permissions are granted
            if ((permission == Manifest.permission.ACCESS_FINE_LOCATION || 
                permission == Manifest.permission.ACCESS_COARSE_LOCATION) && isGranted) {
                locationPermissionGranted = true
            }
            
            // Check if all permissions are granted
            if (!isGranted) {
                allRequiredPermissionsGranted = false
            }
        }
        
        if (locationPermissionGranted && allRequiredPermissionsGranted) {
            // All required permissions granted, start location service
            startLocationServiceIfEnabled()
        } else {
            // Show message about missing permissions
            UIUtils.showSnackbar(findViewById(android.R.id.content),
                "Location permission is required for rider tracking")
        }
    }
    
    // Data
    private lateinit var pendingOrdersAdapter: PendingOrdersAdapter
    private var isOnDuty = true
    private var currentFilter = "all"
    private var allOrders = listOf<Order>()
    
    // Track if RecyclerView is used in this layout
    private var hasRecyclerView = false
    
    // Broadcast receiver for order status changes
    private val orderStatusReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == "com.tranxortrider.deliveryrider.ORDER_STATUS_CHANGED") {
                fetchOrders(currentFilter)
            }
        }
    }
    
    // Add these private variables after the other class variables
    private var firebaseListenerService = FirestoreListenerService()
    
    // Auto-refresh timer
    private var refreshTimer: Timer? = null
    
    // Add a flag to track delivery stats views
    private var hasDeliveryStatsViews = false
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home_screen)
        
        // Initialize dependencies
        sessionManager = SessionManager(this)
        orderRepository = OrderRepository()
        sharedPreferencesManager = SharedPreferencesManager(this)
        firestoreService = FirestoreService()
        
        // Check if user is logged in
        if (sessionManager.getUser() == null) {
            navigateToSignIn()
            return
        }
        
        // Initialize UI components first to allow user to access the screen
        initializeUI()
        
        // Set up click listeners
        setupClickListeners()
        
        // Load user data
        loadUserData()
        
        // Load orders
        fetchOrders(currentFilter)
        
        // Register broadcast receiver
        try {
            registerReceiver(orderStatusReceiver, IntentFilter("com.tranxortrider.deliveryrider.ORDER_STATUS_CHANGED"))
            isReceiverRegistered = true
        } catch (e: Exception) {
            // Log error but continue if registration fails
        }
        
        // Set up periodic refresh for orders
        startOrderRefreshTimer()
        
        // Start listening for order assignments
        startOrderAssignmentListening()
        
        // Setup auto-refresh timer
        startAutoRefresh()
        
        // Check verification status in background without blocking UI
        checkUserVerificationStatus()
        
        // Add status bar configuration if needed
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.primary)
            
            // Set system UI flags to ensure text is visible on colored status bar
            window.decorView.systemUiVisibility = 0 // Reset any light status bar flags
            
            // Adjust top padding for the content to avoid status bar overlap
            findViewById<View>(R.id.topBar)?.setPadding(
                resources.getDimensionPixelSize(R.dimen.spacing_medium),  // left - keep existing padding
                resources.getDimensionPixelSize(R.dimen.spacing_large),   // top - add more padding for status bar
                resources.getDimensionPixelSize(R.dimen.spacing_medium),  // right - keep existing padding
                resources.getDimensionPixelSize(R.dimen.spacing_small)    // bottom - keep existing padding
            )
        }
    }
    
    private fun initializeUI() {
        // Find views
        try {
            tvName = findViewById(R.id.tvName)
            tvDeliveryCount = findViewById(R.id.tvDeliveryCount)
            tvOnTimeRate = findViewById(R.id.tvOnTimeRate)
            
            // Set flag for delivery stats views
            hasDeliveryStatsViews = tvName != null && tvDeliveryCount != null && tvOnTimeRate != null
        } catch (e: Exception) {
            Log.e("HomeScreen", "Error finding delivery stats views: ${e.message}")
            hasDeliveryStatsViews = false
        }
        
        try {
            btnDutyStatus = findViewById(R.id.btnDutyStatus)
            tvDutyStatus = findViewById(R.id.tvDutyStatus)
            btnSearch = findViewById(R.id.btnSearch)
            btnNotifications = findViewById(R.id.btnNotifications)
            notificationCount = findViewById(R.id.notificationCount)
            profileImage = findViewById(R.id.profileImage)
        } catch (e: Exception) {
            Log.e("HomeScreen", "Error finding UI controls: ${e.message}")
        }
        
        // These views might not exist in all layouts
        try {
            pendingOrdersRecyclerView = findViewById(R.id.pendingOrdersRecyclerView)
            emptyOrdersView = findViewById(R.id.emptyOrdersView)
            loadingView = findViewById(R.id.loadingView)
            
            // Mark that RecyclerView exists
            hasRecyclerView = pendingOrdersRecyclerView != null
        } catch (e: Exception) {
            Log.d("HomeScreen", "Some views not found in layout: ${e.message}")
            hasRecyclerView = false
        }
        
        // Initialize duty status if the button exists
        if (::btnDutyStatus.isInitialized) {
            btnDutyStatus.isChecked = isOnDuty
            
            // Initialize text color based on duty status
            if (::tvDutyStatus.isInitialized) {
                if (isOnDuty) {
                    tvDutyStatus.text = "On Duty"
                    tvDutyStatus.setTextColor(ContextCompat.getColor(this, R.color.green))
                } else {
                    tvDutyStatus.text = "Off Duty"
                    tvDutyStatus.setTextColor(ContextCompat.getColor(this, R.color.red))
                }
            }
        }
        
        // Filter buttons
        try {
            filterButtonAll = findViewById(R.id.filterAll)
            filterButtonPending = findViewById(R.id.filterPending)
            filterButtonAccepted = findViewById(R.id.filterAccepted)
            filterButtonCompleted = findViewById(R.id.filterCompleted)
        } catch (e: Exception) {
            Log.d("HomeScreen", "Filter buttons not found in layout: ${e.message}")
        }
        
        // Set up RecyclerView
        pendingOrdersAdapter = PendingOrdersAdapter(
            emptyList(),
            onAcceptClick = { order -> acceptOrder(order) },
            onRejectClick = { order -> showRejectDialog(order) },
            onItemClick = { order -> navigateToOrderDetails(order) }
        )
        
        // Only set up RecyclerView if it exists in this layout
        if (hasRecyclerView && ::pendingOrdersRecyclerView.isInitialized && pendingOrdersRecyclerView != null) {
            pendingOrdersRecyclerView.apply {
                layoutManager = LinearLayoutManager(this@home_screen)
                adapter = pendingOrdersAdapter
            }
            
            // Set the background color and elevation of RecyclerView
            pendingOrdersRecyclerView.setBackgroundColor(ContextCompat.getColor(this, R.color.light_background))
            ViewCompat.setElevation(pendingOrdersRecyclerView, resources.getDimension(R.dimen.card_elevation))
            
            // Make sure it's initially visible
            pendingOrdersRecyclerView.visibility = View.VISIBLE
        }
        
        // Set up bottom navigation
        setupBottomNavigation()
    }
    
    private fun setupBottomNavigation() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        if (bottomNavigationView != null) {
            // Use the NavigationUtils to set up bottom navigation
            com.tranxortrider.deliveryrider.utils.NavigationUtils.setupBottomNavigation(this, bottomNavigationView)
        }
    }
    
    private fun setupClickListeners() {
        // Profile section click
        findViewById<MaterialCardView>(R.id.profileSection).setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        
        // Duty status toggle using checked change listener
        if (::btnDutyStatus.isInitialized) {
            btnDutyStatus.setOnCheckedChangeListener { _, isChecked ->
                // Only toggle if the state actually changed from the current state
                if (isChecked != isOnDuty) {
                    toggleDutyStatus()
                }
            }
        }
        
        // Search button
        btnSearch.setOnClickListener {
            try {
                val intent = Intent(this, search::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                UIUtils.showToast(this, "Search functionality is not available")
            }
        }
        
        // Notifications button
        btnNotifications.setOnClickListener {
            try {
                val intent = Intent(this, notifications::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                UIUtils.showToast(this, "Notifications functionality is not available")
            }
        }
        
        // Filter buttons
        filterButtonAll?.setOnClickListener {
            updateFilter("all")
        }
        
        filterButtonPending?.setOnClickListener {
            updateFilter("pending")
        }
        
        filterButtonAccepted?.setOnClickListener {
            updateFilter("accepted")
        }
        
        filterButtonCompleted?.setOnClickListener {
            updateFilter("completed")
        }
        
        // Scanner Floating Action Button
        findViewById<View>(R.id.fabScanner)?.setOnClickListener {
            startActivity(Intent(this, scanner::class.java))
        }
    }
    
    private fun loadUserData() {
        val user = sessionManager.getUser()
        if (user != null && hasDeliveryStatsViews) {
            tvName.text = user.name
            
            // Load notification count
            checkNotifications()
            
            // Get delivery stats
            fetchDeliveryStats()
        }
    }
    
    private fun checkNotifications() {
        // This would normally be fetched from a repository
        // For now we'll just use a placeholder value
        val count = 2
        if (count > 0) {
            notificationCount.visibility = View.VISIBLE
            notificationCount.text = if (count > 9) "9+" else count.toString()
        } else {
            notificationCount.visibility = View.GONE
        }
    }
    
    private fun fetchDeliveryStats() {
        // Skip if delivery stats views don't exist in this layout
        if (!hasDeliveryStatsViews) {
            Log.d("HomeScreen", "Delivery stats views not available in this layout")
            return
        }
        
        // Get the current user ID
        val currentUserId = sessionManager.getUser()?.id ?: return
        
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Get completed orders count
                val completedOrdersResult = firestoreService.getDeliveryHistory(currentUserId)
                
                // Get on-time rate
                val earningsResult = firestoreService.getEarningsHistory(20)
                
                withContext(Dispatchers.Main) {
                    if (completedOrdersResult.isSuccess && hasDeliveryStatsViews) {
                        val deliveries = completedOrdersResult.getOrDefault(emptyList())
                        // Set the delivery count
                        tvDeliveryCount.text = deliveries.size.toString()
                        
                        // Calculate on-time rate - default to high value if no data available
                        val onTimeOrders = deliveries.count { it.status == OrderStatus.COMPLETED }
                        val onTimeRate = if (deliveries.isNotEmpty()) {
                            (onTimeOrders.toDouble() / deliveries.size * 100).toInt()
                        } else {
                            0
                        }
                        
                        // Set the on-time rate
                        tvOnTimeRate.text = "$onTimeRate%"
                    } else {
                        // Default values if data fetch fails
                        if (hasDeliveryStatsViews) {
                            tvDeliveryCount.text = "0"
                            tvOnTimeRate.text = "0%"
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("HomeScreen", "Error fetching delivery stats", e)
                withContext(Dispatchers.Main) {
                    // Default values if exception occurs
                    if (hasDeliveryStatsViews) {
                        tvDeliveryCount.text = "0"
                        tvOnTimeRate.text = "0%"
                    }
                }
            }
        }
    }
    
    private fun toggleDutyStatus() {
        isOnDuty = !isOnDuty
        if (isOnDuty) {
            // Update the switch state if it's initialized
            if (::btnDutyStatus.isInitialized) {
                btnDutyStatus.isChecked = true
            }
            // Make sure the text is visible and green
            if (::tvDutyStatus.isInitialized) {
                tvDutyStatus.visibility = View.VISIBLE
                tvDutyStatus.text = "On Duty"
                tvDutyStatus.setTextColor(ContextCompat.getColor(this, R.color.green))
            }
            // Show confirmation message
            UIUtils.showSnackbar(findViewById(android.R.id.content), "You are now on duty and will receive orders")
        } else {
            // Confirm going off duty with dialog
            if (allOrders.any { it.status == OrderStatus.ACCEPTED || it.status == OrderStatus.PICKED_UP }) {
                MaterialAlertDialogBuilder(this)
                    .setTitle("Active Orders")
                    .setMessage("You have active orders. Going off duty will hide new orders but you'll need to complete your current deliveries.")
                    .setPositiveButton("Go Off Duty") { _, _ ->
                        setOffDuty()
                    }
                    .setNegativeButton("Cancel") { _, _ ->
                        // Revert switch state
                        isOnDuty = true
                        if (::btnDutyStatus.isInitialized) {
                            btnDutyStatus.isChecked = true
                        }
                        // Make sure the text is visible and green
                        if (::tvDutyStatus.isInitialized) {
                            tvDutyStatus.visibility = View.VISIBLE
                            tvDutyStatus.text = "On Duty"
                            tvDutyStatus.setTextColor(ContextCompat.getColor(this, R.color.green))
                        }
                    }
                    .show()
            } else {
                setOffDuty()
            }
        }
    }
    
    private fun setOffDuty() {
        // Update the switch state if it's initialized
        if (::btnDutyStatus.isInitialized) {
            btnDutyStatus.isChecked = false
        }
        // Make sure the text is visible and red
        if (::tvDutyStatus.isInitialized) {
            tvDutyStatus.visibility = View.VISIBLE
            tvDutyStatus.text = "Off Duty"
            tvDutyStatus.setTextColor(ContextCompat.getColor(this, R.color.red))
        }
        UIUtils.showSnackbar(findViewById(android.R.id.content), "You are now off duty and won't receive new orders")
    }
    
    private fun fetchOrders(status: String? = null, showLoading: Boolean = true) {
        // Check if the views are initialized before showing loading
        if (showLoading && ::loadingView.isInitialized) {
            showLoading(true)
        }
        
        // Always fetch all orders and filter locally
        getOrders(null, 1, 50) { success, message, response ->
            runOnUiThread {
                // Check if the views are initialized before hiding loading
                if (::loadingView.isInitialized) {
                    showLoading(false)
                }
                
                if (success && response != null) {
                    @Suppress("UNCHECKED_CAST")
                    val newOrders = response as List<Order>
                    // Update orders list
                    allOrders = newOrders
                    
                    // Apply filter locally
                    applyCurrentFilter()
                } else {
                    // Show error message
                    UIUtils.showSnackbar(findViewById(android.R.id.content), 
                                       message ?: "Failed to load orders")
                }
            }
        }
    }
    
    private fun applyCurrentFilter() {
        // Simply filter the already fetched orders
        val filteredOrders = when (currentFilter) {
            "pending" -> allOrders.filter { 
                it.status == OrderStatus.PENDING ||
                it.status == OrderStatus.ASSIGNED 
            }
            "accepted" -> allOrders.filter { 
                it.status == OrderStatus.ACCEPTED || 
                it.status == OrderStatus.PICKED_UP ||
                it.status == OrderStatus.IN_TRANSIT
            }
            "completed" -> allOrders.filter { it.status == OrderStatus.COMPLETED }
            else -> allOrders
        }
        
        // Update adapter only if RecyclerView is available
        if (hasRecyclerView && ::pendingOrdersAdapter.isInitialized) {
            pendingOrdersAdapter.updateOrders(filteredOrders)
        }
        
        // Update filter button counts
        updateFilterCounts()
        
        // Show empty state if needed
        if (filteredOrders.isEmpty()) {
            showEmptyState()
        } else {
            if (::emptyOrdersView.isInitialized) {
                emptyOrdersView.visibility = View.GONE
            }
            if (::pendingOrdersRecyclerView.isInitialized) {
                pendingOrdersRecyclerView.visibility = View.VISIBLE
            }
        }
    }
    
    /**
     * Updates the UI based on orders data
     */
    private fun updateUI() {
        // Only proceed if RecyclerView is used in this layout
        if (!hasRecyclerView) {
            return
        }
        
        // Update filter button counts
        updateFilterCounts()
        
        if (allOrders.isEmpty()) {
            if (::emptyOrdersView.isInitialized) {
                emptyOrdersView.visibility = View.VISIBLE
            }
            if (::pendingOrdersRecyclerView.isInitialized) {
                pendingOrdersRecyclerView.visibility = View.GONE
            }
        } else {
            if (::emptyOrdersView.isInitialized) {
                emptyOrdersView.visibility = View.GONE
            }
            if (::pendingOrdersRecyclerView.isInitialized) {
                pendingOrdersRecyclerView.visibility = View.VISIBLE
                pendingOrdersAdapter.notifyDataSetChanged()
            }
        }
    }
    
    private fun showLoading(show: Boolean) {
        // Only proceed if RecyclerView is used in this layout
        if (!hasRecyclerView) {
            return
        }
        
        if (show) {
            if (::pendingOrdersRecyclerView.isInitialized) {
                pendingOrdersRecyclerView.visibility = View.GONE
            }
            if (::emptyOrdersView.isInitialized) {
                emptyOrdersView.visibility = View.GONE
            }
            if (::loadingView.isInitialized) {
                loadingView.visibility = View.VISIBLE
            }
        } else {
            if (::pendingOrdersRecyclerView.isInitialized) {
                pendingOrdersRecyclerView.visibility = View.VISIBLE
            }
            if (::emptyOrdersView.isInitialized) {
                emptyOrdersView.visibility = View.GONE
            }
            if (::loadingView.isInitialized) {
                loadingView.visibility = View.GONE
            }
        }
    }
    
    private fun updateFilter(filter: String) {
        currentFilter = filter
        
        // Check if filter buttons are initialized
        if (!::filterButtonAll.isInitialized || !::filterButtonPending.isInitialized || 
            !::filterButtonAccepted.isInitialized || !::filterButtonCompleted.isInitialized) {
            return
        }
        
        // Reset all buttons to default state
        resetFilterButtonStates()
        
        // Update selected filter button UI
        when (filter) {
            "all" -> {
                filterButtonAll.isSelected = true
                filterButtonAll.setTextColor(ContextCompat.getColor(this, R.color.white))
                filterButtonAll.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.primary))
            }
            "pending" -> {
                filterButtonPending.isSelected = true
                filterButtonPending.setTextColor(ContextCompat.getColor(this, R.color.white))
                filterButtonPending.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.primary))
            }
            "accepted" -> {
                filterButtonAccepted.isSelected = true
                filterButtonAccepted.setTextColor(ContextCompat.getColor(this, R.color.white))
                filterButtonAccepted.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.primary))
            }
            "completed" -> {
                filterButtonCompleted.isSelected = true
                filterButtonCompleted.setTextColor(ContextCompat.getColor(this, R.color.white))
                filterButtonCompleted.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.primary))
            }
        }
        
        // Filter orders locally instead of fetching from Firestore
        applyCurrentFilter()
    }
    
    private fun resetFilterButtonStates() {
        // Reset all buttons to default state
        filterButtonAll.isSelected = false
        filterButtonPending.isSelected = false
        filterButtonAccepted.isSelected = false
        filterButtonCompleted.isSelected = false
        
        // Reset colors to default outlined state
        filterButtonAll.setTextColor(ContextCompat.getColor(this, R.color.primary))
        filterButtonPending.setTextColor(ContextCompat.getColor(this, R.color.primary))
        filterButtonAccepted.setTextColor(ContextCompat.getColor(this, R.color.primary))
        filterButtonCompleted.setTextColor(ContextCompat.getColor(this, R.color.primary))
        
        // Reset backgrounds
        filterButtonAll.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, android.R.color.transparent))
        filterButtonPending.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, android.R.color.transparent))
        filterButtonAccepted.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, android.R.color.transparent))
        filterButtonCompleted.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, android.R.color.transparent))
    }
    
    /**
     * Update the filter button texts with order counts
     */
    private fun updateFilterCounts() {
        // Check if filter buttons are initialized
        if (!::filterButtonAll.isInitialized || !::filterButtonPending.isInitialized || 
            !::filterButtonAccepted.isInitialized || !::filterButtonCompleted.isInitialized) {
            return
        }
    
        // Count orders by status
        val pendingCount = allOrders.count { 
            it.status == OrderStatus.PENDING ||
            it.status == OrderStatus.ASSIGNED 
        }
        val acceptedCount = allOrders.count { 
            it.status == OrderStatus.ACCEPTED || 
            it.status == OrderStatus.PICKED_UP ||
            it.status == OrderStatus.IN_TRANSIT
        }
        val completedCount = allOrders.count { it.status == OrderStatus.COMPLETED }
        val totalCount = allOrders.size
        
        // Update button texts
        filterButtonAll.text = "All Orders ($totalCount)"
        filterButtonPending.text = "Pending ($pendingCount)"
        filterButtonAccepted.text = "Accepted ($acceptedCount)"
        filterButtonCompleted.text = "Completed ($completedCount)"
    }
    
    private fun showEmptyState() {
        // Only proceed if RecyclerView is used in this layout
        if (!hasRecyclerView) {
            return
        }
        
        if (::pendingOrdersRecyclerView.isInitialized) {
            pendingOrdersRecyclerView.visibility = View.GONE
        }
        if (::emptyOrdersView.isInitialized) {
            emptyOrdersView.visibility = View.VISIBLE
        }
    }
    
    private fun startOrderRefreshTimer() {
        lifecycleScope.launch {
            while (true) {
                delay(15000) // Refresh every 15 seconds
                fetchOrders(currentFilter, false) // Don't show loading indicator on auto-refresh
            }
        }
    }
    
    private fun navigateToVerification() {
        val intent = Intent(this, application_verification::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
    
    private fun navigateToSignIn() {
        val intent = Intent(this, sign_in_screen::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
    
    override fun onResume() {
        super.onResume()
        
        fetchOrders(currentFilter)
        checkNotifications()
        
        // Check if location service needs to be started
        if (sharedPreferencesManager.isPendingLocationServiceStart() && sharedPreferencesManager.getBackgroundLocationPreference()) {
            checkAndRequestLocationPermission()
        }
        
        // Refresh data
        startAutoRefresh()
        
        // Check user verification status in background without blocking UI
        checkUserVerificationStatus()
    }
    
    override fun onPause() {
        super.onPause()
        
        // Stop auto-refresh
        stopAutoRefresh()
    }
    
    /**
     * Check if we have the required permissions, and request them if not
     */
    private fun checkAndRequestLocationPermission() {
        val permissionsToRequest = requiredPermissions.filter { permission ->
            ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED
        }.toTypedArray()
        
        if (permissionsToRequest.isEmpty()) {
            // All permissions are already granted
            startLocationServiceIfEnabled()
        } else {
            // Request the required permissions
            requestPermissionLauncher.launch(permissionsToRequest)
        }
    }
    
    /**
     * Start the location service if enabled in settings
     */
    private fun startLocationServiceIfEnabled() {
        if (sharedPreferencesManager.getBackgroundLocationPreference()) {
            try {
                // Now that we have permissions, try to start the service
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(Intent(this, LocationService::class.java))
                } else {
                    startService(Intent(this, LocationService::class.java))
                }
                // Clear the pending flag
                sharedPreferencesManager.setPendingLocationServiceStart(false)
                Log.d("HomeScreen", "Location service started successfully")
            } catch (e: Exception) {
                Log.e("HomeScreen", "Failed to start location service", e)
                UIUtils.showSnackbar(findViewById(android.R.id.content), 
                    "Failed to start location service: ${e.message}")
            }
        }
    }
    
    // Keep track of whether the receiver is registered
    private var isReceiverRegistered = false
    
    override fun onDestroy() {
        super.onDestroy()
        // Only unregister if it was registered successfully
        if (isReceiverRegistered) {
            try {
                unregisterReceiver(orderStatusReceiver)
                isReceiverRegistered = false
            } catch (e: IllegalArgumentException) {
                // Receiver was already unregistered or not registered properly
            }
        }
        
        // Remove Firestore listeners
        firebaseListenerService.removeAllListeners()
    }

    /**
     * Gets orders from the repository based on the given status
     */
    private fun getOrders(status: String? = null, page: Int = 1, limit: Int = 50, 
                          callback: (Boolean, String?, Any?) -> Unit) {
        val riderId = sessionManager.getUser()?.id ?: return
        
        // Modify to fetch all orders at once rather than making different queries
        var allOrders = mutableListOf<Order>()
        var pendingFetched = false
        var assignedFetched = false
        var completedFetched = false
        
        // Define checkAllFetched function at the right scope
        fun checkAllFetched() {
            if (pendingFetched && assignedFetched && completedFetched) {
                callback(true, "Orders fetched successfully", allOrders)
            }
        }
        
        orderRepository.getPendingOrders(page, limit, null) { success, message, orders, hasMore, lastDocId ->
            if (success && orders != null) {
                allOrders.addAll(orders)
            }
            pendingFetched = true
            checkAllFetched()
        }
        
        orderRepository.getAssignedOrders { success, message, orders ->
            if (success && orders != null) {
                allOrders.addAll(orders)
            }
            assignedFetched = true
            checkAllFetched()
        }
        
        orderRepository.getCompletedOrders(riderId, page) { success, message, orders ->
            if (success && orders != null) {
                allOrders.addAll(orders)
            }
            completedFetched = true
            checkAllFetched()
        }
    }

    /**
     * Navigates to the order details activity
     */
    private fun navigateToOrderDetails(order: Order) {
        val intent = Intent(this, order_details::class.java)
        intent.putExtra("ORDER_ID", order.orderId)
        startActivity(intent)
    }

    /**
     * Accepts an order
     */
    private fun acceptOrder(order: Order) {
        if (!NetworkUtils.isNetworkAvailable(this)) {
            UIUtils.showSnackbar(findViewById(android.R.id.content), "No internet connection.")
            return
        }
        
        // Show loading dialog
        showLoading(true)
        
        // Handle different scenarios based on order status
        if (order.status == OrderStatus.ASSIGNED) {
            // This is an admin-assigned order
            orderRepository.acceptAdminAssignedOrder(order.id) { success, message ->
                runOnUiThread {
                    showLoading(false)
                    
                    if (success) {
                        UIUtils.showToast(this, "Assignment accepted successfully")
                        fetchOrders(currentFilter)
                    } else {
                        UIUtils.showSnackbar(findViewById(android.R.id.content), message)
                    }
                }
            }
        } else {
            // This is a regular pending order
            orderRepository.acceptOrder(order.id) { success, message ->
                runOnUiThread {
                    showLoading(false)
                    
                    if (success) {
                        UIUtils.showToast(this, "Order accepted successfully")
                        fetchOrders(currentFilter)
                    } else {
                        UIUtils.showSnackbar(findViewById(android.R.id.content), message)
                    }
                }
            }
        }
    }

    /**
     * Shows a reject dialog
     */
    private fun showRejectDialog(order: Order) {
        // Check if this is an assigned order or a pending order
        if (order.status == OrderStatus.ASSIGNED) {
            // Show reject reasons for assigned orders
            val items = arrayOf(
                "Too far away",
                "Too busy now",
                "Vehicle issues",
                "End of shift",
                "Other reason"
            )
            
            MaterialAlertDialogBuilder(this)
                .setTitle("Reject Assignment")
                .setItems(items) { _, which ->
                    rejectAssignedOrder(order.id, items[which])
                }
                .setNegativeButton("Cancel", null)
                .show()
        } else {
            // Show regular reject dialog for pending orders
            MaterialAlertDialogBuilder(this)
                .setTitle("Reject Order")
                .setMessage("Are you sure you want to reject this order?")
                .setPositiveButton("Reject") { _, _ ->
                    rejectOrder(order, "Rider rejected the order")
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }
    
    /**
     * Rejects an assigned order
     */
    private fun rejectAssignedOrder(orderId: String, reason: String) {
        if (!NetworkUtils.isNetworkAvailable(this)) {
            UIUtils.showSnackbar(findViewById(android.R.id.content), "No internet connection.")
            return
        }
        
        // Show loading dialog
        showLoading(true)
        
        orderRepository.rejectAdminAssignedOrder(orderId, reason) { success, message ->
            runOnUiThread {
                showLoading(false)
                
                if (success) {
                    UIUtils.showToast(this, "Assignment rejected successfully")
                    fetchOrders(currentFilter)
                } else {
                    UIUtils.showSnackbar(findViewById(android.R.id.content), message)
                }
            }
        }
    }

    /**
     * Rejects an order
     */
    private fun rejectOrder(order: Order, reason: String) {
        if (!NetworkUtils.isNetworkAvailable(this)) {
            UIUtils.showSnackbar(findViewById(android.R.id.content), "No internet connection.")
            return
        }
        
        // Show loading dialog
        showLoading(true)
        
        orderRepository.rejectOrder(order.orderId, reason) { success, message ->
            runOnUiThread {
                showLoading(false)
                
                if (success) {
                    UIUtils.showToast(this, "Order rejected successfully")
                    fetchOrders(currentFilter)
                } else {
                    UIUtils.showSnackbar(findViewById(android.R.id.content), message)
                }
            }
        }
    }

    /**
     * Starts listening for real-time order assignments from admin
     */
    private fun startOrderAssignmentListening() {
        orderRepository.startListeningForOrderAssignments(firebaseListenerService) { order ->
            runOnUiThread {
                // Show a notification toast instead of a dialog
                UIUtils.showToast(this, "New order assigned from ${order.restaurantName}")
                
                // Refresh the orders list to show the new assignment
                fetchOrders(currentFilter)
            }
        }
    }
    
    /**
     * Shows a dialog to collect reject reason for admin-assigned orders
     */
    private fun showRejectReasonDialog(orderId: String) {
        val items = arrayOf(
            "Too far away",
            "Too busy now",
            "Vehicle issues",
            "End of shift",
            "Other reason"
        )
        
        MaterialAlertDialogBuilder(this)
            .setTitle("Reason for Rejection")
            .setItems(items) { _, which ->
                rejectAdminAssignedOrder(orderId, items[which])
            }
            .setCancelable(false)
            .show()
    }
    
    /**
     * Accepts an admin-assigned order
     */
    private fun acceptAdminAssignedOrder(orderId: String) {
        if (!NetworkUtils.isNetworkAvailable(this)) {
            UIUtils.showSnackbar(findViewById(android.R.id.content), "No internet connection.")
            return
        }
        
        // Show loading dialog
        showLoading(true)
        
        orderRepository.acceptAdminAssignedOrder(orderId) { success, message ->
            runOnUiThread {
                showLoading(false)
                
                if (success) {
                    UIUtils.showToast(this, "Order accepted successfully")
                    fetchOrders(currentFilter)
                } else {
                    UIUtils.showSnackbar(findViewById(android.R.id.content), message)
                }
            }
        }
    }
    
    /**
     * Rejects an admin-assigned order
     */
    private fun rejectAdminAssignedOrder(orderId: String, reason: String) {
        if (!NetworkUtils.isNetworkAvailable(this)) {
            UIUtils.showSnackbar(findViewById(android.R.id.content), "No internet connection.")
            return
        }
        
        // Show loading dialog
        showLoading(true)
        
        orderRepository.rejectAdminAssignedOrder(orderId, reason) { success, message ->
            runOnUiThread {
                showLoading(false)
                
                if (success) {
                    UIUtils.showToast(this, "Order rejected successfully")
                    fetchOrders(currentFilter)
                } else {
                    UIUtils.showSnackbar(findViewById(android.R.id.content), message)
                }
            }
        }
    }

    /**
     * Sets up a timer to refresh orders every 10 seconds
     */
    private fun startAutoRefresh() {
        stopAutoRefresh() // Cancel any existing timer
        
        refreshTimer = Timer()
        refreshTimer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    fetchOrders(currentFilter, false)
                }
            }
        }, 10000, 10000) // Initial delay 10s, period 10s
    }

    /**
     * Stops the auto-refresh timer
     */
    private fun stopAutoRefresh() {
        refreshTimer?.cancel()
        refreshTimer = null
    }

    // Check if user is verified in the background and only redirect if truly not verified
    private fun checkUserVerificationStatus() {
        val user = sessionManager.getUser(true) // Force refresh of user data
        if (user != null && !user.isVerified) {
            // User is not verified in the local session
            // Check with Firebase in the background without blocking UI
            val userId = user.id
            if (userId.isNotEmpty()) {
                // Check the verification status in Firebase without showing loading indicator
                FirebaseFirestore.getInstance().collection("users").document(userId)
                    .get()
                    .addOnSuccessListener { documentSnapshot ->
                        if (documentSnapshot.exists()) {
                            val verificationStatus = documentSnapshot.getString("verificationStatus") ?: "pending"
                            val isVerified = documentSnapshot.getBoolean("isVerified") ?: false
                            
                            if (verificationStatus.equals("approved", ignoreCase = true) || isVerified) {
                                // User is verified in Firebase but not in session, update session
                                val updatedUser = user.copy(isVerified = true)
                                sessionManager.saveUserData(updatedUser)
                                // No need to redirect, user can stay on home screen
                            } else {
                                // User is truly not verified, redirect to verification screen
                                navigateToVerification()
                            }
                        } else {
                            // User document not found, redirect to verification screen
                            navigateToVerification()
                        }
                    }
                    .addOnFailureListener { e ->
                        // On failure, don't redirect - let user stay on home screen to avoid disruption
                        Log.e("HomeScreen", "Failed to check verification status: ${e.message}")
                    }
            }
        }
    }

    // Add this helper method to get status bar height
    private fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }
}