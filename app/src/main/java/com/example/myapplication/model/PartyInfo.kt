package com.example.myapplication.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PartyInfo(
    val id: Int,
    val title: String,
    val Founder: Player,
    val url_image: String,
    val started: Boolean,
    val created_at: String,
    val accepting_participants: List<Participant>,
    val pending_participants: List<Participant>
)

