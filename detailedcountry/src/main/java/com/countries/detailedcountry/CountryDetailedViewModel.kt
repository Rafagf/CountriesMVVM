package com.countries.detailedcountry

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.countries.core.models.Country
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CountryDetailedViewModel @Inject constructor(
    private val useCase: CountryDetailedUseCase,
    private val mapper: CountryDetailedModelMapper
) : ViewModel() {

    val liveData = MutableLiveData<ViewState>(ViewState.Empty)
    private val compositeDisposable = CompositeDisposable()

    sealed class Event {
        data class CountryFetched(val payload: Country) : Event()
        data class BordersFetched(val payload: CountryBordersModel) : Event()
    }

    sealed class ViewState {
        object Empty : ViewState()
        object Error : ViewState()
        data class Loading(val content: Content? = null) : ViewState()
        data class Content(
            val country: CountryDetailedModel? = null,
            val borders: CountryBordersModel? = null
        ) : ViewState()
    }

    fun start(name: String) {
        fetchCountry(name)
        fetchBorders(name)
    }

    private fun fetchCountry(name: String) {
        compositeDisposable.add(
            useCase.getCountry(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { country ->
                    createNextState(
                        event = Event.CountryFetched(country),
                        currentState = liveData.value!!
                    )
                }
                .toObservable()
                .startWith(ViewState.Loading())
                .onErrorResumeNext { _: Throwable ->
                    Observable.just(ViewState.Error)
                }
                .subscribe {
                    liveData.value = it
                }
        )
    }

    private fun fetchBorders(name: String) {
        compositeDisposable.add(
            useCase.getBorderCountries(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    createNextState(
                        event = Event.BordersFetched(it),
                        currentState = liveData.value!!
                    )
                }
                .toObservable()
                .subscribe {
                    liveData.value = it
                }
        )
    }

    private fun createNextState(event: Event, currentState: ViewState): ViewState {
        return when (event) {
            is Event.CountryFetched -> createCountryState(event, currentState)
            is Event.BordersFetched -> createBordersState(event, currentState)
        }
    }

    private fun createCountryState(
        event: Event.CountryFetched,
        currentState: ViewState
    ): ViewState {
        return when (currentState) {
            is ViewState.Loading -> {
                ViewState.Content(
                    country = mapper.map(event.payload),
                    borders = currentState.content?.borders
                )
            }
            is ViewState.Content -> {
                ViewState.Content(
                    country = mapper.map(event.payload),
                    borders = currentState.borders
                )
            }
            else -> ViewState.Empty
        }
    }

    private fun createBordersState(
        event: Event.BordersFetched,
        currentState: ViewState
    ): ViewState {
        return when (currentState) {
            is ViewState.Loading -> {
                ViewState.Content(
                    country = currentState.content?.country,
                    borders = event.payload
                )
            }
            is ViewState.Content -> {
                ViewState.Content(
                    country = currentState.country,
                    borders = event.payload
                )
            }
            else -> ViewState.Empty
        }
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}