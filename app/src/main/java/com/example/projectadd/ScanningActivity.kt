package com.example.projectadd

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.Manifest
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ScanningActivity : AppCompatActivity() {
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var previewView: PreviewView
    private lateinit var scanButton: Button
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanning)
        previewView = findViewById(R.id.previewView)
        scanButton = findViewById(R.id.scanButton)
        db = FirebaseFirestore.getInstance()
        scanButton.setOnClickListener {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                ActivityCompat.requestPermissions(
                    this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
                )
            }
        }

        cameraExecutor = Executors.newSingleThreadExecutor()
    }
    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }
            val imageAnalyzer = ImageAnalysis.Builder()
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, QRCodeAnalyzer { qrCode ->
                        // QR code detected
                        processScannedQRCode(qrCode)
                    })
                }

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, CameraSelector.DEFAULT_BACK_CAMERA, preview, imageAnalyzer
                )
                // Hide the scan button and show the preview
                scanButton.visibility = android.view.View.GONE
                previewView.visibility = android.view.View.VISIBLE
            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }
    private fun processScannedQRCode(qrCode: String) {
        Log.d(TAG, "Scanned QR Code: $qrCode")

        // Verify if the scanned QR code is a valid ABHA ID
        verifyABHAID(qrCode)
    }
    private fun verifyABHAID(abhaId: String) {
        db.collection("Patients").document(abhaId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    // ABHA ID is valid, navigate to DoctorHomeActivity
                    navigateToDoctorHome(abhaId)
                } else {
                    // ABHA ID not found
                    Toast.makeText(this, "Invalid ABHA ID", Toast.LENGTH_SHORT).show()
                    // Show the scan button again
                    scanButton.visibility = android.view.View.VISIBLE
                    previewView.visibility = android.view.View.GONE
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting document:", exception)
                Toast.makeText(this, "Error verifying ABHA ID", Toast.LENGTH_SHORT).show()
                // Show the scan button again
                scanButton.visibility = android.view.View.VISIBLE
                previewView.visibility = android.view.View.GONE
            }
    }
    private fun navigateToDoctorHome(abhaId: String) {
        val intent = Intent(this, DoctorHomeActivity::class.java)
        intent.putExtra("ABHA_ID", abhaId)
        startActivity(intent)
        finish() // Close the QRScannerActivity
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
                Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
    private class QRCodeAnalyzer(private val onQRCodeDetected: (String) -> Unit) : ImageAnalysis.Analyzer {
        @androidx.camera.core.ExperimentalGetImage

        override fun analyze(image: ImageProxy) {
            val img = image.image
            if (img != null) {
                val inputImage = InputImage.fromMediaImage(img, image.imageInfo.rotationDegrees)

                val scanner = BarcodeScanning.getClient()
                scanner.process(inputImage)
                    .addOnSuccessListener { barcodes ->
                        for (barcode in barcodes) {
                            barcode.rawValue?.let { onQRCodeDetected(it) }
                        }
                    }
                    .addOnFailureListener {
                        Log.e(TAG, "Barcode scanning failed", it)
                    }
                    .addOnCompleteListener {
                        image.close()
                    }
            }
        }
    }

    companion object {
        private const val TAG = "QRScannerActivity"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
}
}
