package com.example.projectadd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class WelcomeActivity : AppCompatActivity() {
private lateinit var doctorWelcome:Button
    private lateinit var patientWelcome:Button
    private lateinit var pharmacistWelcome:Button
    private lateinit var labWelcome : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        doctorWelcome=findViewById(R.id.doctorWelcome)
        patientWelcome=findViewById(R.id.patientWelcome)
        pharmacistWelcome=findViewById(R.id.pharmacistWelcome)
        labWelcome=findViewById(R.id.labWelcome)

        doctorWelcome.setOnClickListener {
            val intent= Intent(this,LogInDoctorActivity::class.java)
            startActivity(intent)
            finish()
        }
        patientWelcome.setOnClickListener {
            val intent= Intent(this,LogInPatientActivity::class.java)
            startActivity(intent)
            finish()
        }
        pharmacistWelcome.setOnClickListener {
            val intent= Intent(this,LogInPharmaCistActivity::class.java)
            startActivity(intent)
            finish()
        }
        labWelcome.setOnClickListener {
            val intent=Intent(this,ChooseLabOptionActivity::class.java)
            startActivity(intent)
            finish()
        }



    }
}

