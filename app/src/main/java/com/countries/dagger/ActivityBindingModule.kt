package com.countries.dagger

import com.countries.detailedcountry.CountryDetailedActivity
import com.countries.listofcountries.CountryListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun countryListActivityInjector(): CountryListActivity

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun countryDetailedActivityInjector(): CountryDetailedActivity
}
