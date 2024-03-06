package com.example.myfamily

import android.content.Context
import android.content.SharedPreferences

private const val NAME = "myFamily"
private const val MODE = Context.MODE_PRIVATE
private lateinit var preferences: SharedPreferences

object SharedPref {

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    // Define a function to store a boolean value in the shared preferences
    fun putBoolean(key: String, value: Boolean) {
        // Get the editor for the shared preferences
        // Put the boolean value with the specified key in the editor
        // Commit the changes to the shared preferences
        preferences.edit().putBoolean(key, value).commit()
    }

    //retrieve a boolean value from the shared preferences
    fun getBoolean(key: String): Boolean {
        // Get the boolean value with the specified key from the shared preferences
        return preferences.getBoolean(key, false)
    }
}