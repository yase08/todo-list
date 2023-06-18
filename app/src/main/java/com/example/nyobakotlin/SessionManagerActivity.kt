package com.example.nyobakotlin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.provider.ContactsContract.CommonDataKinds.Email

class SessionManagerActivity(private val context: Context) {

    private val sharedPreferences = context.getSharedPreferences("my_shared_pref", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    fun saveAuthToken(token: String) {
        editor.putString("auth_token", token)
        editor.apply()
    }

    fun getAuthToken(): String? {
        return sharedPreferences.getString("auth_token", null)
    }

    fun clearAuthToken() {
        editor.remove("auth_token")
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return getAuthToken() != null
    }
}
