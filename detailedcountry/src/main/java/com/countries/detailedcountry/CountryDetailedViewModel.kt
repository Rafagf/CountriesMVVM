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

    val liveData = MutableLiveData<Model>(Model.Empty)
    private val compositeDisposable = CompositeDisposable()

    sealed class Event {
        data class CountryFetched(val payload: Country) : Event()
        data class BordersFetched(val payload: CountryBordersModel) : Event()
    }

    sealed class Model {
        object Empty : Model()
        object Error : Model()
        data class Loading(val content: Content? = null) : Model()
        data class Content(
            val country: CountryDetailedModel? = null,
            val borders: CountryBordersModel? = null
        ) : Model()
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
                    createNextModel(
                        event = Event.CountryFetched(country),
                        currentState = liveData.value!!
                    )
                }
                .toObservable()
                .startWith(Model.Loading())
                .onErrorResumeNext { _: Throwable ->
                    Observable.just(Model.Error)
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
                    createNextModel(
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

    private fun createNextModel(event: Event, currentState: Model): Model {
        return when (event) {
            is Event.CountryFetched -> createCountryModel(event, currentState)
            is Event.BordersFetched -> createBordersModel(event, currentState)
        }
    }

    private fun createCountryModel(
        event: Event.CountryFetched,
        currentState: Model
    ): Model {
        return when (currentState) {
            is Model.Loading -> {
                Model.Content(
                    country = mapper.map(event.payload),
                    borders = currentState.content?.borders
                )
            }
            is Model.Content -> {
                Model.Content(
                    country = mapper.map(event.payload),
                    borders = currentState.borders
                )
            }
            else -> Model.Empty
        }
    }

    private fun createBordersModel(
        event: Event.BordersFetched,
        currentState: Model
    ): Model {
        return when (currentState) {
            is Model.Loading -> {
                Model.Content(
                    country = currentState.content?.country,
                    borders = event.payload
                )
            }
            is Model.Content -> {
                Model.Content(
                    country = currentState.country,
                    borders = event.payload
                )
            }
            else -> Model.Empty
        }
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}