package com.example.myapplication.ui.my_account

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class AccountViewModel(application: Application) : AndroidViewModel(application) {
    private val PREF_ACCESS_TOKEN = "access_token"
    private val PREF_ID = "user_id"
    private val sharedPreferences =
        application.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    private val _userInfo = MutableLiveData<UserInfo>()
    val userInfo: LiveData<UserInfo> get() = _userInfo

    init {
        loadUserInfo()
    }

    private fun loadUserInfo() {
        val token = getAccessToken()
        val id = getId()

        if (token != null && id != null) {
            ApiClient.getUserInfo(token, id) { userInfo ->
                if (userInfo != null) {
                    _userInfo.postValue(userInfo)
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
}

data class Friend(
    val username: String,
    val player_id: Int,
    val asc_id: Int,
    val url_image: String
)
data class Ami(
    val username: String
)

data class UserInfo(
    val id: Int,
    val username: String,
    val email: String,
    val first_name: String,
    val last_name: String,
    val commentaire: Boolean,
    val url_image: String,
    val friends: List<Friend>,
    val invit: List<Any>
)
