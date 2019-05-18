package com.countries.dagger

import com.countries.detailedcountry.dagger.CountryDetailedNavigatorModule
import com.countries.listofcountries.dagger.CountryListNavigatorModule
import dagger.Module

@Module(includes = [CountryListNavigatorModule::class, CountryDetailedNavigatorModule::class])
class AppNavigatorModule