package com.countries.core.themes

import android.content.Context

private const val COUNTRIES_PREFERENCES = "countries_preferences"
private const val PREF_KEY_THEME = "pref_theme"
private val DEFAULT_THEME = Theme.LIGHT

class ThemePreferences(context: Context) {

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