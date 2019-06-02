package com.countries.core.repositories

import com.countries.core.api.CountryEndpoints
import com.countries.core.models.Country
import com.countries.core.mappers.CountryApiMapper
import io.reactivex.Single
import javax.inject.Inject

class CountriesRemoteDataSource @Inject constructor(
    private val endpoints: CountryEndpoints,
    private val mapper: CountryApiMapper
) : CountriesDataSource {

    override fun getCountries(): Single<List<Country>> {
        return endpoints.getCountries().map {
            mapper.toCountries(it)
        }
    }

    override fun getCountryByName(name: String): Single<Country> {
        return endpoints.getCountryByName(name).map {
            mapper.toCountry(it[0])
        }
    }

    override fun getCountryByAlpha3(alpha3: String): Single<Country> {
        return endpoints.getCountryByAlpha3(alpha3).map {
            mapper.toCountry(it)
        }
    }
}