package com.demonhunter.todonoteskotlinversion.utils

import android.content.Context
import android.content.SharedPreferences


object StoreSession {  //here we have initialised the Shared preference from a singleton class
    private var sharedPreferences: SharedPreferences? = null
    fun init(context: Context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(
                PrefConst.SHARED_PREFERENCES_NAME,
                Context.MODE_PRIVATE
            )
        }
    }

    fun write(key: String, value: Boolean) {
        val editor: SharedPreferences.Editor? = sharedPreferences?.edit()
        editor?.putBoolean(key, value)
        editor?.apply()
    }

    fun read(key: String): Boolean? {
        return sharedPreferences?.getBoolean(key, false)
    }

    fun write(key: String, value: String) {
        val editor: SharedPreferences.Editor? = sharedPreferences?.edit()
        editor?.putString(key, value)
        editor?.apply()
    }

    fun readString(key: String): String? {
        return sharedPreferences?.getString(key, "")
    }
}
//this is singleton class to eliminate the boiler plate code