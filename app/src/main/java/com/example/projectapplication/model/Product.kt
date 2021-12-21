package com.example.projectapplication.model

import java.lang.StringBuilder

data class Image(
    val _id: String,
    val image_id: String,
    val image_name: String,
    val image_path: String
)

data class Product(
    var rating: Double=0.0,
    var amount_type: String="",
    var price_type: String="",
    var product_id: String="",
    var username: String="",
    var is_active: Boolean=true,
    var price_per_unit: String="",
    var units: String="",
    var description: String="",
    var title: String="",
    var image: List<Image> = emptyList(),
    var creation_time: Long=0
)

data class ProductResponse(val item_count: Int, val products: List<Product>, val timestamp: Long)

data class AddProductResponse(
    var creation: String,
    var product_id: String,
    var username: String,
    var is_active: Boolean,
    var price_per_unit: String,
    var units: String,
    var description: String,
    var title: String,
    var amount_type: String,
    var price_type: String,
    var images: List<Image>,
    var creation_time: Long
)

data class DeleteProductResponse(
    var message : String,
    var product_id: String,
    var deletion_time: Long
)