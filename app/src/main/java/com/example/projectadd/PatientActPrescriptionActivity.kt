package com.example.projectadd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.text.SimpleDateFormat
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectadd.adapters.RCVMedicinesAdapter
import com.example.projectadd.databinding.ActivityPatientActPrescriptionBinding
import com.example.projectadd.models.Patient
import com.example.projectadd.models.P
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Locale
import android.graphics.Bitmap
import android.graphics.Color
import android.os.CountDownTimer
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import java.util.Date
import java.util.UUID

class PatientActPrescriptionActivity : AppCompatActivity() {
    private var currentPatient: Patient? = null
    private lateinit var abhaID: String
    val TAG = "Act"

    private lateinit var p: P
    private lateinit var binding: ActivityPatientActPrescriptionBinding
    private val qrCodeDuration: Long = 5 * 60 * 1000 // 5 minutes in milliseconds
    private lateinit var countDownTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientActPrescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        abhaID = intent.getStringExtra("ABHA_ID")!!

        val firestore = FirebaseFirestore.getInstance()

        Log.i(TAG, "onCreate: id = $abhaID")
        val documentId = intent.getStringExtra("ABHA_ID")
        if (!documentId.isNullOrEmpty()) {
            firestore.collection("Patients").document(abhaID).get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val document = task.result

                        if (document != null && document.exists()) {
                            Log.i(TAG, "document exists")

                            currentPatient = document.toObject(Patient::class.java)
                            if (currentPatient != null) {
                                Log.i(TAG, "patient exists")
                                binding.fetchNamePres.text = currentPatient!!.name
                                binding.fetchIdPat.text=currentPatient!!.abhaId

                                if (currentPatient!!.prescriptions.isNotEmpty()) {
                                    p = currentPatient!!.prescriptions[currentPatient!!.prescriptions.size - 1]
                                    Log.i(TAG, "onCreate: patient is ${p.mL.size}" )
//                                    Log.i(TAG, "onCreate: patient is ${p.mL[0].name}" )
                                    setUpPrescriptionDetails(p)
                                    generateAndDisplayQRCode() // Generate and display QR code
                                    startQRCodeTimer() // Start QR code timer
                                } else {
                                    Toast.makeText(
                                        applicationContext,
                                        "Null Prescription",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } else {
                            Log.d("PatientActPrescription", "No such document")
                        }
                    } else {
                        Log.e("PatientActPrescription", "Error fetching patient data", task.exception)
                        Toast.makeText(
                            applicationContext,
                            "Error fetching patient data",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel() // Stop the timer when the activity is destroyed
    }

    private fun setUpPrescriptionDetails(p: P) {
        Log.i(TAG, "setUpPrescriptionDetails: ${p.disease}")
        Log.i(TAG, "setUpPrescriptionDetails: ${p.mL.getOrNull(0)?.p}")

        binding.tvDiseaseName.text = p.disease
        //todo remove
        binding.tvDoctorName.text = p.doctorName

        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        binding.fetchdateP.text = "Date: ${dateFormat.format(p.date)}"

        // Set up RecyclerView for medicines
        binding.rcvMedicines.layoutManager = LinearLayoutManager(this)
        binding.rcvMedicines.adapter = RCVMedicinesAdapter(this, p.mL)

        // Set up prescribed tests
        setUpPrescribedTests(p.tests)
    }

    private fun setUpPrescribedTests(tests: List<String>) {
        binding.llPrescribedTests.removeAllViews() // Clear any existing views

        if (tests.isNotEmpty()) {
            tests.forEach { test ->
                val testView = TextView(this).apply {
                    text = "• $test"
                    textSize = 16f
                    setPadding(0, 8, 0, 8)
                }
                binding.llPrescribedTests.addView(testView)
            }
        } else {
            val noTestsView = TextView(this).apply {
                text = "No tests prescribed"
                textSize = 16f
                setPadding(0, 8, 0, 8)
            }
            binding.llPrescribedTests.addView(noTestsView)
        }
    }

    private fun startQRCodeTimer() {
        countDownTimer = object : CountDownTimer(qrCodeDuration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = (millisUntilFinished / 1000) / 60
                val seconds = (millisUntilFinished / 1000) % 60
                binding.timerTextView.text = String.format(Locale.getDefault(), "Time left: %02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                invalidateQRCode()
            }
        }.start()
    }

    private fun invalidateQRCode() {
        binding.qrCodeImageView.setImageBitmap(null)
        binding.timerTextView.text = "QR code expired"
        Toast.makeText(this, "QR code expired", Toast.LENGTH_SHORT).show()
    }

    private fun generateAndDisplayQRCode() {
        val dynamicData = generateDynamicData()
        val qrCodeBitmap = generateQRCode(dynamicData)
        if (qrCodeBitmap != null) {
            binding.qrCodeImageView.setImageBitmap(qrCodeBitmap)
        } else {
            Log.e(TAG, "Failed to generate QR code")
            Toast.makeText(this, "Failed to generate QR code", Toast.LENGTH_SHORT).show()
        }
    }

    private fun generateDynamicData(): String {
        val medicinesList = StringBuilder()

        // Build the medicine details list with abbreviations
        p.mL.forEach { medicine ->
            medicinesList.append(
                "M(id=${medicine.id}, q=${medicine.q}, p=${medicine.p});\n"
            )
        }

        // Format the final output string with "P", "dis", and "mL"
        return "P(dis=${p.disease}, mL=[$medicinesList])"
    }



    private fun generateQRCode(data: String): Bitmap? {
        val qrCodeWriter = QRCodeWriter()
        try {
            val bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 512, 512)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }
            return bitmap
        } catch (e: WriterException) {
            e.printStackTrace()
        }
        return null
    }
}