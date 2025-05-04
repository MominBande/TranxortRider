package com.tranxortrider.deliveryrider

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.android.material.button.MaterialButton
import com.tranxortrider.deliveryrider.adapters.FeedbackAdapter
import com.tranxortrider.deliveryrider.models.FeedbackItem
import com.tranxortrider.deliveryrider.services.FirestoreService
import com.tranxortrider.deliveryrider.utils.NetworkUtils
import com.tranxortrider.deliveryrider.utils.SessionManager
import com.tranxortrider.deliveryrider.utils.UIUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.Date

class PerformanceActivity : AppCompatActivity() {
    
    // UI Components
    private lateinit var backButton: MaterialButton
    private lateinit var overallRatingText: TextView
    private lateinit var deliveryCompletionRateText: TextView
    private lateinit var averageDeliveryTimeText: TextView
    private lateinit var totalCustomersServedText: TextView
    private lateinit var timePerformanceChart: LineChart
    private lateinit var orderTypesChart: PieChart
    private lateinit var feedbackRecyclerView: RecyclerView
    private lateinit var loadingView: View
    private lateinit var errorView: View
    private lateinit var contentView: CardView
    private lateinit var retryButton: Button
    
    // Adapters
    private lateinit var feedbackAdapter: FeedbackAdapter
    
