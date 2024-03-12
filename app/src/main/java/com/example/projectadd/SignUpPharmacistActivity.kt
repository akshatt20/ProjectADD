package com.example.projectadd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SignUpPharmacistActivity : AppCompatActivity() {
    private lateinit var logInDirectToSignUpPharmacist: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_pharmacist)
        logInDirectToSignUpPharmacist=findViewById(R.id.logInDirectToSignUpPharmacist)
        logInDirectToSignUpPharmacist.setOnClickListener {
            val intent= Intent(this,LogInPharmaCistActivity::class.java)
            startActivity(intent)
        }
    }
}