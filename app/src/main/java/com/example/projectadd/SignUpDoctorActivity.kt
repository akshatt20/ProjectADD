package com.example.projectadd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SignUpDoctorActivity : AppCompatActivity() {
    private lateinit var logInDirectToSignUpDoctor:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_doctor)
        logInDirectToSignUpDoctor=findViewById(R.id.logInDirectToSignUpDoctor)
        logInDirectToSignUpDoctor.setOnClickListener {
            val intent= Intent(this,LogInDoctorActivity::class.java)
            startActivity(intent)
        }


    }
}