package com.tranxortrider.deliveryrider

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.tranxortrider.deliveryrider.repositories.OrderRepository
import com.tranxortrider.deliveryrider.utils.UIUtils
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import android.widget.EditText
import android.widget.TextView

class scanner : AppCompatActivity() {
    
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var previewView: PreviewView
    private lateinit var scannerOverlay: View
    private lateinit var scannerIndicator: View
    private lateinit var loadingView: View
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var flashButton: MaterialButton
    private lateinit var manualEntryButton: MaterialButton
    private lateinit var scanInstructionsText: TextView
    
    private var flashEnabled = false
    private lateinit var orderRepository: OrderRepository
    private var isScanningPaused = false
    private var scanMode: ScanMode = ScanMode.ORDER_CODE
    
    // Scan modes
    enum class ScanMode {
        ORDER_CODE,      // Scanning an order QR code
        DELIVERY_CODE,   // Scanning a delivery verification code
        PACKAGE_CODE     // Scanning a package barcode
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_scanner)
        
        // Initialize views
        previewView = findViewById(R.id.previewView)
        scannerOverlay = findViewById(R.id.scannerOverlay)
        scannerIndicator = findViewById(R.id.scannerIndicator)
        loadingView = findViewById(R.id.loadingView)
        bottomNavigation = findViewById(R.id.bottomNavigation)
        flashButton = findViewById(R.id.btnFlash)
        manualEntryButton = findViewById(R.id.btnManualEntry)
        scanInstructionsText = findViewById(R.id.scanInstructionsText)
        
        // Initialize dependencies
        orderRepository = OrderRepository()
        cameraExecutor = Executors.newSingleThreadExecutor()
        
        // Set up bottom navigation
        setupBottomNavigation()
        
        // Get scan mode from intent
        scanMode = intent.getSerializableExtra("SCAN_MODE") as? ScanMode ?: ScanMode.ORDER_CODE
        
        // Update UI based on scan mode
        updateScanInstructions()
        
