package com.example.projectadd.fragments

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.projectadd.R
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter

class PatientProfileFragment : Fragment() {
    private lateinit var fetchNameP: TextView
    private lateinit var fetchAgeP: TextView
    private lateinit var fetchSexP: TextView
    private lateinit var fetchAbhaidP: TextView
    private lateinit var fetchMobileP: TextView
    private lateinit var fetchResidenceP: TextView
    private lateinit var firestore: FirebaseFirestore
    private lateinit var idQrcode: ImageView

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
        var patientID =
            activity?.getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE)?.getString(
                "PATIENT_ID",
                ""
            )
        // Fetch data from Firestore
        firestore.collection("Patients").document(patientID!!).get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    // Populate TextViews with fetched data
                    fetchNameP.text = document.getString("name") ?: ""
                    fetchAgeP.text = document.getString("age") ?: ""
                    fetchSexP.text = document.getString("sex") ?: ""
                    fetchAbhaidP.text = document.getString("abhaid") ?: ""
                    fetchMobileP.text = document.getString("mobile") ?: ""
                    fetchResidenceP.text = document.getString("residence") ?: ""

                    // Generate and set QR code
                    val qrCodeData = patientID
                    val qrCodeBitmap = generateQRCode(qrCodeData)
                    idQrcode.setImageBitmap(qrCodeBitmap)
                } else {
                    // Document doesn't exist or is empty
                }
            }
            .addOnFailureListener { exception ->
                // Handle errors
            }

        return rootView
    }

    private fun generateQRCode(qrCodeData: String): Bitmap? {
        val qrCodeWriter = QRCodeWriter()
        try {
            val bitMatrix: BitMatrix =
                qrCodeWriter.encode(qrCodeData, BarcodeFormat.QR_CODE, 512, 512)
            val width: Int = bitMatrix.width
            val height: Int = bitMatrix.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) 0xFF000000.toInt() else 0xFFFFFFFF.toInt())
                }
            }
            return bitmap
        } catch (e: WriterException) {
            e.printStackTrace()
        }
        return null
    }
}