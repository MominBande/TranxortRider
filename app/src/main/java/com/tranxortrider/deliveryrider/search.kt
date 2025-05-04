package com.tranxortrider.deliveryrider

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.tranxortrider.deliveryrider.adapters.SearchResultsAdapter
import com.tranxortrider.deliveryrider.models.SearchResult
import com.tranxortrider.deliveryrider.models.SearchResultType
import com.tranxortrider.deliveryrider.repositories.OrderRepository
import com.tranxortrider.deliveryrider.utils.UIUtils

class search : AppCompatActivity() {
    
    // UI elements
    private lateinit var searchInput: EditText
    private lateinit var backButton: MaterialButton
    
    // Views
    private lateinit var emptySearchesContainer: View
    
    // Adapters
    private lateinit var searchAdapter: SearchResultsAdapter
    
    // Repositories
    private lateinit var orderRepository: OrderRepository
    
    // State variables
    private var currentSearchQuery: String = ""
    private var currentSearchFilter: SearchResultType = SearchResultType.ALL
    private var searchDebounceHandler = Handler(Looper.getMainLooper())
    private var searchDebounceRunnable: Runnable? = null
    private var recentSearches: MutableList<SearchResult> = mutableListOf()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search)
        
        // Initialize dependencies
        orderRepository = OrderRepository()
        
        // Initialize UI elements
        initializeUI()
        
        // Set up search and filter listeners
        setupListeners()
        
        // Set up initial data
        loadRecentSearches()
        
        // Give focus to the search bar and show keyboard
        searchInput.requestFocus()
    }
    
    private fun initializeUI() {
        try {
            // Find search components
            searchInput = findViewById(R.id.etSearch)
            backButton = findViewById(R.id.btnBack)
            
            // Find container views
            emptySearchesContainer = findViewById(R.id.emptySearchesContainer)
            
            // Ensure the search input is visible and properly styled
            searchInput.visibility = View.VISIBLE
            searchInput.hint = "Search orders, screens, and more..."
            
            // Set drawable tint programmatically
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                searchInput.compoundDrawableTintList = android.content.res.ColorStateList.valueOf(resources.getColor(android.R.color.darker_gray, theme))
            }
            
            Log.d("SearchActivity", "UI components initialized successfully")
        } catch (e: Exception) {
            Log.e("SearchActivity", "Error initializing UI components", e)
            UIUtils.showToast(this, "Error initializing search UI")
        }
    }
    
    private fun setupListeners() {
        // Back button
        backButton.setOnClickListener {
            finish()
        }
        
        // Search input text change
        searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            
            override fun afterTextChanged(s: Editable?) {
                val query = s?.toString()?.trim() ?: ""
                currentSearchQuery = query
                
                // Debounce search
                searchDebounceRunnable?.let { searchDebounceHandler.removeCallbacks(it) }
                if (query.isEmpty()) {
                    showRecentSearches()
                } else {
                    searchDebounceRunnable = Runnable {
                        performSearch(query)
                    }
                    searchDebounceHandler.postDelayed(searchDebounceRunnable!!, 300)
                }
            }
        })
    }
    
    private fun performSearch(query: String) {
        if (query.isEmpty()) return
        
        // Show progress (in a real app, we'd show a loading spinner)
        UIUtils.showToast(this, "Searching for: $query")
        
        // Call repository to search
        orderRepository.search(query, com.tranxortrider.deliveryrider.repositories.SearchResultType.ALL) { success, message, results ->
            runOnUiThread {
                if (success && results != null && results.isNotEmpty()) {
                    displaySearchResults(results)
                } else {
                    showEmptySearchState()
                }
                
                // Save search query to recent searches
                saveRecentSearch(query)
            }
        }
    }
    
    private fun displaySearchResults(results: List<Any>) {
        // Hide empty state container
        emptySearchesContainer.visibility = View.GONE
        
        // Show toast with results count
        UIUtils.showToast(this, "Found ${results.size} results")
    }
    
    private fun showEmptySearchState() {
        // Show empty container
        emptySearchesContainer.visibility = View.VISIBLE
    }
    
    private fun saveRecentSearch(query: String) {
        // Implementation to save recent search
    }
    
    private fun showRecentSearches() {
        // Always show empty container for now since we don't have real search functionality
        emptySearchesContainer.visibility = View.VISIBLE
    }
    
    private fun loadRecentSearches() {
        // In a real app, this would load from SharedPreferences or database
        // For now, show empty state
        showRecentSearches()
    }
}