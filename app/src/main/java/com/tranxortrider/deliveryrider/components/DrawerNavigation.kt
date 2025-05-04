package com.tranxortrider.deliveryrider.components

import android.app.Activity
import android.content.Intent
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.tranxortrider.deliveryrider.DeliveryHistoryActivity
import com.tranxortrider.deliveryrider.EarningsActivity
import com.tranxortrider.deliveryrider.EmergencySOSActivity
import com.tranxortrider.deliveryrider.HelpCenterActivity
import com.tranxortrider.deliveryrider.PerformanceActivity
import com.tranxortrider.deliveryrider.ProfileActivity
import com.tranxortrider.deliveryrider.R
import com.tranxortrider.deliveryrider.SettingsActivity
import com.tranxortrider.deliveryrider.home_screen
import com.tranxortrider.deliveryrider.models.User
import com.tranxortrider.deliveryrider.pending_orders
import com.tranxortrider.deliveryrider.settings
import com.tranxortrider.deliveryrider.utils.SessionManager

/**
 * Helper class to manage drawer navigation throughout the app
 */
class DrawerNavigation(
    private val activity: AppCompatActivity,
    private val toolbar: Toolbar,
    private val drawerLayout: DrawerLayout,
    private val navigationView: NavigationView
) : NavigationView.OnNavigationItemSelectedListener {

    private val sessionManager = SessionManager(activity)
    private var currentItemId: Int = R.id.nav_home

    init {
        setupDrawer()
        updateUserInfo()
    }

    /**
     * Set the current screen to highlight the correct drawer item
     */
    fun setCurrentScreen(itemId: Int) {
        currentItemId = itemId
        navigationView.setCheckedItem(itemId)
    }

    /**
     * Update user information in the drawer header
     */
    fun updateUserInfo() {
        val headerView = navigationView.getHeaderView(0)
        val user = sessionManager.getUser()

        if (user != null) {
            val nameTextView = headerView.findViewById<TextView>(R.id.nav_header_name)
            val emailTextView = headerView.findViewById<TextView>(R.id.nav_header_email)
            val profileImageView = headerView.findViewById<ImageView>(R.id.profileImage)

            nameTextView.text = user.name
            emailTextView.text = user.email

            // In a real app, load profile image using Glide or similar library
            // For now, keep the default image
        }
    }

    private fun setupDrawer() {
        // Set up ActionBarDrawerToggle
        val toggle = ActionBarDrawerToggle(
            activity,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Set up navigation item selection listener
        navigationView.setNavigationItemSelectedListener(this)

        // Set up header click listener
        val headerView = navigationView.getHeaderView(0)
        headerView.setOnClickListener {
            // Navigate to profile when header is clicked
            navigateTo(ProfileActivity::class.java)
            drawerLayout.closeDrawer(GravityCompat.START)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks
        when (item.itemId) {
            R.id.nav_home -> {
                navigateTo(home_screen::class.java)
            }
            R.id.nav_orders -> {
                navigateTo(pending_orders::class.java)
            }
            R.id.nav_earnings -> {
                navigateTo(EarningsActivity::class.java)
            }
            R.id.nav_history -> {
                navigateTo(DeliveryHistoryActivity::class.java)
            }
            R.id.nav_performance -> {
                navigateTo(PerformanceActivity::class.java)
            }
            R.id.nav_emergency -> {
                navigateTo(EmergencySOSActivity::class.java)
            }
            R.id.nav_profile -> {
                navigateTo(ProfileActivity::class.java)
            }
            R.id.nav_settings -> {
                navigateTo(settings::class.java)
            }
            R.id.nav_help -> {
                navigateTo(HelpCenterActivity::class.java)
            }
        }

        // Close the drawer
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    /**
     * Handle back button press to close drawer if open
     */
    fun onBackPressed(): Boolean {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
            return true
        }
        return false
    }

    private fun navigateTo(activityClass: Class<*>) {
        // Don't navigate if we're already on this screen
        if (activity.javaClass == activityClass) {
            return
        }

        val intent = Intent(activity, activityClass)
        activity.startActivity(intent)
    }
} 