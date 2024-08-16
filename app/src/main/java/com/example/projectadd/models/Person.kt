package com.example.projectadd.models

data class Person(
    val id: String = "",
    val name: String = "",
    val prescriptions: ArrayList<Prescription> = ArrayList() // List of prescriptions for the person
)