        // Request camera permission
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }
        
        // Set up click listeners
        flashButton.setOnClickListener {
            toggleFlash()
        }
        
        manualEntryButton.setOnClickListener {
            enterCodeManually()
        }
        
        // Set up close button
        findViewById<MaterialButton>(R.id.btnClose).setOnClickListener {
            finish()
        }
    }
    
    private fun updateScanInstructions() {
        scanInstructionsText.text = when (scanMode) {
            ScanMode.ORDER_CODE -> "Scan the order QR code to view details"
            ScanMode.DELIVERY_CODE -> "Scan the delivery verification code to complete delivery"
            ScanMode.PACKAGE_CODE -> "Scan the package barcode to track the package"
        }
    }
    
    private fun setupBottomNavigation() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        if (bottomNavigationView != null) {
            // Make the bottom navigation visible
            bottomNavigationView.visibility = View.VISIBLE
            // Use the NavigationUtils to set up bottom navigation
            com.tranxortrider.deliveryrider.utils.NavigationUtils.setupBottomNavigation(this, bottomNavigationView)
        }
    }
    
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }
    
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(this, "Camera permission required", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
    
    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        
        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            
            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }
            
            // Image analyzer for barcode scanning
            val imageAnalyzer = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, BarcodeAnalyzer { barcodes ->
                        if (isScanningPaused) return@BarcodeAnalyzer
                        
                        barcodes.firstOrNull()?.let { barcode ->
                            if (barcode.valueType == Barcode.TYPE_TEXT || 
                                barcode.valueType == Barcode.TYPE_URL ||
                                barcode.valueType == Barcode.TYPE_PRODUCT) {
                                
                                val scannedValue = barcode.rawValue ?: return@let
                                handleScannedBarcode(scannedValue)
                            }
                        }
                    })
                }
            
            // Select back camera as default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            
            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()
                
                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageAnalyzer)
                
            } catch (e: Exception) {
                UIUtils.showSnackbar(findViewById(android.R.id.content), "Camera initialization failed")
            }
            
        }, ContextCompat.getMainExecutor(this))
    }
    
    private fun handleScannedBarcode(barcode: String) {
        // Prevent multiple scans of the same barcode
        isScanningPaused = true
        
        // Show loading
        runOnUiThread {
            loadingView.visibility = View.VISIBLE
            scannerIndicator.visibility = View.GONE
        }
        
        // Process based on scan mode
        when (scanMode) {
            ScanMode.ORDER_CODE -> processOrderCode(barcode)
            ScanMode.DELIVERY_CODE -> processDeliveryCode(barcode)
            ScanMode.PACKAGE_CODE -> processPackageCode(barcode)
        }
    }
    
    private fun processOrderCode(barcode: String) {
        // Check if it's an order code
        if (barcode.startsWith("ORDER:")) {
            val orderId = barcode.substring(6)
            lookupOrder(orderId)
        } else {
            // Try to use it as an order ID directly
            lookupOrder(barcode)
        }
    }
    
    private fun processDeliveryCode(barcode: String) {
        val orderId = intent.getStringExtra("ORDER_ID") ?: ""
        if (orderId.isEmpty()) {
            showErrorDialog("Error", "No order ID provided")
            return
        }
        
        // Verify the delivery code
        verifyDeliveryCode(barcode, orderId)
    }
    
    private fun processPackageCode(barcode: String) {
        // Check if it's a package code
        if (barcode.startsWith("PKG:")) {
            val packageId = barcode.substring(4)
            scanPackage(packageId)
        } else {
            // Try to use it as a package ID directly
            scanPackage(barcode)
        }
    }
    
    private fun lookupOrder(orderId: String) {
        orderRepository.getOrderDetails(orderId) { success, message, order ->
            if (success && order != null) {
                navigateToOrderDetails(order.id)
            } else {
                showErrorDialog("Order Not Found", message)
            }
        }
    }
    
    private fun verifyDeliveryCode(code: String, orderId: String) {
        // Show loading
        showLoading("Verifying code...")
        
        orderRepository.verifyDeliveryCode(orderId, code) { success, message ->
            runOnUiThread {
                hideLoading()
                
                if (success) {
                    // Show success dialog
                    showSuccessDialog("Delivery Verified", "Order delivered successfully") {
                        // Go back to order details with result
                        val resultIntent = Intent()
                        resultIntent.putExtra("DELIVERY_VERIFIED", true)
                        setResult(RESULT_OK, resultIntent)
                        finish()
                    }
                } else {
                    // Show error
                    showErrorDialog("Verification Failed", message)
                }
            }
        }
    }
    
    private fun scanPackage(packageId: String) {
        // Show loading
        showLoading("Scanning package...")
        
        orderRepository.scanPackage(packageId) { success, message ->
            runOnUiThread {
                hideLoading()
                
                if (success) {
                    // Show success dialog
                    showSuccessDialog("Package Scanned", message) {
                        // Go back with result
                        val resultIntent = Intent()
                        resultIntent.putExtra("PACKAGE_SCANNED", true)
                        resultIntent.putExtra("PACKAGE_ID", packageId)
                        setResult(RESULT_OK, resultIntent)
                        finish()
                    }
                } else {
                    // Show error
                    showErrorDialog("Scan Failed", message)
                }
            }
        }
    }
    
    private fun navigateToOrderDetails(orderId: String) {
        val intent = Intent(this, order_details::class.java)
        intent.putExtra("ORDER_ID", orderId)
        startActivity(intent)
        finish()
    }
    
    private fun toggleFlash() {
        // This would be implemented to toggle the camera flash
        flashEnabled = !flashEnabled
        flashButton.setIconResource(
            if (flashEnabled) R.drawable.ic_flash_on else R.drawable.ic_flash_off
        )
        // In a real implementation, you would control the camera's torch/flash here
    }
    
    private fun enterCodeManually() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_manual_entry, null)
        val editText = dialogView.findViewById<EditText>(R.id.codeEditText)
        
        val title = when (scanMode) {
            ScanMode.ORDER_CODE -> "Enter Order Code"
            ScanMode.DELIVERY_CODE -> "Enter Delivery Code"
            ScanMode.PACKAGE_CODE -> "Enter Package Code"
        }
        
        MaterialAlertDialogBuilder(this)
            .setTitle(title)
            .setView(dialogView)
            .setPositiveButton("Submit") { _, _ ->
                val code = editText.text.toString().trim()
                if (code.isNotEmpty()) {
                    handleScannedBarcode(code)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun showLoading(message: String) {
        UIUtils.showLoading(this, message)
    }
    
    private fun hideLoading() {
        UIUtils.hideLoading()
        resetScanner()
    }
    
    private fun resetScanner() {
        runOnUiThread {
            loadingView.visibility = View.GONE
            scannerIndicator.visibility = View.VISIBLE
            isScanningPaused = false
        }
    }
    
    private fun showSuccessDialog(title: String, message: String, onDismiss: () -> Unit = {}) {
        UIUtils.showAlert(this, title, message, "OK") {
            onDismiss()
        }
    }
    
    private fun showErrorDialog(title: String, message: String) {
        UIUtils.showAlert(this, title, message, "OK") {
            resetScanner()
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
    
    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS =
            arrayOf(Manifest.permission.CAMERA)
    }
    
    private inner class BarcodeAnalyzer(private val onBarcodesDetected: (List<Barcode>) -> Unit) :
        ImageAnalysis.Analyzer {
        
        private val scanner: BarcodeScanner = BarcodeScanning.getClient(
            BarcodeScannerOptions.Builder()
                .setBarcodeFormats(
                    Barcode.FORMAT_QR_CODE,
                    Barcode.FORMAT_CODE_128,
                    Barcode.FORMAT_CODE_39,
                    Barcode.FORMAT_EAN_13,
                    Barcode.FORMAT_UPC_A
                )
                .build()
        )
        
        @androidx.camera.core.ExperimentalGetImage
        override fun analyze(imageProxy: ImageProxy) {
            val mediaImage = imageProxy.image
            if (mediaImage != null) {
                val image = InputImage.fromMediaImage(
                    mediaImage,
                    imageProxy.imageInfo.rotationDegrees
                )
                
                scanner.process(image)
                    .addOnSuccessListener { barcodes ->
                        if (barcodes.isNotEmpty()) {
                            onBarcodesDetected(barcodes)
                        }
                    }
                    .addOnFailureListener {
                        // Handle any errors
                    }
                    .addOnCompleteListener {
                        // When the image is from CameraX analysis use case, must call image.close() on received
                        // images when finished using them. Otherwise, new images may not be received or the camera
                        // may stall.
                        imageProxy.close()
                    }
            } else {
                imageProxy.close()
            }
        }
    }
}