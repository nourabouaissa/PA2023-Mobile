package com.example.myapplication.newtwork.response

data class AddFriendResponse(
    val id: Int,
    val accepting: Boolean,
    val Player1: Int,
    val Player2: Int,
    val who_ask: Int
)
