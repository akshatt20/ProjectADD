package com.example.projectadd.fragments

import android.Manifest
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.projectadd.R
import com.example.projectadd.WelcomeActivity
import com.example.projectadd.QRCodeActivity
import com.example.projectadd.QRScannerActivity
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class PatientHomeFragment(private val abhaID: String) : Fragment() {

    private lateinit var logoutButton: Button
    private lateinit var visit: Button
    private lateinit var scanM: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_patient_settings, container, false)
        logoutButton = view.findViewById(R.id.logoutButton)
        visit = view.findViewById(R.id.visit)
        scanM = view.findViewById(R.id.scanM)

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