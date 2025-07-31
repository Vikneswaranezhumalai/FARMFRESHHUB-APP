package com.txstate.sampleapicall.model


data class Order(
    val orderId: Int,
    val productName: String,
    val quantity: Int,
    val totalPrice: Double,
    val status: String
)
