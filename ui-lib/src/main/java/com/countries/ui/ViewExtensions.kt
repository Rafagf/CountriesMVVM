package com.countries.ui

import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager

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

