package com.example.projectadd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import android.widget.Toast
import com.example.projectadd.databinding.ActivityMedicineDataBinding
import com.example.projectadd.models.Medicines
import com.example.projectadd.models.MedicinesList
import com.example.projectadd.models.Patient
import com.example.projectadd.models.Prescription

class MedicineDataActivity : AppCompatActivity() {
    private lateinit var firestore: FirebaseFirestore
    private lateinit var recyclerView: RecyclerView
//    private lateinit var searchView: Appcomp
    private lateinit var binding:ActivityMedicineDataBinding
    private lateinit var abhaId:String
    private var selectedDisease:String? = null


    private lateinit var recyclerAdapter: RecyclerContactAdapter
    private var arrContact = ArrayList<Medicines>()
    private  var patient:Patient? = null
    private val diseases = arrayOf("Syphilis(A50-53)", "Chlamydia(A55-56)", "Gonorrhea(A54)", "Pertussis(A37)", "Tetanus(A33-35)", "Measles(B05)")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMedicineDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        abhaId = intent.getStringExtra("ABHA_ID")!!

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance()

        recyclerView = findViewById(R.id.recyclerView)

//        searchView = findViewById(R.id.searchView)
        setupSpinner();

        //TODO : Remove this
        arrContact.add(Medicines("abhayarishta", 0, "1"))
        arrContact.add(Medicines("amritarishta", 0, "2"))
        arrContact.add(Medicines("aragvadharishta", 0, "3"))
        arrContact.add(Medicines("aravindasava", 0, "4"))
        arrContact.add(Medicines("arjunarishta/parthadyarishta", 0, "5"))
        arrContact.add(Medicines("ashokarishta", 0, "6"))
        arrContact.add(Medicines("ashvagandharishta", 0, "7"))
        arrContact.add(Medicines("balarishta", 0, "8"))
        arrContact.add(Medicines("chandanasava", 0, "9"))
        arrContact.add(Medicines("dashamularishta", 0, "10"))
        arrContact.add(Medicines("drakshasava", 0, "11"))
        arrContact.add(Medicines("draksharishta", 0, "12"))
        arrContact.add(Medicines("jirakadyarishta", 0, "13"))
        arrContact.add(Medicines("kanakasava", 0, "14"))
        arrContact.add(Medicines("kumaryasava", 0, "15"))


        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerAdapter = RecyclerContactAdapter(this, arrContact, ::incrementQuantity, ::decrementQuantity)
        recyclerView.adapter = recyclerAdapter

        // Upload data to Firestore
        uploadDataToFirestore(arrContact)
        fetchPatient(abhaId)

        binding.btnSubmit.setOnClickListener {
            prescribeMedicinesToPatient(abhaId)
        }



    }

    private fun setupSpinner() {
        val diseaseSpinner = findViewById<Spinner>(R.id.diseasesSpinner)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, diseases)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        diseaseSpinner.adapter = adapter

        diseaseSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                // An item was selected. You can retrieve the selected item using parent.getItemAtPosition(position)
                selectedDisease = parent.getItemAtPosition(position).toString()


            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
                selectedDisease = parent.getItemAtPosition(0).toString()
            }
        }
    }

    private fun fetchPatient(abhaId: String) {
        firestore.collection("Patients")
            .document(abhaId)
            .get()
            .addOnCompleteListener{
                if(it.isSuccessful){
                    patient = it.result.toObject(Patient::class.java)
                    if(patient != null) {
                        binding.btnSubmit.visibility = View.VISIBLE
                    }
                }
            }
    }

    private fun prescribeMedicinesToPatient(abhaId: String) {
        val prescribedMedicinesList = ArrayList<Medicines>()
        for(i in 0 until arrContact.size){
            if(arrContact[i].quantity > 0){
                prescribedMedicinesList.add(arrContact[i])
            }
        }
        patient!!.prescriptions.add(Prescription(selectedDisease!!, prescribedMedicinesList))
        FirebaseFirestore.getInstance().collection("Patients")
            .document(abhaId)
            .set(patient!!)
            .addOnCompleteListener{
                if(it.isSuccessful){
                    finish()
                    Toast.makeText(this@MedicineDataActivity, "Medicines Prescribed successfully",Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener{
                Toast.makeText(this@MedicineDataActivity, "Some error encountered", Toast.LENGTH_SHORT).show()

            }


    }


    private fun uploadDataToFirestore(arrContact: ArrayList<Medicines>) {
        val medicinesArray = ArrayList<HashMap<String, Any>>()
        val medicinesList = MedicinesList(arrContact)
        val collectionRef = firestore.collection("medalist")
        collectionRef.document("all_medicines").set(medicinesList)
            .addOnSuccessListener {
                Toast.makeText(this@MedicineDataActivity, "All medicines data uploaded successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this@MedicineDataActivity, "Error adding document: $e", Toast.LENGTH_SHORT).show()
            }
    }

    fun updatePatientPrescription() {
        FirebaseFirestore.getInstance().collection("PATIENTS")
            .document()
    }

    private fun updateQuantityInFirestore(itemId: String, newQuantity: Int) {
        val docRef = firestore.collection("updatedQuantities").document(itemId)

        docRef.set(mapOf("quantity" to newQuantity))
            .addOnSuccessListener {
                Toast.makeText(this@MedicineDataActivity, "Quantity updated successfully", Toast.LENGTH_SHORT).show()

                val filteredMedicines = filterMedicinesWithQuantityGreaterThanZero(arrContact)

            }
            .addOnFailureListener { exception ->
                Toast.makeText(this@MedicineDataActivity, "Error updating quantity: $exception", Toast.LENGTH_SHORT).show()
            }
    }

    private fun incrementQuantity(position:Int, itemId: String, currentQuantity: Int) :Unit {
        val newQuantity = currentQuantity + 1
        for(i in 0 until arrContact.size){
            if(arrContact[i].id == itemId) arrContact[i].quantity = newQuantity
        }
        recyclerAdapter.updateData(arrContact)

        Log.i("adi", "decrementQuantity: updated "+itemId+" "+newQuantity)

    }


    private fun decrementQuantity(position:Int, itemId: String, currentQuantity: Int) :Unit {
        if (currentQuantity > 0) {
            val newQuantity = currentQuantity - 1
            for(i in 0 until arrContact.size ){
                if(arrContact[i].id == itemId) arrContact[i].quantity = newQuantity
            }
            recyclerAdapter.updateData(arrContact)
            Log.i("adi", "decrementQuantity: updated "+itemId+" "+newQuantity)
        }
    }

    private fun filterMedicinesWithQuantityGreaterThanZero(arrContact: ArrayList<Medicines>): ArrayList<Medicines> {
        val filteredMedicines = ArrayList<Medicines>()
        for (contact in arrContact) {
            if (contact.quantity > 0) {
                filteredMedicines.add(contact)
            }
        }
        return filteredMedicines
    }
}