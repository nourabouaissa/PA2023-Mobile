package com.example.myapplication.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _gamesList = MutableLiveData<List<Game>>()
    val gamesList: LiveData<List<Game>> get() = _gamesList

    init {
        loadFamesList()
    }

    private fun loadFamesList() {
        ApiClient.getGamesList { gamesList ->
            if (gamesList != null) {
                _gamesList.value = gamesList
            } else {
                // Gérer l'erreur de chargement de la liste d'amis depuis l'API
            }
        }
    }
}

data class Game(val username: String, val email: String)