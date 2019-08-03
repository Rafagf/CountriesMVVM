package com.countries.core

import android.app.Activity
import android.view.View
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.LinearLayoutManager

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

fun Activity.setStatusBarColor(@ColorInt color: Int) {
    this.window.statusBarColor = color
}

fun LinearLayoutManager.isAtTop() = findFirstCompletelyVisibleItemPosition() == 0

