package com.tranxortrider.deliveryrider

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.tranxortrider.deliveryrider.api.DocumentType
import com.tranxortrider.deliveryrider.models.Document
import com.tranxortrider.deliveryrider.repositories.OnboardingRepository
import com.tranxortrider.deliveryrider.utils.NetworkUtils
import com.tranxortrider.deliveryrider.utils.SessionManager
import com.tranxortrider.deliveryrider.utils.UIUtils
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import android.database.Cursor
import android.provider.OpenableColumns
import android.content.ContentResolver
import java.io.FileOutputStream
import java.io.InputStream

/**
 * DocumentType enum for document types
 */
enum class DocumentType {
    DRIVER_LICENSE,
    VEHICLE_INSURANCE,
    VEHICLE_REGISTRATION,
    WORK_AUTHORIZATION
}

class upload_document : AppCompatActivity() {
    
    // UI elements
    private lateinit var driverLicenseButton: MaterialButton
    private lateinit var governmentIdButton: MaterialButton
    private lateinit var vehicleRegistrationButton: MaterialButton
    private lateinit var nextButton: MaterialButton
    
    // Status indicators
    private lateinit var driverLicenseStatus: ImageView
    private lateinit var governmentIdStatus: ImageView
    private lateinit var vehicleRegistrationStatus: ImageView
    
    // Text views
    private lateinit var driverLicenseText: TextView
    private lateinit var governmentIdText: TextView
    private lateinit var vehicleRegistrationText: TextView
    
    // Dependencies
    private lateinit var onboardingRepository: OnboardingRepository
    private lateinit var sessionManager: SessionManager
    
    // Document URIs
    private var driverLicenseUri: Uri? = null
    private var governmentIdUri: Uri? = null
    private var vehicleRegistrationUri: Uri? = null
    
    // Document type being currently selected
    private var currentDocumentType: String = ""
    
    // Uploaded document count
    private var uploadedDocumentCount = 0
    private var totalDocumentsToUpload = 0
    
