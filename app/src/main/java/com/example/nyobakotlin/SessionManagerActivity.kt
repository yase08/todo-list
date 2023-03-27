package com.example.nyobakotlin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.provider.ContactsContract.CommonDataKinds.Email

class SessionManagerActivity(context : Context) {
    private var context: Context = context
    private var sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor = sharedPreferences.edit()

    companion object {
        private const val PREF_NAME = "SESSION"
        private const val KEY_EMAIL = "EMAIL"
        private const val KEY_IS_LOGIN = "IS_LOGIN"
    }

    fun login (email: String) {
        editor.putString(KEY_EMAIL, email)
        editor.putBoolean(KEY_IS_LOGIN, true)
        editor.apply()
    }

    fun logout() {
        editor.clear()
        editor.commit()

        val intent = Intent(context, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGIN, false)
    }

    fun getEmail(): String? {
        return sharedPreferences.getString(KEY_EMAIL, null)
    }
}