package com.tranxortrider.deliveryrider.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tranxortrider.deliveryrider.R
import com.tranxortrider.deliveryrider.models.DeliveryHistoryItem
import com.tranxortrider.deliveryrider.models.OrderStatus
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

class DeliveryHistoryAdapter(
    private var items: List<DeliveryHistoryItem>
) : RecyclerView.Adapter<DeliveryHistoryAdapter.ViewHolder>() {

    private val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US)
    private val dateFormatter = SimpleDateFormat("MMM dd, yyyy - hh:mm a", Locale.US)
    
    fun updateItems(newItems: List<DeliveryHistoryItem>) {
        this.items = newItems
        notifyDataSetChanged()
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_delivery_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dateText: TextView = itemView.findViewById(R.id.dateText)
        private val orderIdText: TextView = itemView.findViewById(R.id.orderIdText)
        private val customerText: TextView = itemView.findViewById(R.id.customerText)
        private val restaurantText: TextView = itemView.findViewById(R.id.restaurantText)
        private val amountText: TextView = itemView.findViewById(R.id.amountText)
        private val earningText: TextView = itemView.findViewById(R.id.earningText)
        private val statusText: TextView = itemView.findViewById(R.id.statusText)
        private val deliveryTimeText: TextView = itemView.findViewById(R.id.deliveryTimeText)
        private val distanceText: TextView = itemView.findViewById(R.id.distanceText)
        private val rootCardView: CardView = itemView.findViewById(R.id.deliveryCardView)

        fun bind(item: DeliveryHistoryItem) {
            dateText.text = dateFormatter.format(item.date)
            orderIdText.text = "Order #${item.orderId}"
            customerText.text = item.customerName
            restaurantText.text = item.restaurantName
            amountText.text = currencyFormatter.format(item.amount)
            earningText.text = currencyFormatter.format(item.earning)
            deliveryTimeText.text = "${item.deliveryTime} min"
            distanceText.text = String.format("%.1f km", item.distance)
            
            // Set status text and color
            statusText.text = when (item.status) {
                OrderStatus.PENDING -> "Pending"
                OrderStatus.ASSIGNED -> "Assigned"
                OrderStatus.ACCEPTED -> "Accepted"
                OrderStatus.PICKED_UP -> "In Transit"
                OrderStatus.IN_TRANSIT -> "In Transit"
                OrderStatus.COMPLETED -> "Completed"
                OrderStatus.CANCELLED -> "Cancelled"
                OrderStatus.FAILED -> "Failed"
            }
            
            val statusColor = when (item.status) {
                OrderStatus.COMPLETED -> R.color.aesthgreen
                OrderStatus.CANCELLED, OrderStatus.FAILED -> R.color.red
                OrderStatus.PICKED_UP, OrderStatus.ACCEPTED, OrderStatus.IN_TRANSIT, OrderStatus.ASSIGNED -> R.color.blue
                OrderStatus.PENDING -> R.color.yellow
            }
            
            statusText.setTextColor(ContextCompat.getColor(itemView.context, statusColor))
            
            // Set click listener to open order details
            rootCardView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, com.tranxortrider.deliveryrider.order_details::class.java)
                intent.putExtra("ORDER_ID", item.id)
                context.startActivity(intent)
            }
        }
    }
} 