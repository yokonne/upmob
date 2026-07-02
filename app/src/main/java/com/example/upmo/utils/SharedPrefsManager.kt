package com.example.upmo.utils

import android.content.Context
import android.content.SharedPreferences

object SharedPrefsManager {
    private const val PREF_NAME = "MatulePrefs"
    private const val KEY_FIRST_LAUNCH = "first_launch"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun isFirstLaunch(context: Context): Boolean {
        return getPrefs(context).getBoolean(KEY_FIRST_LAUNCH, true)
    }

    fun setFirstLaunch(context: Context, isFirst: Boolean) {
        getPrefs(context).edit().putBoolean(KEY_FIRST_LAUNCH, isFirst).apply()
    }
}