package com.countries.ui

import android.app.Activity
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import com.countries.ui.theming.Preferences
import com.countries.ui.theming.Theme
import com.squareup.picasso.Picasso

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

fun Context.loadImage(url: String, view: ImageView) {
    Picasso.with(this)
        .load(url)
        .into(view)
}