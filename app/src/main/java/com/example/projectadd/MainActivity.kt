package com.example.projectadd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var tester:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tester=findViewById(R.id.tester)
        tester.setOnClickListener {
            val intent= Intent(this,WelcomeActivity::class.java)
            startActivity(intent)
        }


    }
}