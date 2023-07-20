package com.example.myapplication.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    private val _friendList = MutableLiveData<List<Game>>()
    val friendsList: LiveData<List<Game>> get() = _friendList

    init {
        loadFriendsList()
    }

    private fun loadFriendsList() {
        ApiClient.getGamesData() { gamesData ->
            if (friendsList != null) {
                _friendList.value = gamesData?.results
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
