package com.a7.checkpostsystem.models

import com.google.firebase.firestore.DocumentReference

data class Vehicle(
    val owner:String? = null,
    val regNo: String? = null,
    val wheels: Int? = null,
    val type: Int? = null,
    val model: String? = null,
    val uid: String? = null
    )

