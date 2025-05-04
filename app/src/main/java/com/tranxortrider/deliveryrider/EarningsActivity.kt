package com.tranxortrider.deliveryrider

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.firebase.auth.FirebaseAuth
import com.tranxortrider.deliveryrider.adapters.EarningsHistoryAdapter
import com.tranxortrider.deliveryrider.models.EarningItem
import com.tranxortrider.deliveryrider.services.FirestoreService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class EarningsActivity : AppCompatActivity() {
    
    // UI Components
    private lateinit var totalEarningsText: TextView
    private lateinit var todayEarningsText: TextView
    private lateinit var pendingEarningsText: TextView
    private lateinit var weeklyEarningsText: TextView
    private lateinit var weeklyChart: LineChart
    private lateinit var earningsRecyclerView: RecyclerView
    private lateinit var loadingView: View
    private lateinit var errorView: View
    private lateinit var contentView: ConstraintLayout
    
    // Services
    private val firestoreService = FirestoreService()
    private val auth = FirebaseAuth.getInstance()
    
    // Data
    private val earningsAdapter = EarningsHistoryAdapter(emptyList())
    private val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_earnings)

        // Initialize views
        initializeViews()
        
        // Load data
        loadEarningsData()
    }
    
    private fun initializeViews() {
        // TextViews for earnings stats
        totalEarningsText = findViewById(R.id.totalEarningsText)
        todayEarningsText = findViewById(R.id.todayEarningsText)
        pendingEarningsText = findViewById(R.id.pendingEarningsText)
        weeklyEarningsText = findViewById(R.id.weeklyEarningsText)
        
        // Chart
        weeklyChart = findViewById(R.id.weeklyEarningsChart)
        setupChart()
        
        // RecyclerView for earnings history
        earningsRecyclerView = findViewById(R.id.earningsHistoryRecyclerView)
        earningsRecyclerView.layoutManager = LinearLayoutManager(this)
        earningsRecyclerView.adapter = earningsAdapter
        
        // State views
        loadingView = findViewById(R.id.loadingView)
        errorView = findViewById(R.id.errorView)
        contentView = findViewById(R.id.contentView)
        
        // Set up bottom navigation
        setupBottomNavigation()
    }
    
    private fun setupChart() {
        weeklyChart.description.isEnabled = false
        weeklyChart.legend.isEnabled = false
        weeklyChart.setDrawGridBackground(false)
        weeklyChart.setPinchZoom(false)
        weeklyChart.setScaleEnabled(false)
        
        val xAxis = weeklyChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f
        
        weeklyChart.axisLeft.setDrawGridLines(false)
        weeklyChart.axisRight.isEnabled = false
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
    
    private fun loadEarningsData() {
        showLoading(true)
        
        val currentUser = auth.currentUser
        if (currentUser == null) {
            showError("User not logged in")
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Get current week's earnings
                val weeklyEarningsResult = firestoreService.getWeeklyEarnings()
                
                // Get total earnings
                val totalEarningsResult = firestoreService.getTotalEarnings()
                
                // Get today's earnings
                val todayEarningsResult = firestoreService.getTodayEarnings()
                
                // Get pending order earnings
                val pendingEarningsResult = firestoreService.getPendingEarnings()
                
                // Get earnings history
                val earningsHistoryResult = firestoreService.getEarningsHistory(10)
                
                withContext(Dispatchers.Main) {
                    if (weeklyEarningsResult.isSuccess && 
                        totalEarningsResult.isSuccess && 
                        todayEarningsResult.isSuccess && 
                        pendingEarningsResult.isSuccess && 
                        earningsHistoryResult.isSuccess) {
                        
                        // Update UI with fetched data
                        updateEarningStats(
                            totalEarningsResult.getOrDefault(0.0),
                            todayEarningsResult.getOrDefault(0.0),
                            pendingEarningsResult.getOrDefault(0.0),
                            weeklyEarningsResult.getOrDefault(mapOf<String, Double>())
                        )
                        
                        // Update earnings history
                        val earnings = earningsHistoryResult.getOrDefault(emptyList())
                        // Convert Earning objects to EarningItem objects
                        val earningItems = earnings.map { earning ->
                            EarningItem(
                                id = earning.id,
                                orderId = earning.orderId,
                                amount = earning.amount,
                                date = earning.date,
                                status = "Completed",
                                customerName = null,
                                restaurantName = earning.description
                            )
                        }
                        updateEarningsHistory(earningItems)
                        
                        showLoading(false)
                    } else {
                        showError("Failed to load earnings data")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showError("An error occurred: ${e.message}")
                }
            }
        }
    }
    
    private fun updateEarningStats(
        totalEarnings: Double, 
        todayEarnings: Double,
        pendingEarnings: Double,
        weeklyEarnings: Map<String, Double>
    ) {
        // Set text for earnings stats
        totalEarningsText.text = currencyFormatter.format(totalEarnings)
        todayEarningsText.text = currencyFormatter.format(todayEarnings)
        pendingEarningsText.text = currencyFormatter.format(pendingEarnings)
        
        // Calculate weekly total
        val weeklyTotal = weeklyEarnings.values.sum()
        weeklyEarningsText.text = currencyFormatter.format(weeklyTotal)
        
        // Update chart with weekly data
        updateWeeklyChart(weeklyEarnings)
    }
    
    private fun updateWeeklyChart(weeklyEarnings: Map<String, Double>) {
        val calendar = Calendar.getInstance()
        val dayLabels = arrayListOf<String>()
        val entries = arrayListOf<Entry>()
        
        // Get current day of week (0 = Sunday, 1 = Monday, etc.)
        val currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1
        
        for (i in 0 until 7) {
            calendar.set(Calendar.DAY_OF_WEEK, i + 1) // Set to each day of week
            val dayFormat = SimpleDateFormat("EEE", Locale.getDefault())
            val dayLabel = dayFormat.format(calendar.time)
            dayLabels.add(dayLabel)
            
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val dateKey = dateFormat.format(calendar.time)
            
            val value = weeklyEarnings[dateKey] ?: 0.0
            entries.add(Entry(i.toFloat(), value.toFloat()))
        }
        
        // Create dataset and styling
        val dataSet = LineDataSet(entries, "Weekly Earnings")
        dataSet.color = resources.getColor(R.color.aesthgreen, null)
        dataSet.lineWidth = 2f
        dataSet.setCircleColor(resources.getColor(R.color.aesthgreen, null))
        dataSet.circleRadius = 4f
        dataSet.setDrawCircleHole(false)
        dataSet.setDrawValues(true)
        dataSet.valueTextSize = 10f
        dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        
        val data = LineData(dataSet)
        data.setValueTextSize(10f)
        
        // Update chart
        weeklyChart.xAxis.valueFormatter = IndexAxisValueFormatter(dayLabels)
        weeklyChart.data = data
        weeklyChart.invalidate()
    }
    
    private fun updateEarningsHistory(earningsHistory: List<EarningItem>) {
        earningsAdapter.updateItems(earningsHistory)
    }
    
    private fun showLoading(isLoading: Boolean) {
        loadingView.visibility = if (isLoading) View.VISIBLE else View.GONE
        contentView.visibility = if (isLoading) View.GONE else View.VISIBLE
        errorView.visibility = View.GONE
    }
    
    private fun showError(message: String) {
        loadingView.visibility = View.GONE
        contentView.visibility = View.GONE
        errorView.visibility = View.VISIBLE
        
        val errorMessageText = findViewById<TextView>(R.id.errorMessageText)
        errorMessageText.text = message
        
        val retryButton = findViewById<Button>(R.id.retryButton)
        retryButton.setOnClickListener {
            loadEarningsData()
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
} 