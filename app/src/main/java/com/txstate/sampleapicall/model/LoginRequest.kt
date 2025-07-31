package com.txstate.sampleapicall.model

data class LoginRequest(
    val email: String,
    val passwordHash: String
)

