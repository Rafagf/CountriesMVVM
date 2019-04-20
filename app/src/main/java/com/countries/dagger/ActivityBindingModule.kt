package com.countries.dagger

import com.countries.listofcountries.CountryListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun countryListActivity(): CountryListActivity
}
