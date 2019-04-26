package com.countries.listofcountries

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.countries.core.models.Country
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CountryListViewModel @Inject constructor(
    private val useCase: CountryListUseCase,
    private val mapper: CountryListModelMapper
) : ViewModel() {

    val liveData = MutableLiveData<Model>(Model.Empty)
    private val compositeDisposable = CompositeDisposable()

    fun start() {
        compositeDisposable.add(
            useCase.getCountries()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map {
                    createNextModel(Event.CountriesReady(it))
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

    private fun createNextModel(event: Event.CountriesReady): Model {
        return Model.Content(mapper.map(event.payload))
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
        data class Content(val countries: List<CountryListModel>) : Model()
    }
}