package com.example.fleet_management_system

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("Registered")
class UserPref  : AppCompatActivity() {

    // File Name: MyPrefsFile (data/data/project/shared_prefs/MyPrefsFile)
    private val PREFS_NAME = "MyPrefsFile"

    // if key exists, we return 1, otherwise return 0
    fun getPrefs(key: String, context: Context): Int {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        return prefs.getInt(key, 0)
    }

    // to set new data, we set 1 to any new keys, otherwise if they do not exist, getInt will return 0
    fun setPrefs(key: String, context: Context){
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        // set new key string with value 1
        prefs.edit().putInt(key, 1).apply()
    }
}