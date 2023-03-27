package com.example.nyobakotlin

import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.*
import androidx.core.content.ContextCompat

class LoginActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManagerActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sessionManager = SessionManagerActivity(this)

        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val myCheckbox = findViewById<CheckBox>(R.id.my_checkbox)

        myCheckbox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                buttonView.buttonTintList =
                    ColorStateList.valueOf(resources.getColor(R.color.background_color))
            } else {
                buttonView.buttonTintList = ColorStateList.valueOf(resources.getColor(R.color.gray))
            }
        }

        loginButton.setOnClickListener() {
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
            } else if (emailEditText.text.toString() == "user@gmail.com" && passwordEditText.text.toString() == "user12345") {
                sessionManager.login(emailEditText.text.toString())
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                emailEditText.background = ContextCompat.getDrawable(this, R.drawable.border_error)
                emailEditText.setTextColor(ContextCompat.getColor(this, R.color.red))
                emailEditText.setHintTextColor(ContextCompat.getColor(this, R.color.red))
                passwordEditText.background =
                    ContextCompat.getDrawable(this, R.drawable.border_error)
                passwordEditText.setTextColor(ContextCompat.getColor(this, R.color.red))
                passwordEditText.setHintTextColor(ContextCompat.getColor(this, R.color.red))
                val errorIcon = resources.getDrawable(R.drawable.error)
                errorIcon.setBounds(
                    -errorIcon.intrinsicWidth / 2,
                    0,
                    errorIcon.intrinsicWidth / 2,
                    errorIcon.intrinsicHeight
                )
                emailEditText.setError("Email salah", errorIcon)
                passwordEditText.setError("Password salah", errorIcon)
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
        }
    }

    override fun onStart() {
        super.onStart()
        if (sessionManager.isLoggedIn()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
