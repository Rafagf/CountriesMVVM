package com.countries.core.repositories

import com.countries.core.db.CountryDao
import com.countries.core.mappers.CountryDBMapper
import com.countries.core.models.Country
import io.reactivex.Maybe
import javax.inject.Inject

class CountriesLocalDataSource @Inject constructor(
    private val countryDao: CountryDao,
    private val mapper: CountryDBMapper
) {

    fun saveCountries(countries: List<Country>) {
        countryDao.insertCountries(mapper.toCountriesDB(countries))
    }

    fun getCountries(): Maybe<List<Country>> {
        return countryDao.getCountries()
            .filter { it.isNotEmpty() }
            .map {
                mapper.toCountries(it)
            }
    }

    fun getCountryByName(name: String): Maybe<Country> {
        return countryDao.getCountryByName(name).map {
            mapper.toCountry(it)
        }
    }

    fun getCountryByAlpha3(alpha3: String): Maybe<Country> {
        return countryDao.getCountryByAlpha3(alpha3).map {
            mapper.toCountry(it)
        }
    }
}
