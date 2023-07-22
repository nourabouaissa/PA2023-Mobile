package com.example.myapplication.ui.dashboard

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.PartyInfo

class DashboardViewModel(application: Application) : AndroidViewModel(application) {
    private val PREF_ACCESS_TOKEN = "access_token"
    private val PREF_ID = "user_id"
    private val sharedPreferences =
        application.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    private val _friendList = MutableLiveData<List<PartyInfo>>()
    val friendsList: LiveData<List<PartyInfo>> get() = _friendList

    init {
        loadFriendsList()
    }
    fun getId(): String? {
        return sharedPreferences.getString(PREF_ID, null)
    }
    fun getAccessToken(): String? {
        return sharedPreferences.getString(PREF_ACCESS_TOKEN, null)
    }
    private fun loadFriendsList() {
        ApiClient.getGameByUser(getId()!!,getAccessToken()?:"") { gamesData ->
            if (friendsList != null) {
                _friendList.value = gamesData
            } else {
                // Gérer l'erreur de chargement de la liste d'amis depuis l'API
            }
        }
    }
}

data class Founder(
    val id: Int,
    val username: String,
    val url_image: String
)

data class Player(
    val id: Int,
    val username: String,
    val url_image: String
)

data class AcceptingParticipant(
    val id: Int,
    val player: Player,
    val tag_player: String?,
    val point: Any?
)

data class Game(
    val id: Int,
    val title: String,
    val Founder: Founder,
    val url_image: String,
    val started: Boolean,
    val created_at: String,
    val accepting_participants: List<AcceptingParticipant>,
    val pending_participants: List<Any>
)

data class GamesData(
    val count: Int,
    val next: Any?,
    val previous: Any?,
    val results: List<Game>
)
