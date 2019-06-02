package com.countries.core.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.countries.core.db.BordersConverter
import com.countries.core.db.LatLngConverter


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
    @TypeConverters(LatLngConverter::class)
    val latlng: LatLng,
    val continent: String?,
    val region: String?,
    @TypeConverters(BordersConverter::class)
    val borderCountryAlphaList: List<String> = listOf()
)