package com.countries.detailedcountry

import android.content.res.Resources
import com.countries.core.getFlagUrl
import com.countries.core.models.Country
import com.countries.core.toAreaFormat
import com.countries.core.toPopulationFormat
import javax.inject.Inject

class CountryDetailedModelMapper @Inject constructor(private val resources: Resources) {

    fun map(country: Country): CountryDetailedModel {
        country.run {
            return CountryDetailedModel(
                name = name,
                flag = getFlagUrl(alpha2Code),
                capital = getCapital(capital),
                continent = getContinent(continent),
                region = getRegion(region),
                population = getPopulation(population),
                area = getArea(area),
                nativeName = getNativeName(nativeName),
                demonym = getDemonym(demonym),
                latLng = latlng
            )
        }
    }

    fun map(countryList: List<Country>): List<CountryDetailedModel> {
        return countryList.map {
            map(it)
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

    private fun getDemonym(demonym: String?) = when {
        demonym != null && demonym.isNotEmpty() -> "${resources.getText(R.string.demonym)} $demonym"
        else -> "${resources.getText(R.string.demonym)}-"

    }

    private fun getNativeName(nativeName: String) = "${resources.getText(R.string.native_name)} $nativeName"

    private fun getArea(area: String?) = when {
        area != null && area.isNotEmpty() -> "${resources.getText(R.string.area)} ${area.toFloat().toAreaFormat()}"
        else -> "${resources.getText(R.string.area)}0 m²"
    }

    private fun getPopulation(population: String?) = when {
        population != null && population.isNotEmpty() -> ("${resources.getText(R.string.population)} ${population.toLong().toPopulationFormat()}")
        else -> "${resources.getText(R.string.population)}0"
    }
}

