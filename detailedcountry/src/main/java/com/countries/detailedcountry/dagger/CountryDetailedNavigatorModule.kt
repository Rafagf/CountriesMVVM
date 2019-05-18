package com.countries.detailedcountry.dagger

import android.content.Context
import android.content.Intent
import com.countries.core.AppNavigator
import com.countries.detailedcountry.CountryDetailedActivity
import dagger.Module
import dagger.Provides

@Module
class CountryDetailedNavigatorModule {

    @Provides
    fun provideCountryDetailedNavigator() = object : AppNavigator.CountryDetailedNavigator {
        override fun open(context: Context, name: String) {
            val intent = Intent(context, CountryDetailedActivity::class.java)
            //todo get name string from activity
            intent.putExtra("name", name)
            context.startActivity(intent)
        }
    }
}