    // Services
    private val firestoreService = FirestoreService()
    private lateinit var sessionManager: SessionManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_performance)
        
        // Initialize session manager
        sessionManager = SessionManager(this)
        
        // Initialize UI
        initViews()
        setupListeners()
        setupRecyclerView()
        
        // Load data
        loadPerformanceData()
    }
    
    private fun initViews() {
        // Find views
        backButton = findViewById(R.id.btnBack)
        overallRatingText = findViewById(R.id.overallRatingText)
        deliveryCompletionRateText = findViewById(R.id.deliveryCompletionRateText)
        averageDeliveryTimeText = findViewById(R.id.averageDeliveryTimeText)
        totalCustomersServedText = findViewById(R.id.totalCustomersServedText)
        timePerformanceChart = findViewById(R.id.timePerformanceChart)
        orderTypesChart = findViewById(R.id.orderTypesChart)
        feedbackRecyclerView = findViewById(R.id.feedbackRecyclerView)
        loadingView = findViewById(R.id.loadingView)
        errorView = findViewById(R.id.errorView)
        contentView = findViewById(R.id.contentView)
        retryButton = findViewById(R.id.retryButton)
    }
    
    private fun setupListeners() {
        // Back button
        backButton.setOnClickListener {
            onBackPressed()
        }
        
        // Retry button
        retryButton.setOnClickListener {
            loadPerformanceData()
        }
    }
    
    private fun setupRecyclerView() {
        // Initialize feedback adapter with empty list
        feedbackAdapter = FeedbackAdapter(emptyList())
        
        // Setup RecyclerView
        feedbackRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@PerformanceActivity)
            adapter = feedbackAdapter
        }
    }
    
    private fun loadPerformanceData() {
        showLoading(true)
        
        // Check user login status
        val user = sessionManager.getUser()
        if (user == null) {
            showError("Not logged in")
            return
        }
        
        // Check network connectivity
        if (!NetworkUtils.isNetworkAvailable(this)) {
            showError("No internet connection")
            return
        }
        
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Here would be the actual API calls to fetch rider performance data
                // For demo purposes, we'll use mock data
                
                withContext(Dispatchers.Main) {
                    showLoading(false)
                    updateUIWithData()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showError("Error loading performance data: ${e.message}")
                }
            }
        }
    }
    
    private fun updateUIWithData() {
        // Update summary stats
        overallRatingText.text = "4.8"
        deliveryCompletionRateText.text = "94%"
        averageDeliveryTimeText.text = "17 min"
        totalCustomersServedText.text = "254"
        
        // Setup time performance chart
        setupTimePerformanceChart()
        
        // Setup order types chart
        setupOrderTypesChart()
        
        // Update feedback list
        updateFeedbackList()
    }
    
    private fun setupTimePerformanceChart() {
        // Sample data for the last 7 days
        val entries = ArrayList<Entry>()
        val days = ArrayList<String>()
        
        val calendar = Calendar.getInstance()
        for (i in 6 downTo 0) {
            calendar.add(Calendar.DAY_OF_MONTH, -i)
            val avgTime = 15f + (Math.random() * 10 - 5).toFloat() // Random value between 10-20
            entries.add(Entry(6 - i.toFloat(), avgTime))
            
            val dayOfWeek = when (calendar.get(Calendar.DAY_OF_WEEK)) {
                Calendar.MONDAY -> "Mon"
                Calendar.TUESDAY -> "Tue"
                Calendar.WEDNESDAY -> "Wed"
                Calendar.THURSDAY -> "Thu"
                Calendar.FRIDAY -> "Fri"
                Calendar.SATURDAY -> "Sat"
                Calendar.SUNDAY -> "Sun"
                else -> ""
            }
            days.add(dayOfWeek)
            calendar.add(Calendar.DAY_OF_MONTH, i) // Reset calendar
        }
        
        val dataSet = LineDataSet(entries, "Average Delivery Time (min)")
        dataSet.color = resources.getColor(R.color.aesthgreen, theme)
        dataSet.setCircleColor(resources.getColor(R.color.aesthgreen, theme))
        dataSet.lineWidth = 2f
        dataSet.circleRadius = 4f
        dataSet.setDrawCircleHole(false)
        dataSet.setDrawValues(false)
        
        val lineData = LineData(dataSet)
        timePerformanceChart.data = lineData
        
        // Customize chart appearance
        timePerformanceChart.description.isEnabled = false
        timePerformanceChart.legend.isEnabled = false
        timePerformanceChart.axisRight.isEnabled = false
        
        val xAxis = timePerformanceChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f
        xAxis.valueFormatter = IndexAxisValueFormatter(days)
        
        val yAxis = timePerformanceChart.axisLeft
        yAxis.setDrawGridLines(true)
        yAxis.axisMinimum = 0f
        
        timePerformanceChart.invalidate()
    }
    
    private fun setupOrderTypesChart() {
        // Sample data for order types
        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(65f, "Food"))
        entries.add(PieEntry(25f, "Groceries"))
        entries.add(PieEntry(10f, "Other"))
        
        val dataSet = PieDataSet(entries, "Order Types")
        val colors = ArrayList<Int>()
        colors.add(resources.getColor(R.color.aesthgreen, theme))
        colors.add(resources.getColor(R.color.blue, theme))
        colors.add(resources.getColor(R.color.orange, theme))
        dataSet.colors = colors
        
        val data = PieData(dataSet)
        data.setValueTextSize(12f)
        data.setValueTextColor(resources.getColor(R.color.white, theme))
        
        orderTypesChart.data = data
        orderTypesChart.description.isEnabled = false
        orderTypesChart.setCenterText("Order Types")
        orderTypesChart.setCenterTextSize(16f)
        orderTypesChart.setHoleColor(resources.getColor(android.R.color.transparent, theme))
        orderTypesChart.setTransparentCircleAlpha(0)
        orderTypesChart.setUsePercentValues(true)
        
        orderTypesChart.invalidate()
    }
    
    private fun updateFeedbackList() {
        // Sample feedback data
        val feedbackList = listOf(
            FeedbackItem(
                id = "1",
                customerName = "John Smith",
                rating = 5,
                comment = "Very professional and friendly. Food arrived hot!",
                date = Date()
            ),
            FeedbackItem(
                id = "2",
                customerName = "Emma Johnson",
                rating = 4,
                comment = "Good service, but arrived slightly later than expected.",
                date = Date(System.currentTimeMillis() - 86400000)
            ),
            FeedbackItem(
                id = "3",
                customerName = "Michael Brown",
                rating = 5,
                comment = "Perfect delivery! Very satisfied.",
                date = Date(System.currentTimeMillis() - 172800000)
            )
        )
        
        feedbackAdapter.updateItems(feedbackList)
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
    }
} 