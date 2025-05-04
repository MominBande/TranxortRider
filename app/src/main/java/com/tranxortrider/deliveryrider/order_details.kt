package com.tranxortrider.deliveryrider

import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tranxortrider.deliveryrider.adapters.OrderItemsAdapter
import com.tranxortrider.deliveryrider.models.Order
import com.tranxortrider.deliveryrider.models.OrderStatus
import com.tranxortrider.deliveryrider.repositories.OrderRepository
import com.tranxortrider.deliveryrider.utils.NavigationUtils
import com.tranxortrider.deliveryrider.utils.NetworkUtils
import com.tranxortrider.deliveryrider.utils.SessionManager
import com.tranxortrider.deliveryrider.utils.UIUtils
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

class order_details : AppCompatActivity() {
    
    // UI components
    private lateinit var orderIdText: TextView
    private lateinit var statusText: TextView
    private lateinit var statusIcon: ImageView
    private lateinit var timeText: TextView
    private lateinit var customerNameText: TextView
    private lateinit var customerPhoneText: TextView
    private lateinit var deliveryAddressText: TextView
    private lateinit var restaurantNameText: TextView
    private lateinit var restaurantPhoneText: TextView
    private lateinit var restaurantAddressText: TextView
    private lateinit var specialInstructionsText: TextView
    private lateinit var specialInstructionsCard: View
    private lateinit var orderItemsRecyclerView: RecyclerView
    private lateinit var subtotalText: TextView
    private lateinit var deliveryFeeText: TextView
    private lateinit var totalAmountText: TextView
    private lateinit var paymentMethodText: TextView
    
    // Action buttons
    private lateinit var navigationButton: MaterialButton
    private lateinit var callCustomerButton: MaterialButton
    private lateinit var callRestaurantButton: MaterialButton
    private lateinit var primaryActionButton: Button
    private lateinit var secondaryActionButton: Button
    private lateinit var pickupButton: MaterialButton
    private lateinit var actionButtonsContainer: ViewGroup
    
    // Dependencies
    private lateinit var orderRepository: OrderRepository
    private lateinit var sessionManager: SessionManager
    
    // Data
    private var order: Order? = null
    private lateinit var orderItemsAdapter: OrderItemsAdapter
    
