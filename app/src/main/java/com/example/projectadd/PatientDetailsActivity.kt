package com.example.projectadd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.projectadd.fragments.PatientHomeFragment
import com.example.projectadd.fragments.PatientProfileFragment
import com.example.projectadd.fragments.PatientSettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class PatientDetailsActivity : AppCompatActivity() {
    private lateinit var patientBottom_Navigation:BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_details)
        patientBottom_Navigation=findViewById(R.id.patientBottom_Navigation)
        val homeFragment=PatientHomeFragment()
        val profileFragment=PatientProfileFragment()
        val settingsFragment=PatientSettingsFragment()

        makeCurrentFragment(homeFragment)

        patientBottom_Navigation.setOnNavigationItemSelectedListener{
            when(it.itemId){
              R.id.patientHomeIcon->makeCurrentFragment(homeFragment)
                R.id.patientProfileIcon->makeCurrentFragment(profileFragment)
                R.id.patientSettingsIcon->makeCurrentFragment(settingsFragment)

            }
            true

        }

    }
    private fun makeCurrentFragment(fragment:Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.patientDet,fragment)
            commit()
        }
}
