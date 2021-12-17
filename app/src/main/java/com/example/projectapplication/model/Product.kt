package com.example.projectapplication.model

data class Image(
    val _id: String,
    val image_id: String,
    val image_name: String,
    val image_path: String
)

data class Product(
    val rating: Double,
    val amount_type: String,
    val price_type: String,
    val product_id: String,
    val username: String,
    val is_Active: Boolean,
    val price_per_unit: String,
    val units: String,
    val descriptor: String,
    val title: String,
    val image: List<Image>,
    val creation_time: Long
)

data class ProductResponse(val item_count: Int, val products: List<Product>, val timestamp: Long)