package com.countries.core.repository

import com.countries.core.models.Country
import io.reactivex.Single
import javax.inject.Inject

class CountriesRepository @Inject constructor(
    private val remoteDataSource: CountriesRemoteDataSource
) {
    fun getCountries(): Single<List<Country>> {
        return remoteDataSource.getCountries()
    }

    fun getCountryByName(name: String): Single<Country> {
        return remoteDataSource.getCountryByName(name)
    }

    fun getCountryByAlpha3(alpha3: String): Single<Country> {
        return remoteDataSource.getCountryByAlpha3(alpha3)
    }
}

//class CountriesRepository @Inject constructor(
//    private val remoteDataSource: CountriesRemoteDataSource,
//    private val localDataSource: CountriesLocalDataSource
//) {
//    fun getCountries(): Single<List<Country>> {
//        return localDataSource.getCountries()
//            .switchIfEmpty(remoteDataSource.getCountries().toMaybe().doOnSuccess {
//                localDataSource.saveCountries(it)
//            }).toSingle()
//    }
//
//    fun getCountryByName(name: String): Single<Country> {
//        return localDataSource.getCountryByName(name)
//            .switchIfEmpty(remoteDataSource.getCountryByName(name).toMaybe()).toSingle()
//    }
//
//    fun getCountryByAlpha3(alpha3: String): Single<Country> {
//        return localDataSource.getCountryByAlpha3(alpha3)
//            .switchIfEmpty(remoteDataSource.getCountryByAlpha3(alpha3).toMaybe()).toSingle()
//    }
//}