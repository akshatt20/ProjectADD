package com.example.projectadd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class WelcomeActivity : AppCompatActivity() {
private lateinit var doctorWelcome:Button
    private lateinit var patientWelcome:Button
    private lateinit var pharmacistWelcome:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        doctorWelcome=findViewById(R.id.doctorWelcome)
        patientWelcome=findViewById(R.id.patientWelcome)
        pharmacistWelcome=findViewById(R.id.pharmacistWelcome)
        doctorWelcome.setOnClickListener {
            val intent= Intent(this,EnterActivity::class.java)
            startActivity(intent)
        }
        patientWelcome.setOnClickListener {
            val intent= Intent(this,MedicineDataActivity::class.java)
            startActivity(intent)
        }
        pharmacistWelcome.setOnClickListener {
            val intent= Intent(this,LogInPharmaCistActivity::class.java)
            startActivity(intent)
        }

    }
}