package com.example.ourclass.model

data class FirebaseItemsClassView(
    val imageUrl: String,
    val class_name: String,
    val class_star: Int,
    val vote_count: Int,
    val price: Int
)