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
            .flatMap { getBorder(it.borderCountryAlphaList) }
            .map { CountryBordersModel(it) }
    }

    private fun getBorder(bordersAlpha: List<String>): Single<List<BorderModel>> {
        return Observable.fromIterable(bordersAlpha)
            .flatMap { alpha ->
                repository.getCountryByAlpha3(alpha)
                    .toObservable()
                    .map { BorderModel(it.name, it.capital)}
            }.toList()
    }
}