package com.countries.persistency

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.countries.persistency.BordersConverter
import com.countries.persistency.LatLngConverter


@Entity
data class CountryDB(
    @PrimaryKey
    val name: String,
    val nativeName: String,
    val alpha2Code: String,
    val alpha3Code: String,
    val capital: String?,
    val population: String? = null,
    val area: String?,
    val demonym: String?,
    @TypeConverters(com.countries.persistency.LatLngConverter::class)
    val latlng: LatLng,
    val continent: String?,
    val region: String?,
    @TypeConverters(com.countries.persistency.BordersConverter::class)
    val borderCountryAlphaList: List<String> = listOf()
)