package com.countries.core.repositories

import com.countries.core.models.CountryFixture
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Maybe
import io.reactivex.Single
import org.junit.Test

private const val COUNTRY_NAME = "Spain"
private const val COUNTRY_ALPHA3 = "SPA"

private val A_COUNTRY_WITH_NAME = CountryFixture.aCountry(name = COUNTRY_NAME)
private val A_COUNTRY_WITH_ALPHA = CountryFixture.aCountry(alpha3 = COUNTRY_ALPHA3)

private val LIST_OF_COUNTRIES = listOf(A_COUNTRY_WITH_NAME)

class CountriesRepositoryTest {

    private val remoteDataSource = mock<CountriesRemoteDataSource>()
    private val localDataSource = mock<CountriesLocalDataSource>()
    private val repository = CountriesRepository(remoteDataSource, localDataSource)

    @Test
    fun `given countries are not in local source when fetching then return from remote and save in local`() {
        whenever(remoteDataSource.getCountries()).thenReturn(Single.just(LIST_OF_COUNTRIES))
        whenever(localDataSource.getCountries()).thenReturn(Maybe.empty())

        repository.getCountries()
            .test()
            .assertValue(LIST_OF_COUNTRIES)

        verify(localDataSource).saveCountries(LIST_OF_COUNTRIES)
    }

    @Test
    fun `given countries are in local source when fetching then return from local`() {
        whenever(remoteDataSource.getCountries()).thenReturn(Single.never())
        whenever(localDataSource.getCountries()).thenReturn(Maybe.just(LIST_OF_COUNTRIES))

        repository.getCountries()
            .test()
            .assertValue(LIST_OF_COUNTRIES)
    }

    @Test
    fun `given country is in local source when fetching by name then return from local `() {
        whenever(remoteDataSource.getCountryByName(COUNTRY_NAME)).thenReturn(Single.never())
        whenever(localDataSource.getCountryByName(COUNTRY_NAME)).thenReturn(Maybe.just(A_COUNTRY_WITH_NAME))

        repository.getCountryByName(COUNTRY_NAME)
            .test()
            .assertValue(A_COUNTRY_WITH_NAME)
    }

    @Test
    fun `given country is not in local source when fetching by name then return from remote `() {
        whenever(remoteDataSource.getCountryByName(COUNTRY_NAME)).thenReturn(Single.just(A_COUNTRY_WITH_NAME))
        whenever(localDataSource.getCountryByName(COUNTRY_NAME)).thenReturn(Maybe.empty())

        repository.getCountryByName(COUNTRY_NAME)
            .test()
            .assertValue(A_COUNTRY_WITH_NAME)
    }

    @Test
    fun `given country is in local source when fetching by alpha3 then return from local `() {
        whenever(remoteDataSource.getCountryByAlpha3(COUNTRY_ALPHA3)).thenReturn(Single.never())
        whenever(localDataSource.getCountryByAlpha3(COUNTRY_ALPHA3)).thenReturn(Maybe.just(A_COUNTRY_WITH_ALPHA))

        repository.getCountryByAlpha3(COUNTRY_ALPHA3)
            .test()
            .assertValue(A_COUNTRY_WITH_ALPHA)
    }

    @Test
    fun `given country is not in local source when fetching by alpha3 then return from remote `() {
        whenever(remoteDataSource.getCountryByAlpha3(COUNTRY_ALPHA3)).thenReturn(Single.just(A_COUNTRY_WITH_ALPHA))
        whenever(localDataSource.getCountryByAlpha3(COUNTRY_ALPHA3)).thenReturn(Maybe.empty())

        repository.getCountryByAlpha3(COUNTRY_ALPHA3)
            .test()
            .assertValue(A_COUNTRY_WITH_ALPHA)
    }
}

