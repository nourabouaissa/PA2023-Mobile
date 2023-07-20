import android.app.Application
import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val PREF_ACCESS_TOKEN = "access_token"
    private val PREF_ID = "user_id"
    private val sharedPreferences =
        application.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    fun connectUser(username: String, password: String, callback: (Boolean) -> Unit) {
        ApiClient.authenticateUser(username, password) { authData ->
            val result = authData != null
            if (result) {
                saveUserId(authData!!.user_id)
                saveAccessToken(authData.token)
            }
            callback(result)
        }
    }

    private fun saveAccessToken(token: String?) {
        viewModelScope.launch {
            sharedPreferences.edit {
                putString(PREF_ACCESS_TOKEN, token)
                apply()
            }
        }
    }

    private fun saveUserId(userId: String?) {
        viewModelScope.launch {
            sharedPreferences.edit {
                putString(PREF_ID, userId)
                apply()
            }
        }
    }
}
