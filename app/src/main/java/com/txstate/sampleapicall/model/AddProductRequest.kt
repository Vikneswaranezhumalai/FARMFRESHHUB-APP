package com.txstate.sampleapicall.model

data class AddProductRequest(
    val farmerId: Int,
    val name : String,
    val categoryId : Int,
    val description : String,
    val pricePerUnit : Double,
    val availableQty: Int,
    val unit: String
)