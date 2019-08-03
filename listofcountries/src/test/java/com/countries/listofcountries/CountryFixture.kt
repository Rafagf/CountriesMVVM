package com.countries.listofcountries

import com.countries.core.models.Country
import com.countries.core.models.LatLng

object CountryFixture {
    fun aCountry(
        name: String,
        alpha2Code: String,
        population: String,
        continent: String = "Europe"
    ): Country {
        return Country(
            name = name,
            alpha2Code = alpha2Code,
            population = population,
            continent = continent,
            nativeName = "",
            alpha3Code = "",
            capital = "",
            area = "",
            demonym = "",
            region = "",
            latlng = LatLng(100.0, 100.0),
            borderCountryAlphaList = listOf()
        )
    }
}