package com.countries.data.repository

import com.countries.data.models.Country
import com.countries.data.models.LatLng
import com.countries.network.CountryApi

fun CountryApi.toCountry(): Country {
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
            latlng = getLatLng(latlng),
            continent = continent,
            region = region,
            borderCountryAlphaList = borderCountryAlphaList
        )
    }
}

private fun getLatLng(latlng: List<Double>): LatLng {
    return when {
        latlng.isEmpty() -> LatLng(0.0, 0.0)
        else -> LatLng(latlng[0], latlng[1])
    }
}


fun List<CountryApi>.toCountries(): List<Country> {
    return map {
        it.toCountry()
    }
}

fun Country.toCountryApi(): CountryApi {
    return run {
        CountryApi(
            name = name,
            nativeName = nativeName,
            alpha2Code = alpha2Code,
            alpha3Code = alpha3Code,
            capital = capital,
            population = population,
            area = area,
            demonym = demonym,
            latlng = listOf(latlng.lat, latlng.lng),
            continent = continent,
            region = region,
            borderCountryAlphaList = borderCountryAlphaList
        )
    }
}

fun List<Country>.toCountriesApi(): List<CountryApi> {
    return map {
        it.toCountryApi()
    }
}
