package com.example.myapplication.services

import TokenRequest
import TokenResponse
import com.example.myapplication.User
import com.example.myapplication.model.SignupRequest
import com.example.myapplication.model.SignupResponse
import com.example.myapplication.ui.dashboard.Friend
import com.example.myapplication.ui.home.Game
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("/api/users/random_user?size=30")
    fun getFriends(): Call<List<Friend>>

    @GET("/api/users/random_user?size=5")
    fun getGames(): Call<List<Game>>

    @POST("auth/")
    fun getToken(@Body request: TokenRequest) : Call<TokenResponse>
    @POST("player/")
    fun signup(@Body request: User) : Call<Void>
}
