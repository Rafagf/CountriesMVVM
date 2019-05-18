package com.countries.core

import android.view.View
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

fun LinearLayoutManager.isAtTop() = findFirstCompletelyVisibleItemPosition() == 0



