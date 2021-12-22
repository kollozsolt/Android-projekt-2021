package com.example.projectapplication.manager

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager(context: Context) {

    private val SHARED_PREFERENCES_NAME = "MarketPlaceSharedPreferences"
    private var preferences: SharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    companion object {
        const val KEY_TOKEN = "SHARED_PREFERENCES_KEY_TOKEN"
        const val USER_NAME = "SHARED_PREFERENCES_USER_NAME"
        const val USER_INFO_NAME = "SHARED_PREFERENCES_USER_INFO_NAME"
        const val LIMIT = "SHARED_PREFERENCES_LIMIT"
        const val FILTER = "SHARED_PREFERENCES_FILTER"
        const val SORT = "SHARED_PREFERENCES_SORT"
        const val SKIP = "SHARED_PREFERENCES_SKIP"
        const val ORDER_LIMIT = "SHARED_PREFERENCES_ORDER_LIMIT"
        const val ORDER_FILTER = "SHARED_PREFERENCES_ORDER_FILTER"
        const val ORDER_SORT = "SHARED_PREFERENCES_ORDER_SORT"
        const val ORDER_SKIP = "SHARED_PREFERENCES_ORDER_SKIP"
    }

    fun putStringValue(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }

    fun getStringValue(key: String, defaultValue: String): String? {
        return preferences.getString(key, defaultValue)
    }
}