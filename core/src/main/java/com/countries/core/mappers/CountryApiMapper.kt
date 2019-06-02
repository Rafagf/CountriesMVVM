package com.countries.core.mappers

import com.countries.core.models.Country
import com.countries.core.models.CountryApi
import javax.inject.Inject

class CountryApiMapper @Inject constructor() {

    fun toCountry(country: CountryApi): Country {
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

    fun toCountries(countries: List<CountryApi>): List<Country> {
        return countries.map {
            toCountry(it)
        }
    }

    fun toCountryApi(country: Country): CountryApi {
        return country.run {
            CountryApi(
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

    fun toCountriesApi(countries: List<Country>): List<CountryApi> {
        return countries.map {
            toCountryApi(it)
        }
    }
}