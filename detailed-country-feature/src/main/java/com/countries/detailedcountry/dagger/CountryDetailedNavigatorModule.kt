package com.countries.detailedcountry.dagger

import android.content.Context
import android.content.Intent
import com.countries.detailedcountry.CountryDetailedActivity
import com.countries.navigator.AppNavigator
import dagger.Module
import dagger.Provides

@Module
class CountryDetailedNavigatorModule {

    @Provides
    fun provideCountryDetailedNavigator() = object : AppNavigator.CountryDetailedNavigator {
        override fun open(context: Context, name: String) {
            val intent = Intent(context, CountryDetailedActivity::class.java)
            intent.putExtra(CountryDetailedActivity.COUNTRY_NAME_TAG, name)
            context.startActivity(intent)
        }
    }
}