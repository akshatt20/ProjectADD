package com.example.projectadd

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class SignUpLabActivity : AppCompatActivity() {
    private lateinit var labName: EditText
    private lateinit var labID: EditText
    private lateinit var labPassword: EditText
    private lateinit var labLocation: EditText
    private lateinit var labSignUpButton: Button
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_lab)

        db = FirebaseFirestore.getInstance()

        labName = findViewById(R.id.labName)
        labID = findViewById(R.id.labID)
        labPassword = findViewById(R.id.labPassword)
        labLocation = findViewById(R.id.labLocation)
        labSignUpButton = findViewById(R.id.labSignUpButton)

        labSignUpButton.setOnClickListener {
            // Get values from EditText fields
            val name = labName.text.toString()
            val id = labID.text.toString()
            val password = labPassword.text.toString()
            val location = labLocation.text.toString()

            // Ensure fields are not empty
            if (name.isEmpty() || id.isEmpty() || password.isEmpty() || location.isEmpty()) {
                // Show a toast message if any field is empty
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create a Lab object
            val lab = hashMapOf(
                "name" to name,
                "id" to id,
                "password" to password,
                "location" to location
            )

            // Add the Lab object to Firestore
            db.collection("Labs")
                .add(lab)
                .addOnSuccessListener { documentReference ->
                    // Show a success message
                    Toast.makeText(this, "Lab added successfully", Toast.LENGTH_SHORT).show()

                    // Redirect to the desired activity
                    val intent = Intent(this, NextActivity::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener { e ->
                    // Show an error message if failed to add Lab
                    Toast.makeText(this, "Error adding Lab: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}