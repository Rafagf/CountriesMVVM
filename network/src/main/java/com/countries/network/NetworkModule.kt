package com.countries.network

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Created by Rafa on 05/04/2018.
 */

@Module
class NetworkModule {

    private companion object {
        private const val API_BASE_URL = "https://restcountries.eu/rest/v1/"
    }

    private val httpClientBuilder =
        OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))

    private var retrofit = Retrofit.Builder()
        .client(OkHttpClient())
        .baseUrl(API_BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create())
        .client(httpClientBuilder.build())
        .build()

    @Provides
    @Singleton
    fun provideCountryApi(): CountryEndpoints = retrofit.create(CountryEndpoints::class.java)
}