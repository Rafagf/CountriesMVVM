package com.countries.core.api

import com.countries.core.models.Country
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Rafa on 06/04/2018.
 */
interface CountryEndpoints {

    @GET("name/{country}")
    fun getCountryByName(@Path("country") name: String): Single<List<Country>>

    @GET("alpha/{countryAlpha}")
    fun getCountryByAlpha3(@Path("countryAlpha") alpha: String): Single<Country>

    @GET("all")
    fun getCountries(): Single<List<Country>>
}