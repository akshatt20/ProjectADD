package com.example.projectadd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context

import android.widget.Button
import com.example.projectadd.databinding.ActivityDoctorHomeBinding

class DoctorHomeActivity : AppCompatActivity() {
    private lateinit var newPatientButton: Button
    private  lateinit var abhaId:String
    private lateinit var binding: ActivityDoctorHomeBinding
    private lateinit var logoutButtonDoctor:Button
    private lateinit var username: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        abhaId = intent.getStringExtra("ABHA_ID")!!
        username = intent.getStringExtra("DOCTOR_NAME") ?: ""



        binding.newPatient.setOnClickListener {
            val i = Intent(this@DoctorHomeActivity, MedicineDataActivity::class.java)
            i.putExtra("ABHA_ID", abhaId)
            i.putExtra("DOCTOR_NAME", username)

            startActivity(i)

        }

        newPatientButton = findViewById(R.id.newPatient)
        logoutButtonDoctor = findViewById(R.id.logoutButtonDoctor)
        logoutButtonDoctor.setOnClickListener {
            val intent = Intent(this@DoctorHomeActivity, WelcomeActivity::class.java)
            startActivity(intent)


        }




    }


}
