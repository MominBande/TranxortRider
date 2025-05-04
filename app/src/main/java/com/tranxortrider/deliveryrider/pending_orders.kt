package com.tranxortrider.deliveryrider

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.tranxortrider.deliveryrider.adapters.PendingOrdersAdapter
import com.tranxortrider.deliveryrider.models.Order
import com.tranxortrider.deliveryrider.models.OrderStatus
import com.tranxortrider.deliveryrider.repositories.OrderRepository
import com.tranxortrider.deliveryrider.services.FirestoreService
import com.tranxortrider.deliveryrider.utils.NetworkUtils
import com.tranxortrider.deliveryrider.utils.SessionManager
import com.tranxortrider.deliveryrider.utils.UIUtils
import android.app.ProgressDialog
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.tranxortrider.deliveryrider.services.FirestoreListenerService
import java.util.*
import android.os.Build
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.appcompat.widget.SwitchCompat

class pending_orders : AppCompatActivity() {
    
    // UI components
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyStateText: TextView
    private lateinit var tvName: TextView
    private lateinit var tvDeliveryCount: TextView
    private lateinit var tvOnTimeRate: TextView
    private lateinit var chipAllOrders: Chip
    private lateinit var chipPending: Chip
    private lateinit var chipInProgress: Chip
    private lateinit var chipCompleted: Chip
    private lateinit var chipFailed: Chip
    private lateinit var btnDutyStatus: androidx.appcompat.widget.SwitchCompat
    private lateinit var tvDutyStatus: TextView
    
    // Adapter
    private lateinit var ordersAdapter: PendingOrdersAdapter
    
    // Dependencies
    private lateinit var orderRepository: OrderRepository
    private lateinit var sessionManager: SessionManager
    private lateinit var firestoreService: FirestoreService
    private lateinit var firestoreListenerService: FirestoreListenerService
    
    // Data
    private val pendingOrders = mutableListOf<Order>()
    private var allOrders = mutableListOf<Order>()
    private var currentFilter = "all" // Changed default filter to "all" to show all orders
    private var isOnDuty = true
    
    // Flags to track UI components existence
    private var hasChipFilters = false
    private var hasEmptyStateText = false
    private var hasDeliveryStatsViews = false
    
    // Auto-refresh timer
    private var refreshTimer: Timer? = null
    
