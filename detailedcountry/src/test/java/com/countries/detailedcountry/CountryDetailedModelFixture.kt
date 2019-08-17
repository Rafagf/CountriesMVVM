package com.countries.detailedcountry

import com.countries.data.models.LatLng

object CountryDetailedModelFixture {
    fun aCountry(name: String = "Spain"): CountryDetailedModel {
        return CountryDetailedModel(
            name = name,
            flag = "http://www.geonames.org/flags/x/es.gif",
            nativeName = "Native name: España",
            capital = "Madrid",
            population = "Population: 10K",
            area = "Area: 20 km²",
            demonym = "Demonym: Spaniards",
            latLng = LatLng(100.0, 100.0),
            continent = "Europe",
            region = "South-Europe"
        )
    }
}