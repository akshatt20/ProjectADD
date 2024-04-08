package com.example.projectadd.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.projectadd.PatientActPrescriptionActivity
import com.example.projectadd.PatientDeactPrescriptionActivity
import com.example.projectadd.PatientDetailsActivity
import com.example.projectadd.R


class PatientHomeFragment(private val abhaID:String) : Fragment() {
    private lateinit var activatedPres: TextView
    private lateinit var deactivatedPres: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_patient_home, container, false)
        activatedPres = view.findViewById(R.id.activatedPres)
        deactivatedPres = view.findViewById(R.id.deactivatedPres)

        // Set OnClickListener for activatedPres
        activatedPres.setOnClickListener {
            val intent = Intent(requireContext(), PatientActPrescriptionActivity::class.java)
            intent.putExtra("ABHA_ID",abhaID)
            startActivity(intent)
            Toast.makeText(requireContext(), "ActivatedPres clicked", Toast.LENGTH_SHORT).show()
        }

        // Set OnClickListener for deactivatedPres
        deactivatedPres.setOnClickListener {
            val intent = Intent(requireContext(), PatientDeactPrescriptionActivity::class.java)
            intent.putExtra("ABHA_ID",abhaID)

            startActivity(intent)
            Toast.makeText(requireContext(), "DeactivatedPres clicked", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}
