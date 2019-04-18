package com.countries

import android.app.Application
import com.countries.dagger.ApplicationComponent
import com.countries.dagger.DaggerApplicationComponent

class CountriesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        getApplicationComponent().inject(this)
    }

    private fun getApplicationComponent(): ApplicationComponent {
        return DaggerApplicationComponent.builder().application(this).build()
    }
}