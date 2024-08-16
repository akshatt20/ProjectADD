package com.example.projectadd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.widget.SearchView

import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit


class EnterActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var searchView: SearchView
    private lateinit var auth: FirebaseAuth
    private lateinit var username: String

    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var nextButtonD: Button
    private var selectedId: String? = null // Variable to store the selected item
    private  lateinit var db:FirebaseFirestore;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter)

        // Initialize ListView and adapter
        listView = findViewById(R.id.userList)
        searchView = findViewById(R.id.searchView)
        nextButtonD = findViewById(R.id.nextButtonD)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1)
        listView.adapter = adapter
        username = intent.getStringExtra("DOCTOR_NAME") ?: ""

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Setup SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                queryFirestoreCollection("Patients", query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        // Initialize Firebase Auth callbacks
        initAuthCallbacks()

        // Usage example
        val collectionPath = "Patients"
        fetchCollectionIds(collectionPath) { ids: List<String>, exception: Exception? ->
            if (exception == null) {
                adapter.clear()
                adapter.addAll(ids)
                showToast("IDs fetched successfully!")
            } else {
                println("Error fetching collection IDs: $exception")
                showToast("Error fetching collection IDs: ${exception.message}")
            }
        }

        // Handle next button click
        nextButtonD.setOnClickListener {
            if (!selectedId.isNullOrEmpty()) {
                Log.i("adi", "onCreate: selected item is " + selectedId.toString())
                sendOTP(selectedId!!)
            } else {
                showToast("Please select an ID first.")
            }
        }

        // Disable the nextButtonD initially
        nextButtonD.isEnabled = false

        // Listen for item selection in the ListView
        listView.setOnItemClickListener { _, _, position, _ ->
            selectedId = adapter.getItem(position) // Update the selected item
            nextButtonD.isEnabled = true
            searchView.setQuery(selectedId, false)
        }
    }


    fun queryFirestoreCollection(collectionPath: String, query: String?) {

        val collectionReference = db.collection(collectionPath)

        if (!query.isNullOrEmpty()) {
            // Perform the query to search for documents where a specific field contains the query
            collectionReference.whereEqualTo("Patients", query) // Replace "fieldName" with the actual field name you want to search on
                .get()
                .addOnSuccessListener { documents ->
                    // Handle successful query results
                    val ids = mutableListOf<String>()
                    for (document in documents) {
                        // Get the document ID and add it to the list
                        ids.add(document.id)
                    }
                    // Update the adapter with the search results
                    adapter.clear()
                    adapter.addAll(ids)
                }
                .addOnFailureListener { exception ->
                    // Handle failed query
                    println("Error searching documents: $exception")
                    showToast("Error searching documents: ${exception.message}")
                }
        } else {
            // If the query is empty or null, fetch all documents in the collection
            fetchCollectionIds(collectionPath) { ids: List<String>, exception: Exception? ->
                if (exception == null) {
                    // Update the adapter with all documents in the collection
                    adapter.clear()
                    adapter.addAll(ids)
                } else {
                    // Handle errors here
                    println("Error fetching collection IDs: $exception")
                    showToast("Error fetching collection IDs: ${exception.message}")
                }
            }
        }
    }

    // Inside sendOTP

    private fun sendOTP(selectedId: String) {
        generateOTP(selectedId)
    }

    // Function to retrieve phone number associated with the selected ID from your data source
    private fun generateOTP(selectedItem: String) {
        val documentReference = db.collection("Patients").document(selectedItem)

        documentReference.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val phoneNumber = documentSnapshot.getString("mobileNo")
                    if (!phoneNumber.isNullOrEmpty()) {
                        // Format the phone number to E.164 format (add country code if missing)
                        val formattedPhoneNumber = if (phoneNumber.startsWith("+")) {
                            phoneNumber // Phone number already has a country code
                        } else {
                            // Add country code (e.g., for India, country code is +91)
                            // Replace "91" with the appropriate country code
                            "+91$phoneNumber"
                        }
                        val options = PhoneAuthOptions.newBuilder(auth)
                            .setPhoneNumber(formattedPhoneNumber)       // Phone number to verify
                            .setTimeout(60L, TimeUnit.SECONDS) // Timeout duration
                            .setActivity(this)                 // Activity (for callback binding)
                            .setCallbacks(callbacks)           // OnVerificationStateChangedCallbacks
                            .build()
                        PhoneAuthProvider.verifyPhoneNumber(options)
                    }
                }
            }
            .addOnFailureListener { exception ->
                // Handle failure to retrieve the document
                showToast("Failed to retrieve phone number: ${exception.message}")
            }
    }




    private fun initAuthCallbacks() {
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter an OTP.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the phone number format is not valid.
                showToast("Verification failed: ${e.message}")
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                // The SMS verification code has been sent to the provided phone number,
                // we now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                // Save the verification ID and resending token so we can use them later
                // to verify the code
                showToast("OTP has been sent to the user's phone number")
                Log.i("adi", "onCodeSent: $verificationId")
                // Save the verification ID somewhere, for example, to the shared preferences
                // or send it to the user's device
                // ...
                val intent = Intent(this@EnterActivity,EnterOTPActivity::class.java)
                intent.putExtra("ABHA_ID",selectedId)
                intent.putExtra("OTP", verificationId)
                intent.putExtra("DOCTOR_NAME", username)

                startActivity(intent)

            }
        }
    }


    private fun fetchCollectionIds(collectionPath: String, callback: (List<String>, Exception?) -> Unit) {
        val firestore = FirebaseFirestore.getInstance()
        val collectionReference = firestore.collection(collectionPath)

        // Fetch all documents in the collection
        collectionReference.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val documentSnapshots = task.result?.documents ?: listOf()
                val ids = mutableListOf<String>()
                for (documentSnapshot in documentSnapshots) {
                    // Get the document ID and add it to the list
                    ids.add(documentSnapshot.id)
                }
                callback(ids, null)
            } else {
                // Handle errors here
                val exception = task.exception ?: Exception("Unknown exception occurred")
                callback(emptyList(), exception)
            }
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithCredential:success")

                    val user = task.result?.user
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w("TAG", "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
