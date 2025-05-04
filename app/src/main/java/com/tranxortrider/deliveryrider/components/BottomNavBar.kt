package com.tranxortrider.deliveryrider.components

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tranxortrider.deliveryrider.ProfileActivity
import com.tranxortrider.deliveryrider.R
import com.tranxortrider.deliveryrider.home_screen
import com.tranxortrider.deliveryrider.pending_orders
import com.tranxortrider.deliveryrider.scanner

/**
 * Custom BottomNavigationView for consistent navigation throughout the app
 */
class BottomNavBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BottomNavigationView(context, attrs, defStyleAttr) {

    private var currentScreenId: Int = R.id.nav_home

    init {
        inflateMenu(R.menu.bottom_nav_menu)
        setupNavigation()
    }

    /**
     * Set the current screen to highlight the correct navigation item
     */
    fun setCurrentScreen(screenId: Int) {
        if (screenId in listOf(R.id.nav_home, R.id.nav_orders, R.id.nav_scanner, R.id.nav_profile)) {
            currentScreenId = screenId
            selectedItemId = currentScreenId
        }
    }

    private fun setupNavigation() {
        setOnItemSelectedListener { item ->
            if (item.itemId == currentScreenId) {
                // Already on this screen
                return@setOnItemSelectedListener true
            }

            when (item.itemId) {
                R.id.nav_home -> {
                    navigateTo(home_screen::class.java)
                    true
                }
                R.id.nav_orders -> {
                    navigateTo(pending_orders::class.java)
                    true
                }
                R.id.nav_scanner -> {
                    navigateTo(scanner::class.java)
                    true
                }
                R.id.nav_profile -> {
                    navigateTo(ProfileActivity::class.java)
                    true
                }
                else -> false
            }
        }
    }

    private fun navigateTo(activityClass: Class<*>) {
        val intent = Intent(context, activityClass)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        context.startActivity(intent)
    }
} 