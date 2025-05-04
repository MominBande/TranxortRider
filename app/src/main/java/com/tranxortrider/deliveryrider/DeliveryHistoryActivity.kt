package com.tranxortrider.deliveryrider

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tranxortrider.deliveryrider.adapters.DeliveryHistoryAdapter
import com.tranxortrider.deliveryrider.models.DeliveryHistoryItem
import com.tranxortrider.deliveryrider.models.OrderStatus
import com.tranxortrider.deliveryrider.services.FirestoreService
import com.tranxortrider.deliveryrider.utils.NetworkUtils
import com.tranxortrider.deliveryrider.utils.SessionManager
import com.tranxortrider.deliveryrider.utils.UIUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class DeliveryHistoryActivity : AppCompatActivity() {
    
    // UI components
    private lateinit var backButton: MaterialButton
    private lateinit var chipAll: Chip
    private lateinit var chipCompleted: Chip
    private lateinit var chipCancelled: Chip
    private lateinit var chipFailed: Chip
    private lateinit var dateRangeButton: Button
    private lateinit var sortButton: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var loadingView: View
    private lateinit var errorView: View
    private lateinit var errorMessageText: TextView
    private lateinit var retryButton: Button
    private lateinit var emptyStateView: View
    
    // Adapters
    private lateinit var deliveryHistoryAdapter: DeliveryHistoryAdapter
    
    // Services
    private val firestoreService = FirestoreService()
    private lateinit var sessionManager: SessionManager
    
    // Data
    private var allDeliveries = mutableListOf<DeliveryHistoryItem>()
    private var filteredDeliveries = mutableListOf<DeliveryHistoryItem>()
    
    // Filters
    private var currentStatus: OrderStatus? = null
    private var startDate: Date? = null
    private var endDate: Date? = null
    private var sortOrder = SortOrder.DATE_DESC
    
    // Date formatting
    private val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.US)
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery_history)
        
        // Initialize session manager
        sessionManager = SessionManager(this)
        
        // Initialize UI components
        initViews()
        setupListeners()
        
        // Set default date range (last 30 days)
        setDefaultDateRange()
        
        // Load data
        loadDeliveryHistory()
    }
    
    private fun initViews() {
        // Buttons
        backButton = findViewById(R.id.btnBack)
        dateRangeButton = findViewById(R.id.btnDateRange)
        sortButton = findViewById(R.id.btnSort)
        retryButton = findViewById(R.id.retryButton)
        
        // Chips
        chipAll = findViewById(R.id.chipAll)
        chipCompleted = findViewById(R.id.chipCompleted)
        chipCancelled = findViewById(R.id.chipCancelled)
        chipFailed = findViewById(R.id.chipFailed)
        
        // Views
        loadingView = findViewById(R.id.loadingView)
        errorView = findViewById(R.id.errorView)
        errorMessageText = findViewById(R.id.errorMessageText)
        emptyStateView = findViewById(R.id.emptyStateView)
        
        // RecyclerView
        recyclerView = findViewById(R.id.deliveryHistoryRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        deliveryHistoryAdapter = DeliveryHistoryAdapter(emptyList())
        recyclerView.adapter = deliveryHistoryAdapter
    }
    
    private fun setupListeners() {
        // Back button
        backButton.setOnClickListener { onBackPressed() }
        
        // Retry button
        retryButton.setOnClickListener { loadDeliveryHistory() }
        
        // Filter chips
        chipAll.setOnClickListener {
            currentStatus = null
            applyFilters()
        }
        
        chipCompleted.setOnClickListener {
            currentStatus = OrderStatus.COMPLETED
            applyFilters()
        }
        
        chipCancelled.setOnClickListener {
            currentStatus = OrderStatus.CANCELLED
            applyFilters()
        }
        
        chipFailed.setOnClickListener {
            currentStatus = OrderStatus.FAILED
            applyFilters()
        }
        
        // Date range button
        dateRangeButton.setOnClickListener {
            showDateRangeDialog()
        }
        
        // Sort button
        sortButton.setOnClickListener {
            showSortDialog()
        }
    }
    
    private fun setDefaultDateRange() {
        val calendar = Calendar.getInstance()
        endDate = calendar.time
        
        calendar.add(Calendar.DAY_OF_MONTH, -30)
        startDate = calendar.time
        
        updateDateRangeButtonText()
    }
    
    private fun updateDateRangeButtonText() {
        val start = startDate?.let { dateFormat.format(it) } ?: "Any"
        val end = endDate?.let { dateFormat.format(it) } ?: "Any"
        dateRangeButton.text = "$start - $end"
    }
    
    private fun showDateRangeDialog() {
        val dateRangeOptions = arrayOf(
            "All Time",
            "Today",
            "Yesterday",
            "Last 7 Days",
            "Last 30 Days",
            "This Month",
            "Last Month",
            "Custom Range"
        )
        
        MaterialAlertDialogBuilder(this)
            .setTitle("Select Date Range")
            .setItems(dateRangeOptions) { dialog, which ->
                when (which) {
                    0 -> {
                        // All time
                        startDate = null
                        endDate = null
                    }
                    1 -> {
                        // Today
                        val calendar = Calendar.getInstance()
                        endDate = calendar.time
                        
                        calendar.set(Calendar.HOUR_OF_DAY, 0)
                        calendar.set(Calendar.MINUTE, 0)
                        calendar.set(Calendar.SECOND, 0)
                        startDate = calendar.time
                    }
                    2 -> {
                        // Yesterday
                        val calendar = Calendar.getInstance()
                        calendar.add(Calendar.DAY_OF_MONTH, -1)
                        endDate = calendar.time
                        
                        calendar.set(Calendar.HOUR_OF_DAY, 0)
                        calendar.set(Calendar.MINUTE, 0)
                        calendar.set(Calendar.SECOND, 0)
                        startDate = calendar.time
                    }
                    3 -> {
                        // Last 7 days
                        val calendar = Calendar.getInstance()
                        endDate = calendar.time
                        
                        calendar.add(Calendar.DAY_OF_MONTH, -7)
                        startDate = calendar.time
                    }
                    4 -> {
                        // Last 30 days
                        val calendar = Calendar.getInstance()
                        endDate = calendar.time
                        
                        calendar.add(Calendar.DAY_OF_MONTH, -30)
                        startDate = calendar.time
                    }
                    5 -> {
                        // This month
                        val calendar = Calendar.getInstance()
                        endDate = calendar.time
                        
                        calendar.set(Calendar.DAY_OF_MONTH, 1)
                        startDate = calendar.time
                    }
                    6 -> {
                        // Last month
                        val calendar = Calendar.getInstance()
                        calendar.set(Calendar.DAY_OF_MONTH, 1)
                        calendar.add(Calendar.DAY_OF_MONTH, -1)
                        endDate = calendar.time
                        
                        calendar.set(Calendar.DAY_OF_MONTH, 1)
                        startDate = calendar.time
                    }
                    7 -> {
                        // Custom range
                        showCustomDateRangePicker()
                        return@setItems
                    }
                }
                
                updateDateRangeButtonText()
                applyFilters()
                dialog.dismiss()
            }
            .show()
    }
    
    private fun showCustomDateRangePicker() {
        val startCalendar = Calendar.getInstance()
        startDate?.let { startCalendar.time = it }
        
        // Show start date picker
        DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                startCalendar.set(Calendar.YEAR, year)
                startCalendar.set(Calendar.MONTH, month)
                startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                startDate = startCalendar.time
                
                // After picking start date, show end date picker
                val endCalendar = Calendar.getInstance()
                endDate?.let { endCalendar.time = it }
                
                DatePickerDialog(
                    this,
                    { _, endYear, endMonth, endDayOfMonth ->
                        endCalendar.set(Calendar.YEAR, endYear)
                        endCalendar.set(Calendar.MONTH, endMonth)
                        endCalendar.set(Calendar.DAY_OF_MONTH, endDayOfMonth)
                        endDate = endCalendar.time
                        
                        updateDateRangeButtonText()
                        applyFilters()
                    },
                    endCalendar.get(Calendar.YEAR),
                    endCalendar.get(Calendar.MONTH),
                    endCalendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            },
            startCalendar.get(Calendar.YEAR),
            startCalendar.get(Calendar.MONTH),
            startCalendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
    
    private fun showSortDialog() {
        val sortOptions = arrayOf(
            "Date (Newest First)",
            "Date (Oldest First)",
            "Amount (High to Low)",
            "Amount (Low to High)",
            "Earnings (High to Low)",
            "Earnings (Low to High)"
        )
        
        MaterialAlertDialogBuilder(this)
            .setTitle("Sort By")
            .setSingleChoiceItems(sortOptions, sortOrder.ordinal) { dialog, which ->
                sortOrder = SortOrder.values()[which]
                applyFilters()
                dialog.dismiss()
            }
            .show()
    }
    
    private fun loadDeliveryHistory() {
        showLoading(true)
        
        // Check if user is logged in
        val user = sessionManager.getUser()
        if (user == null) {
            showError("You need to be logged in")
            return
        }
        
        // Check network connection
        if (!NetworkUtils.isNetworkAvailable(this)) {
            showError("No internet connection")
            return
        }
        
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = firestoreService.getDeliveryHistory(user.id)
                
                withContext(Dispatchers.Main) {
                    if (result.isSuccess) {
                        allDeliveries = result.getOrDefault(emptyList()).toMutableList()
                        applyFilters()
                        showLoading(false)
                    } else {
                        showError(result.exceptionOrNull()?.message ?: "Failed to load delivery history")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showError("An error occurred: ${e.message}")
                }
            }
        }
    }
    
    private fun applyFilters() {
        filteredDeliveries = allDeliveries.toMutableList()
        
        // Apply status filter
        currentStatus?.let { status ->
            filteredDeliveries = filteredDeliveries.filter { it.status == status }.toMutableList()
        }
        
        // Apply date range filter
        if (startDate != null && endDate != null) {
            filteredDeliveries = filteredDeliveries.filter { delivery ->
                delivery.date.after(startDate) && delivery.date.before(endDate)
            }.toMutableList()
        }
        
        // Apply sorting
        filteredDeliveries = when (sortOrder) {
            SortOrder.DATE_DESC -> filteredDeliveries.sortedByDescending { it.date }
            SortOrder.DATE_ASC -> filteredDeliveries.sortedBy { it.date }
            SortOrder.AMOUNT_DESC -> filteredDeliveries.sortedByDescending { it.amount }
            SortOrder.AMOUNT_ASC -> filteredDeliveries.sortedBy { it.amount }
            SortOrder.EARNING_DESC -> filteredDeliveries.sortedByDescending { it.earning }
            SortOrder.EARNING_ASC -> filteredDeliveries.sortedBy { it.earning }
        }.toMutableList()
        
        // Update adapter
        deliveryHistoryAdapter.updateItems(filteredDeliveries)
        
        // Show/hide empty state
        if (filteredDeliveries.isEmpty()) {
            if (allDeliveries.isEmpty()) {
                showEmptyState(true)
            } else {
                showEmptyState(true, "No deliveries match your filters")
            }
        } else {
            showEmptyState(false)
        }
    }
    
    private fun showLoading(isLoading: Boolean) {
        loadingView.visibility = if (isLoading) View.VISIBLE else View.GONE
        recyclerView.visibility = if (isLoading) View.GONE else View.VISIBLE
        errorView.visibility = View.GONE
        emptyStateView.visibility = View.GONE
    }
    
    private fun showError(message: String) {
        loadingView.visibility = View.GONE
        recyclerView.visibility = View.GONE
        errorView.visibility = View.VISIBLE
        emptyStateView.visibility = View.GONE
        
        errorMessageText.text = message
    }
    
    private fun showEmptyState(isEmpty: Boolean, message: String? = null) {
        emptyStateView.visibility = if (isEmpty) View.VISIBLE else View.GONE
        recyclerView.visibility = if (isEmpty) View.GONE else View.VISIBLE
        
        if (message != null && isEmpty) {
            val emptyMessageText = findViewById<TextView>(R.id.emptyMessageText)
            emptyMessageText.text = message
        }
    }
    
    enum class SortOrder {
        DATE_DESC,
        DATE_ASC,
        AMOUNT_DESC,
        AMOUNT_ASC,
        EARNING_DESC,
        EARNING_ASC
    }
} 