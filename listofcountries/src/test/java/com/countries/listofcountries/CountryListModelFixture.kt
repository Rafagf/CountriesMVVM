package com.countries.listofcountries

object CountryListModelFixture {
    fun aCountry(name: String = "Spain"): CountryListModel {
        return CountryListModel(
            name = name,
            flagUrl = "url",
            population = "10000",
            continent = "Europe"
        )
    }
}