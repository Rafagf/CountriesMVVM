package com.countries.core.repositories

import javax.inject.Inject

class CountriesRepository @Inject constructor(
    private val remoteDataSource: CountriesRemoteDataSource,
    private val localDataSource: CountriesLocalDataSource
) {
    fun getCountries() = remoteDataSource.getCountries()
    fun getCountryByName(name: String) = remoteDataSource.getCountryByName(name)
    fun getCountryByAlpha3(alpha3: String) = remoteDataSource.getCountryByAlpha3(alpha3)
}