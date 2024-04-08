package com.example.projectadd

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class LogInPharmaCistActivity : AppCompatActivity() {
    private lateinit var signUpPharmacistOption: TextView
    private lateinit var pharmacistLoginId: EditText
    private lateinit var pharmacistLoginPassword: EditText
    private lateinit var logInPharmacistBtn: Button
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in_pharma_cist)
        pharmacistLoginId = findViewById(R.id.pharmacistLoginId)
        pharmacistLoginPassword = findViewById(R.id.pharmacistLoginPassword)
        logInPharmacistBtn = findViewById(R.id.logInPharmacistBtn)
        signUpPharmacistOption = findViewById(R.id.signUpPharmacistOption) // Initialize signUpPharmacistOption here
        firestore = FirebaseFirestore.getInstance()

        logInPharmacistBtn.setOnClickListener {
            val username = pharmacistLoginId.text.toString()
            val password = pharmacistLoginPassword.text.toString()
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(applicationContext, "Please fill all details", Toast.LENGTH_SHORT).show()
            } else {
                firestore.collection("Pharmacists").document(username).get().addOnSuccessListener { document ->
                    if(document!=null){
                        val storedPassword=document.getString("password")
                        if (password==storedPassword){
                            Toast.makeText(applicationContext,"Login Succeus",Toast.LENGTH_SHORT).show()

                        }
                        else{
                            Toast.makeText(applicationContext,"Invalid username or password",Toast.LENGTH_SHORT).show()
                        }
                    }
                Toast.makeText(applicationContext, "Login Success", Toast.LENGTH_SHORT).show()
            }
        }

        signUpPharmacistOption.setOnClickListener {
            val intent = Intent(this, SignUpPharmacistActivity::class.java)
            startActivity(intent)
        }
    }
}}