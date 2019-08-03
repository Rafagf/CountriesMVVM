package com.countries.listofcountries

import android.content.res.Resources
import javax.inject.Inject

class CountryListViewModelReducer @Inject constructor(
    private val resources: Resources
) {

    fun createNextViewState(
        event: CountryListViewModel.Event,
        currentState: CountryListViewModel.ViewState
    ): CountryListViewModel.ViewState {
        return when (event) {
            is CountryListViewModel.Event.CountriesFetched -> createCountriesFetchedState(event)
            is CountryListViewModel.Event.CountriesFiltered -> createCountriesFilteredState(currentState, event)
        }
    }

    private fun createCountriesFilteredState(
        currentState: CountryListViewModel.ViewState,
        event: CountryListViewModel.Event.CountriesFiltered
    ): CountryListViewModel.ViewState {
        return when (currentState) {
            is CountryListViewModel.ViewState.Content -> {
                val countries = currentState.baseCountries
                val filtered = countries.filter {
                    it.name.toLowerCase().startsWith(event.query.toLowerCase())
                }
                CountryListViewModel.ViewState.Content(baseCountries = countries, countriesToDisplay = filtered)
            }
            else -> CountryListViewModel.ViewState.Empty
        }
    }

    private fun createCountriesFetchedState(event: CountryListViewModel.Event.CountriesFetched): CountryListViewModel.ViewState.Content {
        val countries = event.payload.map(resources)
        return CountryListViewModel.ViewState.Content(baseCountries = countries, countriesToDisplay = countries)
    }
}