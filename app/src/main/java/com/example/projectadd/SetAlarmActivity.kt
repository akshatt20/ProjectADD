package com.example.projectadd

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectadd.adapters.RCVPrescriptionsAdapter
import com.example.projectadd.models.P
import com.example.projectadd.models.Patient
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Locale

class SetAlarmActivity : AppCompatActivity() {

    private lateinit var activatedPrescriptionCard: LinearLayout
    private lateinit var tvDoctorNameActivated: TextView
    private lateinit var tvDiseaseNameActivated: TextView
    private lateinit var rcvDeactivatedPrescriptions: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var adapter: RCVPrescriptionsAdapter
    private var abhaID: String? = null
    private var currentPatient: Patient? = null
    private var activatedPrescription: P? = null  // Store the activated prescription separately

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_alarm)

        abhaID = intent.getStringExtra("ABHA_ID") ?: ""

        // Initialize views
        activatedPrescriptionCard = findViewById(R.id.activatedPrescriptionCard)
        tvDoctorNameActivated = findViewById(R.id.tvDoctorNameActivated)
        tvDiseaseNameActivated = findViewById(R.id.tvDiseaseNameActivated)
        rcvDeactivatedPrescriptions = findViewById(R.id.rcvDeactivatedPrescription)
        rcvDeactivatedPrescriptions.layoutManager = LinearLayoutManager(this)
        searchView = findViewById(R.id.searchView)

        // Initialize Adapter
        adapter = RCVPrescriptionsAdapter(this, ArrayList(), object : RCVPrescriptionsAdapter.PrescriptionListener {
            override fun onPrescriptionClicked(p: P) {
                // Extract medicine names from mL (ArrayList<Md>)
                val medicineNames = ArrayList<String>()
                for (medicine in p.mL) {
                    medicineNames.add(medicine.name)  // Extracting the name of the medicine
                }

                // Pass the medicine names back to AlarmFragment via Intent
                val intent = Intent()
                intent.putStringArrayListExtra("medicines", medicineNames)
                setResult(RESULT_OK, intent)
                finish()  // Finish the activity and return to AlarmFragment
            }
        })
        rcvDeactivatedPrescriptions.adapter = adapter

        // Fetch prescriptions
        fetchPrescriptions()

        // Handle activated prescription card click
        activatedPrescriptionCard.setOnClickListener {
            activatedPrescription?.let { p ->
                val medicineNames = ArrayList<String>()
                for (medicine in p.mL) {
                    medicineNames.add(medicine.name)  // Extracting the name of the medicine
                }

                // Pass the medicine names back to AlarmFragment via Intent
                val intent = Intent()
                intent.putStringArrayListExtra("medicines", medicineNames)
                setResult(RESULT_OK, intent)
                finish()  // Finish the activity and return to AlarmFragment
            }
        }

        // Setup search functionality
        setupSearchView()
    }

    private fun fetchPrescriptions() {
        FirebaseFirestore.getInstance().collection("Patients").document(abhaID!!).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document != null && document.exists()) {
                        currentPatient = document.toObject(Patient::class.java)
                        if (currentPatient != null && currentPatient!!.prescriptions.isNotEmpty()) {
                            // Get the most recent (activated) prescription
                            activatedPrescription = currentPatient!!.prescriptions.last()
                            populateActivatedPrescription(activatedPrescription!!)

                            // Get deactivated prescriptions (all except the last one)
                            if (currentPatient!!.prescriptions.size > 1) {
                                val deactivatedPrescriptions = currentPatient!!.prescriptions.dropLast(1)
                                updateDeactivatedPrescriptions(ArrayList(deactivatedPrescriptions))
                            } else {
                                Toast.makeText(this, "No deactivated prescriptions", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(this, "No prescriptions found", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "No such document", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun populateActivatedPrescription(prescription: P) {
        tvDoctorNameActivated.text = prescription.doctorName
        tvDiseaseNameActivated.text = prescription.disease
    }

    private fun updateDeactivatedPrescriptions(deactivatedPrescriptions: ArrayList<P>) {
        if (deactivatedPrescriptions.isEmpty()) {
            Toast.makeText(this, "No deactivated prescriptions", Toast.LENGTH_SHORT).show()
        } else {
            adapter.updateList(deactivatedPrescriptions)
        }
    }

    private fun setupSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { filterPrescriptions(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { filterPrescriptions(it) }
                return true
            }
        })
    }

    private fun filterPrescriptions(query: String) {
        val lowerCaseQuery = query.toLowerCase(Locale.getDefault())
        adapter.filter(lowerCaseQuery)
    }
}
