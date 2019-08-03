package com.countries.core.mappers

import com.countries.core.models.Country
import com.countries.core.models.LatLng
import com.countries.persistency.CountryDB
import javax.inject.Inject

class CountryDBMapper @Inject constructor() {

    fun toCountry(country: CountryDB): Country {
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
                latlng = LatLng(latlng.lat, latlng.lng),
                continent = continent,
                region = region,
                borderCountryAlphaList = borderCountryAlphaList
            )
        }
    }

    fun toCountries(countries: List<CountryDB>): List<Country> {
        return countries.map {
            toCountry(it)
        }
    }

    fun toCountryDB(country: Country): CountryDB {
        return country.run {
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

    fun toCountriesDB(countries: List<Country>): List<CountryDB> {
        return countries.map {
            toCountryDB(it)
        }
    }
}