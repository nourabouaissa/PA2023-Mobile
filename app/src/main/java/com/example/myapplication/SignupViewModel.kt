package com.example.myapplication

import androidx.lifecycle.ViewModel

class SignupViewModel : ViewModel() {
    fun CreateUser(user : User)
    {
        ApiClient.CreateNewUser(user);
    }
}

data class User(
    val username: String,
    val password: String,
    val confirmPassword: String,
    val first_name: String,
    val last_name: String,
    val email: String,
    val url_image: String
)