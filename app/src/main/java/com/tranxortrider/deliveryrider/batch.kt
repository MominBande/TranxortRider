package com.tranxortrider.deliveryrider

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.tranxortrider.deliveryrider.adapters.BatchOrderAdapter
import com.tranxortrider.deliveryrider.models.Order
import com.tranxortrider.deliveryrider.models.OrderStatus
import com.tranxortrider.deliveryrider.repositories.OrderRepository
import com.tranxortrider.deliveryrider.utils.NavigationUtils
import com.tranxortrider.deliveryrider.utils.NetworkUtils
import com.tranxortrider.deliveryrider.utils.SessionManager
import com.tranxortrider.deliveryrider.utils.UIUtils

class batch : AppCompatActivity() {
    
    // UI Components
    private lateinit var toolbar: MaterialToolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyView: View
    private lateinit var loadingView: View
    private lateinit var batchInfoCard: View
    private lateinit var tvBatchId: TextView
    private lateinit var tvOrderCount: TextView
    private lateinit var tvEstimatedEarnings: TextView
    private lateinit var tvEstimatedTime: TextView
    private lateinit var btnStartBatch: Button
    private lateinit var btnOptimizeRoute: Button
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var fabAddOrder: FloatingActionButton
    
    // Dependencies
    private lateinit var sessionManager: SessionManager
    private lateinit var orderRepository: OrderRepository
    
