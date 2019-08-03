package com.countries.core.repository

import com.countries.core.models.Country
import com.countries.core.models.LatLng
import com.countries.persistency.CountryDB

fun CountryDB.toCountry(): Country {
    return run {
        Country(
            name = name,
            nativeName = nativeName,
            alpha2Code = alpha2Code,
            alpha3Code = alpha3Code,
            capital = capital,
            population = population,
            area = area,
            demonym = demonym,
            latlng = LatLng(latlng.lat, latlng.lng),
            continent = continent,
            region = region,
            borderCountryAlphaList = borderCountryAlphaList
        )
    }
}

fun List<CountryDB>.toCountries(): List<Country> {
    return map {
        it.toCountry()
    }
}

fun Country.toCountryDB(): CountryDB {
    return run {
        CountryDB(
            name = name,
            nativeName = nativeName,
            alpha2Code = alpha2Code,
            alpha3Code = alpha3Code,
            capital = capital,
            population = population,
            area = area,
            demonym = demonym,
            latlng = com.countries.persistency.LatLng(latlng.lat, latlng.lng),
            continent = continent,
            region = region,
            borderCountryAlphaList = borderCountryAlphaList
        )
    }
}

fun List<Country>.toCountriesDB(): List<CountryDB> {
    return map {
        it.toCountryDB()
    }
}
