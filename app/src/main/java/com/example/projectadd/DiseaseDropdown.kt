package com.example.projectadd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import android.view.View


class DiseaseDropdown : AppCompatActivity() {

    private val diseases = arrayOf("Syphilis(A50-53)", "Chlamydia(A55-56)", "Gonorrhea(A54)", "Pertussis(A37)", "Tetanus(A33-35)", "Measles(B05)")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_disease_dropdown)

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
                val selectedDisease = parent.getItemAtPosition(position).toString()
                Toast.makeText(
                    this@DiseaseDropdown,
                    "Selected Disease: $selectedDisease",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }
    }
}
