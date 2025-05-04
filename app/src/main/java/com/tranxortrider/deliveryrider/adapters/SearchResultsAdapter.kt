package com.tranxortrider.deliveryrider.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.tranxortrider.deliveryrider.R
import com.tranxortrider.deliveryrider.models.SearchResult
import com.tranxortrider.deliveryrider.models.SearchResultType

/**
 * Adapter for displaying search results in a RecyclerView
 */
class SearchResultsAdapter(private var results: List<SearchResult>) :
    RecyclerView.Adapter<SearchResultsAdapter.SearchResultViewHolder>() {
    
    private var onItemClickListener: ((SearchResult) -> Unit)? = null
    
    /**
     * Update the results list and refresh the adapter
     */
    fun updateResults(newResults: List<SearchResult>) {
        results = newResults
        notifyDataSetChanged()
    }
    
    /**
     * Set click listener for item clicks
     */
    fun setOnItemClickListener(listener: (SearchResult) -> Unit) {
        onItemClickListener = listener
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search_result, parent, false)
        return SearchResultViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        val result = results[position]
        holder.bind(result)
    }
    
    override fun getItemCount(): Int = results.size
    
    inner class SearchResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cardView: MaterialCardView = itemView.findViewById(R.id.cardView)
        private val iconView: ImageView = itemView.findViewById(R.id.iconView)
        private val titleText: TextView = itemView.findViewById(R.id.titleText)
        private val subtitleText: TextView = itemView.findViewById(R.id.subtitleText)
        private val typeChip: TextView = itemView.findViewById(R.id.typeChip)
        
        fun bind(result: SearchResult) {
            titleText.text = result.title
            subtitleText.text = result.subtitle
            
            // Set type chip text and color based on result type
            when (result.type) {
                SearchResultType.ORDER -> {
                    typeChip.text = "Order"
                    typeChip.setBackgroundResource(R.drawable.chip_order_background)
                    iconView.setImageResource(R.drawable.ic_order)
                }
                SearchResultType.RESTAURANT -> {
                    typeChip.text = "Restaurant"
                    typeChip.setBackgroundResource(R.drawable.chip_restaurant_background)
                    iconView.setImageResource(R.drawable.ic_restaurant)
                }
                SearchResultType.CUSTOMER -> {
                    typeChip.text = "Customer"
                    typeChip.setBackgroundResource(R.drawable.chip_customer_background)
                    iconView.setImageResource(R.drawable.ic_person)
                }
                SearchResultType.ADDRESS -> {
                    typeChip.text = "Address"
                    typeChip.setBackgroundResource(R.drawable.chip_address_background)
                    iconView.setImageResource(R.drawable.ic_location)
                }
                SearchResultType.ALL -> {
                    typeChip.text = "All"
                    typeChip.setBackgroundResource(R.drawable.chip_default_background)
                    iconView.setImageResource(R.drawable.ic_info)
                }
            }
            
            // Set click listener
            cardView.setOnClickListener {
                onItemClickListener?.invoke(result)
            }
        }
    }
} 