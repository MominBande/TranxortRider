package com.tranxortrider.deliveryrider.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tranxortrider.deliveryrider.R
import com.tranxortrider.deliveryrider.models.Order
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Adapter for displaying completed orders in a RecyclerView
 */
class CompletedOrdersAdapter(
    private val orders: List<Order>,
    private val onItemClick: (Order) -> Unit
) : RecyclerView.Adapter<CompletedOrdersAdapter.OrderViewHolder>() {
    
    private val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US)
    private val dateFormatter = SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.US)
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_completed_order, parent, false)
        return OrderViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        holder.bind(order)
    }
    
    override fun getItemCount(): Int = orders.size
    
    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val orderIdText: TextView = itemView.findViewById(R.id.tvOrderId)
        private val customerNameText: TextView = itemView.findViewById(R.id.tvCustomerName)
        private val restaurantNameText: TextView = itemView.findViewById(R.id.tvRestaurantName)
        private val deliveryAddressText: TextView = itemView.findViewById(R.id.tvAddressText)
        private val totalAmountText: TextView = itemView.findViewById(R.id.tvTotalAmount)
        private val completedTimeText: TextView = itemView.findViewById(R.id.tvCompletedTime)
        private val earnedAmountText: TextView = itemView.findViewById(R.id.tvEarnedAmount)
        private val statusBadge: TextView = itemView.findViewById(R.id.tvOrderStatus)
        
        fun bind(order: Order) {
            // Set order details
            orderIdText.text = "Order #${order.orderId}"
            customerNameText.text = order.customerName
            
            // Use null-safe operations for views that might be missing
            restaurantNameText.text = order.restaurantName
            deliveryAddressText.text = order.customerAddress
            totalAmountText.text = currencyFormatter.format(order.totalAmount)
            
            // Format and set completed time
            val completedTime = order.deliveredAt ?: order.createdAt
            completedTimeText.text = dateFormatter.format(completedTime)
            
            // Set earned amount (delivery fee)
            earnedAmountText.text = "Earned: ${currencyFormatter.format(order.deliveryFee)}"
            
            // Set status badge based on order status
            statusBadge.text = "Completed"
            statusBadge.background = ContextCompat.getDrawable(itemView.context, R.drawable.status_badge_success_background)
            statusBadge.setTextColor(ContextCompat.getColor(itemView.context, R.color.aesthgreen))
            
            // Set item click listener
            itemView.setOnClickListener { onItemClick(order) }
        }
    }
} 