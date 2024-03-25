package com.example.projectadd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class LogInPatientActivity : AppCompatActivity() {
    private lateinit var signUpPatientOption: TextView
    private lateinit var patientLoginPassword: EditText
    private lateinit var patientLoginId: EditText
    private lateinit var logInPatientBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in_patient)

        signUpPatientOption = findViewById(R.id.signUpPatientOption)
        patientLoginId = findViewById(R.id.patientLoginId)
        patientLoginPassword = findViewById(R.id.patientLoginPassword)
        logInPatientBtn = findViewById(R.id.logInPatientBtn)
        logInPatientBtn.setOnClickListener {
            val username = patientLoginId.text.toString()
            val password = patientLoginPassword.text.toString()
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(applicationContext, "Please fill all details", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "Login Success", Toast.LENGTH_SHORT).show()
            }

        }
        signUpPatientOption.setOnClickListener {
            val intent = Intent(this, SignUpPatientActivity::class.java)
            startActivity(intent)
        }
    }
}
