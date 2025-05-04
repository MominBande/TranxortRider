package com.tranxortrider.deliveryrider

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar

class HelpCenterActivity : AppCompatActivity() {
    
    // UI Components
    private lateinit var backButton: MaterialButton
    private lateinit var contactSupportButton: Button
    private lateinit var startChatButton: Button
    
    // FAQ Cards and related components
    private lateinit var faqCards: List<CardView>
    private lateinit var faqAnswers: List<TextView>
    private lateinit var faqArrows: List<ImageView>
    
    // Contact method sections
    private lateinit var emailContactLayout: LinearLayout
    private lateinit var phoneContactLayout: LinearLayout
    private lateinit var chatContactLayout: LinearLayout
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help_center)
        
        // Initialize UI components
        initViews()
        setupListeners()
    }
    
    private fun initViews() {
        // Main buttons
        backButton = findViewById(R.id.btnBack)
        contactSupportButton = findViewById(R.id.btnContactSupport)
        startChatButton = findViewById(R.id.btnStartChat)
        
        // FAQ Cards
        faqCards = listOf(
            findViewById(R.id.faqCard1),
            findViewById(R.id.faqCard2),
            findViewById(R.id.faqCard3),
            findViewById(R.id.faqCard4),
            findViewById(R.id.faqCard5)
        )
        
        // FAQ Answers
        faqAnswers = listOf(
            findViewById(R.id.faqAnswer1),
            findViewById(R.id.faqAnswer2),
            findViewById(R.id.faqAnswer3),
            findViewById(R.id.faqAnswer4),
            findViewById(R.id.faqAnswer5)
        )
        
        // FAQ Arrows
        faqArrows = listOf(
            findViewById(R.id.faqArrow1),
            findViewById(R.id.faqArrow2),
            findViewById(R.id.faqArrow3),
            findViewById(R.id.faqArrow4),
            findViewById(R.id.faqArrow5)
        )
        
        // Contact methods
        emailContactLayout = findViewById(R.id.emailContactLayout)
        phoneContactLayout = findViewById(R.id.phoneContactLayout)
        chatContactLayout = findViewById(R.id.chatContactLayout)
    }
    
    private fun setupListeners() {
        // Back button
        backButton.setOnClickListener {
            onBackPressed()
        }
        
        // Contact support button
        contactSupportButton.setOnClickListener {
            sendEmailToSupport()
        }
        
        // Start chat button
        startChatButton.setOnClickListener {
            startLiveChat()
        }
        
        // Setup FAQ card click listeners
        for (i in faqCards.indices) {
            faqCards[i].setOnClickListener {
                toggleFaqAnswer(i)
            }
        }
        
        // Contact method clicks
        emailContactLayout.setOnClickListener {
            sendEmailToSupport()
        }
        
        phoneContactLayout.setOnClickListener {
            callSupportPhone()
        }
    }
    
    private fun toggleFaqAnswer(index: Int) {
        val isVisible = faqAnswers[index].visibility == View.VISIBLE
        
        // Hide all answers first
        for (i in faqAnswers.indices) {
            faqAnswers[i].visibility = View.GONE
            faqArrows[i].rotation = 0f // Reset arrow rotation
        }
        
        // If the clicked answer wasn't already visible, show it
        if (!isVisible) {
            faqAnswers[index].visibility = View.VISIBLE
            faqArrows[index].rotation = 180f // Rotate arrow
        }
    }
    
    private fun sendEmailToSupport() {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:support@tranxortrider.com")
            putExtra(Intent.EXTRA_SUBJECT, "Rider Support Request")
        }
        
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            showSnackbar("No email app found")
        }
    }
    
    private fun callSupportPhone() {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:+18005551234")
        }
        
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            showSnackbar("No phone app found")
        }
    }
    
    private fun startLiveChat() {
        // In a real app, this would open a chat interface or web view
        // For now, we'll just show a snackbar
        showSnackbar("Live chat feature coming soon!")
    }
    
    private fun showSnackbar(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
    }
} 