package com.countries.core.repositories

import com.countries.core.db.CountryDao
import com.countries.core.mappers.CountryDBMapper
import com.countries.core.models.Country
import io.reactivex.Single

class CountriesLocalDataSource(
    private val countryDao: CountryDao,
    private val mapper: CountryDBMapper
) : CountriesDataSource {

    override fun getCountries(): Single<List<Country>> {
        return countryDao.getCountries().map {
            mapper.toCountries(it)
        }
    }

    override fun getCountryByName(name: String): Single<Country> {
        return countryDao.getCountryByName(name).map {
            mapper.toCountry(it)
        }
    }

    override fun getCountryByAlpha3(alpha3: String): Single<Country> {
        return countryDao.getCountryByAlpha3(alpha3).map {
            mapper.toCountry(it)
        }
    }
}
