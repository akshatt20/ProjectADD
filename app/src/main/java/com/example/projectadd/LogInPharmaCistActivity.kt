package com.example.projectadd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class LogInPharmaCistActivity : AppCompatActivity() {
    private lateinit var signUpPharmacistOption:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in_pharma_cist)
        signUpPharmacistOption=findViewById(R.id.signUpPharmacistOption)
        signUpPharmacistOption.setOnClickListener {
            val intent= Intent(this,SignUpPharmacistActivity::class.java)
            startActivity(intent)
        }

    }
}

