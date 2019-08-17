package com.countries.ui

import android.app.Activity
import android.util.TypedValue
import androidx.annotation.ColorInt
import com.countries.ui.theming.Preferences
import com.countries.ui.theming.Theme

fun Activity.applyTheme() {
    val themePreferences = Preferences(this)
    setTheme(themePreferences.getTheme().styleId)
}

fun Activity.toggleTheme() {
    val themePreferences = Preferences(this)
    val theme = themePreferences.getTheme()
    when (theme == Theme.LIGHT) {
        true -> themePreferences.setTheme(Theme.DARK)
        else -> themePreferences.setTheme(Theme.LIGHT)
    }

    recreate()
}

fun Activity.getColorFromTheme(attribute: Int): Int {
    val typedValue = TypedValue()
    val a = obtainStyledAttributes(typedValue.data, intArrayOf(attribute))
    val color = a.getColor(0, 0)
    a.recycle()
    return color
}


fun Activity.setStatusBarColor(@ColorInt color: Int) {
    this.window.statusBarColor = color
}
