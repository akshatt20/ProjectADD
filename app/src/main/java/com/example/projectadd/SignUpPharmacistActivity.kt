package com.example.projectadd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class SignUpPharmacistActivity : AppCompatActivity() {
    private lateinit var logInDirectToSignUpPharmacist: TextView
    private lateinit var pharmacistName: EditText
    private lateinit var pharmacistMobileNoemail: EditText
    private lateinit var pharmacistPassword: EditText
    private lateinit var pharmacistLocation: EditText
    private lateinit var pharmacistDegree: EditText
    private lateinit var pharmacistLicenseNo: EditText
    private lateinit var pharmacistSignUpButton: Button
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_pharmacist)

        // Initialize Firestore
        db = FirebaseFirestore.getInstance()

        logInDirectToSignUpPharmacist=findViewById(R.id.logInDirectToSignUpPharmacist)
        pharmacistName = findViewById(R.id.pharmacistName)
        pharmacistMobileNoemail = findViewById(R.id.pharmacistMobileNoemail)
        pharmacistPassword = findViewById(R.id.pharmacistPassword)
        pharmacistLocation = findViewById(R.id.pharmacistLocation)
        pharmacistDegree = findViewById(R.id.pharmacistDegree)
        pharmacistLicenseNo = findViewById(R.id.pharmacistLicenseNo)
        pharmacistSignUpButton = findViewById(R.id.pharmacistSignUpButton)

        pharmacistSignUpButton.setOnClickListener {
            // Get the values from EditText fields
            val name = pharmacistName.text.toString()
            val mobileNoemail = pharmacistMobileNoemail.text.toString()
            val password = pharmacistPassword.text.toString()
            val location = pharmacistLocation.text.toString()
            val degree = pharmacistDegree.text.toString()
            val licenseNo = pharmacistLicenseNo.text.toString()

            // Ensure fields are not empty
            if (name.isEmpty() || mobileNoemail.isEmpty() || password.isEmpty() || location.isEmpty() || degree.isEmpty() || licenseNo.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create a Pharmacist object
            val pharmacist = hashMapOf(
                "name" to name,
                "mobileNoemail" to mobileNoemail,
                "password" to password,
                "location" to location,
                "degree" to degree,
                "licenseNo" to licenseNo
            )

            // Add a new document with a generated ID
            db.collection("Pharmacists")
                .add(pharmacist)
                .addOnSuccessListener {
                    Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }

        logInDirectToSignUpPharmacist.setOnClickListener {
            val intent= Intent(this, LogInPharmaCistActivity::class.java)
            startActivity(intent)
        }
    }
}
