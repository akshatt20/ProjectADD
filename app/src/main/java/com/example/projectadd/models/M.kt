package com.example.projectadd.models

data class M(
    var id: Int = -1,
    var name: String = "",
    var quantity: Int = 0, // Aligning with Firestore's "quantity" field
    val price: String = "" // Adjust this field based on what "p" represents in Firestore
)