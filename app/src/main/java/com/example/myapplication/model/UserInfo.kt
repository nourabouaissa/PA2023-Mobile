package com.example.myapplication.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserInfo(
    val id: Int,
    val username: String,
    val email: String,
    val first_name: String,
    val last_name: String,
    val commentaire: Boolean,
    val url_image: String,
    val friends: List<Friend>,
    val invit: List<FriendRequest>
)