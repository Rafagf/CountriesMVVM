package com.countries.detailedcountry

import com.countries.core.repositories.CountriesRepository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class CountryDetailedUseCase @Inject constructor(
    private val repository: CountriesRepository
) {

    fun getCountry(name: String) = repository.getCountryByName(name)

    fun getBorderCountries(name: String): Single<CountryBordersModel> {
        return getCountry(name)
            .flatMap { getBorderNames(it.borderCountryAlphaList) }
            .map { CountryBordersModel(it) }
    }

    private fun getBorderNames(bordersAlpha: List<String>): Single<List<String>> {
        return Observable.fromIterable(bordersAlpha)
            .flatMap { alpha ->
                repository.getCountryByAlpha3(alpha)
                    .toObservable()
                    .map { it.name }
            }.toList()
    }
}