    // Data
    private lateinit var batchOrderAdapter: BatchOrderAdapter
    private var batchOrders = mutableListOf<Order>()
    private var batchId: String? = null
    private var isBatchActive = false
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_batch)
        
        // Initialize dependencies
        sessionManager = SessionManager(this)
        orderRepository = OrderRepository()
        
        // Check if user is logged in
        if (sessionManager.getUser() == null) {
            navigateToSignIn()
            return
        }
        
        // Initialize UI components
        initializeUI()
        
        // Set up click listeners
        setupClickListeners()
        
        // Check for batch ID in intent (for shared batches or continued batches)
        batchId = intent.getStringExtra("BATCH_ID")
        if (batchId != null) {
            loadBatchOrders(batchId!!)
        } else {
            // Check if there's an active batch
            checkForActiveBatch()
        }
        
        // Check if we should show the batch summary
        if (intent.getBooleanExtra("SHOW_SUMMARY", false)) {
            // Clear the flag to prevent repeated showings
            intent.removeExtra("SHOW_SUMMARY")
            
            // Show batch summary after orders are loaded
            Handler(Looper.getMainLooper()).postDelayed({
                if (batchOrders.isNotEmpty()) {
                    showBatchSummary()
                }
            }, 500)
        }
    }
    
    private fun initializeUI() {
        // Find views
        toolbar = findViewById(R.id.toolbar)
        recyclerView = findViewById(R.id.recyclerViewBatchOrders)
        emptyView = findViewById(R.id.emptyView)
        loadingView = findViewById(R.id.loadingView)
        batchInfoCard = findViewById(R.id.batchInfoCard)
        tvBatchId = findViewById(R.id.tvBatchId)
        tvOrderCount = findViewById(R.id.tvOrderCount)
        tvEstimatedEarnings = findViewById(R.id.tvEstimatedEarnings)
        tvEstimatedTime = findViewById(R.id.tvEstimatedTime)
        btnStartBatch = findViewById(R.id.btnStartBatch)
        btnOptimizeRoute = findViewById(R.id.btnOptimizeRoute)
        bottomNavigation = findViewById(R.id.bottomNav)
        fabAddOrder = findViewById(R.id.fabAddOrder)
        
        // Set up toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        
        // Set up RecyclerView
        batchOrderAdapter = BatchOrderAdapter(emptyList())
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@batch)
            adapter = batchOrderAdapter
        }
        
        // Set up adapter click listeners
        batchOrderAdapter.setOnItemClickListener { order ->
            val intent = Intent(this, order_details::class.java)
            intent.putExtra("ORDER_ID", order.orderId)
            startActivity(intent)
        }
        
        batchOrderAdapter.setOnRemoveClickListener { order ->
            showRemoveOrderDialog(order)
        }
        
        // Set up bottom navigation
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
                R.id.nav_scanner -> {
                    startActivity(Intent(this, scanner::class.java))
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
        // Start batch button
        btnStartBatch.setOnClickListener {
            if (isBatchActive) {
                // Navigate to the first order in the batch
                if (batchOrders.isNotEmpty()) {
                    val intent = Intent(this, order_details::class.java)
                    intent.putExtra("ORDER_ID", batchOrders[0].orderId)
                    startActivity(intent)
                }
            } else {
                startBatch()
            }
        }
        
        // Optimize route button
        btnOptimizeRoute.setOnClickListener {
            optimizeRoute()
        }
        
        // Add order to batch
        fabAddOrder.setOnClickListener {
            showAvailableOrdersDialog()
        }
    }
    
    private fun checkForActiveBatch() {
        showLoading()
        
        if (!NetworkUtils.isNetworkAvailable(this)) {
            UIUtils.showSnackbar(findViewById(android.R.id.content), "No internet connection")
            hideLoading()
            return
        }
        
        // Check if there's an active batch
        orderRepository.getActiveBatch { success, message, batchDetails ->
            runOnUiThread {
                hideLoading()
                
                if (success && batchDetails != null) {
                    // Active batch found
                    batchId = batchDetails.batchId
                    isBatchActive = true
                    updateBatchInfo(batchDetails)
                    loadBatchOrders(batchDetails.batchId)
                } else {
                    // No active batch
                    showEmptyState()
                }
            }
        }
    }
    
    private fun loadBatchOrders(batchId: String) {
        showLoading()
        
        if (!NetworkUtils.isNetworkAvailable(this)) {
            UIUtils.showSnackbar(findViewById(android.R.id.content), "No internet connection")
            hideLoading()
            return
        }
        
        // Get orders for this batch
        orderRepository.getBatchOrders(batchId) { success, message, orders ->
            runOnUiThread {
                hideLoading()
                
                if (success && orders != null) {
                    batchOrders.clear()
                    batchOrders.addAll(orders)
                    
                    if (batchOrders.isEmpty()) {
                        showEmptyState()
                    } else {
                        showBatchOrders()
                        updateOrdersCount()
                    }
                } else {
                    UIUtils.showSnackbar(findViewById(android.R.id.content), message)
                    showEmptyState()
                }
            }
        }
    }
    
    private fun updateBatchInfo(batchDetails: BatchDetails) {
        tvBatchId.text = "Batch #${batchDetails.batchId}"
        tvEstimatedEarnings.text = "$${batchDetails.estimatedEarnings}"
        tvEstimatedTime.text = "${batchDetails.estimatedTimeMinutes} min"
        
        // Update button text if batch is active
        if (isBatchActive) {
            btnStartBatch.text = "Continue Batch"
            fabAddOrder.visibility = View.GONE
        } else {
            btnStartBatch.text = "Start Batch"
            fabAddOrder.visibility = View.VISIBLE
        }
    }
    
    private fun updateOrdersCount() {
        tvOrderCount.text = "${batchOrders.size} Orders"
    }
    
    private fun showBatchOrders() {
        emptyView.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        batchInfoCard.visibility = View.VISIBLE
        
        batchOrderAdapter.updateOrders(batchOrders)
        
        // Enable/disable buttons based on order count
        btnStartBatch.isEnabled = batchOrders.isNotEmpty()
        btnOptimizeRoute.isEnabled = batchOrders.size > 1
    }
    
    private fun showEmptyState() {
        recyclerView.visibility = View.GONE
        emptyView.visibility = View.VISIBLE
        
        // Hide batch info if no orders
        if (batchOrders.isEmpty()) {
            batchInfoCard.visibility = View.GONE
        } else {
            batchInfoCard.visibility = View.VISIBLE
        }
        
        // Disable buttons if no orders
        btnStartBatch.isEnabled = false
        btnOptimizeRoute.isEnabled = false
    }
    
    private fun showLoading() {
        loadingView.visibility = View.VISIBLE
    }
    
    private fun hideLoading() {
        loadingView.visibility = View.GONE
    }
    
    private fun startBatch() {
        if (batchOrders.isEmpty()) return
        
        showLoading()
        
        // Activate the batch
        orderRepository.startBatch(batchId!!) { success, message ->
            runOnUiThread {
                hideLoading()
                
                if (success) {
                    isBatchActive = true
                    btnStartBatch.text = "Continue Batch"
                    fabAddOrder.visibility = View.GONE
                    
                    // Navigate to first order
                    val intent = Intent(this, order_details::class.java)
                    intent.putExtra("ORDER_ID", batchOrders[0].orderId)
                    startActivity(intent)
                } else {
                    UIUtils.showSnackbar(findViewById(android.R.id.content), message)
                }
            }
        }
    }
    
    private fun optimizeRoute() {
        if (batchOrders.size <= 1) return
        
        showLoading()
        
        // Call API to optimize the route
        orderRepository.optimizeBatchRoute(batchId!!) { success, message, optimizedOrders ->
            runOnUiThread {
                hideLoading()
                
                if (success && optimizedOrders != null) {
                    batchOrders.clear()
                    batchOrders.addAll(optimizedOrders)
                    batchOrderAdapter.updateOrders(batchOrders)
                    
                    showOptimizationDetails()
                } else {
                    UIUtils.showSnackbar(findViewById(android.R.id.content), message)
                }
            }
        }
    }
    
    /**
     * Show a detailed dialog with information about the route optimization
     */
    private fun showOptimizationDetails() {
        val builder = MaterialAlertDialogBuilder(this)
            .setTitle("Route Optimized")
            .setMessage("Your delivery route has been optimized for maximum efficiency. Follow the sequence numbers to deliver orders in the most optimal route.\n\n" +
                    "Optimization factors:\n" +
                    "• Minimized total travel distance\n" +
                    "• Geographic proximity between deliveries\n" +
                    "• Estimated delivery times\n" +
                    "• Traffic conditions (when available)")
            .setPositiveButton("Got it", null)
        
        // Add navigation option if batch is active
        if (isBatchActive) {
            builder.setNeutralButton("Start Navigation") { _, _ ->
                if (batchOrders.isNotEmpty()) {
                    val intent = Intent(this, order_details::class.java)
                    intent.putExtra("ORDER_ID", batchOrders[0].orderId)
                    startActivity(intent)
                }
            }
        }
        
        builder.show()
    }
    
    private fun showRemoveOrderDialog(order: Order) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Remove Order")
            .setMessage("Are you sure you want to remove order #${order.orderId} from this batch?")
            .setPositiveButton("Remove") { _, _ ->
                removeOrderFromBatch(order)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun removeOrderFromBatch(order: Order) {
        if (isBatchActive) {
            UIUtils.showSnackbar(findViewById(android.R.id.content), "Cannot remove orders from an active batch")
            return
        }
        
        showLoading()
        
        orderRepository.removeOrderFromBatch(batchId!!, order.orderId) { success, message ->
            runOnUiThread {
                hideLoading()
                
                if (success) {
                    // Remove from local list
                    batchOrders.remove(order)
                    batchOrderAdapter.updateOrders(batchOrders)
                    updateOrdersCount()
                    
                    // Update UI based on remaining orders
                    if (batchOrders.isEmpty()) {
                        showEmptyState()
                    }
                    
                    UIUtils.showSnackbar(findViewById(android.R.id.content), "Order removed from batch")
                } else {
                    UIUtils.showSnackbar(findViewById(android.R.id.content), message)
                }
            }
        }
    }
    
    private fun showAvailableOrdersDialog() {
        showLoading()
        
        // Fetch available orders that can be added to this batch
        orderRepository.getAvailableOrdersForBatch { success, message, availableOrders ->
            runOnUiThread {
                hideLoading()
                
                if (success && availableOrders != null && availableOrders.isNotEmpty()) {
                    // Show dialog with available orders
                    val orderItems = availableOrders.map { 
                        "${it.orderId} - ${it.restaurantName} to ${it.customerName}" 
                    }.toTypedArray()
                    
                    MaterialAlertDialogBuilder(this)
                        .setTitle("Add Order to Batch")
                        .setItems(orderItems) { _, which ->
                            addOrderToBatch(availableOrders[which])
                        }
                        .setNegativeButton("Cancel", null)
                        .show()
                } else {
                    UIUtils.showSnackbar(findViewById(android.R.id.content), 
                        message ?: "No available orders to add to batch")
                }
            }
        }
    }
    
    private fun addOrderToBatch(order: Order) {
        showLoading()
        
        // If no batch exists yet, create one first
        if (batchId == null) {
            createNewBatch { newBatchId ->
                if (newBatchId != null) {
                    batchId = newBatchId
                    addOrderToExistingBatch(order)
                } else {
                    hideLoading()
                    UIUtils.showSnackbar(findViewById(android.R.id.content), "Failed to create new batch")
                }
            }
        } else {
            addOrderToExistingBatch(order)
        }
    }
    
    private fun createNewBatch(callback: (String?) -> Unit) {
        orderRepository.createBatch { success, message, newBatchId ->
            runOnUiThread {
                if (success && newBatchId != null) {
                    callback(newBatchId)
                } else {
                    callback(null)
                }
            }
        }
    }
    
    private fun addOrderToExistingBatch(order: Order) {
        orderRepository.addOrderToBatch(batchId!!, order.orderId) { success, message ->
            runOnUiThread {
                hideLoading()
                
                if (success) {
                    // Add to local list
                    batchOrders.add(order)
                    showBatchOrders()
                    updateOrdersCount()
                    
                    // Calculate and update batch info
                    calculateBatchInfo()
                    
                    UIUtils.showSnackbar(findViewById(android.R.id.content), "Order added to batch")
                } else {
                    UIUtils.showSnackbar(findViewById(android.R.id.content), message)
                }
            }
        }
    }
    
    private fun calculateBatchInfo() {
        // In a real app, this would call the API to get updated batch details
        // For now, we'll just estimate based on the orders we have
        
        var totalEarnings = 0.0
        var totalTime = 0
        
        for (order in batchOrders) {
            totalEarnings += 5.0 // Base delivery fee
            totalTime += 15 // Average time per delivery in minutes
        }
        
        // Update UI with calculated values
        tvEstimatedEarnings.text = "$${String.format("%.2f", totalEarnings)}"
        tvEstimatedTime.text = "$totalTime min"
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
     * Show batch summary with statistics about completed deliveries
     */
    private fun showBatchSummary() {
        // Calculate statistics
        val completedOrders = batchOrders.filter { it.status == OrderStatus.COMPLETED }
        val failedOrders = batchOrders.filter { 
            it.status == OrderStatus.FAILED || it.status == OrderStatus.CANCELLED 
        }
        val pendingOrders = batchOrders.filter { 
            it.status == OrderStatus.PENDING || 
            it.status == OrderStatus.ACCEPTED || 
            it.status == OrderStatus.PICKED_UP || 
            it.status == OrderStatus.IN_TRANSIT
        }
        
        val totalEarnings = completedOrders.sumOf { it.deliveryFee }
        val totalDistance = completedOrders.sumOf { it.distance }
        val totalTime = if (completedOrders.isNotEmpty()) {
            // Calculate time from first pick up to last delivery
            val earliestPickup = completedOrders.mapNotNull { it.pickedUpAt }.minOrNull()
            val latestDelivery = completedOrders.mapNotNull { it.deliveredAt }.maxOrNull()
            
            if (earliestPickup != null && latestDelivery != null) {
                (latestDelivery.time - earliestPickup.time) / (1000 * 60) // convert to minutes
            } else {
                0L
            }
        } else {
            0L
        }
        
        // Build and show the dialog
        val builder = MaterialAlertDialogBuilder(this)
            .setTitle("Batch Summary")
            .setMessage(
                "Completed: ${completedOrders.size} orders\n" +
                "Failed/Cancelled: ${failedOrders.size} orders\n" +
                "Pending: ${pendingOrders.size} orders\n\n" +
                "Total Earnings: $${String.format("%.2f", totalEarnings)}\n" +
                "Total Distance: ${String.format("%.1f", totalDistance)} km\n" +
                "Total Time: ${totalTime} minutes"
            )
            .setPositiveButton("Great!") { dialog, _ ->
                dialog.dismiss()
                
                // If all orders are completed, show option to complete batch
                if (pendingOrders.isEmpty() && batchOrders.isNotEmpty()) {
                    showCompleteBatchOption()
                }
            }
        
        // Show completion option directly if all orders are done
        if (pendingOrders.isEmpty() && batchOrders.isNotEmpty()) {
            builder.setNeutralButton("Complete Batch") { dialog, _ ->
                dialog.dismiss()
                completeBatch()
            }
        }
        
        builder.show()
    }
    
    /**
     * Show dialog asking if rider wants to complete the batch
     */
    private fun showCompleteBatchOption() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Complete Batch?")
            .setMessage("All orders in this batch have been completed or cancelled. Would you like to complete the batch now?")
            .setPositiveButton("Complete Batch") { dialog, _ ->
                dialog.dismiss()
                completeBatch()
            }
            .setNegativeButton("Later") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
    
    /**
     * Mark the batch as completed and navigate to home screen
     */
    private fun completeBatch() {
        if (batchId == null) return
        
        showLoading()
        
        orderRepository.completeBatch(batchId!!) { success, message ->
            runOnUiThread {
                hideLoading()
                
                if (success) {
                    UIUtils.showToast(this, "Batch completed successfully!")
                    
                    // Navigate to home screen
                    val intent = Intent(this, home_screen::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    UIUtils.showSnackbar(findViewById(android.R.id.content), message)
                }
            }
        }
    }
    
    // Data class for batch details
    data class BatchDetails(
        val batchId: String,
        val estimatedEarnings: Double,
        val estimatedTimeMinutes: Int
    )
}