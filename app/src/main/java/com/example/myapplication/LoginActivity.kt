package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.myapplication.databinding.ActivityLoginBinding

class LoginActivity : Activity() {

    private lateinit var binding: ActivityLoginBinding

    lateinit var username : EditText
    lateinit var password: EditText
    lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener(View.OnClickListener {
            if (binding.username.text.toString() == "user" && binding.password.text.toString() == "1234"){
                navigateToMainActivity()
            } else {
                Toast.makeText(this, "Login Failed!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun navigateToMainActivity() {
        // Créer un intent pour passer à MainActivity
        val intent = Intent(this, MainActivity::class.java)

        // Si vous avez besoin de passer des données à MainActivity, vous pouvez utiliser putExtra()
        // intent.putExtra("key", value)

        // Démarrer l'activité MainActivity
        startActivity(intent)

        // Optionnellement, si vous souhaitez fermer LoginActivity après avoir démarré MainActivity, vous pouvez utiliser finish()
        //finish()
    }
}