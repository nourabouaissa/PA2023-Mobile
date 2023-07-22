package com.example.myapplication.newtwork.response

import com.example.myapplication.model.PartyInfo

data class PartyInfoResponse (
    val count: Long,
    val next: Any? = null,
    val previous: Any? = null,
    val results: List<PartyInfo>
)