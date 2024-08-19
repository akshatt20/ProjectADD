package com.example.projectadd.models

import java.io.Serializable
import java.util.Date

data class P(
    val disease: String = "",
    var mL: ArrayList<Md> = ArrayList(),
    val doctorName: String = "",
    val date: Date = Date(),
    val tests: List<String> = listOf()
) : Serializable {
    // No-argument constructor for Firestore
    constructor() : this("", ArrayList(), "", Date(), listOf())
}
