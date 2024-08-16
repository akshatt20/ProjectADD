package com.example.projectadd.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.projectadd.R
import com.example.projectadd.models.Patient
import com.google.firebase.firestore.FirebaseFirestore

class PatientProfileFragment(private val abhaID: String) : Fragment() {
    private lateinit var fetchNameP: TextView
    private lateinit var fetchAgeP: TextView
    private lateinit var fetchSexP: TextView
    private lateinit var fetchAbhaidP: TextView
    private lateinit var fetchMobileP: TextView
    private lateinit var fetchResidenceP: TextView
    private lateinit var firestore: FirebaseFirestore
    private lateinit var fetchEmergencyNo: TextView
    private var currentPatient: Patient? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_patient_profile, container, false)

        // Initialize your TextViews after inflating the layout
        fetchNameP = rootView.findViewById(R.id.fetchNameP)
        fetchAgeP = rootView.findViewById(R.id.fetchAgeP)
        fetchSexP = rootView.findViewById(R.id.fetchSexP)
        fetchAbhaidP = rootView.findViewById(R.id.fetchAbhaidP)
        fetchMobileP = rootView.findViewById(R.id.fetchMobileP)
        fetchResidenceP = rootView.findViewById(R.id.fetchResidenceP)
        fetchEmergencyNo = rootView.findViewById(R.id.fetchEmergencyNo)

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance()

        // Fetch data from Firestore only if abhaID is not empty
        if (abhaID.isNotEmpty()) {
            firestore.collection("Patients").document(abhaID).get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val document = task.result
                        if (document.exists()) {
                            currentPatient = document.toObject(Patient::class.java)
                            if (currentPatient != null) {
                                updateUI()
                            }
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Patient data not found",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Log.e(
                            "PatientProfileFragment",
                            "Error fetching patient data",
                            task.exception
                        )
                        Toast.makeText(
                            requireContext(),
                            "Error fetching patient data",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

        return rootView
    }

    private fun updateUI() {
        fetchNameP.text = currentPatient!!.name
        fetchAgeP.text = currentPatient!!.age
        fetchSexP.text = currentPatient!!.gender
        fetchAbhaidP.text = currentPatient!!.abhaId
        fetchMobileP.text = currentPatient!!.mobileNo
        fetchResidenceP.text = currentPatient!!.address
        fetchEmergencyNo.text = currentPatient!!.emergency
    }
}