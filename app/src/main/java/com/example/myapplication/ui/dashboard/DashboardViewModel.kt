package com.example.myapplication.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.ui.home.Game

class DashboardViewModel : ViewModel() {

    private val _friendList = MutableLiveData<List<Friend>>()
    val friendsList: LiveData<List<Friend>> get() = _friendList

    init {
        loadFriendsList()
    }

    private fun loadFriendsList() {
        ApiClient.getFriendsList { friendsList ->
            if (friendsList != null) {
                _friendList.value = friendsList
            } else {
                // Gérer l'erreur de chargement de la liste d'amis depuis l'API
            }
        }
    }
}

data class Friend(val first_name: String, val avatar: String)