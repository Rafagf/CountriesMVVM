package com.countries.detailedcountry

import android.content.res.Resources
import com.countries.data.models.Country
import com.countries.data.models.getFlagUrl
import com.countries.ui.toAreaFormat
import com.countries.ui.toPopulationFormat

fun Country.map(resources: Resources): CountryDetailedModel {
    apply {
        return CountryDetailedModel(
            name = name,
            flag = getFlagUrl(),
            capital = getCapital(capital),
            continent = getContinent(continent),
            region = getRegion(region),
            population = getPopulation(population, resources),
            area = getArea(area, resources),
            nativeName = getNativeName(nativeName, resources),
            demonym = getDemonym(demonym, resources),
            latLng = latlng
        )
    }
}

private fun getCapital(capital: String?): String? = when {
    capital != null && capital.isNotEmpty() -> capital
    else -> "-"
}

private fun getContinent(continent: String?): String? = when {
    continent != null && continent.isNotEmpty() -> continent
    else -> "-"
}

private fun getRegion(region: String?): String? = when {
    region != null && region.isNotEmpty() -> region
    else -> "-"
}

private fun getDemonym(demonym: String?, resources: Resources) = when {
    demonym != null && demonym.isNotEmpty() -> "${resources.getText(R.string.demonym)}: $demonym"
    else -> "${resources.getText(R.string.demonym)}: -"

}

private fun getNativeName(nativeName: String, resources: Resources) =
    "${resources.getText(R.string.native_name)}: $nativeName"

private fun getArea(area: String?, resources: Resources) = when {
    area != null && area.isNotEmpty() -> "${resources.getText(R.string.area)}: ${area.toFloat().toAreaFormat()}"
    else -> "${resources.getText(R.string.area)}: 0 m²"
}

private fun getPopulation(population: String?, resources: Resources) = when {
    population != null && population.isNotEmpty() -> ("${resources.getText(R.string.population)}: ${population.toLong().toPopulationFormat()}")
    else -> "${resources.getText(R.string.population)}: 0"
}