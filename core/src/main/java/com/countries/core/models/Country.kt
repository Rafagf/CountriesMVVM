package com.countries.core.models

data class Country(
    val name: String,
    val nativeName: String,
    val alpha2Code: String,
    val alpha3Code: String,
    val capital: String?,
    val population: String? = null,
    val area: String?,
    val demonym: String?,
    val latlng: LatLng,
    val continent: String?,
    val region: String?,
    val borderCountryAlphaList: List<String> = listOf()
)