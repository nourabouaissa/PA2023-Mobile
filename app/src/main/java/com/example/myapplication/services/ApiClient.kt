import android.util.Log
import com.example.myapplication.User
import com.example.myapplication.services.ApiService
import com.example.myapplication.ui.dashboard.Friend
import com.example.myapplication.ui.home.Game
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.Console

object ApiClient {
    private const val BASE_URL = "http://10.0.2.2:8000/api/"
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: ApiService = retrofit.create(ApiService::class.java)

    fun getFriendsList(callback: (List<Friend>?) -> Unit) {
        val call = apiService.getFriends()
        call.enqueue(object : Callback<List<Friend>> {
            override fun onResponse(call: Call<List<Friend>>, response: Response<List<Friend>>) {
                if (response.isSuccessful) {
                    val apiFriendsList = response.body()
                    val friendsList = apiFriendsList?.map { apiData ->
                        Friend(apiData.first_name, apiData.avatar)
                    }
                    callback(friendsList)
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<Friend>>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun getGamesList(callback: (List<Game>?) -> Unit) {
        val call = apiService.getGames()
        call.enqueue(object : Callback<List<Game>> {
            override fun onResponse(call: Call<List<Game>>, response: Response<List<Game>>) {
                if (response.isSuccessful) {
                    val apiList = response.body()
                    val gamesList = apiList?.map { apiData ->
                        Game(apiData.username, apiData.email)
                    }
                    callback(gamesList)
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<Game>>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun CreateNewUser(user: User)
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

    fun getToken(username: String, password: String, callback: (String?) -> Unit) {
        val request = TokenRequest(username, password)

        Log.e("Auth", "login: ${request.username}")
        Log.e("Auth", "password: ${request.password}")

        val call = apiService.getToken(request)
        call.enqueue(object : Callback<TokenResponse> {
            override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                if (response.isSuccessful) {
                    val tokenResponse = response.body()
                    val token = tokenResponse?.token
                    callback(token)
                    Log.e("Auth", "Connected: ${token}")
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

}

data class TokenResponse(
    val token: String,
    val user_id: String
)



data class TokenRequest(
    val username: String,
    val password: String
)
