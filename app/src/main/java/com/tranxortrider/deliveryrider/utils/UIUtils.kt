package com.tranxortrider.deliveryrider.utils

import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import android.app.Activity
import android.app.Dialog
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import androidx.annotation.IdRes
import com.tranxortrider.deliveryrider.R
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.view.Gravity
import android.view.WindowManager
import android.widget.Button
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.File

/**
 * Utility class for UI-related helpers
 */
object UIUtils {
    
    private const val TAG = "UIUtils"
    private var loadingDialog: Dialog? = null
    
    /**
     * Show a short toast message
     */
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
    
    /**
     * Show a long toast message
     */
    fun showLongToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
    
    /**
     * Shows a snackbar with the given message
     * 
     * @param view The view to find a parent from
     * @param message The message to show
     * @param duration The duration to show the snackbar
     */
    fun showSnackbar(view: View, message: String, duration: Int = Snackbar.LENGTH_LONG) {
        Snackbar.make(view, message, duration).show()
    }
    
    /**
     * Shows a snackbar with the given message and an action
     * 
     * @param view The view to find a parent from
     * @param message The message to show
     * @param actionText The text to display for the action
     * @param action The action to perform when clicked
     * @param duration The duration to show the snackbar
     */
    fun showSnackbarWithAction(
        view: View,
        message: String,
        actionText: String,
        action: () -> Unit,
        duration: Int = Snackbar.LENGTH_LONG
    ) {
        Snackbar.make(view, message, duration)
            .setAction(actionText) { action() }
            .show()
    }
    
    /**
     * Show an alert dialog
     */
    fun showAlertDialog(
        context: Context,
        title: String,
        message: String,
        positiveButtonText: String = "OK",
        positiveAction: (() -> Unit)? = null,
        negativeButtonText: String? = null,
        negativeAction: (() -> Unit)? = null
    ) {
        val builder = AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveButtonText) { dialog, _ ->
                dialog.dismiss()
                positiveAction?.invoke()
            }
        
        if (negativeButtonText != null) {
            builder.setNegativeButton(negativeButtonText) { dialog, _ ->
                dialog.dismiss()
                negativeAction?.invoke()
            }
        }
        
        builder.create().show()
    }

    /**
     * Find a view with the given ID, or log an error if it doesn't exist
     * 
     * @param activity The activity to find the view in
     * @param viewId The ID of the view to find
     * @return The view, or null if it doesn't exist
     */
    fun <T : View> findViewSafely(activity: Activity, @IdRes viewId: Int): T? {
        return try {
            activity.findViewById(viewId)
        } catch (e: Exception) {
            Log.e(TAG, "Could not find view with ID $viewId", e)
            null
        }
    }

    /**
     * Show a view safely, handling potential null references
     */
    fun showViewSafely(view: View?) {
        try {
            view?.visibility = View.VISIBLE
        } catch (e: Exception) {
            Log.e(TAG, "Error showing view", e)
        }
    }

    /**
     * Hide a view safely, handling potential null references
     */
    fun hideViewSafely(view: View?) {
        try {
            view?.visibility = View.GONE
        } catch (e: Exception) {
            Log.e(TAG, "Error hiding view", e)
        }
    }

    /**
     * Shows a loading dialog
     *
     * @param context The context to show the dialog in
     * @param message The loading message
     */
    fun showLoading(context: Context, message: String = "Loading...") {
        // If a dialog is already showing, dismiss it
        hideLoading()
        
        // Create a new dialog
        loadingDialog = Dialog(context).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            setContentView(R.layout.dialog_loading)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            
            // Set the message
            findViewById<TextView>(R.id.loadingMessage)?.text = message
            
            // Show the dialog
            show()
        }
    }

    /**
     * Hides the loading dialog
     */
    fun hideLoading() {
        loadingDialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
        loadingDialog = null
    }

    /**
     * Show an alert dialog with a title and message
     */
    fun showAlert(context: Context, title: String, message: String, 
                  positiveButtonText: String = "OK", 
                  positiveAction: (() -> Unit)? = null) {
        MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveButtonText) { dialog, _ ->
                dialog.dismiss()
                positiveAction?.invoke()
            }
            .show()
    }
    
    /**
     * Show a confirmation dialog with positive and negative buttons
     */
    fun showConfirmationDialog(
        context: Context,
        title: String,
        message: String,
        positiveButtonText: String = "Yes",
        negativeButtonText: String = "No",
        positiveAction: () -> Unit,
        negativeAction: (() -> Unit)? = null
    ) {
        MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveButtonText) { dialog, _ ->
                dialog.dismiss()
                positiveAction()
            }
            .setNegativeButton(negativeButtonText) { dialog, _ ->
                dialog.dismiss()
                negativeAction?.invoke()
            }
            .show()
    }
    
    /**
     * Show file size information dialog
     */
    fun showFileSizeDialog(context: Context, fileSize: Long) {
        val readableFileSize = getReadableFileSize(fileSize)
        val isOverLimit = isFileSizeExceeded(fileSize)
        
        val title = if (isOverLimit) "File Size Exceeded" else "File Size Information"
        val message = when {
            isOverLimit -> {
                "The selected file is $readableFileSize, which exceeds the 5MB limit.\n\n" +
                "Large files might be compressed during upload to meet the size requirement."
            }
            else -> {
                "The selected file is $readableFileSize, which is within the 5MB limit."
            }
        }
        
        val buttonText = "OK"
        
        MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(buttonText, null)
            .show()
    }

    /**
     * Convert bytes to human-readable file size
     */
    private fun getReadableFileSize(size: Long): String {
        if (size <= 0) return "0 B"
        
        val units = arrayOf("B", "KB", "MB", "GB", "TB")
        val digitGroups = (Math.log10(size.toDouble()) / Math.log10(1024.0)).toInt()
        
        return String.format("%.1f %s", size / Math.pow(1024.0, digitGroups.toDouble()), units[digitGroups])
    }

    /**
     * Check if file size exceeds the limit (5MB)
     */
    private fun isFileSizeExceeded(size: Long): Boolean {
        val maxSize = 5 * 1024 * 1024 // 5MB in bytes
        return size > maxSize
    }
} 