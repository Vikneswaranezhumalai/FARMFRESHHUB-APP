package com.txstate.sampleapicall.model

data class AuthResponse(
    val success: Boolean,
    val message: String,
    val status : Int,
    val user: User,
)

