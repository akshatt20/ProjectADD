package com.example.projectadd


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class LogInDoctorActivity : AppCompatActivity() {
    private lateinit var signUpDoctorOption: TextView
    private lateinit var doctorLoginId: EditText
    private lateinit var doctorLoginPassword: EditText
    private lateinit var logInDoctorBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in_doctor)

        signUpDoctorOption = findViewById(R.id.signUpDoctorOption)
        doctorLoginId = findViewById(R.id.doctorLoginId)
        doctorLoginPassword = findViewById(R.id.doctorLoginPassword)
        logInDoctorBtn = findViewById(R.id.logInDoctorBtn)

        logInDoctorBtn.setOnClickListener {
            val username = doctorLoginId.text.toString()
            val password = doctorLoginPassword.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(applicationContext, "Please fill all details", Toast.LENGTH_SHORT).show()
            } else {
                // Here you might want to implement your actual login logic.
                // For now, just displaying a success message.
                Toast.makeText(applicationContext, "Login Successful", Toast.LENGTH_SHORT).show()
            }
        }

        signUpDoctorOption.setOnClickListener {
            val intent = Intent(this, SignUpDoctorActivity::class.java)
            startActivity(intent)
        }
    }
}

