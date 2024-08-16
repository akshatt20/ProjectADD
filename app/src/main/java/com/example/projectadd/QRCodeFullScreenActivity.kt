package com.example.projectadd

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter

class QRCodeFullScreenActivity : AppCompatActivity() {

    private lateinit var qrCodeImageView: ImageView
    private lateinit var timerTextView: TextView
    private var qrCodeTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode_full_screen)

        qrCodeImageView = findViewById(R.id.fullScreenQRCode)
        timerTextView = findViewById(R.id.timerTextView)

        val abhaID = intent.getStringExtra("ABHA_ID") ?: ""
        generateQRCode(abhaID)
    }

    private fun generateQRCode(abhaID: String) {
        val currentTimeMillis = System.currentTimeMillis()
        val expirationTimeMillis = currentTimeMillis + (5 * 60 * 1000) // 5 minutes
        val qrCodeContent = "PatientID: $abhaID, Expiration: $expirationTimeMillis"

        try {
            val qrCodeWriter = QRCodeWriter()
            val bitMatrix = qrCodeWriter.encode(qrCodeContent, BarcodeFormat.QR_CODE, 1024, 1024)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }

            qrCodeImageView.setImageBitmap(bitmap)
            startQRCodeTimer()
        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }

    private fun startQRCodeTimer() {
        qrCodeTimer?.cancel()
        qrCodeTimer = object : CountDownTimer(5 * 60 * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = millisUntilFinished / 60000
                val seconds = (millisUntilFinished % 60000) / 1000
                timerTextView.text = String.format("Expires in: %02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                timerTextView.text = "QR Code expired"
                finish()
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        qrCodeTimer?.cancel()
    }
}