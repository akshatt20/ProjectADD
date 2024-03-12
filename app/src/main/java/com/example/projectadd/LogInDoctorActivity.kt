package com.example.projectadd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class LogInDoctorActivity : AppCompatActivity() {
    private lateinit var signUpDoctorOption:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in_doctor)

        signUpDoctorOption=findViewById(R.id.signUpDoctorOption)
        signUpDoctorOption.setOnClickListener {
            val intent= Intent(this,SignUpDoctorActivity::class.java)
            startActivity(intent)
        }
    }
}


