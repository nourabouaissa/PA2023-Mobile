package com.example.myapplication.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Player(
    val id: Int,
    val username: String,
    val url_image: String
)
