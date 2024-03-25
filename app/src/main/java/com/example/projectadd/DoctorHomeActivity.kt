package com.example.projectadd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class DoctorHomeActivity : AppCompatActivity() {
    private lateinit var newPatientButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_home)

        newPatientButton = findViewById(R.id.newPatient)
        newPatientButton.setOnClickListener {
            navigateToDoctorScanPatientActivity()
        }
    }

    private fun navigateToDoctorScanPatientActivity() {
        val intent = Intent(this, DoctorScanPatientActivity::class.java)
        startActivity(intent)
    }
}
