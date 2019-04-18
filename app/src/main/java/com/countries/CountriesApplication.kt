package com.countries

import android.app.Activity
import android.app.Application
import com.countries.dagger.ApplicationComponent
import com.countries.dagger.DaggerApplicationComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class CountriesApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidActivityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        getApplicationComponent().inject(this)
    }

    private fun getApplicationComponent(): ApplicationComponent {
        return DaggerApplicationComponent.builder().application(this).build()
    }

    override fun activityInjector(): DispatchingAndroidInjector<Activity>? {
        return dispatchingAndroidActivityInjector
    }

}