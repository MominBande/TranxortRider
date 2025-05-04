package com.tranxortrider.deliveryrider.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tranxortrider.deliveryrider.R
import com.tranxortrider.deliveryrider.models.EarningItem
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

class EarningsHistoryAdapter(
    private var items: List<EarningItem>
) : RecyclerView.Adapter<EarningsHistoryAdapter.ViewHolder>() {

    private val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US)
    private val dateFormatter = SimpleDateFormat("MMM dd, yyyy", Locale.US)
    
    fun updateItems(newItems: List<EarningItem>) {
        this.items = newItems
        notifyDataSetChanged()
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_earning_history, parent, false)
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
        private val amountText: TextView = itemView.findViewById(R.id.amountText)
        private val statusText: TextView = itemView.findViewById(R.id.statusText)

        fun bind(item: EarningItem) {
            dateText.text = dateFormatter.format(item.date)
            orderIdText.text = "Order #${item.orderId}"
            amountText.text = currencyFormatter.format(item.amount)
            
            // Set status text and color
            statusText.text = item.status
            val statusColor = when (item.status.uppercase()) {
                "COMPLETED" -> R.color.aesthgreen
                "PENDING" -> R.color.yellow
                else -> R.color.gray
            }
            statusText.setTextColor(itemView.context.getColor(statusColor))
        }
    }
} 