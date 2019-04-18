package com.countries.core.dagger

import com.countries.core.api.CountryApi
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
class NetworkModule(baseUrl: String) {

    private val httpClientBuilder =
        OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
    private var retrofit = Retrofit.Builder()
        .client(OkHttpClient())
        .baseUrl(baseUrl)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create())
        .client(httpClientBuilder.build())
        .build()

    @Provides
    @Singleton
    fun provideCountryApi() = retrofit.create(CountryApi::class.java)
}