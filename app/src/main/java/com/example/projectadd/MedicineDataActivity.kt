package com.example.projectadd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import java.util.Date
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import com.example.projectadd.databinding.ActivityMedicineDataBinding
import com.example.projectadd.models.M
import com.example.projectadd.models.Md
import com.example.projectadd.models.mL
import com.example.projectadd.models.Patient
import com.example.projectadd.models.P


class MedicineDataActivity : AppCompatActivity() {
    private lateinit var firestore: FirebaseFirestore
    private lateinit var recyclerView: RecyclerView
    private lateinit var username: String
    private val selectedTests = mutableListOf<String>()
    //    private lateinit var searchView: Appcomp
    private lateinit var binding:ActivityMedicineDataBinding
    private lateinit var abhaId:String
    private var selectedDisease:String? = null
    private  lateinit var medicinesList:ArrayList<Md>;

    private lateinit var recyclerAdapter: RecyclerContactAdapter
    private var arrContact = ArrayList<M>()
    private  var patient:Patient? = null
    private val diseases = arrayOf("Syphilis(A50-53)", "Chlamydia(A55-56)", "Gonorrhea(A54)", "Pertussis(A37)", "Tetanus(A33-35)", "Measles(B05)")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMedicineDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        abhaId = intent.getStringExtra("ABHA_ID")!!
        username = intent.getStringExtra("DOCTOR_NAME") ?: ""


        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance()
        binding.testOption1.setOnCheckedChangeListener { _, isChecked ->
            updateSelectedTests("Biopsy", isChecked)
        }
        binding.testOption2.setOnCheckedChangeListener { _, isChecked ->
            updateSelectedTests("Blood Test", isChecked)
        }
        binding.testOption3.setOnCheckedChangeListener { _, isChecked ->
            updateSelectedTests("Endoscopy", isChecked)
        }
        binding.testOption4.setOnCheckedChangeListener { _, isChecked ->
            updateSelectedTests("Ultrasound", isChecked)
        }
        recyclerView = findViewById(R.id.recyclerView)
//        searchView = findViewById(R.id.searchView)



//        searchView = findViewById(R.id.searchView)
        setupSpinner();

        //TODO : Remove this
        arrContact.add(M(1,"abhayarishta", 0, "1"))
        arrContact.add(M(2,"amritarishta", 0, "2"))
        arrContact.add(M(3,"aragvadharishta", 0, "3"))
        arrContact.add(M(4,"aravindasava", 0, "4"))
        arrContact.add(M(5,"arjunarishta/parthadyarishta", 0, "5"))
        arrContact.add(M(6,"ashokarishta", 0, "6"))
        arrContact.add(M(7,"ashvagandharishta", 0, "7"))
        arrContact.add(M(8,"balarishta", 0, "8"))
        arrContact.add(M(9,"chandanasava", 0, "9"))
        arrContact.add(M(10,"dashamularishta", 0, "10"))
        arrContact.add(M(11,"drakshasava", 0, "11"))
        arrContact.add(M(12,"draksharishta", 0, "12"))
        arrContact.add(M(13,"jirakadyarishta", 0, "13"))
        arrContact.add(M(14,"kanakasava", 0, "14"))
        arrContact.add(M(15,"kumaryasava", 0, "15"))


        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerAdapter = RecyclerContactAdapter(this, arrContact, ::incrementQuantity, ::decrementQuantity)
        recyclerView.adapter = recyclerAdapter




        // Upload data to Firestore

        medicinesList = ArrayList()
        for(i in 0 until arrContact.size) {
            medicinesList.add(Md(arrContact[i].id,arrContact[i].quantity, arrContact[i].price, arrContact[i].name))
        }
        uploadDataToFirestore(medicinesList)
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
    private fun updateSelectedTests(test: String, isChecked: Boolean) {
        if (isChecked) {
            selectedTests.add(test)
        } else {
            selectedTests.remove(test)
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
    var TAG = "Adi"
    private fun prescribeMedicinesToPatient(abhaId: String) {
        Log.i(TAG, "prescribeMedicinesToPatient: abha id = ${abhaId}")
        val prescribedMedicinesList = ArrayList<Md>()
        Log.i(TAG, "prescribeMedicinesToPatient: medicines list size = ${medicinesList.size}")

        for(i in 0 until medicinesList.size){
            if(medicinesList[i].q > 0){
                val prescribedMedicine = Md(
                    id = medicinesList[i].id,
                    q = medicinesList[i].q,
                    p = medicinesList[i].p,
                    name = medicinesList[i].name
                )
                prescribedMedicinesList.add(prescribedMedicine)
            }
        }
        Log.i(TAG, "prescribeMedicinesToPatient: prescribed list size = ${prescribedMedicinesList.size}")
        Log.i(TAG, "prescribeMedicinesToPatient: tests list size = ${selectedTests.size}")

        if(prescribedMedicinesList.isEmpty() && selectedTests.isEmpty()) return

        // Create a new prescription with the doctor's name, current date, medicines with names, and tests
        val newPrescription = P(selectedDisease!!, prescribedMedicinesList, username, Date(), selectedTests)

        // Add the new prescription to the patient's list of prescriptions
        Log.i(TAG, "prescribeMedicinesToPatient: prescriptions list size before= ${patient!!.prescriptions.size}")

        patient!!.prescriptions.add(newPrescription)
        Log.i(TAG, "prescribeMedicinesToPatient: prescriptions list size after= ${patient!!.prescriptions.size}")

        FirebaseFirestore.getInstance().collection("Patients")
            .document(abhaId)
            .set(patient!!)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    finish()
                    Toast.makeText(this@MedicineDataActivity, "Prescription (medicines and tests) added successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MedicineDataActivity, "Error adding prescription: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun uploadDataToFirestore(arrContact: ArrayList<Md>) {
        medicinesList = ArrayList()
        for(i in 0 until arrContact.size) {
            medicinesList.add(Md(arrContact[i].id,arrContact[i].q, arrContact[i].p))
        }
        val mL = mL(arrContact)
        val collectionRef = firestore.collection("medalist")
        collectionRef.document("all_medicines").set(mL)
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

    private fun incrementQuantity(position:Int, itemId: Int, currentQuantity: Int) :Unit {
        val newQuantity = currentQuantity + 1
        for(i in 0 until medicinesList.size){
            if(medicinesList[i].id == itemId) medicinesList[i].q = newQuantity
            if(arrContact[i].id == itemId) arrContact[i].quantity = newQuantity
        }
        recyclerAdapter.updateData(arrContact)

        Log.i("adi", "decrementQuantity: updated "+itemId+" "+newQuantity)

    }


    private fun decrementQuantity(position:Int, itemId: Int, currentQuantity: Int) :Unit {
        if (currentQuantity > 0) {
            val newQuantity = currentQuantity - 1
            for(i in 0 until arrContact.size ){
                if(medicinesList[i].id == itemId) medicinesList[i].q = newQuantity
                if(arrContact[i].id == itemId) arrContact[i].quantity = newQuantity
            }
            recyclerAdapter.updateData(arrContact)
            Log.i("adi", "decrementQuantity: updated "+itemId+" "+newQuantity)
        }
    }

    private fun filterMedicinesWithQuantityGreaterThanZero(arrContact: ArrayList<M>): ArrayList<M> {
        val filteredMedicines = ArrayList<M>()
        for (contact in arrContact) {
            if (contact.quantity > 0) {
                filteredMedicines.add(contact)
            }
        }
        return filteredMedicines
    }
}

