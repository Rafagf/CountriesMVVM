package com.countries.network

import com.squareup.moshi.Json

/**
 * Created by Rafa on 05/04/2018.
 */
data class CountryApi(
    val name: String,
    val nativeName: String,
    val alpha2Code: String,
    val alpha3Code: String,
    val capital: String?,
    val population: String? = null,
    val area: String?,
    val demonym: String?,
    val latlng: List<Double>,
    @field:Json(name = "region") val continent: String?,
    @field:Json(name = "subregion") val region: String?,
    @field:Json(name = "borders") val borderCountryAlphaList: List<String> = listOf()
)