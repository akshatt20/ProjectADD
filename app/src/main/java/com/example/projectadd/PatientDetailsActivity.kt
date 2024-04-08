package com.example.projectadd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.projectadd.fragments.PatientHomeFragment
import com.example.projectadd.fragments.PatientProfileFragment
import com.example.projectadd.fragments.PatientSettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class PatientDetailsActivity : AppCompatActivity() {
    private lateinit var patientBottom_Navigation: BottomNavigationView
    private lateinit var abhaID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_details)

        // Retrieve the patient ID from intent extras
        abhaID = intent.getStringExtra("ABHA_ID") ?: ""

        // Initialize the bottom navigation view
        patientBottom_Navigation = findViewById(R.id.patientBottom_Navigation)

        // Create instances of the fragments
        val homeFragment = PatientHomeFragment(abhaID)
        val profileFragment = PatientProfileFragment(abhaID)
        val settingsFragment = PatientSettingsFragment()

        // Display the home fragment initially
        makeCurrentFragment(homeFragment)

        // Set listener for bottom navigation items
        patientBottom_Navigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.patientHomeIcon -> makeCurrentFragment(homeFragment)
                R.id.patientProfileIcon -> makeCurrentFragment(profileFragment)
                R.id.patientSettingsIcon -> makeCurrentFragment(settingsFragment)
            }
            true
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.patientDet, fragment)
            commit()
        }
    }
}
