package com.countries.core

import android.app.Activity
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.LinearLayoutManager
import com.countries.core.themes.Theme
import com.countries.core.themes.ThemePreferences

fun View.visibleOrGone(isVisible: Boolean) {
    if (isVisible) {
        visible()
    } else {
        gone()
    }
}

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun <T> View.readAttributes(
    attributeSet: AttributeSet,
    ids: IntArray,
    defStyleAttr: Int,
    block: TypedArray.() -> T
): T {
    fun <T> AttributeSet.read(ids: IntArray, block: TypedArray.() -> T): T {
        val attributes = context.theme.obtainStyledAttributes(this, ids, defStyleAttr, 0)
        try {
            return block(attributes)
        } finally {
            attributes.recycle()
        }
    }
    return attributeSet.read(ids, block)
}

fun LinearLayoutManager.isAtTop() = findFirstCompletelyVisibleItemPosition() == 0

fun Activity.setStatusBarColor(@ColorInt color: Int) {
    this.window.statusBarColor = color
}

fun Activity.applyTheme() {
    val themePreferences = ThemePreferences(this)
    setTheme(themePreferences.getTheme().styleId)
}

fun Activity.toggleTheme() {
    val themePreferences = ThemePreferences(this)
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

