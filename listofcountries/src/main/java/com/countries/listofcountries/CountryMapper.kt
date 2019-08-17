package com.countries.listofcountries

import android.content.res.Resources
import com.countries.core.getFlagUrl
import com.countries.core.models.Country
import com.countries.core.toPopulationFormat

fun Country.map(resources: Resources): CountryListModel {
    apply {
        return CountryListModel(
            name = name,
            flagUrl = getFlagUrl(),
            continent = getContinent(continent),
            population = getPopulation(population, resources)
        )
    }
}

fun List<Country>.map(resources: Resources): List<CountryListModel> {
    return map {
        it.map(resources)
    }
}

private fun getContinent(continent: String?): String {
    return when (continent?.isNotEmpty()) {
        true -> "$continent"
        else -> "-"
    }
}

private fun getPopulation(population: String?, resources: Resources): String {
    return when (population?.isNotEmpty()) {
        true -> "${resources.getString(R.string.population)}: ${population.toLong().toPopulationFormat()}"
        else -> "${resources.getString(R.string.population)}: -"
    }
}