package com.example.projectadd.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectadd.PatientActPrescriptionActivity
import com.example.projectadd.R
import com.example.projectadd.adapters.RCVPrescriptionsAdapter
import com.example.projectadd.models.P
import com.example.projectadd.models.Patient
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class PatientHistoryFragment(private val abhaID: String) : Fragment() {

    private lateinit var activatedPrescriptionCard: LinearLayout
    private lateinit var tvDoctorNameActivated: TextView
    private lateinit var tvDiseaseNameActivated: TextView
    private lateinit var tvDateActivated: TextView
    private lateinit var tvTimeActivated: TextView
    private lateinit var rcvDeactivatedPrescriptions: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var adapter: RCVPrescriptionsAdapter
    private var currentPatient: Patient? = null
    private var activatedPrescription: P? = null  // Store the activated prescription separately

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_patient_home, container, false)

        // Initialize Activated Prescription Card Views
        activatedPrescriptionCard = view.findViewById(R.id.activatedPrescriptionCard)
        tvDoctorNameActivated = view.findViewById(R.id.tvDoctorNameActivated)
        tvDiseaseNameActivated = view.findViewById(R.id.tvDiseaseNameActivated)
        tvDateActivated = view.findViewById(R.id.tvDateActivated)
        tvTimeActivated = view.findViewById(R.id.tvTimeActivated)

        // Initialize RecyclerView for Deactivated Prescriptions
        rcvDeactivatedPrescriptions = view.findViewById(R.id.rcvDeactivatedPrescription)
        rcvDeactivatedPrescriptions.layoutManager = LinearLayoutManager(requireContext())

        // Initialize SearchView
        searchView = view.findViewById(R.id.searchView)

        // Initialize Adapter
        adapter = RCVPrescriptionsAdapter(requireContext(), ArrayList(), object : RCVPrescriptionsAdapter.PrescriptionListener {
            override fun onPrescriptionClicked(p: P) {
                Toast.makeText(requireContext(), "Clicked on: ${p.disease}", Toast.LENGTH_SHORT).show()
            }
        })
        rcvDeactivatedPrescriptions.adapter = adapter

        // Fetch Deactivated Prescriptions
        fetchDeactivatedPrescriptions()

        // Fetch the activated prescription and populate the card
        fetchActivatedPrescription()

        // Set onClickListener for the Activated Prescription Card
        activatedPrescriptionCard.setOnClickListener {
            val intent = Intent(requireContext(), PatientActPrescriptionActivity::class.java)
            intent.putExtra("ABHA_ID", abhaID)
            startActivity(intent)
        }

        // Setup search functionality
        setupSearchView()

        return view
    }

    private fun fetchActivatedPrescription() {
        FirebaseFirestore.getInstance().collection("Patients").document(abhaID).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document != null && document.exists()) {
                        currentPatient = document.toObject(Patient::class.java)
                        if (currentPatient != null && currentPatient!!.prescriptions.isNotEmpty()) {
                            // Get the most recent (activated) prescription
                            activatedPrescription = currentPatient!!.prescriptions.last()
                            populateActivatedPrescription(activatedPrescription!!)
                        } else {
                            Toast.makeText(requireContext(), "No activated prescription found", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(requireContext(), "No such document", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Error fetching data", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun fetchDeactivatedPrescriptions() {
        FirebaseFirestore.getInstance().collection("Patients").document(abhaID).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document != null && document.exists()) {
                        currentPatient = document.toObject(Patient::class.java)
                        if (currentPatient != null && currentPatient!!.prescriptions.isNotEmpty()) {
                            val prescriptions = currentPatient!!.prescriptions
                            if (prescriptions.size > 1) {
                                // All except the last prescription are considered deactivated
                                val deactivatedPrescriptions = prescriptions.dropLast(1)
                                updateDeactivatedPrescriptions(ArrayList(deactivatedPrescriptions))
                            } else {
                                Toast.makeText(requireContext(), "No deactivated prescriptions", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(requireContext(), "No prescriptions found", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(requireContext(), "No such document", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Error fetching data", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun updateDeactivatedPrescriptions(deactivatedPrescriptions: ArrayList<P>) {
        if (deactivatedPrescriptions.isEmpty()) {
            Toast.makeText(requireContext(), "No deactivated prescriptions", Toast.LENGTH_SHORT).show()
        } else {
            adapter.updateList(deactivatedPrescriptions)
        }
    }

    private fun populateActivatedPrescription(prescription: P) {
        tvDoctorNameActivated.text = prescription.doctorName
        tvDiseaseNameActivated.text = prescription.disease

        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())

        tvDateActivated.text = dateFormat.format(prescription.date)
        tvTimeActivated.text = timeFormat.format(prescription.date)
    }

    private fun setupSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    filterPrescriptions(it)  // Apply the filter when the query is submitted
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    filterPrescriptions(it)  // Apply the filter as the user types
                }
                return true
            }
        })
    }

    private fun filterPrescriptions(query: String) {
        val lowerCaseQuery = query.lowercase(Locale.getDefault())

        // Date and time formats
        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())

        // Filter the activated prescription separately
        activatedPrescription?.let {
            val matchesActivated = it.doctorName.lowercase(Locale.getDefault()).contains(lowerCaseQuery)
                    || it.disease.lowercase(Locale.getDefault()).contains(lowerCaseQuery)
                    || dateFormat.format(it.date).contains(lowerCaseQuery)
                    || timeFormat.format(it.date).contains(lowerCaseQuery)

            activatedPrescriptionCard.visibility = if (matchesActivated) View.VISIBLE else View.GONE
        }

        // Filter the deactivated prescriptions through the adapter
        adapter.filter(lowerCaseQuery)
    }
}
