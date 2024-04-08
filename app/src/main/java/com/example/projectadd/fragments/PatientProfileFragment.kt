package com.example.projectadd.fragments

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.projectadd.R
import com.example.projectadd.models.Patient
import com.example.projectadd.models.Prescription
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter

class PatientProfileFragment(private val abhaID:String) : Fragment() {
    private lateinit var fetchNameP: TextView
    private lateinit var fetchAgeP: TextView
    private lateinit var fetchSexP: TextView
    private lateinit var fetchAbhaidP: TextView
    private lateinit var fetchMobileP: TextView
    private lateinit var fetchResidenceP: TextView
    private lateinit var firestore: FirebaseFirestore
    private lateinit var idQrcode: ImageView
    private var currentPatient: Patient? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_patient_profile, container, false)

        // Initialize your TextViews after inflating the layout
        fetchNameP = rootView.findViewById(R.id.fetchNameP)
        fetchAgeP = rootView.findViewById(R.id.fetchAgeP)
        fetchSexP = rootView.findViewById(R.id.fetchSexP)
        fetchAbhaidP = rootView.findViewById(R.id.fetchAbhaidP)
        fetchMobileP = rootView.findViewById(R.id.fetchMobileP)
        fetchResidenceP = rootView.findViewById(R.id.fetchResidenceP)
        idQrcode = rootView.findViewById(R.id.idQrcode)


        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance()


        // Fetch data from Firestore
        // Fetch data from Firestore only if abhaID is not null or empty
        if (abhaID.isNotEmpty()) {
            firestore.collection("Patients").document(abhaID).get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Handle successful document retrieval
                        val document = task.result
                        if (document.exists()) {
                            // Document exists, process data
                            currentPatient = document.toObject(Patient::class.java)
                            if (currentPatient != null) {
                                updateUI()
                                if (currentPatient!!.prescriptions.isNotEmpty()) {
                                    // Generate and set QR code with prescription data
                                    val qrCodeBitmap =
                                        generateQRCode(currentPatient!!.prescriptions)
                                    idQrcode.setImageBitmap(qrCodeBitmap)
                                } else {
                                    // Prescription is empty or null
                                    // Handle this case, for example, hide the QR code view or take appropriate action
                                    idQrcode.visibility = View.GONE // Hide the QR code ImageView
                                    Toast.makeText(
                                        requireContext(),
                                        "Prescription data not found",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } else {
                            // Document doesn't exist
                            // Handle this case, for example, show a message to the user
                            Toast.makeText(
                                requireContext(),
                                "Patient data not found",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        // Handle errors
                        Log.e(
                            "PatientProfileFragment",
                            "Error fetching patient data",
                            task.exception
                        )
                        Toast.makeText(
                            requireContext(),
                            "Error fetching patient data",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

        return rootView
    }

    private fun updateUI() {
        fetchNameP.text = currentPatient!!.name
        fetchAgeP.text = currentPatient!!.age
        fetchSexP.text = currentPatient!!.gender
        fetchAbhaidP.text = currentPatient!!.abhaId
        fetchMobileP.text = currentPatient!!.mobileNo
        fetchResidenceP.text = currentPatient!!.address

    }

    private fun generateQRCode(data: ArrayList<Prescription>): Bitmap? {
        val qrCodeWriter = QRCodeWriter()

        // Get the last prescription from the list
        val lastPrescription = data.lastOrNull()
        if (lastPrescription != null) {
            val qrCodeData =
                lastPrescription.toString() // Assuming Prescription has a meaningful toString() method
            try {
                val bitMatrix: BitMatrix =
                    qrCodeWriter.encode(qrCodeData, BarcodeFormat.QR_CODE, 512, 512)
                val width: Int = bitMatrix.width
                val height: Int = bitMatrix.height
                val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
                for (x in 0 until width) {
                    for (y in 0 until height) {
                        bitmap.setPixel(
                            x,
                            y,
                            if (bitMatrix[x, y]) 0xFF000000.toInt() else 0xFFFFFFFF.toInt()
                        )
                    }
                }
                return bitmap
            } catch (e: WriterException) {
                e.printStackTrace()
            }
        }
        return null
    }
}