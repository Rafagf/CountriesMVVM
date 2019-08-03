package com.countries.dagger

import com.countries.listofcountries.dagger.CountryListNavigatorModule
import dagger.Module

@Module(includes = [CountryListNavigatorModule::class])
class AppNavigatorModule

//@Module(includes = [CountryListNavigatorModule::class, CountryDetailedNavigatorModule::class])
//class AppNavigatorModule