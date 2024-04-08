package com.example.projectadd.models

data class Patient(
    val name: String = "",
    val abhaId: String = "",
    val mobileNo: String = "",
    val age: String = "",
    val address: String = "",
    val gender: String = "",
    val password:String="",
    var prescriptions: ArrayList<Prescription> = ArrayList()
)
