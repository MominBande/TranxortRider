package com.tranxortrider.deliveryrider.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.tranxortrider.deliveryrider.R
import com.tranxortrider.deliveryrider.models.Notification
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Adapter for displaying notifications in a RecyclerView
 */
class NotificationsAdapter(private var notifications: List<Notification>) :
    RecyclerView.Adapter<NotificationsAdapter.NotificationViewHolder>() {
    
    private var onItemClickListener: ((Notification) -> Unit)? = null
    private var onMarkReadClickListener: ((Notification, Int) -> Unit)? = null
    private var onDeleteClickListener: ((Notification, Int) -> Unit)? = null
    
    private val dateFormatter = SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.US)
    
    /**
     * Update the notifications list and refresh the adapter
     */
    fun updateNotifications(newNotifications: List<Notification>) {
        notifications = newNotifications
        notifyDataSetChanged()
    }
    
    /**
     * Set click listener for item clicks
     */
    fun setOnItemClickListener(listener: (Notification) -> Unit) {
        onItemClickListener = listener
    }
    
    /**
     * Set click listener for mark as read button
     */
    fun setOnMarkReadClickListener(listener: (Notification, Int) -> Unit) {
        onMarkReadClickListener = listener
    }
    
    /**
     * Set click listener for delete button
     */
    fun setOnDeleteClickListener(listener: (Notification, Int) -> Unit) {
        onDeleteClickListener = listener
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_notification, parent, false)
        return NotificationViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val notification = notifications[position]
        holder.bind(notification, position)
    }
    
    override fun getItemCount(): Int = notifications.size
    
    inner class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cardView: CardView = itemView.findViewById(R.id.cardView)
        private val iconView: ImageView = itemView.findViewById(R.id.iconView)
        private val titleText: TextView = itemView.findViewById(R.id.titleText)
        private val messageText: TextView = itemView.findViewById(R.id.messageText)
        private val timeText: TextView? = itemView.findViewById(R.id.timeText)
        private val unreadIndicator: View? = itemView.findViewById(R.id.unreadIndicator)
        private val markReadButton: ImageButton? = itemView.findViewById(R.id.markReadButton)
        private val deleteButton: ImageButton? = itemView.findViewById(R.id.deleteButton)
        
        fun bind(notification: Notification, position: Int) {
            titleText.text = notification.title
            messageText.text = notification.message
            timeText?.text = dateFormatter.format(notification.createdAt)
            
            // Set read/unread status
            unreadIndicator?.visibility = if (notification.isRead) View.GONE else View.VISIBLE
            
            // Set icon based on notification type
            when (notification.type) {
                Notification.Type.NEW_ORDER -> {
                    iconView.setImageResource(R.drawable.ic_new_order)
                }
                Notification.Type.ORDER_STATUS_CHANGED -> {
                    iconView.setImageResource(R.drawable.ic_status_changed)
                }
                Notification.Type.EARNINGS -> {
                    iconView.setImageResource(R.drawable.ic_earnings)
                }
                Notification.Type.ANNOUNCEMENT -> {
                    iconView.setImageResource(R.drawable.ic_announcement)
                }
                Notification.Type.BATCH_ASSIGNMENT -> {
                    iconView.setImageResource(R.drawable.ic_batch)
                }
            }
            
            // Show/hide mark read button based on notification status
            markReadButton?.visibility = if (notification.isRead) View.GONE else View.VISIBLE
            
            // Set click listeners
            cardView.setOnClickListener {
                onItemClickListener?.invoke(notification)
            }
            
            markReadButton?.setOnClickListener {
                onMarkReadClickListener?.invoke(notification, position)
            }
            
            deleteButton?.setOnClickListener {
                onDeleteClickListener?.invoke(notification, position)
            }
        }
    }
} 