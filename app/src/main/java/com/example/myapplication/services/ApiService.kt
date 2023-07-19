package com.example.myapplication.services

import com.example.myapplication.ui.dashboard.Friend
import com.example.myapplication.ui.home.Game
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("/api/users/random_user?size=30")
    fun getFriends(): Call<List<Friend>>

    @GET("/api/users/random_user?size=5")
    fun getGames(): Call<List<Game>>
}
