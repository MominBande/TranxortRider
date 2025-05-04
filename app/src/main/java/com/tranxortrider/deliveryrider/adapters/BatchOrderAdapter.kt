package com.tranxortrider.deliveryrider.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.tranxortrider.deliveryrider.R
import com.tranxortrider.deliveryrider.models.Order

/**
 * Adapter for displaying orders in a batch
 */
class BatchOrderAdapter(private var orders: List<Order>) : 
    RecyclerView.Adapter<BatchOrderAdapter.BatchOrderViewHolder>() {
    
    private var onItemClickListener: ((Order) -> Unit)? = null
    private var onRemoveClickListener: ((Order) -> Unit)? = null
    
    /**
     * Update the orders list and refresh the adapter
     */
    fun updateOrders(newOrders: List<Order>) {
        orders = newOrders
        notifyDataSetChanged()
    }
    
    /**
     * Set click listener for item clicks
     */
    fun setOnItemClickListener(listener: (Order) -> Unit) {
        onItemClickListener = listener
    }
    
    /**
     * Set click listener for remove button clicks
     */
    fun setOnRemoveClickListener(listener: (Order) -> Unit) {
        onRemoveClickListener = listener
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BatchOrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_batch_order, parent, false)
        return BatchOrderViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: BatchOrderViewHolder, position: Int) {
        val order = orders[position]
        holder.bind(order, position + 1)
    }
    
    override fun getItemCount(): Int = orders.size
    
    inner class BatchOrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val orderCard: MaterialCardView = itemView.findViewById(R.id.orderCard)
        private val tvOrderNumber: TextView = itemView.findViewById(R.id.tvOrderNumber)
        private val tvRestaurantName: TextView = itemView.findViewById(R.id.tvRestaurantName)
        private val tvOrderInfo: TextView = itemView.findViewById(R.id.tvOrderInfo)
        private val tvSequence: TextView = itemView.findViewById(R.id.tvSequence)
        private val btnRemove: ImageButton = itemView.findViewById(R.id.btnRemove)
        
        fun bind(order: Order, position: Int) {
            // Set values
            tvOrderNumber.text = "Order #${order.orderId}"
            tvRestaurantName.text = order.restaurantName
            tvOrderInfo.text = "${order.items.size} items • ${order.customerName}"
            
            // Set sequence indicator
            tvSequence.text = "$position"
            tvSequence.visibility = View.VISIBLE
            
            // Set click listeners
            orderCard.setOnClickListener {
                onItemClickListener?.invoke(order)
            }
            
            btnRemove.setOnClickListener {
                onRemoveClickListener?.invoke(order)
            }
        }
    }
} 