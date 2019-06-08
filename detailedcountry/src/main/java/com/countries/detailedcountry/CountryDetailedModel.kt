package com.countries.detailedcountry

import com.countries.core.models.LatLng

data class CountryDetailedModel(
    val name: String,
    val nativeName: String,
    val flag: String,
    val capital: String?,
    val continent: String?,
    val region: String?,
    val latLng: LatLng,
    val population: String?,
    val area: String?,
    val demonym: String?
)