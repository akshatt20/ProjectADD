package com.example.projectadd.models

data class Prescription(
    val disease:String= "",
    var medicinesList:ArrayList<Medicines> = ArrayList()
)
