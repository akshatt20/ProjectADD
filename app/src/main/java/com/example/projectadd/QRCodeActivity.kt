package com.example.projectadd

import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.os.CountDownTimer
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter

class QRCodeActivity : AppCompatActivity() {
    private lateinit var qrCodeImageView: ImageView
    private lateinit var timerTextView: TextView
    private var countDownTimer: CountDownTimer? = null
    private lateinit var abhaID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode)

        qrCodeImageView = findViewById(R.id.qrCodeImageView)
        timerTextView = findViewById(R.id.timerTextView)

        // Retrieve ABHA ID from intent
        abhaID = intent.getStringExtra("abhaID") ?: ""

        generateQRCodeFor5Minutes()
    }

    private fun generateQRCodeFor5Minutes() {
        if (abhaID.isEmpty()) {
            Toast.makeText(this, "ABHA ID is empty", Toast.LENGTH_SHORT).show()
            return
        }
        val qrCode = generateQRCode(abhaID)
        if (qrCode != null) {
            qrCodeImageView.setImageBitmap(qrCode)
            qrCodeImageView.visibility = View.VISIBLE
            countDownTimer?.cancel()
            countDownTimer = object : CountDownTimer(5 * 60 * 1000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val secondsLeft = millisUntilFinished / 1000
                    timerTextView.text = "Valid for: ${secondsLeft}s"
                    timerTextView.visibility = View.VISIBLE
                }
                override fun onFinish() {
                    qrCodeImageView.setImageDrawable(null)
                    qrCodeImageView.visibility = View.GONE
                    timerTextView.visibility = View.GONE
                    Toast.makeText(this@QRCodeActivity, "QR Code expired", Toast.LENGTH_SHORT).show()
                }
            }.start()
        } else {
            Toast.makeText(this, "Failed to generate QR Code", Toast.LENGTH_SHORT).show()
        }
    }

    private fun generateQRCode(content: String): Bitmap? {
        if (content.isEmpty()) {
            return null
        }
        val writer = QRCodeWriter()
        return try {
            val bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 512, 512)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }
            bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }
}