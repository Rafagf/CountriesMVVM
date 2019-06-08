package com.countries.detailedcountry

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CountryDetailedViewModel @Inject constructor(
    private val useCase: CountryDetailedUseCase,
    private val mapper: CountryDetailedModelMapper
) : ViewModel() {

    val liveData = MutableLiveData<Model>(Model.Empty)
    private val compositeDisposable = CompositeDisposable()

    sealed class Model {
        object Empty : Model()
        object Error : Model()
        object Loading : Model()
        data class Content(
            val country: CountryDetailedModel, val borders: List<String>? = null
        ) : Model()
    }

    fun start(name: String) {
        getCountry(name)
    }

    private fun getCountry(name: String) {
        compositeDisposable.add(
            useCase.getCountry(name)
                .subscribeOn(Schedulers.io())
                .map {
                    Model.Content(country = mapper.map(it))
                }
                .doOnSuccess { getBorders(it) }
                .toObservable()
//                .startWith(Model.Loading)
//                .onErrorResumeNext { _: Throwable ->
//                    Observable.just(Model.Error)
//                }
                .subscribe {
                    liveData.postValue(it)
                }
        )
    }

    private fun getBorders(payload: Model.Content) {
        val country = payload.country
        compositeDisposable.add(
            useCase.getBorderNames(country.borderCountryAlphaList)
                .subscribeOn(Schedulers.io())
                .map {
                    Model.Content(country = country, borders = it)
                }
                .toObservable()
                .subscribe {
                    liveData.postValue(it)
                }
        )
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}