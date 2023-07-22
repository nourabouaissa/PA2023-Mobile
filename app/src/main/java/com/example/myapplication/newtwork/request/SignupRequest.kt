package com.example.myapplication.newtwork.request

data class SignupRequest(
    val username: String,
    val password: String,
    val confirmPassword: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val profileImageUrl: String
)

