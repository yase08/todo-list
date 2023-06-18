package com.example.nyobakotlin

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.*
import androidx.core.content.ContextCompat
import com.example.nyobakotlin.api.ApiConfig
import com.example.nyobakotlin.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManagerActivity
    lateinit var sph : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        sessionManager = SessionManagerActivity(this)

        val myCheckbox = findViewById<CheckBox>(R.id.my_checkbox)
        val loginButton = findViewById<Button>(R.id.loginButton)

        myCheckbox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                buttonView.buttonTintList =
                    ColorStateList.valueOf(resources.getColor(R.color.background_color))
            } else {
                buttonView.buttonTintList = ColorStateList.valueOf(resources.getColor(R.color.gray))
            }
        }

        loginButton.setOnClickListener {
            login()
        }
    }

    private fun login() {
//        val request = UserRequest()
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (TextUtils.isEmpty(emailEditText.text)) {
            emailEditText.background = ContextCompat.getDrawable(this, R.drawable.border_error)
            emailEditText.setTextColor(ContextCompat.getColor(this, R.color.red))
            emailEditText.setHintTextColor(ContextCompat.getColor(this, R.color.red))
            val errorIcon = resources.getDrawable(R.drawable.error)
            errorIcon.setBounds(
                -errorIcon.intrinsicWidth / 2,
                0,
                errorIcon.intrinsicWidth / 2,
                errorIcon.intrinsicHeight
            )
            emailEditText.setError("Email tidak boleh kosong", errorIcon)
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailEditText.text).matches()) {
            emailEditText.background = ContextCompat.getDrawable(this, R.drawable.border_error)
            emailEditText.setTextColor(ContextCompat.getColor(this, R.color.red))
            emailEditText.setHintTextColor(ContextCompat.getColor(this, R.color.red))
            val errorIcon = resources.getDrawable(R.drawable.error)
            errorIcon.setBounds(
                -errorIcon.intrinsicWidth / 2,
                0,
                errorIcon.intrinsicWidth / 2,
                errorIcon.intrinsicHeight
            )
            emailEditText.setError("Email tidak valid", errorIcon)
        }

        if (TextUtils.isEmpty(passwordEditText.text)) {
            passwordEditText.background =
                ContextCompat.getDrawable(this, R.drawable.border_error)
            passwordEditText.setTextColor(ContextCompat.getColor(this, R.color.red))
            passwordEditText.setHintTextColor(ContextCompat.getColor(this, R.color.red))
            val errorIcon = resources.getDrawable(R.drawable.error)
            errorIcon.setBounds(
                -errorIcon.intrinsicWidth / 2,
                0,
                errorIcon.intrinsicWidth / 2,
                errorIcon.intrinsicWidth
            )
            passwordEditText.setError("Password tidak boleh kosong", errorIcon)
        } else if (passwordEditText.text.length < 8) {
            passwordEditText.background =
                ContextCompat.getDrawable(this, R.drawable.border_error)
            passwordEditText.setTextColor(ContextCompat.getColor(this, R.color.red))
            passwordEditText.setHintTextColor(ContextCompat.getColor(this, R.color.red))
            val errorIcon = resources.getDrawable(R.drawable.error)
            errorIcon.setBounds(
                -errorIcon.intrinsicWidth / 2,
                0,
                errorIcon.intrinsicWidth / 2,
                errorIcon.intrinsicWidth
            )
            passwordEditText.setError("Password kurang dari 8 karakter", errorIcon)
        }

//        val retro = Retro().getRetroClientInstance().create(UserApi::class.java)
//        retro.login(request).enqueue(object : Callback<UserResponse> {
//            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
//                Log.e("error", "Gagal Login")
//            }
//
//            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
//                val userResponse = response.body()
//                if (userResponse?.data != null) {
//                    Log.e("status", "Berhasil login")
//                    val token = userResponse.data!!.token
//                    if (token != null) {
//                        sessionManager.saveAuthToken(token)
//                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
//                        finish()
//                    }
//                } else {
//                    Log.e("error", "Response body is null or data is null")
//                }
//            }
//        })

        ApiConfig.instanceRetrofit.login(email, password)
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                    val response = response.body()
                    if(response != null) {
                        val token = response.token
                        if (token != null) {
                            sessionManager.saveAuthToken(token)
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        }else {
                    Log.e("error", "Response body is null or data is null")
                }
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("error", "Gagal Login")
                }
            })
    }

    override fun onStart() {
        super.onStart()
        if (sessionManager.isLoggedIn()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}