package com.txstate.sampleapicall.model

data class Product(
    val product_id: Int,
    val farmer_id: Int?,
    val name: String = "",
    val category_id: Int?,
    val description: String,
    val price_per_unit: Double,
    val available_qty: Int?,
    val unit: String?,
    val created_at: String = ""
)
