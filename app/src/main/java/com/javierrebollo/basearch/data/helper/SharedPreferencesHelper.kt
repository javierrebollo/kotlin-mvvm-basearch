package com.javierrebollo.basearch.data.helper

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.javierrebollo.basearch.domain.entity.Token

private const val USER_PREFERENCE_DATA = "USER_PREFERENCE"
private const val USER_PREFERENCE_TOKEN = "USER_PREFERENCE_TOKEN"

class SharedPreferencesHelper(
    private val context: Context
) {
    private fun getPreferences(preferencesName: String): SharedPreferences {
        return context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
    }

    private fun getPreferencesEditor(preferencesName: String): SharedPreferences.Editor {
        return getPreferences(preferencesName).edit()
    }

    //#########################
    //#### APP PREFERENCES ####
    //#########################

    private val appPreferences: SharedPreferences by lazy {
        getPreferences(USER_PREFERENCE_DATA)
    }

    var token: Token?
        get() = appPreferences.getString(USER_PREFERENCE_TOKEN, null)
        set(value) {
            appPreferences.edit { putString(USER_PREFERENCE_TOKEN, value) }
        }
}