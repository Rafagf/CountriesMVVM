package com.countries.data.repositories

import com.countries.data.models.Country
import com.countries.data.models.LatLng

object CountryFixture {
    fun aCountry(name: String = "Spain", alpha3: String = "SPA"): Country {
        return Country(
            name = name,
            nativeName = "España",
            alpha3Code = "SPA",
            alpha2Code = "ES",
            capital = "Madrid",
            population = "10000",
            area = "20000",
            demonym = "Spaniards",
            latlng = LatLng(100.0, 100.0),
            continent = "Europe",
            region = "South-Europe",
            borderCountryAlphaList = listOf("Portugal, Marocco, France, Andorra, Gibraltar")
        )
    }
}