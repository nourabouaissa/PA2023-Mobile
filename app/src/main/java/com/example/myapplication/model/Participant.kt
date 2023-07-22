package com.example.myapplication.model

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Participant(
    val id: Int,
    val player: Player,
    val tag_player: String,
    val point: Int
)