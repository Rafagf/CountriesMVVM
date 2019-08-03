package com.countries.core.repository

import com.countries.core.models.Country
import com.countries.network.CountryEndpoints
import io.reactivex.Single
import javax.inject.Inject


class CountriesRemoteDataSource @Inject constructor(
    private val api: CountryEndpoints,
    private val mapper: CountryApiMapper
) {

    fun getCountries(): Single<List<Country>> {
        return api.getCountries().map {
            mapper.toCountries(it)
        }
    }

    fun getCountryByName(name: String): Single<Country> {
        return api.getCountryByName(name).map {
            mapper.toCountry(it[0])
        }
    }

    fun getCountryByAlpha3(alpha3: String): Single<Country> {
        return api.getCountryByAlpha3(alpha3).map {
            mapper.toCountry(it)
        }
    }
}