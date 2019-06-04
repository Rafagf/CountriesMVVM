package com.countries.detailedcountry

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
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
            val country: CountryDetailedModel
        ) : Model()
    }

    fun start(name: String) {
        compositeDisposable.add(
            useCase.getCountry(name)
                .subscribeOn(Schedulers.io())
                .map {
                    Model.Content(mapper.map(it)) as Model
                }
                .toObservable()
                .startWith(Model.Loading)
                .onErrorResumeNext { _: Throwable ->
                    Observable.just(Model.Error)
                }
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