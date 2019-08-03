package com.countries.ui.theming

import android.content.Context
import javax.inject.Inject

private const val COUNTRIES_PREFERENCES = "countries_preferences"
private const val PREF_KEY_THEME = "pref_theme"
private val DEFAULT_THEME = Theme.LIGHT

class Preferences @Inject constructor(context: Context) {

    private val userThemePreferences by lazy {
        context.getSharedPreferences(COUNTRIES_PREFERENCES, Context.MODE_PRIVATE)
    }

    fun getTheme(): Theme {
        val currentTheme = userThemePreferences.getString(PREF_KEY_THEME, DEFAULT_THEME.name) ?: DEFAULT_THEME.name
        return Theme.valueOf(currentTheme)
    }

    fun setTheme(theme: Theme) {
        userThemePreferences.edit().putString(PREF_KEY_THEME, theme.name).apply()
    }
}