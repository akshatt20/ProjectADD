package com.example.projectadd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.projectadd.databinding.ActivityEnterOtpactivityBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class EnterOTPActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEnterOtpactivityBinding
    private  lateinit var abhaId:String
    private lateinit var username: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnterOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        abhaId = intent.getStringExtra("ABHA_ID")!!


        val otp = intent.getStringExtra("OTP")!!

        binding.verifyOtp.setOnClickListener {
            var enteredOtp = (binding.otp1.text.toString()+
                    binding.otp2.text.toString()+binding.otp3.text.toString() +
                    binding.otp4.text.toString() + binding.otp5.text.toString() +
                    binding.otp6.text.toString())
            Log.i("adi", "onCreate: entered " + enteredOtp)
            Log.i("adi", "onCreate: original " + otp)

            var credential : PhoneAuthCredential =  PhoneAuthProvider.getCredential(otp,enteredOtp)
            signInWithPhoneAuthCredential(credential)
        }
        val editTexts = arrayOf(binding.otp1, binding.otp2, binding.otp3, binding.otp4, binding.otp5, binding.otp6)

        for (i in 0 until editTexts.size) {
            val currentEditText = editTexts[i]
            val nextEditText = if (i < editTexts.size - 1) editTexts[i + 1] else null
            currentEditText.addTextChangedListener(OtpTextWatcher(currentEditText, nextEditText))
        }

    }
    // Usage in your activity or fragment

    // Assuming you have 6 EditText fields for OTP entry named editText1, editText2, ..., editText6


    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("adi", "signInWithCredential:success")
                    Toast.makeText(this@EnterOTPActivity,"OTP Verified successfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@EnterOTPActivity,DoctorHomeActivity::class.java)
                    intent.putExtra("ABHA_ID", abhaId)

                    startActivity(intent)
                    val user = task.result?.user

                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w("adi", "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                       Toast.makeText(this@EnterOTPActivity,"OTP is Incorrect",Toast.LENGTH_SHORT).show()
                    }

                }
            }
    }
}






