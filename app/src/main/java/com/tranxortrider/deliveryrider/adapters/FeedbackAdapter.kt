package com.tranxortrider.deliveryrider.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tranxortrider.deliveryrider.R
import com.tranxortrider.deliveryrider.models.FeedbackItem
import java.text.SimpleDateFormat
import java.util.Locale

class FeedbackAdapter(
    private var items: List<FeedbackItem>
) : RecyclerView.Adapter<FeedbackAdapter.ViewHolder>() {

    private val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.US)

    fun updateItems(newItems: List<FeedbackItem>) {
        this.items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_feedback, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val customerNameText: TextView = itemView.findViewById(R.id.customerNameText)
        private val feedbackDateText: TextView = itemView.findViewById(R.id.feedbackDateText)
        private val ratingText: TextView = itemView.findViewById(R.id.ratingText)
        private val commentText: TextView = itemView.findViewById(R.id.commentText)

        fun bind(item: FeedbackItem) {
            customerNameText.text = item.customerName
            feedbackDateText.text = dateFormat.format(item.date)
            ratingText.text = item.rating.toString()
            commentText.text = item.comment
        }
    }
} 