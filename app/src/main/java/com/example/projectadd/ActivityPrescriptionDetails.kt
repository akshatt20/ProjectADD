package com.example.projectadd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectadd.adapters.RCVMedicinesAdapter
import com.example.projectadd.databinding.ActivityPrescriptionDetailsBinding
import com.example.projectadd.models.P
import com.google.gson.Gson

class ActivityPrescriptionDetails : AppCompatActivity() {

    private lateinit var p:P
    private lateinit var binding: ActivityPrescriptionDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrescriptionDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        p = Gson().fromJson(intent.getStringExtra("PRESCRIPTION")!!,P::class.java)

        binding.tvPDDiseaseName.text = p.disease
        binding.rcvPDMedicines.layoutManager =  LinearLayoutManager(this);
        binding.rcvPDMedicines.adapter =  RCVMedicinesAdapter(this,p.mL)

    }
}