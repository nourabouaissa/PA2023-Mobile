package com.example.myapplication.services


import TokenRequest
import TokenResponse
import com.example.myapplication.User
import com.example.myapplication.model.PartyInfo
import com.example.myapplication.model.UserInfo
import com.example.myapplication.newtwork.response.AddFriendResponse
import com.example.myapplication.newtwork.response.PartyInfoResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @POST("player/")
    fun signup(@Body request: User) : Call<Void>
    @POST("addfriend/{friendId}/{userId}/")
    fun addFriend(@Path("friendId") friendId:String,@Path("userId") userId:String): Call<AddFriendResponse>
    @POST("auth/")
    fun getToken(@Body request: TokenRequest) : Call<TokenResponse>

    @GET("player/{id}")
    fun getUserInfo(@Header("Authorization") token: String, @Path("id") id: String): Call<UserInfo>

    @GET("myparty/{id}/")
    fun getGameByUser(@Header("Authorization") token: String, @Path("id") id: String): Call<PartyInfoResponse>
   //get by username
    @GET("playerName/{userId}")
    fun getbyUser(@Header("Authorization") token: String, @Path("userId") id: String): Call<UserInfo>
    @PUT("acceptfriend/{friendId}/")
    fun acceptFriend(@Header("Authorization") token:String ,@Path("friendId")friendId: Int): Call<AddFriendResponse>


}
