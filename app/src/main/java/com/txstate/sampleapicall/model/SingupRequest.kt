package com.txstate.sampleapicall.model

data class SignupRequest(
    val name: String,
    val email: String,
    val passwordHash: String,
    val phone: String?,
    val userType : String?,
    val address: String?,
    val createdAt : String = ""
)
