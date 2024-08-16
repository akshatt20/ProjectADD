package com.example.projectadd.models

data class Prescription(
    val id: String = "",
    val dis: String = "",
    var status: String = "active" // Can be "active" or "deactivated"
)
