package com.countries.core.repositories

import com.countries.core.api.CountryApi
import com.countries.core.models.Country
import io.reactivex.Single
import javax.inject.Inject

class CountriesRemoteDataSource @Inject constructor(private val api: CountryApi) {

    fun getCountries(): Single<List<Country>> = api.getAllCountries()
    fun getCountryByName(name: String): Single<Country> = api.getCountryByName(name).map { it[0] }
    fun getCountryByAlpha3(alpha3: String): Single<Country> = api.getCountryByAlpha3(alpha3)
}