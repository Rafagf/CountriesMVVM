package com.countries.navigator

import android.content.Context

class AppNavigator @Inject constructor(
    val countryListNavigator: AppNavigator.CountryListNavigator,
    val countryDetailedNavigator: AppNavigator.CountryDetailedNavigator
) {

    interface CountryListNavigator {
        fun open(context: Context)
    }

    interface CountryDetailedNavigator {
        fun open(context: Context, name: String)
    }
}
