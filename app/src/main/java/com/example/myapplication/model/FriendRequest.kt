package com.example.myapplication.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FriendRequest(
    val username: String,
    val player_id: Int,
    val asc_id: Int,
    val url_image: String
)
