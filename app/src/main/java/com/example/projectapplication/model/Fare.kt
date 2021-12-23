package com.example.projectapplication.model

import java.sql.Timestamp

data class Message(
    var username: String = "",
    var message_id: String = "",
    var message: String = "",
    var creation_time: Long = 0
)

data class Order(
    var order_id: String = "",
    var username: String = "",
    var status: String = "",
    var owner_username: String = "",
    var price_per_unit: String = "",
    var units: String = "",
    var description: String = "",
    var title: String = "",
    var images: List<Image> = emptyList(),
    var creation_time: Long = 0,
    var messages: List<Message> = emptyList()
)

data class FareResponse(
    var item_count: Int,
    var orders: List<Order> = emptyList(),
    var timestamp: Long = 0
)

data class AddOrderResponse(
    var creation: String = "",
    var order_id: String = "",
    var username: String = "",
    var status: String = "",
    var owner_username: String = "",
    var price_per_unit: String = "",
    var units: String = "",
    var description: String = "",
    var title: String = "",
    var images: List<Image> = emptyList(),
    var creation_time: Long = 0
)

data class OrderedProduct(
    var title: String = "",
    var description: String = "",
    var price_per_unit: String = "",
    var units: String = "",
    var owner_username: String = "",
)

data class DeleteOrderResponse(
    var message: String = "",
    var order_id: String = "",
    var deletion_time: Long = 0
)