package com.example.myapplication.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Friend(
    val username: String,
    val player_id: Int,
    val asc_id: Int,
    val url_image: String
)

data class Ami(
    val username: String
)