package com.a7.checkpostsystem.models

import com.google.firebase.Timestamp


data class Permit (
    val type: Int? = null,
    var status: Int? = null,
    val regNo: String? = null,
    val uid: String? = null,
    var validity: Timestamp? = null,
    val applyDate: Timestamp? = null,
    var appId: String? = null
)