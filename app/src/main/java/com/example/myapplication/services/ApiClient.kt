import android.util.Log
import com.example.myapplication.User
import com.example.myapplication.services.ApiService
import com.example.myapplication.ui.dashboard.GamesData
import com.example.myapplication.ui.my_account.Ami
import com.example.myapplication.ui.my_account.Friend
import com.example.myapplication.ui.my_account.UserInfo
import com.squareup.picasso.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiClient {
    private const val BASE_URL = "http://10.0.2.2:8000/api/"

    private fun provideHttpClient()=OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
        level=if(BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    }).build()
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(provideHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: ApiService = retrofit.create(ApiService::class.java)


    fun createNewUser(user: User)
    {
        val call = apiService.signup(user)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("CreateNewUser", "Erreur lors de l'appel API: ${t.message}")
            }
        })
    }
    fun addFriend(newFriend: Ami): Call<Void> {
        val call = apiService.addFriend("2","1")
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    // Friend added successfully, handle the response if needed
                    Log.d("AddFriend", "Friend added successfully")
                } else {
                    // Handle error response
                    Log.e("AddFriend", "Failed to add friend: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // Handle network failure
                Log.e("AddFriend", "Erreur lors de l'appel API: ${t.message}")
            }
        })
        return call
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

    fun getGamesData(callback: (GamesData?) -> Unit) {
        val call = apiService.getGames()
        call.enqueue(object : Callback<GamesData> {
            override fun onResponse(call: Call<GamesData>, response: Response<GamesData>) {
                if (response.isSuccessful) {
                    val gamesData = response.body()
                    callback(gamesData)
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<GamesData>, t: Throwable) {
                callback(null)
            }
        })
    }
}

data class TokenResponse(
    val token: String,
    val user_id: String
)



data class TokenRequest(
    val username: String,
    val password: String
)

data class FriendsResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Friend>
)

