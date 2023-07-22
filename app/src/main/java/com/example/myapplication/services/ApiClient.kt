import android.util.Log
import com.example.myapplication.User
import com.example.myapplication.services.ApiService
import com.example.myapplication.model.Friend
import com.example.myapplication.model.PartyInfo
import com.example.myapplication.model.UserInfo
import com.example.myapplication.newtwork.response.AddFriendResponse
import com.example.myapplication.newtwork.response.PartyInfoResponse
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.picasso.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory


object ApiClient {
    private const val BASE_URL = "http://10.0.2.2:8000/api/"

    private fun provideHttpClient() =
        OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }).build()
//    val moshi = Moshi.Builder()
//        .addLast(KotlinJsonAdapterFactory())
//        .build()
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(provideHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: ApiService = retrofit.create(ApiService::class.java)


    fun createNewUser(user: User) {
        val call = apiService.signup(user)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("CreateNewUser", "Erreur lors de l'appel API: ${t.message}")
            }
        })
    }

    fun addFriend(userId: String, friendId: String): Call<AddFriendResponse> {

        return apiService.addFriend(userId, friendId)
    }

    fun getUserByUsername(acccessToken: String, username: String): Call<UserInfo> {
        return apiService.getbyUser(acccessToken, username)
    }

    fun authenticateUser(username: String, password: String, callback: (TokenResponse?) -> Unit) {
        val request = TokenRequest(username, password)

        Log.e("Auth", "login: ${request.username}")
        Log.e("Auth", "password: ${request.password}")

        val call = apiService.getToken(request)
        call.enqueue(object : Callback<TokenResponse> {
            override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                if (response.isSuccessful) {
                    val tokenResponse = response.body()
                    callback(tokenResponse)
                    Log.e("Auth", "Connected: ${tokenResponse?.token}")
                } else {
                    Log.e("Auth", "Erreur d'authent: ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                Log.e("Auth", "Erreur d'authent: ${t.message}")
                callback(null)
            }
        })
    }

    fun getUserInfo(token: String, id: String, callback: (UserInfo?) -> Unit){
        val call = apiService.getUserInfo("Token $token", id)
        call.enqueue(object : Callback<UserInfo> {
            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                if (response.isSuccessful) {
                    val userInfo = response.body()
                    callback(userInfo)
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<UserInfo?>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun getGameByUser(userId: String, accessToken: String?, callback: (List<PartyInfo>?) -> Unit) {
        val call = apiService.getGameByUser(accessToken!!, userId)
        call.enqueue(object : Callback<PartyInfoResponse> {
            override fun onResponse(call: Call<PartyInfoResponse>, response: Response<PartyInfoResponse>) {
                if (response.isSuccessful) {
                    val gamesData = response.body()
                    callback(gamesData?.results)
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<PartyInfoResponse>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun acceptFriend(accessToken: String?, playerId: Int): Call<AddFriendResponse> {
        return apiService.acceptFriend(accessToken ?: "", playerId)
    }
}
@JsonClass(generateAdapter = true)
data class TokenResponse(
    val token: String,
    val user_id: String
)

@JsonClass(generateAdapter = true)
data class TokenRequest(
    val username: String,
    val password: String
)
@JsonClass(generateAdapter = true)
data class FriendsResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Friend>
)

