package com.example.projectadd

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LogInLabActivity : AppCompatActivity() {
    private lateinit var labLoginId: EditText
    private lateinit var labLoginPassword: EditText
    private lateinit var logInLabBtn: Button
    private lateinit var signUpLabOption: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in_lab)

        labLoginId = findViewById(R.id.labLoginId)
        labLoginPassword = findViewById(R.id.labLoginPassword)
        logInLabBtn = findViewById(R.id.logInLabBtn)
        signUpLabOption = findViewById(R.id.signUpLabOption)

        logInLabBtn.setOnClickListener {

        }

        signUpLabOption.setOnClickListener {
            val intent = Intent(this, SignUpLabActivity::class.java)
            startActivity(intent)
        }
    }
}
