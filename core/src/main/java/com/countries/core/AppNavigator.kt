package com.countries.core

import android.content.Context
import javax.inject.Inject

class AppNavigator @Inject constructor(
    val countryListNavigator: AppNavigator.CountryListNavigator
) {

    interface CountryListNavigator {
        fun open(context: Context)
    }

    interface CountryDetailedNavigator {
        fun open(context: Context, name: String)
    }
}
