package com.example.myapplication.ui.my_account

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.model.FriendRequest
import com.example.myapplication.newtwork.response.AddFriendResponse
import retrofit2.Call

class FriendsRequestViewModel(application: Application) : AndroidViewModel(application) {
    private val PREF_ACCESS_TOKEN = "access_token"
    private val PREF_ID = "user_id"
    private val sharedPreferences =
        application.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    private val _friendRequest = MutableLiveData<List<FriendRequest>>()
    val friendRequest: LiveData<List<FriendRequest>> get() = _friendRequest

    init {
        friendRequest()
    }

       fun friendRequest() {
        val token = getAccessToken()
        val id = getId()

        if (token != null && id != null) {

            ApiClient.getUserInfo(token, id) { userInfo ->

                if (userInfo != null) {
                    _friendRequest.postValue(userInfo.invit)
                } else {
                    // Gérer l'erreur de chargement de la liste d'amis depuis l'API
                }
            }
        } else {
            // Gérer le cas où le token ou l'ID est null
        }
    }

    fun getId(): String? {
        return sharedPreferences.getString(PREF_ID, null)
    }

    fun getAccessToken(): String? {
        return sharedPreferences.getString(PREF_ACCESS_TOKEN, null)
    }

    fun acceptFriend(playerId: Int): Call<AddFriendResponse> {
       return ApiClient.acceptFriend(getAccessToken(),playerId)
    }
}
