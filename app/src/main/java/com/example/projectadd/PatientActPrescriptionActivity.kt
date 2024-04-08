package com.example.projectadd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectadd.adapters.RCVMedicinesAdapter
import com.example.projectadd.databinding.ActivityPatientActPrescriptionBinding
import com.example.projectadd.models.Patient
import com.example.projectadd.models.Prescription
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.math.log

class PatientActPrescriptionActivity() : AppCompatActivity() {
    private var currentPatient: Patient? = null
    private lateinit var abhaID:String;
    val TAG = "Act"

    private  lateinit var prescription: Prescription
    private lateinit var binding:ActivityPatientActPrescriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientActPrescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        abhaID = intent.getStringExtra("ABHA_ID")!!

        // Access a Cloud Firestore instance
        val firestore = FirebaseFirestore.getInstance()

        // Assuming 'Patients' is your collection name
        Log.i(TAG, "onCreate: id  = "+abhaID)
        val documentId = intent.getStringExtra("ABHA_ID") // Replace "patientId" with the actual key used to pass the document ID
        if (!documentId.isNullOrEmpty()) {
            firestore.collection("Patients").document(abhaID).get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        val document = task.result

                        if (document != null && document.exists()) {
                            // Document exists, process data
                            Log.i(TAG, "document exists")

                            currentPatient = document.toObject(Patient::class.java)
                            if (currentPatient != null) {
                                Log.i(TAG, "patient exists")

                                if (currentPatient!!.prescriptions.isNotEmpty()) {
                                    prescription = currentPatient!!.prescriptions[currentPatient!!.prescriptions.size-1]

                                    setUpRCV(prescription);
                                } else {
                                    // Prescription is empty or null
                                    Toast.makeText(
                                        applicationContext,
                                        "Null Prescription",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    // Handle this case, for example, hide the QR code view or take appropriate action
                                }
                            }
                        } else {
                            // Document doesn't exist
                            // Handle this case, for example, show a message to the user
                            Log.d("PatientActPrescription", "No such document")
                        }
                    } else {
                        // Handle errors
                        Log.e("PatientActPrescription", "Error fetching patient data", task.exception)
                        Toast.makeText(
                            applicationContext,
                            "Error fetching patient data",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

    private fun setUpRCV(prescription: Prescription) {
        Log.i(TAG, "setUpRCV: ${prescription.disease}")
        Log.i(TAG, "setUpRCV: ${prescription.medicinesList.get(0).id}")
        Log.i(TAG, "setUpRCV: ${prescription.medicinesList.get(0).name}")
        binding.tvDiseaseName.text = prescription.disease
        binding.rcvMedicines.layoutManager = LinearLayoutManager(this)
        binding.rcvMedicines.adapter = RCVMedicinesAdapter(this,prescription.medicinesList)


    }
}