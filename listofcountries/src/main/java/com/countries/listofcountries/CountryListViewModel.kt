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

    sealed class Event {
        data class CountriesFetched(val payload: List<Country>) : Event()
        data class CountriesFiltered(val query: String) : Event()
    }

    sealed class Model {
        object Empty : Model()
        object Error : Model()
        object Loading : Model()
        data class Content(
            val baseCountries: List<CountryListModel>,
            val countriesToDisplay: List<CountryListModel>
        ) : Model()
    }

    fun start() {
        compositeDisposable.add(
            useCase.getCountries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    createNextModel(Event.CountriesFetched(it), liveData.value!!)
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

    private fun createNextModel(event: Event, currentState: Model): Model {
        return when (event) {
            is Event.CountriesFetched -> getCountriesFetchedModel(event)
            is Event.CountriesFiltered -> getCountriesFilteredModel(currentState, event)
        }
    }

    private fun getCountriesFilteredModel(
        currentState: Model,
        event: Event.CountriesFiltered
    ): Model {
        return when (currentState) {
            is Model.Content -> {
                val countries = currentState.baseCountries
                val filtered = countries.filter {
                    it.name.toLowerCase().startsWith(event.query.toLowerCase())
                }
                Model.Content(baseCountries = countries, countriesToDisplay = filtered)
            }
            else -> Model.Empty
        }
    }

    private fun getCountriesFetchedModel(event: Event.CountriesFetched): Model.Content {
        val countries = mapper.map(event.payload)
        return Model.Content(baseCountries = countries, countriesToDisplay = countries)
    }

    fun onCountriesFiltered(query: String) {
        liveData.postValue(createNextModel(Event.CountriesFiltered(query), liveData.value!!))
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}