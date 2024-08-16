package com.example.projectadd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectadd.adapters.RCVPrescriptionsAdapter
import com.example.projectadd.databinding.ActivityPatientDeactPrescriptionBinding
import com.example.projectadd.models.Patient
import com.example.projectadd.models.P
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson

class PatientDeactPrescriptionActivity : AppCompatActivity() {
    private var currentPatient: Patient? = null
    private lateinit var abhaID:String;
    private  lateinit var p: P
    private  lateinit var binding: ActivityPatientDeactPrescriptionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientDeactPrescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        abhaID = intent.getStringExtra("ABHA_ID")!!

        binding.rcvPrescriptions.layoutManager = LinearLayoutManager(this)
        fetchPatient();

    }

    private fun fetchPatient() {
        FirebaseFirestore.getInstance().collection("Patients").document(abhaID).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    val document = task.result

                    if (document != null && document.exists()) {
                        // Document exists, process data
                        currentPatient = document.toObject(Patient::class.java)
                        if (currentPatient != null) {

                            if (currentPatient!!.prescriptions.isNotEmpty()) {
                                Toast.makeText(this, "Prescriptions fetched successfully", Toast.LENGTH_SHORT).show()
                                setUpRCV(currentPatient!!.prescriptions);
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

    private fun setUpRCV(prescriptions: ArrayList<P>) {
        var deactivatedPS : ArrayList<P> = ArrayList()
        for(i in 0 .. prescriptions.size - 2) {
            deactivatedPS.add(prescriptions[i]);
        }
        if(deactivatedPS.size == 0) {
            Toast.makeText(this, "No deactivated Prescriptions", Toast.LENGTH_SHORT).show()
            return
        }
        binding.rcvPrescriptions.adapter = RCVPrescriptionsAdapter(this,deactivatedPS,object : RCVPrescriptionsAdapter.PrescriptionListener{
            override fun onPrescriptionClicked(p: P) {
                var intent = Intent(this@PatientDeactPrescriptionActivity,ActivityPrescriptionDetails::class.java)
                intent.putExtra("PRESCRIPTION",Gson().toJson(p));
                startActivity(intent)
            }

        })

    }}