package com.tranxortrider.deliveryrider

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.firebase.auth.FirebaseAuth
import com.tranxortrider.deliveryrider.models.User
import com.tranxortrider.deliveryrider.services.FirestoreService
import com.tranxortrider.deliveryrider.services.StorageService
import com.tranxortrider.deliveryrider.utils.NetworkUtils
import com.tranxortrider.deliveryrider.utils.SessionManager
import com.tranxortrider.deliveryrider.utils.UIUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileActivity : AppCompatActivity() {
    
    // UI Components
    private lateinit var backButton: MaterialButton
    private lateinit var saveButton: Button
    private lateinit var profileImageView: ImageView
    private lateinit var changePhotoButton: Button
    private lateinit var nameEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var addressEditText: EditText
    private lateinit var vehicleTypeSpinner: Spinner
    private lateinit var vehicleMakeEditText: EditText
    private lateinit var vehicleModelEditText: EditText
    private lateinit var vehiclePlateEditText: EditText
    private lateinit var availabilitySwitch: SwitchMaterial
    private lateinit var availabilityStatusText: TextView
    private lateinit var loadingView: View
    
    // Services
    private lateinit var firestoreService: FirestoreService
    private lateinit var storageService: StorageService
    private lateinit var sessionManager: SessionManager
    private lateinit var auth: FirebaseAuth
    
    // Data
    private var user: User? = null
    private var selectedImageUri: Uri? = null
    private var isPhotoChanged = false
    
    // Activity Result Launcher for image selection
    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                selectedImageUri = uri
                isPhotoChanged = true
                Glide.with(this)
                    .load(uri)
                    .circleCrop()
                    .into(profileImageView)
            }
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        
        // Initialize services
        firestoreService = FirestoreService()
        storageService = StorageService()
        sessionManager = SessionManager(this)
        auth = FirebaseAuth.getInstance()
        
        // Initialize UI components
        initViews()
        setupListeners()
        setupVehicleTypeSpinner()
        
        // Load user data
        loadUserProfile()
    }
    
    private fun initViews() {
        // Main buttons
        backButton = findViewById(R.id.btnBack)
        saveButton = findViewById(R.id.btnSaveProfile)
        
        // Profile image
        profileImageView = findViewById(R.id.profileImageView)
        changePhotoButton = findViewById(R.id.btnChangePhoto)
        
        // Personal information
        nameEditText = findViewById(R.id.nameEditText)
        phoneEditText = findViewById(R.id.phoneEditText)
        emailEditText = findViewById(R.id.emailEditText)
        addressEditText = findViewById(R.id.addressEditText)
        
        // Vehicle information
        vehicleTypeSpinner = findViewById(R.id.vehicleTypeSpinner)
        vehicleMakeEditText = findViewById(R.id.vehicleMakeEditText)
        vehicleModelEditText = findViewById(R.id.vehicleModelEditText)
        vehiclePlateEditText = findViewById(R.id.licensePlateEditText)
        
        // Availability
        availabilitySwitch = findViewById(R.id.availabilitySwitch)
        availabilityStatusText = findViewById(R.id.availabilityStatusText)
        
        // Loading view
        loadingView = findViewById(R.id.loadingView)
        
        // Set up bottom navigation
        setupBottomNavigation()
    }
    
    private fun setupBottomNavigation() {
        val bottomNavigationView = findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottomNavigation)
        if (bottomNavigationView != null) {
            // Make the bottom navigation visible
            bottomNavigationView.visibility = View.VISIBLE
            // Use the NavigationUtils to set up bottom navigation
            com.tranxortrider.deliveryrider.utils.NavigationUtils.setupBottomNavigation(this, bottomNavigationView)
        }
    }
    
    private fun setupListeners() {
        // Back button
        backButton.setOnClickListener {
            onBackPressed()
        }
        
        // Save button
        saveButton.setOnClickListener {
            if (validateInputs()) {
                updateProfile()
            }
        }
        
        // Change photo button
        changePhotoButton.setOnClickListener {
            openImagePicker()
        }
        
        // Availability switch
        availabilitySwitch.setOnCheckedChangeListener { _, isChecked ->
            updateAvailabilityUI(isChecked)
        }
    }
    
    private fun setupVehicleTypeSpinner() {
        val vehicleTypes = resources.getStringArray(R.array.vehicle_types) ?: arrayOf("Motorcycle", "Car", "Bicycle", "Scooter", "Van")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, vehicleTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        vehicleTypeSpinner.adapter = adapter
    }
    
    private fun loadUserProfile() {
        showLoading(true)
        
        val currentUser = auth.currentUser
        if (currentUser == null) {
            navigateToLogin()
            return
        }
        
        if (!NetworkUtils.isNetworkAvailable(this)) {
            showSnackbar("No internet connection")
            showLoading(false)
            return
        }
        
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = firestoreService.getUserProfile(currentUser.uid)
                
                withContext(Dispatchers.Main) {
                    showLoading(false)
                    
                    if (result.isSuccess) {
                        user = result.getOrNull()
                        user?.let { populateUserData(it) }
                    } else {
                        showSnackbar("Failed to load profile: ${result.exceptionOrNull()?.message}")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showLoading(false)
                    showSnackbar("An error occurred: ${e.message}")
                }
            }
        }
    }
    
    private fun populateUserData(user: User) {
        // Personal information
        nameEditText.setText(user.name)
        phoneEditText.setText(user.phone)
        emailEditText.setText(user.email)
        addressEditText.setText(user.address)
        
        // Vehicle information
        val vehicleTypes = resources.getStringArray(R.array.vehicle_types)
        val vehicleTypeIndex = vehicleTypes.indexOf(user.vehicleType)
        if (vehicleTypeIndex >= 0) {
            vehicleTypeSpinner.setSelection(vehicleTypeIndex)
        }
        
        vehicleMakeEditText.setText(user.vehicleMake)
        vehicleModelEditText.setText(user.vehicleModel)
        vehiclePlateEditText.setText(user.vehiclePlate)
        
        // Availability
        availabilitySwitch.isChecked = user.isAvailable
        updateAvailabilityUI(user.isAvailable)
        
        // Profile image
        if (user.photoUrl != null && user.photoUrl.isNotEmpty()) {
            Glide.with(this)
                .load(user.photoUrl)
                .circleCrop()
                .into(profileImageView)
        }
    }
    
    private fun updateAvailabilityUI(isAvailable: Boolean) {
        if (isAvailable) {
            availabilityStatusText.text = "Available for Deliveries"
            availabilityStatusText.setTextColor(ContextCompat.getColor(this, R.color.aesthgreen))
        } else {
            availabilityStatusText.text = "Unavailable for Deliveries"
            availabilityStatusText.setTextColor(ContextCompat.getColor(this, R.color.red))
        }
    }
    
    private fun validateInputs(): Boolean {
        var isValid = true
        
        // Name validation
        if (nameEditText.text.isNullOrBlank()) {
            nameEditText.error = "Name is required"
            isValid = false
        }
        
        // Phone validation
        if (phoneEditText.text.isNullOrBlank()) {
            phoneEditText.error = "Phone number is required"
            isValid = false
        }
        
        // Email validation (read-only, no validation needed)
        
        // Address validation
        if (addressEditText.text.isNullOrBlank()) {
            addressEditText.error = "Address is required"
            isValid = false
        }
        
        // Vehicle make validation
        if (vehicleMakeEditText.text.isNullOrBlank()) {
            vehicleMakeEditText.error = "Vehicle make is required"
            isValid = false
        }
        
        // Vehicle model validation
        if (vehicleModelEditText.text.isNullOrBlank()) {
            vehicleModelEditText.error = "Vehicle model is required"
            isValid = false
        }
        
        // Vehicle plate validation
        if (vehiclePlateEditText.text.isNullOrBlank()) {
            vehiclePlateEditText.error = "License plate is required"
            isValid = false
        }
        
        return isValid
    }
    
    private fun updateProfile() {
        showLoading(true)
        
        val currentUser = auth.currentUser ?: return
        val userId = currentUser.uid
        
        if (!NetworkUtils.isNetworkAvailable(this)) {
            showSnackbar("No internet connection")
            showLoading(false)
            return
        }
        
        CoroutineScope(Dispatchers.IO).launch {
            try {
                var photoUrl = user?.photoUrl
                
                // If photo was changed, upload it first
                if (isPhotoChanged && selectedImageUri != null) {
                    val uploadResult = storageService.uploadProfileImage(userId, selectedImageUri!!)
                    if (uploadResult.isSuccess) {
                        photoUrl = uploadResult.getOrNull() ?: ""
                    } else {
                        withContext(Dispatchers.Main) {
                            showSnackbar("Failed to upload profile image")
                        }
                    }
                }
                
                // Build updated user object
                val updatedUser = User(
                    id = userId,
                    name = nameEditText.text.toString(),
                    email = emailEditText.text.toString(),
                    phone = phoneEditText.text.toString(),
                    address = addressEditText.text.toString(),
                    vehicleType = vehicleTypeSpinner.selectedItem.toString(),
                    vehicleMake = vehicleMakeEditText.text.toString(),
                    vehicleModel = vehicleModelEditText.text.toString(),
                    vehiclePlate = vehiclePlateEditText.text.toString(),
                    isAvailable = availabilitySwitch.isChecked,
                    photoUrl = photoUrl ?: ""
                )
                
                // Update profile in Firestore
                val result = firestoreService.updateUserProfile(updatedUser)
                
                withContext(Dispatchers.Main) {
                    showLoading(false)
                    
                    if (result.isSuccess) {
                        // Update session data
                        sessionManager.saveUserData(updatedUser)
                        
                        showSnackbar("Profile updated successfully")
                        
                        // If availability status changed, broadcast the change
                        if (user?.isAvailable != updatedUser.isAvailable) {
                            sendAvailabilityBroadcast(updatedUser.isAvailable)
                        }
                        
                        // Update user reference
                        user = updatedUser
                    } else {
                        showSnackbar("Failed to update profile: ${result.exceptionOrNull()?.message}")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showLoading(false)
                    showSnackbar("An error occurred: ${e.message}")
                }
            }
        }
    }
    
    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImageLauncher.launch(intent)
    }
    
    private fun navigateToLogin() {
        val intent = Intent(this, sign_in_screen::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
    
    private fun sendAvailabilityBroadcast(isAvailable: Boolean) {
        val intent = Intent("com.tranxortrider.deliveryrider.AVAILABILITY_CHANGED")
        intent.putExtra("IS_AVAILABLE", isAvailable)
        sendBroadcast(intent)
    }
    
    private fun showLoading(isLoading: Boolean) {
        loadingView.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    
    private fun showSnackbar(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
    }
} 