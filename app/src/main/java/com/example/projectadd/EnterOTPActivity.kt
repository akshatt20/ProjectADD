package com.example.projectadd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class EnterOTPActivity : AppCompatActivity() {
    private lateinit var verifyOtp:Button
    private lateinit var otp1:EditText
    private lateinit var otp2:EditText
    private lateinit var otp3:EditText
    private lateinit var otp4:EditText
    private lateinit var otp5:EditText
    private lateinit var otp6:EditText
    private lateinit var resendOtpMob:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_otpactivity)
        verifyOtp=findViewById(R.id.verifyOtp)
        otp1=findViewById(R.id.otp1)
        otp2=findViewById(R.id.otp2)
        otp3=findViewById(R.id.otp3)
        otp4=findViewById(R.id.otp4)
        otp5=findViewById(R.id.otp5)
        otp6=findViewById(R.id.otp6)
        resendOtpMob=findViewById(R.id.resendOtpMob)

    }
}






