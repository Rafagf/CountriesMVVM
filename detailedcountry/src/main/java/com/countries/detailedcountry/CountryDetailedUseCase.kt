package com.countries.detailedcountry

import com.countries.core.models.Country
import io.reactivex.Single
import javax.inject.Inject

class CountryDetailedUseCase @Inject constructor(
//    private val repository: CountriesRepository
) {

    fun getCountry(name: String): Single<Country> {
        return Single.never()
    }

    fun getBorderCountries(name: String): Single<CountryBordersModel> {
        return Single.never()
//        return getCountry(name)
//            .flatMap { getBorder(it.borderCountryAlphaList) }
//            .map { CountryBordersModel(it) }
    }

    private fun getBorder(bordersAlpha: List<String>): Single<List<BorderModel>> {
        return Single.never()
//        return Observable.fromIterable(bordersAlpha)
//            .flatMap { alpha ->
//                repository.getCountryByAlpha3(alpha)
//                    .toObservable()
//                    .map { BorderModel(it.name, it.capital) }
//            }.toList()
    }
}