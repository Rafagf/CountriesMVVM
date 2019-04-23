package com.countries.listofcountries

import android.content.res.Resources
import com.countries.core.getFlagUrl
import com.countries.core.models.Country
import com.countries.core.toPopulationFormat
import javax.inject.Inject


class CountryListModelMapper @Inject constructor(private val resources: Resources) {

    fun map(country: Country): CountryListModel {
        return CountryListModel(
            name = country.name,
            flagUrl = getFlagUrl(country.alpha2Code),
            continent = getContinent(country),
            population = getPopulation(country)
        )
    }

    fun map(countryList: List<Country>): List<CountryListModel> {
        return countryList.map {
            map(it)
        }
    }

    private fun getContinent(country: Country): String {
        return when (country.continent?.isNotEmpty()) {
            true -> "${resources.getString(R.string.continent)}: ${country.continent}"
            else -> "${resources.getString(R.string.continent)}: -"
        }
    }

    private fun getPopulation(country: Country): String {
        return when (country.population?.isNotEmpty()) {
            true -> "${resources.getString(R.string.population)}: ${country.population?.toLong()?.toPopulationFormat()}"
            else -> "${resources.getString(R.string.population)}: -"
        }
    }
}

