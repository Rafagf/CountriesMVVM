package com.countries.ui.theming

import androidx.annotation.StyleRes
import com.countries.ui.R

enum class Theme(@StyleRes val styleId: Int) {
    LIGHT(R.style.Light),
    DARK(R.style.Dark)
}