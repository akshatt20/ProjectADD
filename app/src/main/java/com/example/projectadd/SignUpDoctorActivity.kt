package com.example.projectadd


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.firestore.FirebaseFirestore
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class SignUpDoctorActivity : AppCompatActivity() {
    private lateinit var logInDirectToSignUpDoctor: TextView
    private lateinit var doctorName: EditText
    private lateinit var doctorMobileNoemail: EditText
    private lateinit var doctorPassword: EditText
    private lateinit var doctorOrganization: EditText
    private lateinit var doctorAge: EditText
    private lateinit var doctorGender: EditText
    private lateinit var doctorLicenseNo: EditText
    private lateinit var doctorSpeciality: EditText
    private lateinit var doctorSignUpButton: Button
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_doctor)

        logInDirectToSignUpDoctor = findViewById(R.id.logInDirectToSignUpDoctor)
        doctorName = findViewById(R.id.doctorName)
        doctorMobileNoemail = findViewById(R.id.doctorMobileNoemail)
        doctorPassword = findViewById(R.id.doctorPassword)
        doctorOrganization = findViewById(R.id.doctorOrganization)
        doctorAge = findViewById(R.id.doctorAge)
        doctorGender = findViewById(R.id.doctorGender)
        doctorLicenseNo = findViewById(R.id.doctorLicenseNo)
        doctorSpeciality = findViewById(R.id.doctorSpeciality)
        doctorSignUpButton = findViewById(R.id.doctorSignUpButton)

        db = FirebaseFirestore.getInstance()

        logInDirectToSignUpDoctor.setOnClickListener {
            val intent = Intent(this, LogInDoctorActivity::class.java)
            startActivity(intent)
        }

        doctorSignUpButton.setOnClickListener {
            // Get values from EditText fields
            val name = doctorName.text.toString()
            val mobileNoemail = doctorMobileNoemail.text.toString()
            val password = doctorPassword.text.toString()
            val organization = doctorOrganization.text.toString()
            val age = doctorAge.text.toString()
            val gender = doctorGender.text.toString()
            val licenseNo = doctorLicenseNo.text.toString()
            val speciality = doctorSpeciality.text.toString()
            if (name.isEmpty() || mobileNoemail.isEmpty() || age.isEmpty() || gender.isEmpty() || password.isEmpty() || licenseNo.isEmpty() || organization.isEmpty()|| speciality.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create a Doctor map
            val doctor = hashMapOf(
                "name" to name,
                "mobileNoemail" to mobileNoemail,
                "password" to password,
                "organization" to organization,
                "age" to age,
                "gender" to gender,
                "licenseNo" to licenseNo,
                "speciality" to speciality
            )

            // Add a new document with a generated ID
            db.collection("Doctors")
                .document(name)  // Use the doctor's name as the document ID
                .set(doctor)
                .addOnSuccessListener {
                    Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show()
                    // Navigate to DoctorHomeActivity after successful signup
                    val intent = Intent(this, EnterActivity::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
        }
    }
}