package com.countries.detailedcountry

import com.countries.core.repositories.CountriesRepository
import javax.inject.Inject

class CountryDetailedUseCase @Inject constructor(
    private val repository: CountriesRepository
) {

    fun getCountry(name: String) = repository.getCountryByName(name)
}