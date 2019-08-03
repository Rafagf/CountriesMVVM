package com.countries.navigator

import android.content.Context
import javax.inject.Inject

class AppNavigator @Inject constructor(
    val countryListNavigator: CountryListNavigator,
    val countryDetailedNavigator: CountryDetailedNavigator
) {

    interface CountryListNavigator {
        fun open(context: Context)
    }

    interface CountryDetailedNavigator {
        fun open(context: Context, name: String)
    }
}
