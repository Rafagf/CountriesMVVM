package com.countries.listofcountries

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.countries.core.models.Country
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class CountryListViewModel @Inject constructor(private val useCase: CountryListUseCase) : ViewModel() {

    val liveData = MutableLiveData<Model>(Model.Empty)
    private val compositeDisposable = CompositeDisposable()

    fun start() {
        compositeDisposable.add(
            useCase.getCountries()
                .toObservable()
                .map {
                    createNextModel(Event.CountriesReady(it))
                }
                .onErrorResumeNext { e: Throwable ->
                    Observable.just(Model.Error)
                }
                .subscribe {
                    liveData.postValue(it)
                }
        )
    }

    private fun createNextModel(event: Event.CountriesReady): Model {
        return Model.Content(event.payload)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    sealed class Event {
        data class CountriesReady(val payload: List<Country>) : Event()
    }

    sealed class Model {
        object Empty : Model()
        object Error : Model()
        object Loading : Model()
        data class Content(val countries: List<Country>) : Model()
    }
}