    // Define the auto-refresh interval
    private val AUTO_REFRESH_INTERVAL_MS: Long = 10000 // 10 seconds
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pending_orders)
        
        // Initialize window insets
        val rootView = findViewById<View>(android.R.id.content)
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        // Initialize dependencies
        orderRepository = OrderRepository()
        sessionManager = SessionManager(this)
        firestoreService = FirestoreService()
        firestoreListenerService = FirestoreListenerService()
        
        // Check if user is logged in
        if (sessionManager.getUser() == null) {
            navigateToSignIn()
            return
        }
        
        // Initialize UI components - will call loadUserData at the end
        initializeUI()
        
        // Set up RecyclerView
        setupRecyclerView()
        
        // Fetch orders
        fetchOrders()
        
        // Start listening for order assignments
        startOrderAssignmentListening()
        
        // Setup auto-refresh timer
        startAutoRefresh()
        
        // Add status bar configuration
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.primary)
            window.decorView.systemUiVisibility = 0 // Reset any light status bar flags
        }
    }
    
    override fun onResume() {
        super.onResume()
        
        // Refresh the orders list
        fetchOrders()
        
        // Start listening for assigned orders in real-time
        firestoreListenerService.listenForAssignedOrders { orders ->
            Log.d("PendingOrders", "Real-time listener received ${orders.size} assigned orders: ${orders.map { it.status }}")
            
            // Update assigned orders in the list
            val assignedOrdersList = mutableListOf<Order>()
            assignedOrdersList.addAll(orders)
            
            // First filter out any existing assigned or in-progress orders to avoid duplicates
            val existingOrders = allOrders.filter { it.status != OrderStatus.ASSIGNED && 
                                                   it.status != OrderStatus.ACCEPTED &&
                                                   it.status != OrderStatus.PICKED_UP && 
                                                   it.status != OrderStatus.IN_TRANSIT }
            
            // Then add the new assigned and in-progress orders
            allOrders.clear()
            allOrders.addAll(existingOrders)
            allOrders.addAll(assignedOrdersList)
            
            // Apply filter and update UI
            applyFilter()
            updateFilterCounts()
        }
        
        // Listen for order assignments
        firestoreListenerService.listenForOrderAssignments { newOrder ->
            Log.d("PendingOrders", "New order assigned: ${newOrder.id}, status: ${newOrder.status}")
            showToast("New order assigned from ${newOrder.restaurantName}")
            
            // Refresh orders to include the new assignment
            fetchOrders()
        }
        
        // Schedule auto-refresh timer
        scheduleRefreshTimer()
    }
    
    override fun onPause() {
        super.onPause()
        
        // Stop listening for assignments when activity is paused
        firestoreListenerService.removeAllListeners()
        
        // Cancel auto-refresh timer
        refreshTimer?.cancel()
        refreshTimer = null
    }
    
    override fun onDestroy() {
        super.onDestroy()
        // Remove all Firestore listeners
        firestoreListenerService.removeAllListeners()
    }
    
    /**
     * Starts listening for real-time order assignments from admin
     */
    private fun startOrderAssignmentListening() {
        orderRepository.startListeningForOrderAssignments(firestoreListenerService) { order ->
            runOnUiThread {
                // Show a notification toast instead of a dialog
                showToast("New order assigned from ${order.restaurantName}")
                
                // Refresh the orders list to show the new assignment
                fetchOrders()
            }
        }
    }
    
    private fun initializeUI() {
        // Find main views
        try {
            recyclerView = findViewById(R.id.pendingOrdersRecyclerView)
            
            // Explicitly find and initialize tvName
            tvName = findViewById(R.id.tvName)
            Log.d("PendingOrders", "Found tvName: ${tvName != null}")
            
            emptyStateText = findViewById(R.id.emptyStateText)
            
            // Try to find delivery stats views
            tvDeliveryCount = findViewById(R.id.tvDeliveryCount)
            tvOnTimeRate = findViewById(R.id.tvOnTimeRate)
            
            // Initialize duty status UI components
            btnDutyStatus = findViewById(R.id.btnDutyStatus)
            tvDutyStatus = findViewById(R.id.tvDutyStatus)
            
            // Initialize duty status UI
            if (::btnDutyStatus.isInitialized) {
                btnDutyStatus.isChecked = isOnDuty
            }
            
            if (::tvDutyStatus.isInitialized) {
                tvDutyStatus.text = if (isOnDuty) "On Duty" else "Off Duty"
                tvDutyStatus.setTextColor(
                    ContextCompat.getColor(
                        this,
                        if (isOnDuty) R.color.green else R.color.red
                    )
                )
            }
            
            // Set flags based on whether views were found
            hasEmptyStateText = emptyStateText != null
            hasDeliveryStatsViews = tvName != null && tvDeliveryCount != null && tvOnTimeRate != null
        } catch (e: Exception) {
            Log.e("PendingOrders", "Error finding main views: ${e.message}", e)
            hasEmptyStateText = false
            hasDeliveryStatsViews = false
        }
        
        // Safely find chip filters
        try {
            chipAllOrders = findViewById(R.id.chipAllOrders)
            chipPending = findViewById(R.id.chipPending)
            chipInProgress = findViewById(R.id.chipInProgress)
            chipCompleted = findViewById(R.id.chipCompleted)
            chipFailed = findViewById(R.id.chipFailed)
            
            // Set hasChipFilters to true only if all chip views are found
            hasChipFilters = chipAllOrders != null && chipPending != null && 
                            chipInProgress != null && chipCompleted != null && chipFailed != null
        } catch (e: Exception) {
            Log.d("PendingOrders", "Chip filters not found in layout: ${e.message}")
            hasChipFilters = false
        }
        
        // Add navigation buttons
        setupActionButtons()
        
        // Set up filter click listeners only if chips exist
        if (hasChipFilters) {
            setupFilterListeners()
        }
        
        // Set up bottom navigation
        setupBottomNavigation()
        
        // Explicitly call loadUserData after all UI is initialized
        loadUserData()
    }
    
    private fun setupActionButtons() {
        // Find the search and notification buttons if they exist
        val btnSearch = findViewById<View>(R.id.btnSearch)
        val btnNotifications = findViewById<View>(R.id.btnNotifications)
        
        // Set up duty status toggle
        if (::btnDutyStatus.isInitialized) {
            btnDutyStatus.setOnCheckedChangeListener { _, isChecked ->
                // Only toggle if the state actually changed from the current state
                if (isChecked != isOnDuty) {
                    toggleDutyStatus()
                }
            }
        }
        
        // Set up search button
        btnSearch?.setOnClickListener {
            try {
                val intent = Intent(this, search::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                UIUtils.showToast(this, "Search functionality is not available")
            }
        }
        
        // Set up notifications button
        btnNotifications?.setOnClickListener {
            try {
                val intent = Intent(this, notifications::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                UIUtils.showToast(this, "Notifications functionality is not available")
            }
        }
    }
    
    private fun setupFilterListeners() {
        // If chips don't exist in this layout, don't set up the listeners
        if (!hasChipFilters) {
            return
        }
        
        // Set "All Orders" as the default selected filter
        chipAllOrders.isChecked = true
        
        chipAllOrders.setOnClickListener {
            currentFilter = "all"
            applyFilter()
        }
        
        chipPending.setOnClickListener {
            currentFilter = "pending"
            applyFilter()
        }
        
        chipInProgress.setOnClickListener {
            currentFilter = "in_progress"
            applyFilter()
        }
        
        chipCompleted.setOnClickListener {
            currentFilter = "completed"
            applyFilter()
        }
        
        chipFailed.setOnClickListener {
            currentFilter = "failed"
            applyFilter()
        }
    }
    
    private fun setupBottomNavigation() {
        val bottomNavigationView = findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottomNavigation)
        if (bottomNavigationView != null) {
            // Make the bottom navigation visible
            bottomNavigationView.visibility = View.VISIBLE
            // Use the NavigationUtils to set up bottom navigation
            com.tranxortrider.deliveryrider.utils.NavigationUtils.setupBottomNavigation(this, bottomNavigationView)
        }
    }
    
    private fun setupRecyclerView() {
        // Initialize adapter
        ordersAdapter = PendingOrdersAdapter(pendingOrders,
            onAcceptClick = { order ->
                showAcceptConfirmationDialog(order)
            },
            onRejectClick = { order ->
                showRejectReasonDialog(order)
            },
            onItemClick = { order ->
                navigateToOrderDetails(order.id)
            }
        )
        
        // Set up RecyclerView
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@pending_orders)
            adapter = ordersAdapter
        }
    }
    
    private fun loadUserData() {
        try {
            val user = sessionManager.getUser()
            Log.d("PendingOrders", "Loading user data: ${user?.name}")
            
            // Make sure tvName is properly initialized
            if (!::tvName.isInitialized) {
                tvName = findViewById(R.id.tvName)
            }
            
            // Set the user name with explicit null check
            if (user != null && ::tvName.isInitialized) {
                tvName.text = user.name ?: "Unknown User"
                Log.d("PendingOrders", "Set user name to: ${user.name}")
                
                // Get delivery stats
                fetchDeliveryStats()
            } else {
                Log.e("PendingOrders", "Failed to set user name - User: ${user != null}, tvName initialized: ${::tvName.isInitialized}")
            }
        } catch (e: Exception) {
            Log.e("PendingOrders", "Error loading user data: ${e.message}", e)
        }
    }
    
    private fun fetchDeliveryStats() {
        // Skip if delivery stats views don't exist in this layout
        if (!hasDeliveryStatsViews) {
            Log.d("PendingOrders", "Delivery stats views not available in this layout")
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
                Log.e("PendingOrders", "Error fetching delivery stats", e)
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
    
    private fun fetchOrders() {
        Log.d("PendingOrders", "Fetching all orders...")
        
        // Show loading UI if available
        if (hasEmptyStateText) {
            emptyStateText.visibility = View.GONE
        }
        recyclerView.visibility = View.GONE
        
        // Use null for status to get all types of orders
        getOrders(null, 1, 50) { success, message, response ->
            if (success && response != null) {
                @Suppress("UNCHECKED_CAST")
                val orders = response as List<Order>
                
                Log.d("PendingOrders", "Fetched ${orders.size} orders of all statuses")
                
                // Update our list of all orders
                allOrders.clear()
                allOrders.addAll(orders)
                
                // Apply the current filter
                applyFilter()
                
                // Update filter counts if we have chips
                if (hasChipFilters) {
                    updateFilterCounts()
                }
            } else {
                Log.e("PendingOrders", "Error fetching orders: $message")
                showToast("Error loading orders: $message")
            }
        }
    }
    
    /**
     * Gets orders from the repository based on the given status
     */
    private fun getOrders(status: String? = null, page: Int = 1, limit: Int = 50, 
                          callback: (Boolean, String?, Any?) -> Unit) {
        val riderId = sessionManager.getUser()?.id ?: return
        
        when (status) {
            "pending" -> orderRepository.getPendingOrders(page, limit, null) { success, message, orders, hasMore, lastDocId ->
                callback(success, message, orders)
            }
            "assigned" -> orderRepository.getAssignedOrders { success, message, orders ->
                callback(success, message, orders)
            }
            "completed" -> orderRepository.getCompletedOrders(riderId, page) { success, message, orders ->
                callback(success, message, orders)
            }
            else -> {
                // Fetch all types of orders
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
        }
    }
    
    /**
     * Apply filter to orders based on current selection
     */
    private fun applyFilter() {
        Log.d("PendingOrders", "Applying filter: $currentFilter (total orders: ${allOrders.size})")
        
        // Clear pending orders list
        pendingOrders.clear()
        
        // Apply filter
        when (currentFilter) {
            "all" -> {
                // Show all orders regardless of status
                pendingOrders.addAll(allOrders)
            }
            "pending" -> {
                pendingOrders.addAll(allOrders.filter { it.status == OrderStatus.PENDING })
            }
            "assigned" -> {
                pendingOrders.addAll(allOrders.filter { 
                    it.status == OrderStatus.ASSIGNED || 
                    it.status == OrderStatus.ACCEPTED || 
                    it.status == OrderStatus.PICKED_UP || 
                    it.status == OrderStatus.IN_TRANSIT 
                })
            }
            "completed" -> {
                pendingOrders.addAll(allOrders.filter { it.status == OrderStatus.COMPLETED })
            }
            "failed" -> {
                pendingOrders.addAll(allOrders.filter { it.status == OrderStatus.CANCELLED || it.status == OrderStatus.FAILED })
            }
        }
        
        // Update adapter
        ordersAdapter.updateOrders(pendingOrders)
        
        // Show/hide empty state
        if (pendingOrders.isEmpty()) {
            showEmptyState()
        } else {
            hideEmptyState()
        }
    }
    
    /**
     * Shows the empty state with appropriate message based on the current filter
     */
    private fun showEmptyState() {
        // Skip if emptyStateText is not available in this layout
        if (!hasEmptyStateText) {
            Log.d("PendingOrders", "Empty state text view not available in this layout")
            return
        }
        
        val emptyMessage = when (currentFilter) {
            "all" -> "No orders found"
            "pending" -> "No pending orders"
            "assigned" -> "No orders in progress"
            "completed" -> "No completed orders"
            "failed" -> "No failed or cancelled orders"
            else -> "No orders found"
        }
        
        emptyStateText.text = emptyMessage
        
        // Check if emptyStateContainer exists before accessing it
        val emptyStateContainer = findViewById<View>(R.id.emptyStateContainer)
        if (emptyStateContainer != null) {
            emptyStateContainer.visibility = View.VISIBLE
        }
        
        recyclerView.visibility = View.GONE
    }
    
    /**
     * Hides the empty state
     */
    private fun hideEmptyState() {
        // Check if emptyStateContainer exists before accessing it
        val emptyStateContainer = findViewById<View>(R.id.emptyStateContainer)
        if (emptyStateContainer != null) {
            emptyStateContainer.visibility = View.GONE
        }
        
        recyclerView.visibility = View.VISIBLE
    }
    
    private fun updateFilterCounts() {
        // If chips don't exist in this layout, don't update them
        if (!hasChipFilters) {
            return
        }
        
        val pendingCount = allOrders.count { 
            it.status == OrderStatus.PENDING || 
            it.status == OrderStatus.ASSIGNED 
        }
        val inProgressCount = allOrders.count { 
            it.status == OrderStatus.ACCEPTED || 
            it.status == OrderStatus.PICKED_UP || 
            it.status == OrderStatus.IN_TRANSIT 
        }
        val completedCount = allOrders.count { it.status == OrderStatus.COMPLETED }
        val failedCount = allOrders.count { 
            it.status == OrderStatus.FAILED || 
            it.status == OrderStatus.CANCELLED 
        }
        val totalCount = allOrders.size
        
        // Update chip texts
        chipAllOrders.text = "All Orders ($totalCount)"
        chipPending.text = "Pending ($pendingCount)"
        chipInProgress.text = "In Progress ($inProgressCount)"
        chipCompleted.text = "Completed ($completedCount)"
        chipFailed.text = "Failed ($failedCount)"
    }
    
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    
    private fun showAcceptConfirmationDialog(order: Order) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Accept Order")
            .setMessage("Are you sure you want to accept this order from ${order.restaurantName}?")
            .setPositiveButton("Accept") { _, _ ->
                acceptOrder(order)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun acceptOrder(order: Order) {
        val dialog = ProgressDialog(this).apply {
            setMessage("Accepting order...")
            setCancelable(false)
            show()
        }

        // Handle different scenarios based on order status
        if (order.status == OrderStatus.ASSIGNED) {
            // This is an admin-assigned order
            orderRepository.acceptAdminAssignedOrder(order.id) { success, message ->
                runOnUiThread {
                    dialog.dismiss()
                    if (success) {
                        showToast("Assignment accepted successfully")
                        fetchOrders()
                    } else {
                        handleError("Failed to accept order: $message")
                    }
                }
            }
        } else {
            // This is a regular pending order
            orderRepository.acceptOrder(order.id) { success, message ->
                runOnUiThread {
                    dialog.dismiss()
                    if (success) {
                        showToast("Order accepted successfully")
                        fetchOrders()
                    } else {
                        handleError("Failed to accept order: $message")
                    }
                }
            }
        }
    }
    
    // Add missing handleError method
    private fun handleError(message: String) {
        // Display error message
        showToast(message)
        
        // Log error
        Log.e("PendingOrders", message)
        
        // Show Snackbar for more persistent error display
        val rootView = findViewById<View>(android.R.id.content)
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show()
    }
    
    /**
     * Shows a dialog to collect reject reason for admin-assigned orders
     */
    private fun showRejectReasonDialog(order: Order) {
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
            val reasons = arrayOf(
                "Too far away",
                "Too busy now",
                "Restaurant has long wait times",
                "Problem with delivery area",
                "Other"
            )
            
            MaterialAlertDialogBuilder(this)
                .setTitle("Reason for Rejection")
                .setItems(reasons) { _, which ->
                    rejectOrder(order, reasons[which])
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }
    
    private fun rejectAssignedOrder(orderId: String, reason: String) {
        val dialog = ProgressDialog(this).apply {
            setMessage("Rejecting assignment...")
            setCancelable(false)
            show()
        }

        orderRepository.rejectAdminAssignedOrder(orderId, reason) { success, message ->
            runOnUiThread {
                dialog.dismiss()
                if (success) {
                    showToast("Assignment rejected successfully")
                    fetchOrders()
                } else {
                    handleError("Failed to reject assignment: $message")
                }
            }
        }
    }
    
    private fun rejectOrder(order: Order, reason: String) {
        val dialog = ProgressDialog(this).apply {
            setMessage("Rejecting order...")
            setCancelable(false)
            show()
        }

        orderRepository.rejectOrder(order.id, reason) { success, message ->
            runOnUiThread {
                dialog.dismiss()
                if (success) {
                    showToast("Order rejected successfully")
                    fetchOrders()
                } else {
                    handleError("Failed to reject order: $message")
                }
            }
        }
    }
    
    private fun navigateToOrderDetails(orderId: String) {
        val intent = Intent(this, order_details::class.java)
        intent.putExtra("ORDER_ID", orderId)
        startActivity(intent)
    }
    
    private fun navigateToHome() {
        val intent = Intent(this, home_screen::class.java)
        startActivity(intent)
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
    
    /**
     * Sets up a timer to refresh orders every 10 seconds
     */
    private fun startAutoRefresh() {
        stopAutoRefresh() // Cancel any existing timer
        
        refreshTimer = Timer()
        refreshTimer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    // Remove reference to swipeRefreshLayout
                    fetchOrders()
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
    
    private fun scheduleRefreshTimer() {
        // Cancel existing timer if present
        refreshTimer?.cancel()
        
        // Create a new timer
        refreshTimer = Timer()
        refreshTimer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                // Skip refresh if already refreshing
                runOnUiThread {
                    Log.d("PendingOrders", "Auto-refreshing orders...")
                    fetchOrders()
                }
            }
        }, AUTO_REFRESH_INTERVAL_MS, AUTO_REFRESH_INTERVAL_MS)
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
} 