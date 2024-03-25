package com.example.projectadd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.util.Log
import com.example.projectadd.R
import com.google.firebase.firestore.FirebaseFirestore

class MedicineListActivity : AppCompatActivity() {
    private lateinit var firestore: FirebaseFirestore
    private val medicines: MutableList<Medicine> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicine_list)

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance()

        // Populate the medicines list
        populateMedicines()

        // Set onClickListeners for all medicines add and subtract buttons
        for ((index, medicine) in medicines.withIndex()) {
            val addImageViewId = resources.getIdentifier("tab${index + 1}Add", "id", packageName)
            findViewById<ImageView>(addImageViewId)?.setOnClickListener {
                updateDataInFirestore(medicine, 1)
            }

            val subtractImageViewId = resources.getIdentifier("tab${index + 1}Subtract", "id", packageName)
            findViewById<ImageView>(subtractImageViewId)?.setOnClickListener {
                updateDataInFirestore(medicine, -1)
            }
        }
    }

    private fun populateMedicines() {
        // Add medicines to the list
        medicines.add(Medicine("1", "Abhayarishta", 1, 10.00))
        medicines.add(Medicine("2","Amritarishta", 1, 20.00))
        medicines.add(Medicine("3","Aragvadharishta", 1, 10.00))
        medicines.add(Medicine("4","Aravindasava", 1, 20.00))
        medicines.add(Medicine("5","Arjunarishta", 1, 10.00))
        medicines.add(Medicine("6","Ashokarishta", 1, 20.00))
        medicines.add(Medicine("7","Ashavagandharishta", 1, 10.00))
        medicines.add(Medicine("8","Balarishta", 1, 20.00))
        medicines.add(Medicine("9","Chandanasava", 1, 10.00))
        medicines.add(Medicine("10","Dashamularishta", 2, 20.00))
        medicines.add(Medicine("11","Drakshava", 1, 10.00))
        medicines.add(Medicine("12","Jirakadyarishta", 2, 20.00))
        medicines.add(Medicine("13","Kanakasava", 1, 10.00))
        medicines.add(Medicine("14","Kumarysava", 2, 20.00))
        medicines.add(Medicine("15","Kutajarishta", 1, 10.00))
        medicines.add(Medicine("16","Khadirishta", 2, 20.00))
        medicines.add(Medicine("17","Lodhrasava", 1, 10.00))
        // Add more medicines as needed
    }

    private fun updateDataInFirestore(medicine: Medicine, quantityChange: Int) {
        // Access the "medicines" collection
        val medicinesCollection = firestore.collection("medicines")

        // Update quantity
        val newQuantity = medicine.quantity + quantityChange

        // Create a HashMap to store the data
        val data = hashMapOf(
            "name" to medicine.name,
            "quantity" to newQuantity,
            "price" to medicine.price
        )

        // Update data in Firestore
        medicinesCollection.document(medicine.name).set(data)
            .addOnSuccessListener {
                // Data updated successfully
                // Update UI to reflect the new quantity (if needed)
            }
            .addOnFailureListener { e ->
                // Error handling
                // Log the error for debugging
                Log.e("Firestore", "Error updating medicine quantity: ${e.message}")
            }
    }
}

data class Medicine(val id: String, val name: String, val quantity: Int, val price: Double)
