package com.example.projectadd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.projectadd.fragments.PatientProfileFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LogInPatientActivity : AppCompatActivity() {
    private lateinit var signUpPatientOption: TextView
    private lateinit var patientLoginPassword: EditText
    private lateinit var patientLoginId: EditText
    private lateinit var logInPatientBtn: Button
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in_patient)

        signUpPatientOption = findViewById(R.id.signUpPatientOption)
        patientLoginId = findViewById(R.id.patientLoginId)
        patientLoginPassword = findViewById(R.id.patientLoginPassword)
        logInPatientBtn = findViewById(R.id.logInPatientBtn)
        firestore = FirebaseFirestore.getInstance()
        logInPatientBtn.setOnClickListener {
            val username = patientLoginId.text.toString()
            val password = patientLoginPassword.text.toString()
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(applicationContext, "Please fill all details", Toast.LENGTH_SHORT).show()
            } else {

                firestore.collection("Patients").document(username).get().addOnSuccessListener { document ->
                    if(document!=null){
                        val storedPassword=document.getString("password")
                        if (password==storedPassword){
                            Toast.makeText(applicationContext,"Login Succeus",Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, PatientDetailsActivity::class.java)
                            // Add any extra data if needed
                            intent.putExtra("ABHA_ID", username)

                            startActivity(intent)
                            finish()

                        }
                        else{
                            Toast.makeText(applicationContext,"Invalid username or password",Toast.LENGTH_SHORT).show()
                        }
                    }
                    else{
                        Toast.makeText(applicationContext,"Invalid username or password",Toast.LENGTH_SHORT).show()

                    }
                }
                    .addOnFailureListener { exception ->
                        Toast.makeText(applicationContext,"Error: $exception",Toast.LENGTH_SHORT).show()
                    }

            }

        }
        signUpPatientOption.setOnClickListener {
            val intent = Intent(this, SignUpPatientActivity::class.java)
            startActivity(intent)
        }
    }
}
