package com.countries.data.repository

import com.countries.data.models.Country
import com.countries.persistency.CountryDao
import io.reactivex.Maybe
import javax.inject.Inject

class CountriesLocalDataSource @Inject constructor(
    private val countryDao: CountryDao
) {

    fun saveCountries(countries: List<Country>) {
        countryDao.insertCountries(countries.toCountriesDB())
    }

    fun getCountries(): Maybe<List<Country>> {
        return countryDao.getCountries()
            .filter { it.isNotEmpty() }
            .map {
                it.toCountries()
            }
    }

    fun getCountryByName(name: String): Maybe<Country> {
        return countryDao.getCountryByName(name).map {
            it.toCountry()
        }
    }

    fun getCountryByAlpha3(alpha3: String): Maybe<Country> {
        return countryDao.getCountryByAlpha3(alpha3).map {
            it.toCountry()
        }
    }
}
