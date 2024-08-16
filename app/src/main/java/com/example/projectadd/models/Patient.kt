package com.example.projectadd.models

data class Patient(
    val name: String = "",
    val abhaId: String = "",
    val mobileNo: String = "",
    val age: String = "",
    val address: String = "",
    val gender: String = "",
    val password:String="",
    val emergency:String="",
    var prescriptions: ArrayList<P> = ArrayList()
)
