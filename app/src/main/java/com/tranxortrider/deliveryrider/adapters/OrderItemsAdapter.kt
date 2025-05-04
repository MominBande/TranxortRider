package com.tranxortrider.deliveryrider.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tranxortrider.deliveryrider.R
import com.tranxortrider.deliveryrider.models.OrderItem
import java.text.NumberFormat
import java.util.Locale

/**
 * Adapter for displaying order items in a RecyclerView
 */
class OrderItemsAdapter(
    private var items: List<Any>
) : RecyclerView.Adapter<OrderItemsAdapter.ItemViewHolder>() {
    
    private val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US)
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order_item, parent, false)
        return ItemViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }
    
    override fun getItemCount(): Int = items.size
    
    fun updateItems(newItems: List<Any>) {
        this.items = newItems
        notifyDataSetChanged()
    }
    
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemNameText: TextView = itemView.findViewById(R.id.itemNameText)
        private val itemQuantityText: TextView = itemView.findViewById(R.id.itemQuantityText)
        private val itemPriceText: TextView = itemView.findViewById(R.id.itemPriceText)
        private val itemNotesText: TextView = itemView.findViewById(R.id.itemNotesText)
        
        fun bind(item: Any) {
            try {
                when (item) {
                    is OrderItem -> {
                        // Handle regular OrderItem object
                        itemNameText.text = item.name
                        itemQuantityText.text = "x${item.quantity}"
                        itemPriceText.text = currencyFormatter.format(item.price * item.quantity)
                        
                        // Show or hide notes
                        if (item.notes.isNullOrEmpty()) {
                            itemNotesText.visibility = View.GONE
                        } else {
                            itemNotesText.visibility = View.VISIBLE
                            itemNotesText.text = "Note: ${item.notes}"
                        }
                    }
                    is Map<*, *> -> {
                        // Handle Map/HashMap from Firestore
                        val name = item["name"] as? String ?: "Unknown Item"
                        val quantity = (item["quantity"] as? Number)?.toInt() ?: 1
                        val price = (item["price"] as? Number)?.toDouble() ?: 0.0
                        val notes = item["notes"] as? String
                        
                        itemNameText.text = name
                        itemQuantityText.text = "x$quantity"
                        itemPriceText.text = currencyFormatter.format(price * quantity)
                        
                        // Show or hide notes
                        if (notes.isNullOrEmpty()) {
                            itemNotesText.visibility = View.GONE
                        } else {
                            itemNotesText.visibility = View.VISIBLE
                            itemNotesText.text = "Note: $notes"
                        }
                    }
                    else -> {
                        // Handle unexpected item type
                        Log.e("OrderItemsAdapter", "Unexpected item type: ${item.javaClass.name}")
                        itemNameText.text = "Unknown Item"
                        itemQuantityText.text = "x1"
                        itemPriceText.text = currencyFormatter.format(0.0)
                        itemNotesText.visibility = View.GONE
                    }
                }
            } catch (e: Exception) {
                Log.e("OrderItemsAdapter", "Error binding item", e)
                // Fallback display
                itemNameText.text = "Error displaying item"
                itemQuantityText.text = "x1"
                itemPriceText.text = currencyFormatter.format(0.0)
                itemNotesText.visibility = View.GONE
            }
        }
    }
} 