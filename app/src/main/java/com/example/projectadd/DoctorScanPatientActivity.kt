package com.example.projectadd

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class DoctorScanPatientActivity : AppCompatActivity() {
    private lateinit var scanQRPatient:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_scan_patient)
        scanQRPatient=findViewById(R.id.scanQRPatient)
        scanQRPatient.setOnClickListener {
            val intent= Intent(this,DoctorHomeActivity::class.java)
            startActivity(intent)
        }
    }}
