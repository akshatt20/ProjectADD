package com.example.projectadd

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class DoctorScanPatientActivity : AppCompatActivity() {
    private lateinit var scanQRPatient: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_scan_patient)
        scanQRPatient = findViewById(R.id.scanQRPatient)
        scanQRPatient.setOnClickListener {
            startQRScanner()
        }
    }

    private fun startQRScanner() {
        IntentIntegrator(this@DoctorScanPatientActivity)
            .setOrientationLocked(true) // Unlock orientation to allow both landscape and portrait
            .initiateScan() // Start the QR scanner
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == IntentIntegrator.REQUEST_CODE) {
            val result: IntentResult? = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if (result != null && result.contents != null) {
                // Handle the scanned QR code data here
                val scannedData = result.contents
                // Do whatever you need to do with the scanned data

                // Start the activity that hosts the PatientHomeFragment
                val intent = Intent(this, PatientDetailsActivity::class.java)
                startActivity(intent)
            } else {
                // Handle case where user canceled the scan or no QR code was found
            }
        }
    }
}


