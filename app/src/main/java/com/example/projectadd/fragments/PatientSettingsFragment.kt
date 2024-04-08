package com.example.projectadd.fragments

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.projectadd.LogInPatientActivity
import com.example.projectadd.R




class PatientSettingsFragment : Fragment() {

    private lateinit var logoutButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_patient_settings, container, false)
        logoutButton = view.findViewById(R.id.logoutButton)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logoutButton.setOnClickListener {
            logout()
        }
    }

    private fun logout() {
        // Clear session data here
        // For example, clear shared preferences
        val sharedPref = requireActivity().getSharedPreferences("my_app_pref", MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.clear()
        editor.apply()

        // Navigate back to LoginActivity
        val intent = Intent(requireContext(), LogInPatientActivity::class.java)
        // Clear the back stack so that the user cannot go back to MainActivity
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
    }
}