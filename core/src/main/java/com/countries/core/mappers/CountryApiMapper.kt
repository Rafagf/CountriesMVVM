package com.countries.core.mappers

import com.countries.core.models.Country
import javax.inject.Inject

class CountryApiMapper @Inject constructor() {

    fun toCountry(country: Country): Country {
        return country.run {
            Country(
                name = name,
                nativeName = nativeName,
                alpha2Code = alpha2Code,
                alpha3Code = alpha3Code,
                capital = capital,
                population = population,
                area = area,
                demonym = demonym,
                latlng = latlng,
                continent = continent,
                region = region,
                borderCountryAlphaList = borderCountryAlphaList
            )
        }
    }

    fun toCountries(countries: List<Country>): List<Country> {
        return countries.map {
            toCountry(it)
        }
    }

    fun toCountryApi(country: Country): Country {
        return country.run {
            Country(
                name = name,
                nativeName = nativeName,
                alpha2Code = alpha2Code,
                alpha3Code = alpha3Code,
                capital = capital,
                population = population,
                area = area,
                demonym = demonym,
                latlng = latlng,
                continent = continent,
                region = region,
                borderCountryAlphaList = borderCountryAlphaList
            )
        }

    }

    fun toCountriesApi(countries: List<Country>): List<Country> {
        return countries.map {
            toCountryApi(it)
        }
    }
}