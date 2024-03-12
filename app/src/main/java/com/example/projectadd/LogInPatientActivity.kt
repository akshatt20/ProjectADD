package com.example.projectadd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class LogInPatientActivity : AppCompatActivity() {
    private lateinit var signUpPatientOption:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in_patient)
        signUpPatientOption=findViewById(R.id.signUpPatientOption)
        signUpPatientOption.setOnClickListener {
            val intent = Intent(this,SignUpPatientActivity::class.java)
            startActivity(intent)
        }
    }
}