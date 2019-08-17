package com.countries.listofcountries

object CountryListModelFixture {
    fun aCountry(
        name: String,
        flagUrl: String,
        population: String,
        continent: String = "Europe"
    ): CountryListModel {
        return CountryListModel(
            name = name,
            flagUrl = flagUrl,
            population = population,
            continent = continent
        )
    }
}