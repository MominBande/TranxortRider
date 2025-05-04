package com.tranxortrider.deliveryrider

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.tranxortrider.deliveryrider.adapters.CompletedOrdersAdapter
import com.tranxortrider.deliveryrider.models.Order
import com.tranxortrider.deliveryrider.repositories.OrderRepository
import com.tranxortrider.deliveryrider.utils.NetworkUtils
import com.tranxortrider.deliveryrider.utils.SessionManager
import com.tranxortrider.deliveryrider.utils.UIUtils

class completed_orders : AppCompatActivity() {
    
    // UI components
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var emptyStateText: TextView
    
    // Adapter
    private lateinit var ordersAdapter: CompletedOrdersAdapter
    
    // Dependencies
    private lateinit var orderRepository: OrderRepository
    private lateinit var sessionManager: SessionManager
    
    // Data
    private val completedOrders = mutableListOf<Order>()
    private var currentPage = 1
    private var totalPages = 1
    private var isLoading = false
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_completed_orders)
        
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
        
        // Check if user is logged in
        if (sessionManager.getUser() == null) {
            navigateToSignIn()
            return
        }
        
        // Initialize UI components
        initializeUI()
        
        // Set up RecyclerView
        setupRecyclerView()
        
        // Fetch completed orders
        fetchCompletedOrders(true)
    }
    
    private fun initializeUI() {
        // Find views
        recyclerView = findViewById(R.id.completedOrdersRecyclerView)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        emptyStateText = findViewById(R.id.emptyStateText)
        
        // Set up swipe refresh
        swipeRefreshLayout.setOnRefreshListener {
            // Reset pagination and fetch first page
            currentPage = 1
            fetchCompletedOrders(true)
        }
        
        // Set up toolbar
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.apply {
            title = "Completed Orders"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }
    
    private fun setupRecyclerView() {
        // Initialize adapter
        ordersAdapter = CompletedOrdersAdapter(completedOrders) { order ->
            navigateToOrderDetails(order.id)
        }
        
        // Set up RecyclerView
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@completed_orders)
            adapter = ordersAdapter
            
            // Add scroll listener for pagination
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                    
                    if (!isLoading && currentPage < totalPages) {
                        if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0
                        ) {
                            loadMoreOrders()
                        }
                    }
                }
            })
        }
    }
    
    private fun fetchCompletedOrders(clearExisting: Boolean) {
        // Show loading state
        swipeRefreshLayout.isRefreshing = true
        isLoading = true
        
        // Check network connectivity
        if (!NetworkUtils.isNetworkAvailable(this)) {
            handleError("No internet connection.")
            return
        }
        
        // Get user id from session
        val userId = sessionManager.getUser()?.id
        if (userId.isNullOrEmpty()) {
            handleError("User information not found. Please sign in again.")
            navigateToSignIn()
            return
        }
        
        // Fetch orders from repository
        orderRepository.getCompletedOrders(userId, currentPage) { success, message, orders ->
            runOnUiThread {
                swipeRefreshLayout.isRefreshing = false
                isLoading = false
                
                if (success && orders != null) {
                    // Use a hardcoded value for totalPages if it's not available
                    totalPages = 1
                    updateOrders(orders, clearExisting)
                } else {
                    handleError(message)
                }
            }
        }
    }
    
    private fun loadMoreOrders() {
        if (isLoading) return
        
        currentPage++
        fetchCompletedOrders(false)
    }
    
    private fun updateOrders(orders: List<Order>, clearExisting: Boolean) {
        if (clearExisting) {
            completedOrders.clear()
        }
        
        val startPosition = completedOrders.size
        completedOrders.addAll(orders)
        
        if (clearExisting) {
            ordersAdapter.notifyDataSetChanged()
        } else {
            ordersAdapter.notifyItemRangeInserted(startPosition, orders.size)
        }
        
        // Show empty state if no orders
        if (completedOrders.isEmpty()) {
            emptyStateText.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            emptyStateText.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }
    
    private fun handleError(message: String) {
        swipeRefreshLayout.isRefreshing = false
        isLoading = false
        UIUtils.showSnackbar(findViewById(android.R.id.content), message)
    }
    
    private fun navigateToOrderDetails(orderId: String) {
        val intent = Intent(this, order_details::class.java).apply {
            putExtra("ORDER_ID", orderId)
        }
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
}