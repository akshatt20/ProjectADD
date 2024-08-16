package com.example.projectadd.models

data class Md(
    val id: Int = 0,
    var q: Int = 0,
    val p: String = "",
    val name: String = "" // Add this line to include the medicine name
)