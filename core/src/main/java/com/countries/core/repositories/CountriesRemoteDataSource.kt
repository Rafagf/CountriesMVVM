package com.countries.core.repositories

import com.countries.core.api.CountryApi
import javax.inject.Inject

class CountriesRemoteDataSource @Inject constructor(private val api: CountryApi) {

    fun getCountries() = api.getAllCountries()
    fun getCountryByName(name: String) = api.getCountryByName(name).map { it[0] }
    fun getCountryByAlpha3(alpha3: String) = api.getCountryByAlpha3(alpha3)
}