package com.countries.ui

import android.content.Context
import android.widget.ImageView
import com.squareup.picasso.Picasso

fun Context.loadImage(url: String, view: ImageView) {
    Picasso.with(this)
        .load(url)
        .into(view)
}