package com.countries.core.db

import androidx.room.*
import com.countries.core.models.CountryDB
import io.reactivex.Single

@Dao
interface CountryDao {
    @Insert
    fun insertCountry(country: CountryDB)

    @Update
    fun updateCountry(country: CountryDB)

    @Delete
    fun deleteCountry(country: CountryDB)

    @Query("SELECT * FROM CountryDB")
    fun getCountries(): Single<List<CountryDB>>

    @Query("SELECT * FROM CountryDB WHERE name == :name")
    fun getCountryByName(name: String): Single<CountryDB>

    @Query("SELECT * FROM CountryDB WHERE alpha3Code == :alpha3")
    fun getCountryByAlpha3(alpha3: String): Single<CountryDB>
}
