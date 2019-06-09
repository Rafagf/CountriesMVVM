package com.countries.detailedcountry

object BorderFixture {
    fun aBorder(name: String): BorderModel {
        return BorderModel(
            name = name,
            capital = "Paris"
        )
    }
}