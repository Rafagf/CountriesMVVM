package com.countries.core.themes

import androidx.annotation.StyleRes
import com.countries.core.R

enum class Theme(@StyleRes val styleId: Int) {
    LIGHT(R.style.Light),
    DARK(R.style.Dark)
}