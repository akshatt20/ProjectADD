package com.example.projectadd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.SearchView

import com.example.projectadd.databinding.ActivityEnterBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit


class EnterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEnterBinding
    private lateinit var userAdapter: ArrayAdapter<String>
    private lateinit var confirmID: Button
    private lateinit var selectedUserID: String // Store the selected user ID

    // Firebase Authentication
    private lateinit var auth: FirebaseAuth
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var verificationId: String = ""

    // Sample database to map user IDs to phone numbers
    private val userPhoneNumbers = mapOf(
        "9203" to "+919755710670",
        "UserID2" to "PhoneNumber2"
        // Add more user IDs and phone numbers as needed
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        confirmID = binding.confirmID

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Initialize user adapter with empty array
        userAdapter = ArrayAdapter(
            this, android.R.layout.simple_list_item_1,
            emptyArray()
        )
        binding.userList.adapter = userAdapter

        // Initialize Firebase PhoneAuthProvider
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification.
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // Failed to verify the phone number.
                Toast.makeText(
                    this@EnterActivity,
                    "Verification failed: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                // The SMS verification code has been sent to the provided phone number,
                this@EnterActivity.verificationId = verificationId
            }
        }

        binding.userList.setOnItemClickListener { parent, view, position, id ->
            // Retrieve the selected user ID from the adapter
            selectedUserID = userAdapter.getItem(position) ?: ""
            // Enable the button when a user is selected
            confirmID.isEnabled = true
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    // Filter based on query (You may need to fetch from DB based on query)
                    userAdapter.clear()
                    userAdapter.addAll(userPhoneNumbers.keys.filter { userID ->
                        userID.contains(query, ignoreCase = true)
                    })
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    // Filter based on text change (You may need to fetch from DB based on text change)
                    userAdapter.clear()
                    userAdapter.addAll(userPhoneNumbers.keys.filter { userID ->
                        userID.contains(newText, ignoreCase = true)
                    })
                }
                return false
            }
        })

        // Disable the button initially
        confirmID.isEnabled = false

        confirmID.setOnClickListener {
            // Here you can initiate OTP verification
            val phoneNumber = userPhoneNumbers[selectedUserID]
            if (!phoneNumber.isNullOrBlank()) {
                initiateOTPVerification(phoneNumber)
            } else {
                Toast.makeText(this, "Phone number not found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initiateOTPVerification(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                  // Activity (for callback binding)
            .setCallbacks(callbacks)            // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Verification successful, proceed to the next activity
                    val intent = Intent(this, EnterOTPActivity::class.java)
                    startActivity(intent)
                } else {
                    // Verification failed
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
}
