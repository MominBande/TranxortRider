package com.tranxortrider.deliveryrider

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.FirebaseFirestore
import com.tranxortrider.deliveryrider.models.EmergencyContact
import com.tranxortrider.deliveryrider.models.User
import com.tranxortrider.deliveryrider.utils.NetworkUtils
import com.tranxortrider.deliveryrider.utils.SessionManager
import com.tranxortrider.deliveryrider.utils.UIUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class EmergencySOSActivity : AppCompatActivity() {
    
    // UI Components
    private lateinit var backButton: MaterialButton
    private lateinit var sosButton: Button
    private lateinit var callPoliceButton: Button
    private lateinit var callAmbulanceButton: Button
    private lateinit var callSupportButton: Button
    private lateinit var emergencyContact1Card: View
    private lateinit var emergencyContact2Card: View
    private lateinit var emergencyContact1Name: TextView
    private lateinit var emergencyContact1Phone: TextView
    private lateinit var emergencyContact2Name: TextView
    private lateinit var emergencyContact2Phone: TextView
    private lateinit var addContactButton: Button
    private lateinit var countdownText: TextView
    private lateinit var cancelSOSButton: Button
    private lateinit var sosActivatedView: View
    
    // Services
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var firestore: FirebaseFirestore
    private lateinit var sessionManager: SessionManager
    
    // Data
    private var emergencyContacts = mutableListOf<EmergencyContact>()
    private var currentLocation: Location? = null
    private var isSOSActive = false
    private var countdownTimer: CountDownTimer? = null
    
    // Constants
    private val LOCATION_PERMISSION_REQUEST_CODE = 100
    private val EMERGENCY_COUNTDOWN_SECONDS = 15
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency_sos)
        
        // Initialize services
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        firestore = FirebaseFirestore.getInstance()
        sessionManager = SessionManager(this)
        
        // Initialize UI
        initViews()
        setupListeners()
        
        // Check permissions
        checkLocationPermission()
        
        // Load emergency contacts
        loadEmergencyContacts()
    }
    
    private fun initViews() {
        backButton = findViewById(R.id.btnBack)
        sosButton = findViewById(R.id.sosButton)
        callPoliceButton = findViewById(R.id.callPoliceButton)
        callAmbulanceButton = findViewById(R.id.callAmbulanceButton)
        callSupportButton = findViewById(R.id.callSupportButton)
        
        emergencyContact1Card = findViewById(R.id.contact1Card)
        emergencyContact2Card = findViewById(R.id.contact2Card)
        emergencyContact1Name = findViewById(R.id.contact1Name)
        emergencyContact1Phone = findViewById(R.id.contact1Phone)
        emergencyContact2Name = findViewById(R.id.contact2Name)
        emergencyContact2Phone = findViewById(R.id.contact2Phone)
        addContactButton = findViewById(R.id.addContactButton)
        
        countdownText = findViewById(R.id.countdownText)
        cancelSOSButton = findViewById(R.id.cancelSOSButton)
        sosActivatedView = findViewById(R.id.sosActivatedView)
    }
    
    private fun setupListeners() {
        // Back button
        backButton.setOnClickListener {
            onBackPressed()
        }
        
        // SOS Button
        sosButton.setOnClickListener {
            startSOSCountdown()
        }
        
        // Cancel SOS Button
        cancelSOSButton.setOnClickListener {
            cancelSOS()
        }
        
        // Emergency Call Buttons
        callPoliceButton.setOnClickListener {
            callEmergencyService("911")
        }
        
        callAmbulanceButton.setOnClickListener {
            callEmergencyService("911")
        }
        
        callSupportButton.setOnClickListener {
            callEmergencyService("+18005551234") // Support number
        }
        
        // Emergency Contact Actions
        emergencyContact1Card.setOnClickListener {
            if (emergencyContacts.isNotEmpty()) {
                callEmergencyService(emergencyContacts[0].phoneNumber)
            }
        }
        
        emergencyContact2Card.setOnClickListener {
            if (emergencyContacts.size > 1) {
                callEmergencyService(emergencyContacts[1].phoneNumber)
            }
        }
        
        // Add Contact Button
        addContactButton.setOnClickListener {
            showAddContactDialog()
        }
    }
    
    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            getCurrentLocation()
        }
    }
    
    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        currentLocation = location
                    }
                }
        }
    }
    
    private fun loadEmergencyContacts() {
        val user = sessionManager.getUser() ?: return
        
        if (!NetworkUtils.isNetworkAvailable(this)) {
            UIUtils.showSnackbar(findViewById(android.R.id.content), "No internet connection")
            return
        }
        
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val contactsSnapshot = firestore.collection("users")
                    .document(user.id)
                    .collection("emergency_contacts")
                    .get()
                    .await()
                
                val contacts = contactsSnapshot.documents.mapNotNull { doc ->
                    doc.toObject(EmergencyContact::class.java)?.copy(id = doc.id)
                }
                
                withContext(Dispatchers.Main) {
                    emergencyContacts = contacts.toMutableList()
                    updateEmergencyContactsUI()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    UIUtils.showSnackbar(
                        findViewById(android.R.id.content),
                        "Error loading contacts: ${e.message}"
                    )
                }
            }
        }
    }
    
    private fun updateEmergencyContactsUI() {
        if (emergencyContacts.isEmpty()) {
            emergencyContact1Card.visibility = View.GONE
            emergencyContact2Card.visibility = View.GONE
            addContactButton.text = "Add Emergency Contact"
        } else {
            // First contact
            emergencyContact1Card.visibility = View.VISIBLE
            emergencyContact1Name.text = emergencyContacts[0].name
            emergencyContact1Phone.text = emergencyContacts[0].phoneNumber
            
            // Second contact
            if (emergencyContacts.size > 1) {
                emergencyContact2Card.visibility = View.VISIBLE
                emergencyContact2Name.text = emergencyContacts[1].name
                emergencyContact2Phone.text = emergencyContacts[1].phoneNumber
                addContactButton.text = "Edit Contacts"
            } else {
                emergencyContact2Card.visibility = View.GONE
                addContactButton.text = "Add Another Contact"
            }
        }
    }
    
    private fun showAddContactDialog() {
        // In a real app, this would show a contact picker or form
        // For demo, we'll just add a mock contact
        val mockContact = EmergencyContact(
            id = System.currentTimeMillis().toString(),
            name = "Emergency Contact ${emergencyContacts.size + 1}",
            phoneNumber = "+1234567890",
            relationship = "Family"
        )
        
        emergencyContacts.add(mockContact)
        updateEmergencyContactsUI()
        
        UIUtils.showSnackbar(
            findViewById(android.R.id.content),
            "Emergency contact added"
        )
    }
    
    private fun startSOSCountdown() {
        if (isSOSActive) return
        
        countdownText.visibility = View.VISIBLE
        cancelSOSButton.visibility = View.VISIBLE
        sosButton.visibility = View.GONE
        
        countdownTimer = object : CountDownTimer(EMERGENCY_COUNTDOWN_SECONDS * 1000L, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                countdownText.text = "SOS will be triggered in $secondsRemaining seconds"
            }
            
            override fun onFinish() {
                activateSOS()
            }
        }.start()
    }
    
    private fun cancelSOS() {
        countdownTimer?.cancel()
        countdownText.visibility = View.GONE
        cancelSOSButton.visibility = View.GONE
        sosButton.visibility = View.VISIBLE
        
        if (isSOSActive) {
            isSOSActive = false
            sosActivatedView.visibility = View.GONE
            
            // In a real app, we would notify the backend that SOS has been canceled
        }
    }
    
    private fun activateSOS() {
        isSOSActive = true
        countdownText.visibility = View.GONE
        cancelSOSButton.visibility = View.VISIBLE
        sosActivatedView.visibility = View.VISIBLE
        
        getCurrentLocation()
        
        // Send SOS notification to emergency contacts
        sendSOSNotification()
        
        // Send SOS alert to support team
        sendSOSToSupport()
    }
    
    private fun sendSOSNotification() {
        // In a real app, this would send SMS or push notifications to emergency contacts
        // For demo, just show a toast
        UIUtils.showToast(this, "SOS notification sent to emergency contacts")
    }
    
    private fun sendSOSToSupport() {
        val user = sessionManager.getUser() ?: return
        
        if (!NetworkUtils.isNetworkAvailable(this)) {
            UIUtils.showSnackbar(findViewById(android.R.id.content), "No internet connection")
            return
        }
        
        val sosData = hashMapOf(
            "userId" to user.id,
            "userName" to user.name,
            "phoneNumber" to user.phone,
            "latitude" to (currentLocation?.latitude ?: 0.0),
            "longitude" to (currentLocation?.longitude ?: 0.0),
            "timestamp" to System.currentTimeMillis(),
            "resolved" to false
        )
        
        CoroutineScope(Dispatchers.IO).launch {
            try {
                firestore.collection("sos_alerts")
                    .add(sosData)
                    .await()
                
                withContext(Dispatchers.Main) {
                    UIUtils.showSnackbar(
                        findViewById(android.R.id.content),
                        "SOS alert sent to support team"
                    )
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    UIUtils.showSnackbar(
                        findViewById(android.R.id.content),
                        "Failed to send SOS alert: ${e.message}"
                    )
                }
            }
        }
    }
    
    private fun callEmergencyService(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phoneNumber")
        startActivity(intent)
    }
    
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation()
            } else {
                UIUtils.showSnackbar(
                    findViewById(android.R.id.content),
                    "Location permission is required for SOS functionality"
                )
            }
        }
    }
    
    override fun onBackPressed() {
        if (isSOSActive) {
            MaterialAlertDialogBuilder(this)
                .setTitle("SOS Active")
                .setMessage("You have an active SOS alert. Are you sure you want to leave this screen?")
                .setPositiveButton("Stay") { dialog, _ -> dialog.dismiss() }
                .setNegativeButton("Leave") { _, _ -> super.onBackPressed() }
                .show()
        } else {
            super.onBackPressed()
        }
    }
} 