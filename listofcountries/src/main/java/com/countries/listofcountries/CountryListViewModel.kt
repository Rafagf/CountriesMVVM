package com.countries.listofcountries

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.countries.core.LiveDataEvent
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
        data class CountrySelected(val name: String) : Event()
    }

    sealed class Model {
        object Empty : Model()
        object Error : Model()
        object Loading : Model()
        data class Content(
            val baseCountries: List<CountryListModel>,
            val countriesToDisplay: List<CountryListModel>
        ) : Model()

        data class CountrySelected(val countryLiveDataEvent: LiveDataEvent<String>) : Model()
    }

    fun start() {
        compositeDisposable.add(
            useCase.getCountries()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map {
                    createNextModel(Event.CountriesFetched(it))
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

    private fun createNextModel(event: Event): Model {
        return when (event) {
            is Event.CountriesFetched -> getContentModel(event, liveData.value!!)
            is Event.CountriesFiltered -> getContentModel(event, liveData.value!!)
            is Event.CountrySelected -> getContentModel(event, liveData.value!!)
        }
    }

    private fun getContentModel(event: Event, currentState: Model): Model {
        return when (event) {
            is Event.CountriesFetched -> {
                val countries = mapper.map(event.payload)
                Model.Content(baseCountries = countries, countriesToDisplay = countries)
            }
            is Event.CountriesFiltered -> {
                val countries = (currentState as Model.Content).baseCountries
                val filtered = countries.filter {
                    it.name.toLowerCase().startsWith(event.query.toLowerCase())
                }
                Model.Content(baseCountries = countries, countriesToDisplay = filtered)
            }
            is Event.CountrySelected -> {
                Model.CountrySelected(LiveDataEvent(event.name))
            }
        }
    }

    fun onCountrySelected(name: String) {
        liveData.postValue(createNextModel(Event.CountrySelected(name)))
    }

    fun onCountriesFiltered(query: String) {
        liveData.postValue(createNextModel(Event.CountriesFiltered(query)))
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}