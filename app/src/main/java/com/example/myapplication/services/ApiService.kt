package com.example.myapplication.services


import TokenRequest
import TokenResponse
import com.example.myapplication.User
import com.example.myapplication.ui.dashboard.GamesData
import com.example.myapplication.ui.my_account.Ami
import com.example.myapplication.ui.my_account.Friend
import com.example.myapplication.ui.my_account.UserInfo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("player/")
    fun signup(@Body request: User) : Call<Void>
    @POST("addfriend/{friendId}/{userId}/")
    fun addFriend(@Path("userId") userId:String,@Path("friendId") friendId:String): Call<Void>
    @POST("auth/")
    fun getToken(@Body request: TokenRequest) : Call<TokenResponse>

    @GET("player/{id}")
    fun getUserInfo(@Header("Authorization") token: String, @Path("id") id: String): Call<UserInfo>

    @GET("party/")
    fun getGames() : Call<GamesData>
}