    // Formatters
    private val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US)
    private val dateFormatter = SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.US)
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_order_details)
        
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
        
        // Get order ID from intent
        val orderId = intent.getStringExtra("ORDER_ID")
        if (orderId.isNullOrEmpty()) {
            UIUtils.showSnackbar(findViewById(android.R.id.content), "Order ID not provided")
            finish()
            return
        }
        
        // Fetch order details
        fetchOrderDetails(orderId)
    }
    
    private fun initializeUI() {
        try {
            // Find views with safety checks
            try {
                // Basic order info
                orderIdText = findViewById(R.id.tvOrderNumber) ?: throw Exception("tvOrderNumber not found")
                statusText = findViewById(R.id.tvStatus) ?: throw Exception("tvStatus not found")
                statusIcon = findViewById(R.id.statusIcon) ?: throw Exception("statusIcon not found")
                timeText = findViewById(R.id.tvAssignedDate) ?: throw Exception("tvAssignedDate not found")
                
                // Customer details
                customerNameText = findViewById(R.id.tvRecipientName) ?: throw Exception("tvRecipientName not found")
                customerPhoneText = findViewById(R.id.customerPhoneText) ?: throw Exception("customerPhoneText not found")
                deliveryAddressText = findViewById(R.id.tvRecipientAddress) ?: throw Exception("tvRecipientAddress not found")
                
                // Restaurant details
                restaurantNameText = findViewById(R.id.tvSenderName) ?: throw Exception("tvSenderName not found")
                restaurantPhoneText = findViewById(R.id.restaurantPhoneText) ?: throw Exception("restaurantPhoneText not found")
                restaurantAddressText = findViewById(R.id.tvSenderAddress) ?: throw Exception("tvSenderAddress not found")
                
                // Special instructions
                specialInstructionsText = findViewById(R.id.specialInstructionsText) ?: throw Exception("specialInstructionsText not found")
                specialInstructionsCard = findViewById(R.id.specialInstructionsCard) ?: throw Exception("specialInstructionsCard not found")
                
                // Order items and payment details
                orderItemsRecyclerView = findViewById(R.id.orderItemsRecyclerView) ?: throw Exception("orderItemsRecyclerView not found")
                subtotalText = findViewById(R.id.subtotalText) ?: throw Exception("subtotalText not found")
                deliveryFeeText = findViewById(R.id.deliveryFeeText) ?: throw Exception("deliveryFeeText not found")
                totalAmountText = findViewById(R.id.totalAmountText) ?: throw Exception("totalAmountText not found")
                paymentMethodText = findViewById(R.id.paymentMethodText) ?: throw Exception("paymentMethodText not found")
                
                // Action buttons
                navigationButton = findViewById(R.id.navigationButton) ?: throw Exception("navigationButton not found")
                callCustomerButton = findViewById(R.id.callCustomerButton) ?: throw Exception("callCustomerButton not found")
                callRestaurantButton = findViewById(R.id.callRestaurantButton) ?: throw Exception("callRestaurantButton not found")
                primaryActionButton = findViewById(R.id.primaryActionButton) ?: throw Exception("primaryActionButton not found")
                secondaryActionButton = findViewById(R.id.secondaryActionButton) ?: throw Exception("secondaryActionButton not found")
                pickupButton = findViewById(R.id.pickupButton) ?: throw Exception("pickupButton not found")
                actionButtonsContainer = findViewById(R.id.actionButtonsContainer) ?: throw Exception("actionButtonsContainer not found")
                
                // Configure pickup button
                pickupButton.setOnClickListener {
                    markOrderAsPickedUp()
                    // After marking as picked up, ensure navigation is available
                    order?.let { currentOrder ->
                        setupNavigationButton(currentOrder)
                    }
                }
                
                // Set up RecyclerView
                orderItemsAdapter = OrderItemsAdapter(emptyList())
                orderItemsRecyclerView.apply {
                    layoutManager = LinearLayoutManager(this@order_details)
                    adapter = orderItemsAdapter
                }
                
                // Set up back button
                findViewById<View>(R.id.btnBack)?.setOnClickListener {
                    onBackPressed()
                }
                
                Log.d("OrderDetails", "UI components initialized successfully")
            } catch (e: Exception) {
                Log.e("OrderDetails", "Error finding view: ${e.message}")
                UIUtils.showToast(this, "Error finding view: ${e.message}")
                throw e
            }
        } catch (e: Exception) {
            Log.e("OrderDetails", "Error initializing UI components", e)
            UIUtils.showToast(this, "Error initializing UI: ${e.message}")
        }
    }
    
    /**
     * Directly check order status and update UI accordingly
     * This is a safety measure to ensure the UI is properly updated
     */
    private fun checkOrderStatusAndUpdateUI() {
        try {
            // Get the current order
            val currentOrder = order ?: return
            
            Log.d("OrderDetails", "Direct status check: ${currentOrder.status}")
            
            // If order is ACCEPTED, ensure pickup button is visible AND setup navigation
            if (currentOrder.status == OrderStatus.ACCEPTED) {
                Log.d("OrderDetails", "Direct check: Order is ACCEPTED, showing pickup button")
                runOnUiThread {
                    pickupButton.visibility = View.VISIBLE
                    actionButtonsContainer.visibility = View.GONE
                    
                    // Ensure navigation button is set up for ACCEPTED status
                    Log.d("OrderDetails", "Direct check: Setting up navigation button for ACCEPTED status")
                    setupNavigationButton(currentOrder)
                    setupCallButtons(currentOrder)
                }
            }
        } catch (e: Exception) {
            Log.e("OrderDetails", "Error in direct status check", e)
        }
    }
    
    private fun fetchOrderDetails(orderId: String) {
        // Show loading
        UIUtils.showLoading(this, "Loading order details...")
        
        // Check network connectivity
        if (!NetworkUtils.isNetworkAvailable(this)) {
            UIUtils.hideLoading()
            UIUtils.showSnackbar(findViewById(android.R.id.content), "No internet connection.")
            return
        }
        
        // Fetch order from repository
        orderRepository.getOrderDetails(orderId) { success, message, orderDetails ->
            runOnUiThread {
                UIUtils.hideLoading()
                
                if (success && orderDetails != null) {
                    Log.d("OrderDetails", "Order details fetched successfully: $orderDetails")
                    this.order = orderDetails
                    updateUIWithOrderDetails(orderDetails)
                    
                    // Additional direct check to ensure UI is updated correctly
                    Handler(Looper.getMainLooper()).postDelayed({
                        checkOrderStatusAndUpdateUI()
                    }, 500)
                } else {
                    Log.e("OrderDetails", "Failed to fetch order details: $message")
                    UIUtils.showSnackbar(findViewById(android.R.id.content), message)
                }
            }
        }
    }
    
    private fun updateUIWithOrderDetails(order: Order) {
        try {
            Log.d("OrderDetails", "Updating UI with order details: ${order.orderId}, status: ${order.status}")
            
            // Basic order info
            orderIdText.text = "#${order.orderId}"
            timeText.text = "Created: ${dateFormatter.format(order.createdAt)}"
            
            // Status
            updateStatusUI(order.status)
            
            // Customer details
            customerNameText.text = order.customerName
            customerPhoneText.text = order.customerPhone
            deliveryAddressText.text = order.customerAddress
            
            // Restaurant 
            restaurantNameText.text = order.restaurantName
            restaurantPhoneText.text = order.restaurantPhone
            restaurantAddressText.text = order.restaurantAddress
            
            // Special instructions
            if (order.specialInstructions.isNullOrEmpty()) {
                specialInstructionsCard.visibility = View.GONE
            } else {
                specialInstructionsCard.visibility = View.VISIBLE
                specialInstructionsText.text = order.specialInstructions
            }
            
            // Order items
            orderItemsAdapter.updateItems(order.items)
            
            // Payment details
            val subtotal = order.totalAmount - order.deliveryFee
            subtotalText.text = currencyFormatter.format(subtotal)
            deliveryFeeText.text = currencyFormatter.format(order.deliveryFee)
            totalAmountText.text = currencyFormatter.format(order.totalAmount)
            paymentMethodText.text = order.paymentMethod
            
            // Log coordinates for debugging
            Log.d("OrderDetails", "Restaurant coordinates: ${order.restaurantLatitude}, ${order.restaurantLongitude}")
            Log.d("OrderDetails", "Customer coordinates: ${order.customerLatitude}, ${order.customerLongitude}")
            
            // Setup navigation button regardless of order status
            setupNavigationButton(order)
            
            // Show/hide pickup button based on order status
            Log.d("OrderDetails", "Order status is: ${order.status}, comparing with ACCEPTED: ${order.status == OrderStatus.ACCEPTED}")
            
            if (order.status == OrderStatus.ACCEPTED) {
                Log.d("OrderDetails", "Setting pickup button to VISIBLE")
                pickupButton.visibility = View.VISIBLE
                actionButtonsContainer.visibility = View.GONE
                
                // For ACCEPTED status, we still need to setup other UI elements
                setupCallButtons(order)
            } else {
                Log.d("OrderDetails", "Setting pickup button to GONE, status is: ${order.status}")
                pickupButton.visibility = View.GONE
                actionButtonsContainer.visibility = View.VISIBLE
                
                // Set up action buttons
                setupActionButtons(order)
            }
            
            // Force layout update
            pickupButton.invalidate()
            actionButtonsContainer.invalidate()
            
            Log.d("OrderDetails", "UI updated successfully with order details")
        } catch (e: Exception) {
            Log.e("OrderDetails", "Error updating UI with order details", e)
            UIUtils.showToast(this, "Error updating UI: ${e.message}")
        }
    }
    
    private fun updateStatusUI(status: OrderStatus) {
        when (status) {
            OrderStatus.PENDING -> {
                statusText.text = "Pending"
                statusText.setTextColor(ContextCompat.getColor(this, R.color.yellow))
                statusIcon.setImageResource(R.drawable.ic_pending)
                statusIcon.setColorFilter(ContextCompat.getColor(this, R.color.yellow))
            }
            OrderStatus.ASSIGNED -> {
                statusText.text = "Assigned"
                statusText.setTextColor(ContextCompat.getColor(this, R.color.blue))
                statusIcon.setImageResource(R.drawable.ic_accepted)
                statusIcon.setColorFilter(ContextCompat.getColor(this, R.color.blue))
            }
            OrderStatus.ACCEPTED -> {
                statusText.text = "Accepted"
                statusText.setTextColor(ContextCompat.getColor(this, R.color.blue))
                statusIcon.setImageResource(R.drawable.ic_accepted)
                statusIcon.setColorFilter(ContextCompat.getColor(this, R.color.blue))
            }
            OrderStatus.PICKED_UP -> {
                statusText.text = "In Transit"
                statusText.setTextColor(ContextCompat.getColor(this, R.color.blue))
                statusIcon.setImageResource(R.drawable.ic_in_transit)
                statusIcon.setColorFilter(ContextCompat.getColor(this, R.color.blue))
            }
            OrderStatus.IN_TRANSIT -> {
                statusText.text = "In Transit"
                statusText.setTextColor(ContextCompat.getColor(this, R.color.blue))
                statusIcon.setImageResource(R.drawable.ic_in_transit)
                statusIcon.setColorFilter(ContextCompat.getColor(this, R.color.blue))
            }
            OrderStatus.COMPLETED -> {
                statusText.text = "Completed"
                statusText.setTextColor(ContextCompat.getColor(this, R.color.aesthgreen))
                statusIcon.setImageResource(R.drawable.ic_completed)
                statusIcon.setColorFilter(ContextCompat.getColor(this, R.color.aesthgreen))
            }
            OrderStatus.CANCELLED -> {
                statusText.text = "Cancelled"
                statusText.setTextColor(ContextCompat.getColor(this, R.color.red))
                statusIcon.setImageResource(R.drawable.ic_cancelled)
                statusIcon.setColorFilter(ContextCompat.getColor(this, R.color.red))
            }
            OrderStatus.FAILED -> {
                statusText.text = "Failed"
                statusText.setTextColor(ContextCompat.getColor(this, R.color.red))
                statusIcon.setImageResource(R.drawable.ic_error)
                statusIcon.setColorFilter(ContextCompat.getColor(this, R.color.red))
            }
        }
    }
    
    private fun setupActionButtons(order: Order) {
        try {
            // Log the order id and status for debugging
            Log.d("OrderDetails", "Setting up action buttons for order ${order.id}, status: ${order.status}")
            
            // Configure primary and secondary action buttons based on order status
            when (order.status) {
                OrderStatus.PENDING -> {
                    primaryActionButton.text = "Accept Order"
                    primaryActionButton.setBackgroundColor(ContextCompat.getColor(this, R.color.aesthgreen))
                    primaryActionButton.setOnClickListener { updateOrderStatus(OrderStatus.ACCEPTED) }
                    
                    secondaryActionButton.text = "Decline Order"
                    secondaryActionButton.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
                    secondaryActionButton.setOnClickListener { showDeclineOrderDialog() }
                    
                    primaryActionButton.visibility = View.VISIBLE
                    secondaryActionButton.visibility = View.VISIBLE
                }
                OrderStatus.ASSIGNED -> {
                    primaryActionButton.text = "Accept Assignment"
                    primaryActionButton.setBackgroundColor(ContextCompat.getColor(this, R.color.aesthgreen))
                    primaryActionButton.setOnClickListener { acceptAssignedOrder(order.id) }
                    
                    secondaryActionButton.text = "Reject Assignment"
                    secondaryActionButton.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
                    secondaryActionButton.setOnClickListener { showRejectAssignmentDialog() }
                    
                    primaryActionButton.visibility = View.VISIBLE
                    secondaryActionButton.visibility = View.VISIBLE
                }
                OrderStatus.ACCEPTED -> {
                    primaryActionButton.text = "Order Picked Up"
                    primaryActionButton.setBackgroundColor(ContextCompat.getColor(this, R.color.aesthgreen))
                    primaryActionButton.setOnClickListener { markOrderAsPickedUp() }
                    
                    secondaryActionButton.text = "Cancel Delivery"
                    secondaryActionButton.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
                    secondaryActionButton.setOnClickListener { showCancelDeliveryDialog() }
                    
                    primaryActionButton.visibility = View.VISIBLE
                    secondaryActionButton.visibility = View.VISIBLE
                }
                OrderStatus.PICKED_UP -> {
                    primaryActionButton.text = "Order Delivered"
                    primaryActionButton.setBackgroundColor(ContextCompat.getColor(this, R.color.aesthgreen))
                    primaryActionButton.setOnClickListener { updateOrderStatus(OrderStatus.COMPLETED) }
                    
                    secondaryActionButton.text = "Delivery Failed"
                    secondaryActionButton.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
                    secondaryActionButton.setOnClickListener { showDeliveryFailedDialog() }
                    
                    primaryActionButton.visibility = View.VISIBLE
                    secondaryActionButton.visibility = View.VISIBLE
                }
                OrderStatus.IN_TRANSIT -> {
                    primaryActionButton.text = "Order Delivered"
                    primaryActionButton.setBackgroundColor(ContextCompat.getColor(this, R.color.aesthgreen))
                    primaryActionButton.setOnClickListener { updateOrderStatus(OrderStatus.COMPLETED) }
                    
                    secondaryActionButton.text = "Delivery Failed"
                    secondaryActionButton.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
                    secondaryActionButton.setOnClickListener { showDeliveryFailedDialog() }
                    
                    primaryActionButton.visibility = View.VISIBLE
                    secondaryActionButton.visibility = View.VISIBLE
                }
                OrderStatus.COMPLETED -> {
                    primaryActionButton.visibility = View.GONE
                    secondaryActionButton.visibility = View.GONE
                }
                OrderStatus.CANCELLED -> {
                    primaryActionButton.visibility = View.GONE
                    secondaryActionButton.visibility = View.GONE
                }
                OrderStatus.FAILED -> {
                    primaryActionButton.visibility = View.GONE
                    secondaryActionButton.visibility = View.GONE
                }
            }
            
            // Also setup call buttons as they apply to all statuses
            setupCallButtons(order)
        } catch (e: Exception) {
            Log.e("OrderDetails", "Error setting up action buttons", e)
            UIUtils.showToast(this, "Error setting up action buttons: ${e.message}")
        }
    }
    
    /**
     * Setup navigation button click handler
     */
    private fun setupNavigationButton(order: Order) {
        try {
            Log.d("OrderDetails", "Setting up navigation button for order ${order.id}, status: ${order.status}")
            
        navigationButton.setOnClickListener {
            try {
                Log.d("OrderDetails", "Navigation button clicked, order status: ${order.status}")
                
                // Log available coordinates for debugging
                Log.d("OrderDetails", "Restaurant coordinates available: lat=${order.restaurantLatitude}, lng=${order.restaurantLongitude}")
                Log.d("OrderDetails", "Customer coordinates available: lat=${order.customerLatitude}, lng=${order.customerLongitude}")
                
                // Check if coordinates are valid
                val hasRestaurantCoords = (order.restaurantLatitude != null && order.restaurantLatitude != 0.0 &&
                                          order.restaurantLongitude != null && order.restaurantLongitude != 0.0)
                val hasCustomerCoords = (order.customerLatitude != null && order.customerLatitude != 0.0 &&
                                        order.customerLongitude != null && order.customerLongitude != 0.0)
                
                if (!hasRestaurantCoords && !hasCustomerCoords) {
                    Log.e("OrderDetails", "No valid coordinates available for navigation")
                    UIUtils.showToast(this, "No valid coordinates available for navigation")
                    return@setOnClickListener
                }
                
                // Determine which coordinates to use based on order status
                var targetLat = 0.0
                var targetLng = 0.0
                var targetName = ""
                var targetAddress = ""
                
                when (order.status) {
                    OrderStatus.PENDING, OrderStatus.ASSIGNED, OrderStatus.ACCEPTED -> {
                        // For pending/assigned/accepted orders, navigate to restaurant
                        if (hasRestaurantCoords) {
                            targetLat = order.restaurantLatitude ?: 0.0
                            targetLng = order.restaurantLongitude ?: 0.0
                            targetName = order.restaurantName
                            targetAddress = order.restaurantAddress
                            
                            Log.d("OrderDetails", "Using restaurant coordinates: lat=$targetLat, lng=$targetLng")
                        } else {
                            Log.e("OrderDetails", "Restaurant coordinates not available")
                            UIUtils.showToast(this, "Restaurant coordinates not available")
                            return@setOnClickListener
                        }
                    }
                    OrderStatus.PICKED_UP, OrderStatus.IN_TRANSIT -> {
                        // For picked up orders, navigate directly to customer
                        if (hasCustomerCoords) {
                            targetLat = order.customerLatitude ?: 0.0
                            targetLng = order.customerLongitude ?: 0.0
                            targetName = order.customerName
                            targetAddress = order.customerAddress
                            
                            Log.d("OrderDetails", "Using customer coordinates: lat=$targetLat, lng=$targetLng")
                        } else {
                            Log.e("OrderDetails", "Customer coordinates not available")
                            UIUtils.showToast(this, "Customer coordinates not available")
                            return@setOnClickListener
                        }
                    }
                    else -> {
                        // For other statuses, show options dialog
                        showExternalNavigationOptionsDialog(order)
                        return@setOnClickListener
                    }
                }
                
                // Validate final coordinates
                if (targetLat == 0.0 || targetLng == 0.0) {
                    Log.e("OrderDetails", "Invalid target coordinates: lat=$targetLat, lng=$targetLng")
                    UIUtils.showToast(this, "Invalid coordinates for navigation")
                    return@setOnClickListener
                }
                
                    // Launch in-app navigation with OpenRouteService
                    launchInAppNavigation(targetLat, targetLng, targetName, targetAddress)
                
            } catch (e: Exception) {
                Log.e("OrderDetails", "Error in navigation button click", e)
                UIUtils.showToast(this, "Navigation error: ${e.message}")
                }
            }
        } catch (e: Exception) {
            Log.e("OrderDetails", "Error setting up navigation button", e)
            UIUtils.showToast(this, "Error setting up navigation: ${e.message}")
            }
        }
        
    /**
     * Setup call buttons click handlers
     */
    private fun setupCallButtons(order: Order) {
        try {
        // Configure call buttons
        callCustomerButton.setOnClickListener {
            if (order.customerPhone.isNotEmpty()) {
                dialPhoneNumber(order.customerPhone)
            } else {
                UIUtils.showToast(this, "Customer phone number not available")
            }
        }
        
        callRestaurantButton.setOnClickListener {
            if (order.restaurantPhone.isNotEmpty()) {
                dialPhoneNumber(order.restaurantPhone)
            } else {
                UIUtils.showToast(this, "Restaurant phone number not available")
            }
        }
        } catch (e: Exception) {
            Log.e("OrderDetails", "Error setting up call buttons", e)
            UIUtils.showToast(this, "Error setting up call buttons: ${e.message}")
        }
    }
    
    /**
     * Launch the in-app navigation activity with OpenRouteService
     */
    private fun launchInAppNavigation(latitude: Double, longitude: Double, name: String, address: String) {
        try {
            Log.d("OrderDetails", "Launching in-app navigation to $name ($latitude, $longitude)")
            
            val intent = Intent(this, NavigationActivity::class.java).apply {
                putExtra("destination_lat", latitude)
                putExtra("destination_lng", longitude)
                putExtra("destination_name", name)
                putExtra("destination_address", address)
                putExtra("customer_phone", order?.customerPhone ?: "")
                putExtra("restaurant_phone", order?.restaurantPhone ?: "")
                putExtra("order_status", order?.status?.name ?: "")
                
                // Log the values for debugging
                Log.d("OrderDetails", "Navigation intent extras: " +
                    "destination=$name, " +
                    "order_status=${order?.status?.name}, " +
                    "restaurant_phone=${order?.restaurantPhone}, " +
                    "customer_phone=${order?.customerPhone}")
            }
            
            startActivity(intent)
        } catch (e: Exception) {
            Log.e("OrderDetails", "Failed to launch in-app navigation", e)
            
            // Fallback to external navigation
            openInGoogleMaps(latitude, longitude, name)
        }
    }
    
    /**
     * Open Google Maps with the specified coordinates
     * 
     * @param latitude Destination latitude
     * @param longitude Destination longitude
     * @param name Destination name
     */
    private fun openInGoogleMaps(latitude: Double, longitude: Double, name: String) {
        try {
            Log.d("OrderDetails", "Opening Google Maps with coordinates: lat=$latitude, lng=$longitude, name=$name")
            
            // Try Google Maps with navigation mode first
            try {
                val googleMapsUri = Uri.parse("google.navigation:q=$latitude,$longitude")
                val googleMapsIntent = Intent(Intent.ACTION_VIEW, googleMapsUri)
                googleMapsIntent.setPackage("com.google.android.apps.maps")
                
                if (googleMapsIntent.resolveActivity(packageManager) != null) {
                    Log.d("OrderDetails", "Launching Google Maps navigation")
                    startActivity(googleMapsIntent)
                    UIUtils.showToast(this, "Opening navigation in Google Maps")
                    return
                } else {
                    Log.d("OrderDetails", "Google Maps app not found, trying alternatives")
                }
            } catch (e: Exception) {
                Log.e("OrderDetails", "Error launching Google Maps navigation", e)
            }
            
            // Try Google Maps web URL as fallback
            try {
                val mapUrl = "https://www.google.com/maps/dir/?api=1&destination=$latitude,$longitude&destination_place_id=$name"
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(mapUrl))
                startActivity(browserIntent)
                UIUtils.showToast(this, "Opening navigation in web browser")
                return
            } catch (e: Exception) {
                Log.e("OrderDetails", "Error launching web navigation", e)
            }
            
            // Generic map intent as last resort
            val geoUri = Uri.parse("geo:0,0?q=$latitude,$longitude($name)")
            val mapIntent = Intent(Intent.ACTION_VIEW, geoUri)
            startActivity(mapIntent)
            UIUtils.showToast(this, "Opening in maps app")
            
        } catch (e: Exception) {
            Log.e("OrderDetails", "Error opening external navigation", e)
            UIUtils.showToast(this, "Could not open navigation: ${e.message}")
        }
    }
    
    /**
     * Show dialog for external navigation options
     * 
     * @param order The order to navigate to
     */
    private fun showExternalNavigationOptionsDialog(order: Order) {
        try {
            // Check if coordinates are valid before showing dialog
            val hasRestaurantCoords = (order.restaurantLatitude != null && order.restaurantLatitude != 0.0 &&
                                      order.restaurantLongitude != null && order.restaurantLongitude != 0.0)
            val hasCustomerCoords = (order.customerLatitude != null && order.customerLatitude != 0.0 &&
                                    order.customerLongitude != null && order.customerLongitude != 0.0)
            
            if (!hasRestaurantCoords && !hasCustomerCoords) {
                UIUtils.showToast(this, "No valid coordinates available for navigation")
                return
            }
            
            // Build dialog options based on available coordinates
            val dialogBuilder = MaterialAlertDialogBuilder(this)
                .setTitle("Navigation Options")
                .setMessage("Where would you like to navigate to?")
                .setCancelable(true)
                .setNeutralButton("Cancel") { dialog, _ -> dialog.dismiss() }
            
            if (hasCustomerCoords) {
                dialogBuilder.setPositiveButton("Customer Address") { dialog, _ ->
                    val lat = order.customerLatitude ?: 0.0
                    val lng = order.customerLongitude ?: 0.0
                    launchInAppNavigation(lat, lng, order.customerName, order.customerAddress)
                    dialog.dismiss()
                }
            }
            
            if (hasRestaurantCoords) {
                dialogBuilder.setNegativeButton("Restaurant") { dialog, _ ->
                    val lat = order.restaurantLatitude ?: 0.0
                    val lng = order.restaurantLongitude ?: 0.0
                    launchInAppNavigation(lat, lng, order.restaurantName, order.restaurantAddress)
                    dialog.dismiss()
                }
            }
            
            dialogBuilder.show()
            
        } catch (e: Exception) {
            Log.e("OrderDetails", "Error showing navigation options", e)
            UIUtils.showSnackbar(findViewById(android.R.id.content), "Error showing navigation options")
        }
    }
    
    private fun updateOrderStatus(newStatus: OrderStatus) {
        val orderId = order?.id ?: return
        
        // If changing from PENDING to ACCEPTED, show a dialog explaining batch optimization
        if (order?.status == OrderStatus.PENDING && newStatus == OrderStatus.ACCEPTED) {
            MaterialAlertDialogBuilder(this)
                .setTitle("Auto-Batch Optimization")
                .setMessage("This order will be added to your current delivery batch and the route will be automatically optimized for the most efficient delivery sequence.")
                .setPositiveButton("Accept") { _, _ ->
                    performOrderStatusUpdate(orderId, newStatus)
                }
                .setNegativeButton("Cancel", null)
                .show()
        } else {
            performOrderStatusUpdate(orderId, newStatus)
        }
    }
    
    private fun performOrderStatusUpdate(orderId: String, newStatus: OrderStatus) {
        UIUtils.showLoading(this, "Updating order status...")
        
        if (!NetworkUtils.isNetworkAvailable(this)) {
            UIUtils.hideLoading()
            UIUtils.showSnackbar(findViewById(android.R.id.content), "No internet connection")
            return
        }
        
        orderRepository.updateOrderStatus(orderId, newStatus) { success, message ->
            runOnUiThread {
                UIUtils.hideLoading()
                
                if (success) {
                    // Update local order status
                    order = order?.copy(status = newStatus)
                    
                    // Update UI with new status
                    order?.let { updateUIWithOrderDetails(it) }
                    
                    // Show success message
                    when (newStatus) {
                        OrderStatus.ACCEPTED -> {
                            UIUtils.showToast(this, message)
                            // Show batch navigation option
                            showBatchNavigationOption()
                        }
                        OrderStatus.ASSIGNED -> UIUtils.showToast(this, "Order assigned successfully")
                        OrderStatus.PICKED_UP -> UIUtils.showToast(this, "Order marked as picked up")
                        OrderStatus.IN_TRANSIT -> UIUtils.showToast(this, "Order is in transit")
                        OrderStatus.COMPLETED -> {
                            UIUtils.showToast(this, "Order completed successfully")
                            // Check for next order in the sequence
                            findNextOrderInSequence()
                        }
                        OrderStatus.CANCELLED -> UIUtils.showToast(this, "Order cancelled")
                        OrderStatus.FAILED -> UIUtils.showToast(this, "Order marked as failed")
                        else -> UIUtils.showToast(this, "Order status updated")
                    }
                    
                    // Notify any listeners about the status change
                    sendOrderStatusBroadcast(newStatus)
                    
                    // If order is completed or failed, navigate back after a delay ONLY if not showing next order
                    if (newStatus == OrderStatus.CANCELLED || newStatus == OrderStatus.FAILED) {
                        Handler(Looper.getMainLooper()).postDelayed({
                            finish()
                        }, 1500)
                    }
                } else {
                    UIUtils.showSnackbar(findViewById(android.R.id.content), message)
                }
            }
        }
    }
    
    /**
     * Find the next order in the optimized sequence and offer navigation
     */
    private fun findNextOrderInSequence() {
        // Get the batch ID from the current order
        val batchId = order?.batchId
        if (batchId.isNullOrEmpty()) {
            // No batch, just finish the activity
            Handler(Looper.getMainLooper()).postDelayed({
                finish()
            }, 1500)
            return
        }
        
        // Show loading while we find the next order
        UIUtils.showLoading(this, "Finding next delivery...")
        
        // Get all orders in the batch
        orderRepository.getBatchOrdersWithSequence(batchId) { success, message, sequencedOrders ->
            runOnUiThread {
                UIUtils.hideLoading()
                
                if (success && !sequencedOrders.isNullOrEmpty()) {
                    // Find the current order in the sequence
                    val currentOrderId = order?.id
                    val currentOrder = sequencedOrders.find { it.first.id == currentOrderId }
                    val currentSequence = currentOrder?.second ?: -1
                    
                    // Find the next order in sequence
                    val nextOrder = sequencedOrders
                        .filter { it.first.status != OrderStatus.COMPLETED && 
                                it.first.status != OrderStatus.CANCELLED && 
                                it.first.status != OrderStatus.FAILED }
                        .sortedBy { it.second }
                        .firstOrNull { it.second > currentSequence }?.first
                    
                    if (nextOrder != null) {
                        // Found next order, show navigation dialog
                        showNextOrderNavigationDialog(nextOrder)
                    } else {
                        // No more orders to deliver
                        showBatchCompletedDialog()
                    }
                } else {
                    // Just finish this activity
                    Handler(Looper.getMainLooper()).postDelayed({
                        finish()
                    }, 1500)
                }
            }
        }
    }
    
    /**
     * Show dialog to navigate to the next order
     */
    private fun showNextOrderNavigationDialog(nextOrder: Order) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Next Delivery")
            .setMessage("Great job completing this delivery! Next up is order #${nextOrder.orderId} for ${nextOrder.customerName}.\n\nWould you like to navigate to the next pickup location?")
            .setPositiveButton("Navigate") { _, _ ->
                // Open the next order details and start navigation
                val intent = Intent(this, order_details::class.java)
                intent.putExtra("ORDER_ID", nextOrder.id)
                intent.putExtra("START_NAVIGATION", true)
                startActivity(intent)
                finish()
            }
            .setNegativeButton("View Order") { _, _ ->
                // Just open the order details
                val intent = Intent(this, order_details::class.java)
                intent.putExtra("ORDER_ID", nextOrder.id)
                startActivity(intent)
                finish()
            }
            .setNeutralButton("Later") { _, _ ->
                // Go back to batch view
                val intent = Intent(this, batch::class.java)
                startActivity(intent)
                finish()
            }
            .setCancelable(false)
            .show()
    }
    
    /**
     * Show dialog when all orders in batch are completed
     */
    private fun showBatchCompletedDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Batch Completed")
            .setMessage("Congratulations! You have completed all orders in this batch.")
            .setPositiveButton("View Summary") { _, _ ->
                // Go to batch summary (which would be implemented in the batch activity)
                val intent = Intent(this, batch::class.java)
                intent.putExtra("SHOW_SUMMARY", true)
                startActivity(intent)
                finish()
            }
            .setNegativeButton("Back to Home") { _, _ ->
                // Go back to home screen
                val intent = Intent(this, home_screen::class.java)
                startActivity(intent)
                finish()
            }
            .setCancelable(false)
            .show()
    }

    // Check if we should start navigation automatically
    override fun onResume() {
        super.onResume()
        
        try {
            // Refresh order details to ensure everything is up to date
            order?.id?.let { orderId ->
                Log.d("OrderDetails", "Refreshing order details on resume")
                refreshOrderDetails()
            }
            
            if (intent.getBooleanExtra("START_NAVIGATION", false)) {
                // Remove the flag to prevent repeated navigation
                intent.removeExtra("START_NAVIGATION")
                
                Log.d("OrderDetails", "Auto-starting navigation from intent")
                
                // Get the order location to navigate to
                order?.let { currentOrder ->
                    // Check if coordinates are valid
                    val hasRestaurantCoords = (currentOrder.restaurantLatitude != null && currentOrder.restaurantLatitude != 0.0 &&
                                              currentOrder.restaurantLongitude != null && currentOrder.restaurantLongitude != 0.0)
                    val hasCustomerCoords = (currentOrder.customerLatitude != null && currentOrder.customerLatitude != 0.0 &&
                                            currentOrder.customerLongitude != null && currentOrder.customerLongitude != 0.0)
                    
                    if (!hasRestaurantCoords && !hasCustomerCoords) {
                        UIUtils.showToast(this, "No valid coordinates available for navigation")
                        return
                    }
                    
                    // Determine which coordinates to use based on order status
                    if (hasRestaurantCoords && 
                        (currentOrder.status == OrderStatus.PENDING || 
                         currentOrder.status == OrderStatus.ASSIGNED || 
                         currentOrder.status == OrderStatus.ACCEPTED)) {
                         
                        // Navigate to restaurant
                        launchInAppNavigation(
                                    currentOrder.restaurantLatitude ?: 0.0,
                                    currentOrder.restaurantLongitude ?: 0.0,
                            currentOrder.restaurantName,
                            currentOrder.restaurantAddress
                        )
                    } else if (hasCustomerCoords) {
                        // Navigate to customer
                        launchInAppNavigation(
                                    currentOrder.customerLatitude ?: 0.0,
                                    currentOrder.customerLongitude ?: 0.0,
                            currentOrder.customerName,
                            currentOrder.customerAddress
                                )
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("OrderDetails", "Error auto-starting navigation", e)
        }
    }
    
    private fun showBatchNavigationOption() {
        // Show an option to navigate to the batch screen to see the optimized route
        MaterialAlertDialogBuilder(this)
            .setTitle("View Optimized Route")
            .setMessage("This order has been added to a batch with optimized delivery sequence. Would you like to view the full batch?")
            .setPositiveButton("View Batch") { _, _ ->
                val intent = Intent(this, batch::class.java)
                startActivity(intent)
            }
            .setNegativeButton("Continue", null)
            .show()
    }
    
    private fun showDeclineOrderDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Decline Order")
            .setMessage("Are you sure you want to decline this order? This action cannot be undone.")
            .setPositiveButton("Decline") { dialog, _ ->
                updateOrderStatus(OrderStatus.CANCELLED)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
    
    private fun showCancelDeliveryDialog() {
        val reasonOptions = arrayOf(
            "Restaurant is closed",
            "Item not available",
            "Too far to deliver",
            "Other reasons"
        )
        
        MaterialAlertDialogBuilder(this)
            .setTitle("Cancel Delivery")
            .setItems(reasonOptions) { dialog, which ->
                val reason = reasonOptions[which]
                cancelOrderWithReason(reason)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
    
    private fun showDeliveryFailedDialog() {
        val reasonOptions = arrayOf(
            "Customer not available",
            "Wrong address",
            "Customer rejected the order",
            "Other reasons"
        )
        
        MaterialAlertDialogBuilder(this)
            .setTitle("Mark Delivery as Failed")
            .setItems(reasonOptions) { dialog, which ->
                val reason = reasonOptions[which]
                failOrderWithReason(reason)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
    
    private fun cancelOrderWithReason(reason: String) {
        val orderId = order?.id ?: return
        
        UIUtils.showLoading(this, "Cancelling order...")
        
        orderRepository.cancelOrder(orderId, reason) { success, message ->
            runOnUiThread {
                UIUtils.hideLoading()
                
                if (success) {
                    order = order?.copy(status = OrderStatus.CANCELLED, cancelReason = reason)
                    order?.let { updateUIWithOrderDetails(it) }
                    UIUtils.showToast(this, "Order cancelled: $reason")
                    
                    sendOrderStatusBroadcast(OrderStatus.CANCELLED)
                    
                    // Navigate back after a short delay
                    Handler(Looper.getMainLooper()).postDelayed({
                        finish()
                    }, 1500)
                } else {
                    UIUtils.showSnackbar(findViewById(android.R.id.content), message)
                }
            }
        }
    }
    
    private fun failOrderWithReason(reason: String) {
        val orderId = order?.id ?: return
        
        UIUtils.showLoading(this, "Marking delivery as failed...")
        
        orderRepository.failOrder(orderId, reason) { success, message ->
            runOnUiThread {
                UIUtils.hideLoading()
                
                if (success) {
                    order = order?.copy(status = OrderStatus.FAILED, cancelReason = reason)
                    order?.let { updateUIWithOrderDetails(it) }
                    UIUtils.showToast(this, "Delivery marked as failed: $reason")
                    
                    sendOrderStatusBroadcast(OrderStatus.FAILED)
                    
                    // Navigate back after a short delay
                    Handler(Looper.getMainLooper()).postDelayed({
                        finish()
                    }, 1500)
                } else {
                    UIUtils.showSnackbar(findViewById(android.R.id.content), message)
                }
            }
        }
    }
    
    private fun dialPhoneNumber(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phoneNumber")
        startActivity(intent)
    }
    
    private fun sendOrderStatusBroadcast(status: OrderStatus) {
        val intent = Intent("com.tranxortrider.deliveryrider.ORDER_STATUS_CHANGED")
        intent.putExtra("ORDER_ID", order?.id)
        intent.putExtra("ORDER_STATUS", status.name)
        sendBroadcast(intent)
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
    
    // Add these new methods to handle assigned orders
    
    private fun acceptAssignedOrder(orderId: String) {
        UIUtils.showLoading(this, "Accepting assignment...")
        
        if (!NetworkUtils.isNetworkAvailable(this)) {
            UIUtils.hideLoading()
            UIUtils.showSnackbar(findViewById(android.R.id.content), "No internet connection")
            return
        }
        
        orderRepository.acceptAdminAssignedOrder(orderId) { success, message ->
            runOnUiThread {
                UIUtils.hideLoading()
                
                if (success) {
                    // Update local order status
                    order = order?.copy(status = OrderStatus.ACCEPTED)
                    
                    // Update UI with new status
                    order?.let { updateUIWithOrderDetails(it) }
                    
                    // Show success message
                    UIUtils.showToast(this, "Assignment accepted successfully")
                    
                    // Notify any listeners about the status change
                    sendOrderStatusBroadcast(OrderStatus.ACCEPTED)
                } else {
                    UIUtils.showSnackbar(findViewById(android.R.id.content), message)
                }
            }
        }
    }
    
    private fun showRejectAssignmentDialog() {
        val reasons = arrayOf(
            "Too far away",
            "Too busy now",
            "Restaurant has long wait times",
            "Problem with delivery area",
            "End of shift",
            "Other"
        )
        
        MaterialAlertDialogBuilder(this)
            .setTitle("Reason for Rejection")
            .setItems(reasons) { _, which ->
                rejectAssignedOrder(order?.id ?: "", reasons[which])
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun rejectAssignedOrder(orderId: String, reason: String) {
        UIUtils.showLoading(this, "Rejecting assignment...")
        
        if (!NetworkUtils.isNetworkAvailable(this)) {
            UIUtils.hideLoading()
            UIUtils.showSnackbar(findViewById(android.R.id.content), "No internet connection")
            return
        }
        
        orderRepository.rejectAdminAssignedOrder(orderId, reason) { success, message ->
            runOnUiThread {
                UIUtils.hideLoading()
                
                if (success) {
                    UIUtils.showToast(this, "Assignment rejected successfully")
                    finish() // Go back after rejection
                } else {
                    UIUtils.showSnackbar(findViewById(android.R.id.content), message)
                }
            }
        }
    }

    /**
     * Mark order as picked up
     */
    private fun markOrderAsPickedUp() {
        val orderId = order?.id ?: return
        
        UIUtils.showLoading(this, "Marking order as picked up...")
        
        if (!NetworkUtils.isNetworkAvailable(this)) {
            UIUtils.hideLoading()
            UIUtils.showSnackbar(findViewById(android.R.id.content), "No internet connection")
            return
        }
        
        // Use the dedicated pickupOrder method instead of generic updateOrderStatus
        orderRepository.pickupOrder(orderId) { success, message ->
            runOnUiThread {
                UIUtils.hideLoading()
                
                if (success) {
                    // Show success message
                    UIUtils.showToast(this, "Order marked as picked up")
                    
                    // Refresh order details from server
                    refreshOrderDetails()
                    
                    // Ask if user wants to navigate to customer
                    showNavigateToCustomerDialog()
                    
                    // Notify any listeners about the status change
                    sendOrderStatusBroadcast(OrderStatus.PICKED_UP)
                } else {
                    UIUtils.showSnackbar(findViewById(android.R.id.content), message)
                }
            }
        }
    }
    
    /**
     * Show dialog asking if user wants to navigate to customer after pickup
     */
    private fun showNavigateToCustomerDialog() {
        val customerCoords = (order?.customerLatitude != null && order?.customerLatitude != 0.0 &&
                            order?.customerLongitude != null && order?.customerLongitude != 0.0)
        
        if (customerCoords) {
            MaterialAlertDialogBuilder(this)
                .setTitle("Navigate to Customer")
                .setMessage("Would you like to navigate to the customer's location now?")
                .setPositiveButton("Navigate") { dialog, _ ->
                    // Navigate to customer using in-app navigation
                    launchInAppNavigation(
                        order?.customerLatitude ?: 0.0,
                        order?.customerLongitude ?: 0.0,
                        order?.customerName ?: "Customer",
                        order?.customerAddress ?: ""
                    )
                    dialog.dismiss()
                }
                .setNegativeButton("Later") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    /**
     * Refresh order details from the server
     */
    private fun refreshOrderDetails() {
        val orderId = order?.id ?: return
        
        // Show loading
        UIUtils.showLoading(this, "Refreshing order details...")
        
        // Check network connectivity
        if (!NetworkUtils.isNetworkAvailable(this)) {
            UIUtils.hideLoading()
            UIUtils.showSnackbar(findViewById(android.R.id.content), "No internet connection.")
            return
        }
        
        // Fetch order from repository
        orderRepository.getOrderDetails(orderId) { success, message, orderDetails ->
            runOnUiThread {
                UIUtils.hideLoading()
                
                if (success && orderDetails != null) {
                    Log.d("OrderDetails", "Order details refreshed successfully: $orderDetails")
                    this.order = orderDetails
                    updateUIWithOrderDetails(orderDetails)
                    
                    // Make sure the action buttons are properly set up, including navigation
                    setupActionButtons(orderDetails)
                } else {
                    Log.e("OrderDetails", "Failed to refresh order details: $message")
                    UIUtils.showSnackbar(findViewById(android.R.id.content), message)
                }
            }
        }
    }
}