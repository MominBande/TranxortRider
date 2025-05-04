package com.tranxortrider.deliveryrider.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tranxortrider.deliveryrider.R
import com.tranxortrider.deliveryrider.models.Order
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Adapter for displaying pending orders in a RecyclerView
 */
class PendingOrdersAdapter(
    private var orders: List<Order>,
    private val onAcceptClick: (Order) -> Unit,
    private val onRejectClick: (Order) -> Unit,
    private val onItemClick: (Order) -> Unit
) : RecyclerView.Adapter<PendingOrdersAdapter.OrderViewHolder>() {
    
    private val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US)
    private val dateFormatter = SimpleDateFormat("MMM dd, hh:mm a", Locale.US)
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pending_order, parent, false)
        return OrderViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        holder.bind(order)
    }
    
    override fun getItemCount(): Int = orders.size
    
    /**
     * Updates the orders list and refreshes the adapter
     */
    fun updateOrders(newOrders: List<Order>) {
        orders = newOrders
        notifyDataSetChanged()
    }
    
    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val orderIdText: TextView = itemView.findViewById(R.id.orderIdText)
        private val restaurantNameText: TextView? = itemView.findViewById(R.id.restaurantNameText)
        private val restaurantAddressText: TextView? = itemView.findViewById(R.id.restaurantAddressText)
        private val customerAddressText: TextView? = itemView.findViewById(R.id.customerAddressText)
        private val orderAmountText: TextView? = itemView.findViewById(R.id.orderAmountText)
        private val deliveryFeeText: TextView? = itemView.findViewById(R.id.deliveryFeeText)
        private val distanceText: TextView? = itemView.findViewById(R.id.distanceText)
        private val estimatedTimeText: TextView? = itemView.findViewById(R.id.estimatedTimeText)
        private val timeText: TextView? = itemView.findViewById(R.id.timeText)
        
        fun bind(order: Order) {
            // Set order details
            orderIdText.text = "Order #${order.orderId}"
            restaurantNameText?.text = order.restaurantName
            restaurantAddressText?.text = order.restaurantAddress
            customerAddressText?.text = "Deliver to: ${order.customerAddress}"
            orderAmountText?.text = "Order: ${currencyFormatter.format(order.totalAmount)}"
            deliveryFeeText?.text = "Fee: ${currencyFormatter.format(order.deliveryFee)}"
            distanceText?.text = "${String.format("%.1f", order.distance)} km"
            estimatedTimeText?.text = "${order.estimatedDeliveryTime} min"
            timeText?.text = "Created: ${dateFormatter.format(order.createdAt)}"
            
            // Set item click listener
            itemView.setOnClickListener { onItemClick(order) }
        }
    }
} 