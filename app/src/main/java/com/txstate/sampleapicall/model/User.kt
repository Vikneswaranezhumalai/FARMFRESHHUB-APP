package com.txstate.sampleapicall.model

data class User(val userId: Int,
                val name: String,
                val email: String,
                val userType: String,
                val phone: String?,
                val address: String?,
                val createdAt: String?)