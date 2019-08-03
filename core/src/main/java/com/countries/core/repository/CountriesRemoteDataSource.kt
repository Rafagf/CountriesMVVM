package com.countries.core.repository

import com.countries.core.models.Country
import com.countries.network.CountryEndpoints
import io.reactivex.Single
import javax.inject.Inject

class CountriesRemoteDataSource @Inject constructor(
    private val api: CountryEndpoints
) {

    fun getCountries(): Single<List<Country>> {
        return api.getCountries().map {
            it.toCountries()
        }
    }

    fun getCountryByName(name: String): Single<Country> {
        return api.getCountryByName(name).map {
            it[0].toCountry()
        }
    }

    fun getCountryByAlpha3(alpha3: String): Single<Country> {
        return api.getCountryByAlpha3(alpha3).map {
            it.toCountry()
        }
    }
}