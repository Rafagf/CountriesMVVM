package com.countries.detailedcountry

import android.content.res.Resources
import javax.inject.Inject

class CountryDetailedViewModelReducer @Inject constructor(val resources: Resources) {

    fun createNextState(
        event: CountryDetailedViewModel.Event,
        currentState: CountryDetailedViewModel.ViewState
    ): CountryDetailedViewModel.ViewState {
        return when (event) {
            is CountryDetailedViewModel.Event.CountryFetched -> createCountryState(event, currentState)
            is CountryDetailedViewModel.Event.BordersFetched -> createBordersState(event, currentState)
        }
    }

    private fun createCountryState(
        event: CountryDetailedViewModel.Event.CountryFetched,
        currentState: CountryDetailedViewModel.ViewState
    ): CountryDetailedViewModel.ViewState {
        return when (currentState) {
            is CountryDetailedViewModel.ViewState.Loading -> {
                CountryDetailedViewModel.ViewState.Content(
                    country = event.payload.map(resources),
                    borders = currentState.content?.borders
                )
            }
            is CountryDetailedViewModel.ViewState.Content -> {
                CountryDetailedViewModel.ViewState.Content(
                    country = event.payload.map(resources),
                    borders = currentState.borders
                )
            }
            else -> CountryDetailedViewModel.ViewState.Empty
        }
    }

    private fun createBordersState(
        event: CountryDetailedViewModel.Event.BordersFetched,
        currentState: CountryDetailedViewModel.ViewState
    ): CountryDetailedViewModel.ViewState {
        return when (currentState) {
            is CountryDetailedViewModel.ViewState.Loading -> {
                CountryDetailedViewModel.ViewState.Content(
                    country = currentState.content?.country,
                    borders = event.payload
                )
            }
            is CountryDetailedViewModel.ViewState.Content -> {
                CountryDetailedViewModel.ViewState.Content(
                    country = currentState.country,
                    borders = event.payload
                )
            }
            else -> CountryDetailedViewModel.ViewState.Empty
        }
    }
}