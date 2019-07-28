package com.countries.listofcountries

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.countries.core.models.Country
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CountryListViewModel @Inject constructor(
    private val useCase: CountryListUseCase,
    private val reducer: CountryListViewModelReducer
) : ViewModel() {

    private val liveData = MutableLiveData<ViewState>(ViewState.Empty)
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

    fun getLiveData(): LiveData<ViewState> = liveData

    fun start() {
        compositeDisposable.add(
            useCase.getCountries()
                .subscribeOn(Schedulers.io())
                .map {
                    reducer.createNextViewState(Event.CountriesFetched(it), liveData.value!!)
                }
                .doOnSubscribe {
                    liveData.postValue(ViewState.Loading)
                }
                .subscribe({
                    liveData.postValue(it)
                }, {
                    liveData.postValue(ViewState.Error)
                })
        )
    }

    fun onCountriesFiltered(query: String) {
        liveData.postValue(reducer.createNextViewState(Event.CountriesFiltered(query), liveData.value!!))
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}