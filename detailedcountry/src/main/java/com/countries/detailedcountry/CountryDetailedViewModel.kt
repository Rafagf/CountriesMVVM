package com.countries.detailedcountry

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.countries.core.models.Country
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CountryDetailedViewModel(
    private val useCase: CountryDetailedUseCase,
    private val mapper: CountryDetailedModelMapper
) : ViewModel() {

    val liveData = MutableLiveData<Model>(Model.Empty)
    private val compositeDisposable = CompositeDisposable()

    sealed class Event {
        data class CountryFetched(val payload: Country) : Event()
    }

    sealed class Model {
        object Empty : Model()
        object Error : Model()
        object Loading : Model()
        data class Content(
            val country: CountryDetailedModel
        ) : Model()
    }

    fun start(name: String) {
        compositeDisposable.add(
            useCase.getCountry(name)
                .subscribeOn(Schedulers.io())
                .map {
                    Model.Content(mapper.map(it))
                }
                .toObservable()
                .subscribe {
                    liveData.postValue(it)
                }
        )
    }
}