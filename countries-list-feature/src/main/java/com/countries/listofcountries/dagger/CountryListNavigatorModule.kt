package com.countries.listofcountries.dagger

import android.content.Context
import android.content.Intent
import com.countries.listofcountries.CountryListActivity
import com.countries.navigator.AppNavigator
import dagger.Module
import dagger.Provides

@Module
class CountryListNavigatorModule {

    @Provides
    fun provideCountryListNavigator() = object : AppNavigator.CountryListNavigator {
        override fun open(context: Context) {
            val intent = Intent(context, CountryListActivity::class.java)
            context.startActivity(intent)
        }
    }
}