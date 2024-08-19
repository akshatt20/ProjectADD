package com.example.projectadd.fragments

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.projectadd.R
import com.example.projectadd.WelcomeActivity
import com.example.projectadd.QRCodeActivity
import com.example.projectadd.QRScannerActivity

class PatientHomeFragment(private val abhaID: String) : Fragment() {

    private lateinit var logoutButton: ImageView
    private lateinit var visit: Button
    private lateinit var scanM: Button
    private lateinit var textView3: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_patient_settings, container, false)

        // Initialize views
        logoutButton = view.findViewById(R.id.logoutButton)
        visit = view.findViewById(R.id.visit)
        scanM = view.findViewById(R.id.scanM)
        textView3 = view.findViewById(R.id.textView3)

        // Retrieve the patient's name from SharedPreferences


        // Set the patient's name to textView3

        visit.setOnClickListener {
            startQRCodeActivity()
        }

        scanM.setOnClickListener {
            startQRScannerActivity()
        }

        logoutButton.setOnClickListener {
            logout()
        }

        return view
    }

    private fun logout() {
        val sharedPref = requireActivity().getSharedPreferences("my_app_pref", MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.clear()
        editor.apply()

        val intent = Intent(requireContext(), WelcomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
    }

    private fun startQRCodeActivity() {
        val intent = Intent(requireContext(), QRCodeActivity::class.java).apply {
            putExtra("abhaID", abhaID)
        }
        startActivity(intent)
    }

    private fun startQRScannerActivity() {
        val intent = Intent(requireContext(), QRScannerActivity::class.java)
        startActivity(intent)
    }

    companion object {
        private const val TAG = "PatientHomeFragment"
    }
}
