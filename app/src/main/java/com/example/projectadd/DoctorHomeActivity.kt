package com.example.projectadd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.projectadd.databinding.ActivityDoctorDetailsBinding
import com.example.projectadd.databinding.ActivityDoctorHomeBinding
import com.example.projectadd.databinding.ActivityMedicineDataBinding

class DoctorHomeActivity : AppCompatActivity() {
    private lateinit var newPatientButton: Button
    private  lateinit var abhaId:String
    private lateinit var binding: ActivityDoctorHomeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        abhaId = intent.getStringExtra("ABHA_ID")!!

        binding.newPatient.setOnClickListener {
            val i = Intent(this@DoctorHomeActivity, MedicineDataActivity::class.java)
            i.putExtra("ABHA_ID", abhaId)
            startActivity(i)

        }

        newPatientButton = findViewById(R.id.newPatient)

    }


}
