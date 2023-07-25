package com.example.myapplication

import LoginViewModel
import SignupFragment
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    lateinit var username : EditText
    lateinit var password: EditText
    lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener(View.OnClickListener {
            var viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

            viewModel.connectUser(binding.username.text.toString(), binding.password.text.toString()) {
                result ->
                if (result){
                    Log.e("Connection", "User connected")
                    navigateToMainActivity()
                } else {
                    Log.e("Connection", "User not connected")
                    Toast.makeText(this, "Login Failed!", Toast.LENGTH_SHORT).show()
                }
            }
        })

        binding.signinButton.setOnClickListener(View.OnClickListener {
            val signUpModalDialog = SignupFragment()
            signUpModalDialog.show(supportFragmentManager, "SignUpModalDialog")
        })
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)

        startActivity(intent)

    }
}