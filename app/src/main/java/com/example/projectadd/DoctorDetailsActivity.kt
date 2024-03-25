package com.example.projectadd
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.projectadd.R
import com.google.firebase.firestore.FirebaseFirestore

class DoctorDetailsActivity : AppCompatActivity() {
    private lateinit var fetchNameD: TextView
    private lateinit var fetchAgeD: TextView
    private lateinit var fetchSexD: TextView
    private lateinit var fetchLicenseNoD: TextView
    private lateinit var fetchMobileD: TextView
    private lateinit var fetchSpecialityD: TextView
    private lateinit var fetchHospitalD: TextView
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_details)

        // Initialize TextViews
        fetchNameD = findViewById(R.id.fetchNameD)
        fetchAgeD = findViewById(R.id.fetchAgeD)
        fetchSexD = findViewById(R.id.fetchSexD)
        fetchLicenseNoD = findViewById(R.id.fetchLicenseNoD)
        fetchMobileD = findViewById(R.id.fetchMobileD)
        fetchSpecialityD = findViewById(R.id.fetchSpecialityD)
        fetchHospitalD = findViewById(R.id.fetchHospitalD)

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance()

    }
}
