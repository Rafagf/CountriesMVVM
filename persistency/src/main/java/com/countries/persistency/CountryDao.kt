package com.countries.persistency

import androidx.room.*
import io.reactivex.Maybe

@Dao
interface CountryDao {
    @Insert
    fun insertCountry(country: CountryDB)

    @Insert
    fun insertCountries(countries: List<CountryDB>)

    @Update
    fun updateCountry(country: CountryDB)

    @Delete
    fun deleteCountry(country: CountryDB)

    @Query("SELECT * FROM CountryDB")
    fun getCountries(): Maybe<List<CountryDB>>

    @Query("SELECT * FROM CountryDB WHERE name == :name")
    fun getCountryByName(name: String): Maybe<CountryDB>

    @Query("SELECT * FROM CountryDB WHERE alpha3Code == :alpha3")
    fun getCountryByAlpha3(alpha3: String): Maybe<CountryDB>
}
