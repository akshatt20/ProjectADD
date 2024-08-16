package com.example.projectadd


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class LogInDoctorActivity : AppCompatActivity() {
    private lateinit var signUpDoctorOption: TextView
    private lateinit var doctorLoginId: EditText
    private lateinit var doctorLoginPassword: EditText
    private lateinit var logInDoctorBtn: Button
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in_doctor)

        signUpDoctorOption = findViewById(R.id.signUpDoctorOption)
        doctorLoginId = findViewById(R.id.doctorLoginId)
        doctorLoginPassword = findViewById(R.id.doctorLoginPassword)
        logInDoctorBtn = findViewById(R.id.logInDoctorBtn)
        firestore = FirebaseFirestore.getInstance()

        logInDoctorBtn.setOnClickListener {
            val username = doctorLoginId.text.toString()
            val password = doctorLoginPassword.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(applicationContext, "Please fill all details", Toast.LENGTH_SHORT).show()
            } else {
                firestore.collection("Doctors").document(username).get().addOnSuccessListener { document ->
                    if(document!=null){
                        val storedPassword=document.getString("password")
                        if (password==storedPassword) {
                            Toast.makeText(applicationContext, "Login Succeus", Toast.LENGTH_SHORT)
                                .show()
                            val intent = Intent(this, EnterActivity::class.java)
                            intent.putExtra("DOCTOR_NAME", username)


                            startActivity(intent)
                        }
                            else{
                                Toast.makeText(applicationContext,"Invalid username or password",Toast.LENGTH_SHORT).show()
                            }
                        }
                        else{
                            Toast.makeText(applicationContext,"Invalid username or password",Toast.LENGTH_SHORT).show()

                        }
            }
        }


    }
        signUpDoctorOption.setOnClickListener {
            val intent = Intent(this, SignUpDoctorActivity::class.java)
            startActivity(intent)
        }
}}

