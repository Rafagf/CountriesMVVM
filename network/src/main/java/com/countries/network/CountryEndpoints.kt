package com.countries.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Rafa on 06/04/2018.
 */
interface CountryEndpoints {

    @GET("name/{country}")
    fun getCountryByName(@Path("country") name: String): Single<List<CountryApi>>

    @GET("alpha/{countryAlpha}")
    fun getCountryByAlpha3(@Path("countryAlpha") alpha: String): Single<CountryApi>

    @GET("all")
    fun getCountries(): Single<List<CountryApi>>
}