    // Result launcher for picking documents
    private val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val uri = result.data?.data
            uri?.let { 
                // Check file size
                val fileSizeOk = checkFileSize(it)
                
                when (currentDocumentType) {
                    "driver_license" -> {
                        driverLicenseUri = it
                        updateDocumentStatus(driverLicenseStatus, driverLicenseText, true)
                        if (!fileSizeOk) {
                            driverLicenseText.text = "Driver License (will be compressed)"
                        }
                    }
                    "government_id" -> {
                        governmentIdUri = it
                        updateDocumentStatus(governmentIdStatus, governmentIdText, true)
                        if (!fileSizeOk) {
                            governmentIdText.text = "Government ID (will be compressed)"
                        }
                    }
                    "vehicle_registration" -> {
                        vehicleRegistrationUri = it
                        updateDocumentStatus(vehicleRegistrationStatus, vehicleRegistrationText, true)
                        if (!fileSizeOk) {
                            vehicleRegistrationText.text = "Vehicle Registration (will be compressed)"
                        }
                    }
                }
                checkEnableNextButton()
            }
        }
    }
    
    // Permission launchers for different Android versions
    private val requestStoragePermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        // Check if storage permissions were granted
        val allGranted = permissions.all { it.value }
        
        if (allGranted) {
            openGallery()
        } else {
            showPermissionDeniedMessage()
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_upload_document)
        
        // Initialize window insets
        val rootView = findViewById<View>(android.R.id.content)
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        // Initialize dependencies
        onboardingRepository = OnboardingRepository()
        sessionManager = SessionManager(this)
        
        // Check if user is logged in
        if (sessionManager.getUser() == null) {
            navigateToSignIn()
            return
        }
        
        // Initialize UI elements
        initializeUI()
        
        // Set up click listeners
        setupClickListeners()
        
        // Check if any documents are already uploaded
        checkExistingDocuments()
    }
    
    private fun initializeUI() {
        // Find button views
        driverLicenseButton = findViewById(R.id.uploaddriverLicenseButton)
        governmentIdButton = findViewById(R.id.uploadgovernmentIdButton)
        vehicleRegistrationButton = findViewById(R.id.uploadworkAuthorizationButton)
        nextButton = findViewById(R.id.nextButton)
        
        // Find status indicators
        driverLicenseStatus = findViewById(R.id.driverLicenseStatus)
        governmentIdStatus = findViewById(R.id.vehicleInsuranceStatus)
        vehicleRegistrationStatus = findViewById(R.id.vehicleRegistrationStatus)
        
        // Find text views
        driverLicenseText = findViewById(R.id.driverLicenseText)
        governmentIdText = findViewById(R.id.vehicleInsuranceText)
        vehicleRegistrationText = findViewById(R.id.vehicleRegistrationText)
        
        // Update text labels to match actual document types
        governmentIdText.text = "Government ID"
        vehicleRegistrationText.text = "Vehicle Registration"
        
        // Initially disable next button
        nextButton.isEnabled = false
    }
    
    private fun setupClickListeners() {
        // Upload Driver License button
        driverLicenseButton.setOnClickListener {
            currentDocumentType = "driver_license"
            checkGalleryPermissionAndOpenGallery()
        }
        
        // Upload Government ID button
        governmentIdButton.setOnClickListener {
            currentDocumentType = "government_id"
            checkGalleryPermissionAndOpenGallery()
        }
        
        // Upload Vehicle Registration button
        vehicleRegistrationButton.setOnClickListener {
            currentDocumentType = "vehicle_registration"
            checkGalleryPermissionAndOpenGallery()
        }
        
        // Continue button
        nextButton.setOnClickListener {
            uploadDocuments()
        }
    }
    
    private fun checkExistingDocuments() {
        val userId = sessionManager.getUser()?.id ?: return
        
        // Show loading indicator
        UIUtils.showLoading(this, "Checking documents...")
        
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val documentsResult = onboardingRepository.getUserDocuments(userId)
                
                withContext(Dispatchers.Main) {
                    // Hide loading
                    UIUtils.hideLoading()
                    
                    if (documentsResult.isSuccess) {
                        val documents = documentsResult.getOrNull() ?: emptyList()
                        
                        // Check for each document type
                        for (document in documents) {
                            when (document.type) {
                                "DRIVER_LICENSE" -> {
                                    updateDocumentStatus(driverLicenseStatus, driverLicenseText, true)
                                    driverLicenseButton.text = "Uploaded"
                                    driverLicenseButton.isEnabled = document.status.equals("rejected", ignoreCase = true)
                                }
                                "GOVERNMENT_ID" -> {
                                    updateDocumentStatus(governmentIdStatus, governmentIdText, true)
                                    governmentIdButton.text = "Uploaded"
                                    governmentIdButton.isEnabled = document.status.equals("rejected", ignoreCase = true)
                                }
                                "VEHICLE_REGISTRATION" -> {
                                    updateDocumentStatus(vehicleRegistrationStatus, vehicleRegistrationText, true)
                                    vehicleRegistrationButton.text = "Uploaded"
                                    vehicleRegistrationButton.isEnabled = document.status.equals("rejected", ignoreCase = true)
                                }
                            }
                        }
                        
                        // Enable next button if any documents are uploaded
                        checkEnableNextButton()
                        
                        // If all required documents are uploaded, enable next button
                        val allSubmittedResult = onboardingRepository.checkAllDocumentsSubmitted(userId)
                        if (allSubmittedResult.isSuccess && allSubmittedResult.getOrNull() == true) {
                            nextButton.isEnabled = true
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    UIUtils.hideLoading()
                    UIUtils.showSnackbar(findViewById(android.R.id.content), "Error checking documents: ${e.message}")
                }
            }
        }
    }
    
    private fun checkGalleryPermissionAndOpenGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13+ uses READ_MEDIA_IMAGES
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_MEDIA_IMAGES
                ) == PackageManager.PERMISSION_GRANTED -> {
                    openGallery()
                }
                ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.READ_MEDIA_IMAGES
                ) -> {
                    showPermissionRationaleDialog(Manifest.permission.READ_MEDIA_IMAGES)
                }
                else -> {
                    requestStoragePermissionLauncher.launch(arrayOf(Manifest.permission.READ_MEDIA_IMAGES))
                }
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Android 10-12 just needs READ_EXTERNAL_STORAGE
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                openGallery()
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) -> {
                    showPermissionRationaleDialog(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
                else -> {
                    requestStoragePermissionLauncher.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
                }
            }
        } else {
            // Android 9 and below needs both READ and WRITE permissions
            val hasReadPermission = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
            
            val hasWritePermission = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
            
            if (hasReadPermission && hasWritePermission) {
                openGallery()
            } else {
                val permissionsToRequest = mutableListOf<String>()
                
                if (!hasReadPermission) {
                    permissionsToRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
                
                if (!hasWritePermission) {
                    permissionsToRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
                
                if (permissionsToRequest.isNotEmpty()) {
                    requestStoragePermissionLauncher.launch(permissionsToRequest.toTypedArray())
                }
            }
        }
    }
    
    private fun showPermissionRationaleDialog(permission: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Permission Required")
            .setMessage("We need access to your photos to upload documents for verification.")
            .setPositiveButton("Grant Permission") { _, _ ->
                if (permission == Manifest.permission.READ_MEDIA_IMAGES) {
                    requestStoragePermissionLauncher.launch(arrayOf(permission))
                } else if (permission == Manifest.permission.READ_EXTERNAL_STORAGE) {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                        // For Android 9 and below, request both READ and WRITE
                        requestStoragePermissionLauncher.launch(arrayOf(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ))
                    } else {
                        // For Android 10+, just request READ
                        requestStoragePermissionLauncher.launch(arrayOf(permission))
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/* application/pdf"
        getContent.launch(intent)
    }
    
    private fun updateDocumentStatus(statusImageView: ImageView, textView: TextView, isSelected: Boolean) {
        if (isSelected) {
            statusImageView.setImageResource(R.drawable.ic_check_circle)
            statusImageView.visibility = View.VISIBLE
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check_circle, 0)
        } else {
            statusImageView.visibility = View.GONE
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        }
    }
    
    private fun checkEnableNextButton() {
        // Enable next button if at least one document is selected
        nextButton.isEnabled = driverLicenseUri != null || governmentIdUri != null || vehicleRegistrationUri != null
    }
    
    private fun checkFileSize(uri: Uri): Boolean {
        try {
            val fileSize = getFileSizeFromUri(uri)
            if (fileSize != null) {
                // Show file size information dialog
                UIUtils.showFileSizeDialog(this, fileSize)
                
                // Return whether the file size is within limits (5MB)
                val maxFileSize = 5 * 1024 * 1024
                return fileSize <= maxFileSize
            }
            return true
        } catch (e: Exception) {
            return false
        }
    }
    
    private fun getFileSizeFromUri(uri: Uri): Long? {
        val contentResolver = contentResolver
        val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
        
        cursor?.use { cursor ->
            if (cursor.moveToFirst()) {
                val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                if (sizeIndex != -1) {
                    return cursor.getLong(sizeIndex)
                }
            }
        }
        
        // Try to get size by opening the input stream
        try {
            contentResolver.openInputStream(uri)?.use { inputStream ->
                return inputStream.available().toLong()
            }
        } catch (e: Exception) {
            // Ignore
        }
        
        return null
    }
    
    private fun getFileFromUri(uri: Uri): File? {
        val contentResolver = contentResolver
        val fileName = getFileNameFromUri(uri) ?: "document_${System.currentTimeMillis()}"
        val tempFile = File(cacheDir, fileName)
        
        try {
            contentResolver.openInputStream(uri)?.use { inputStream ->
                FileOutputStream(tempFile).use { outputStream ->
                    val buffer = ByteArray(4 * 1024) // 4KB buffer
                    var read: Int
                    while (inputStream.read(buffer).also { read = it } != -1) {
                        outputStream.write(buffer, 0, read)
                    }
                    outputStream.flush()
                    return tempFile
                }
            }
        } catch (e: Exception) {
            // Log error
        }
        
        return null
    }
    
    private fun getFileNameFromUri(uri: Uri): String? {
        val contentResolver = contentResolver
        val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
        
        cursor?.use { cursor ->
            if (cursor.moveToFirst()) {
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex != -1) {
                    return cursor.getString(nameIndex)
                }
            }
        }
        
        // If unable to get name from cursor, extract it from the Uri
        val uriString = uri.toString()
        val lastPathSegment = uri.lastPathSegment
        
        return when {
            lastPathSegment != null -> lastPathSegment
            uriString.contains("/") -> uriString.substring(uriString.lastIndexOf("/") + 1)
            else -> "document_${System.currentTimeMillis()}"
        }
    }
    
    private fun showPermissionDeniedMessage() {
        Snackbar.make(
            findViewById(android.R.id.content),
            "Storage permission is required to upload documents",
            Snackbar.LENGTH_LONG
        ).setAction("Settings") {
            // Open app settings
            val intent = Intent(
                android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", packageName, null)
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        }.show()
    }
    
    private fun navigateToSignIn() {
        // Navigate to sign in screen
        Toast.makeText(this, "Please sign in to continue", Toast.LENGTH_SHORT).show()
        finish()
    }
    
    private fun uploadDocuments() {
        // Collect all document information
        val documentsToUpload = mutableListOf<Pair<Uri, DocumentType>>()
        
        driverLicenseUri?.let {
            documentsToUpload.add(Pair(it, DocumentType.DRIVER_LICENSE))
        }
        
        governmentIdUri?.let {
            documentsToUpload.add(Pair(it, DocumentType.VEHICLE_INSURANCE))
        }
        
        vehicleRegistrationUri?.let {
            documentsToUpload.add(Pair(it, DocumentType.VEHICLE_REGISTRATION))
        }
        
        // Upload documents to Firebase
        if (documentsToUpload.isNotEmpty() && NetworkUtils.isNetworkAvailable(this)) {
            // Show loading dialog
            UIUtils.showLoading(this, "Uploading documents...")
            
            val userId = sessionManager.getUser()?.id ?: return
            uploadedDocumentCount = 0
            totalDocumentsToUpload = documentsToUpload.size
            
            for (document in documentsToUpload) {
                val documentUri = document.first
                val documentType = document.second
                
                // Call uploadDocument with correct parameters
                onboardingRepository.uploadDocument(
                    context = this,
                    userId = userId,
                    documentType = documentType,
                    documentUri = documentUri,
                    onResponse = { success, message, response ->
                        uploadedDocumentCount++
                        
                        // If all documents uploaded or this is the last one
                        if (uploadedDocumentCount >= totalDocumentsToUpload) {
                            // Hide loading dialog
                            UIUtils.hideLoading()
                            
                            // Check if all uploads were successful
                            if (success) {
                                // Navigate to verification screen
                                val intent = Intent(this, application_verification::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                // Show error message
                                UIUtils.showSnackbar(findViewById(android.R.id.content), message)
                            }
                        }
                    }
                )
            }
        } else if (!NetworkUtils.isNetworkAvailable(this)) {
            UIUtils.showSnackbar(findViewById(android.R.id.content),
                "No internet connection. Please try again later.")
        } else {
            UIUtils.showSnackbar(findViewById(android.R.id.content),
                "Please upload at least one document")
        }
    }
}