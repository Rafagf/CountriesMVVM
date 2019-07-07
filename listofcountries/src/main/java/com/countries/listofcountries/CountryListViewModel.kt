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

    val liveData = MutableLiveData<ViewState>(ViewState.Empty)
    private val compositeDisposable = CompositeDisposable()

    sealed class Event {
        data class CountriesFetched(val payload: List<Country>) : Event()
        data class CountriesFiltered(val query: String) : Event()
    }

    sealed class ViewState {
        object Empty : ViewState()
        object Error : ViewState()
        object Loading : ViewState()
        data class Content(
            val baseCountries: List<CountryListModel>,
            val countriesToDisplay: List<CountryListModel>
        ) : ViewState()
    }

    fun start() {
        compositeDisposable.add(
            useCase.getCountries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    createNextViewState(Event.CountriesFetched(it), liveData.value!!)
                }
                .toObservable()
                .startWith(ViewState.Loading)
                .onErrorResumeNext { _: Throwable ->
                    Observable.just(ViewState.Error)
                }
                .subscribe {
                    liveData.postValue(it)
                }
        )
    }

    private fun createNextViewState(event: Event, currentState: ViewState): ViewState {
        return when (event) {
            is Event.CountriesFetched -> createCountriesFetchedState(event)
            is Event.CountriesFiltered -> createCountriesFilteredState(currentState, event)
        }
    }

    private fun createCountriesFilteredState(
        currentState: ViewState,
        event: Event.CountriesFiltered
    ): ViewState {
        return when (currentState) {
            is ViewState.Content -> {
                val countries = currentState.baseCountries
                val filtered = countries.filter {
                    it.name.toLowerCase().startsWith(event.query.toLowerCase())
                }
                ViewState.Content(baseCountries = countries, countriesToDisplay = filtered)
            }
            else -> ViewState.Empty
        }
    }

    private fun createCountriesFetchedState(event: Event.CountriesFetched): ViewState.Content {
        val countries = mapper.map(event.payload)
        return ViewState.Content(baseCountries = countries, countriesToDisplay = countries)
    }

    fun onCountriesFiltered(query: String) {
        liveData.postValue(createNextViewState(Event.CountriesFiltered(query), liveData.value!!))
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}