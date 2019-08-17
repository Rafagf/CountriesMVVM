package com.countries.listofcountries

import com.countries.data.repository.CountriesRepository
import javax.inject.Inject

class CountryListUseCase @Inject constructor(private val repository: CountriesRepository) {

    fun getCountries() = repository.getCountries()
}