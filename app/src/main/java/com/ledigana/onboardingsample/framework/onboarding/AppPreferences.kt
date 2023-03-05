package com.ledigana.onboardingsample.framework.onboarding

import android.content.Context
import android.content.SharedPreferences
import com.ledigana.data.PreferencesSource

object AppPreferences : PreferencesSource {
    private const val NAME = "OnboardingPreferences"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    override fun getBool(name: String): Boolean {
        return preferences.getBoolean(name, false)
    }

    override fun setBool(name: String, value: Boolean) {
        preferences.edit {
            it.putBoolean(name, value)
        }
    }
}