package com.countries.detailedcountry

import com.countries.core.repositories.CountriesRepository

class CountryDetailedUseCase(private val repository: CountriesRepository) {

    fun getCountry(name: String) = repository.getCountryByName(name)
}