package com.example.projectadd

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.projectadd.fragments.PatientProfileFragment
import com.example.projectadd.models.Patient
import com.google.firebase.firestore.FirebaseFirestore
import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Spinner


class SignUpPatientActivity : AppCompatActivity() {
    private lateinit var loginPatient: TextView
    private lateinit var patientName: EditText
    private lateinit var patientMobile: EditText
    private lateinit var patientAge: EditText
    private lateinit var patientSex: Spinner
    private lateinit var patientPassword: EditText
    private lateinit var patientResidence: EditText
    private lateinit var patientAbhaId: EditText
    private lateinit var patientSignup: Button
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_patient)
        // Initialize Firebase components
        db = FirebaseFirestore.getInstance()

        loginPatient = findViewById(R.id.loginPatient)
        patientName = findViewById(R.id.patientName)
        patientMobile = findViewById(R.id.patientMobile)
        patientAge = findViewById(R.id.patientAge)
        patientSex = findViewById(R.id.patientSex)
        patientPassword = findViewById(R.id.patientPassword)
        patientResidence = findViewById(R.id.patientResidence)
        patientAbhaId = findViewById(R.id.patientAbhaId)
        patientSignup = findViewById(R.id.patientSignup)
        val patientSex: Spinner = findViewById(R.id.patientSex)
        val textSize = patientName.textSize
        val genders = resources.getStringArray(R.array.gender_array)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, genders)
        patientSex.adapter = adapter

        loginPatient.setOnClickListener {
            val intent = Intent(this, LogInPatientActivity::class.java)
            startActivity(intent)
        }
        patientSignup.setOnClickListener {
            // Get the values from EditText fields
            val name = patientName.text.toString()
            val mobile = patientMobile.text.toString()
            val age = patientAge.text.toString()
            val sex = patientSex.selectedItem.toString()
            val password = patientPassword.text.toString()
            val residence = patientResidence.text.toString()
            val abhaid = patientAbhaId.text.toString()

            // Validate fields
            if (name.isEmpty() || mobile.isEmpty() || age.isEmpty() || sex.isEmpty() || password.isEmpty() || residence.isEmpty() || abhaid.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Generate a unique document ID
            val documentId = db.collection("Patients").document().id

            // Add user data to Firestore with the custom document ID
            val patientData = hashMapOf(
                "id" to documentId, // Add the generated document ID
                "name" to name,
                "mobile" to mobile,
                "age" to age,
                "sex" to sex,
                "password" to password,
                "residence" to residence,
                "abhaid" to abhaid // Add Abha ID to patient data
            )
            // "ID" is the key, yourID is the value

            val patient = Patient(name, abhaid, mobile, age, residence, sex,password, ArrayList())

//            val editor = getSharedPreferences("APP_PREFS", MODE_PRIVATE).edit()
//            editor.putString("PATIENT_ID",abhaid);
//            editor.apply();
            db.collection("Patients")
                .document(abhaid)
                .set(patient)
                .addOnSuccessListener {
                    // Add the following line to pass the document ID to the PatientProfileFragment
                    val patientProfileFragment = PatientProfileFragment(abhaid)
                    val bundle = Bundle()
                    bundle.putString("document_id", documentId)
                    patientProfileFragment.arguments = bundle

                    Toast.makeText(this, "Patient signed up successfully", Toast.LENGTH_SHORT).show()
                    // Start PatientDetailsActivity after successful signup
                    val intent = Intent(this, PatientDetailsActivity::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to add user data: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }

        }

}