package com.example.projectadd

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectadd.adapters.RCVMedicinesAdapter
import com.example.projectadd.databinding.ActivityDeactivatedPrescriptionDetailsBinding
import com.example.projectadd.models.P
import com.google.gson.Gson

class DeactivatedPrescriptionDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDeactivatedPrescriptionDetailsBinding
    private lateinit var p: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeactivatedPrescriptionDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve the prescription data passed through intent
        val json = intent.getStringExtra("PRESCRIPTION")
        if (json != null) {
            p = Gson().fromJson(json, P::class.java)
            setUpPrescriptionDetails(p)
        } else {
            Toast.makeText(this, "Error loading prescription", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpPrescriptionDetails(p: P) {
        binding.tvDiseaseName.text = p.disease
        binding.tvDoctorName.text = p.doctorName

        val dateFormat = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
        binding.fetchdateP.text = "Date: ${dateFormat.format(p.date)}"

        // Set up RecyclerView for medicines
        binding.rcvMedicines.layoutManager = LinearLayoutManager(this)
        binding.rcvMedicines.adapter = RCVMedicinesAdapter(this, p.mL)

        // Set up prescribed tests
        setUpPrescribedTests(p.tests)
    }

    private fun setUpPrescribedTests(tests: List<String>) {
        binding.llPrescribedTests.removeAllViews()

        if (tests.isNotEmpty()) {
            tests.forEach { test ->
                val testView = TextView(this).apply {
                    text = "• $test"
                    textSize = 16f
                    setPadding(0, 8, 0, 8)
                }
                binding.llPrescribedTests.addView(testView)
            }
        } else {
            val noTestsView = TextView(this).apply {
                text = "No tests prescribed"
                textSize = 16f
                setPadding(0, 8, 0, 8)
            }
            binding.llPrescribedTests.addView(noTestsView)
        }
    }
}
