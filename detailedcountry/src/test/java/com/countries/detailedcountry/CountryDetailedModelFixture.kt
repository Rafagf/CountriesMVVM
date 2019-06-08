package com.countries.detailedcountry

import com.countries.core.models.LatLng

object CountryDetailedModelFixture {
    fun aCountry(name: String = "Spain"): CountryDetailedModel {
        return CountryDetailedModel(
            name = name,
            flag = "flag",
            nativeName = "España",
            capital = "Madrid",
            population = "10000",
            area = "20000",
            demonym = "Spaniards",
            latLng = LatLng(100.0, 100.0),
            continent = "Europe",
            region = "South-Europe"
        )
    }
}