package com.countries.core.repositories

import com.countries.core.models.Country
import io.reactivex.Single

interface CountriesDataSource {
    fun getCountries(): Single<List<Country>>
    fun getCountryByName(name: String): Single<Country>
    fun getCountryByAlpha3(alpha3: String): Single<Country>
}
