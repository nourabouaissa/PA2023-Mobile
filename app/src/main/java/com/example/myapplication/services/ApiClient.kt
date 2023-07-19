import com.example.myapplication.services.ApiService
import com.example.myapplication.ui.dashboard.Friend
import com.example.myapplication.ui.home.Game
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://random-data-api.com/"
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
}
