package com.countries.detailedcountry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.countries.data.models.Country
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CountryDetailedViewModel @Inject constructor(
    private val useCase: CountryDetailedUseCase,
    private val reducer: CountryDetailedViewModelReducer
) : ViewModel() {

    private val liveData = MutableLiveData<ViewState>(ViewState.Empty)
    private val compositeDisposable = CompositeDisposable()

    sealed class Event {
        data class CountryFetched(val payload: Country) : Event()
        data class BordersFetched(val payload: CountryBordersModel) : Event()
    }

    sealed class ViewState {
        object Empty : ViewState()
        object Error : ViewState()
        data class Loading(val content: Content? = null) : ViewState()
        data class Content(
            val country: CountryDetailedModel? = null,
            val borders: CountryBordersModel? = null
        ) : ViewState()
    }

    fun getLiveData(): LiveData<ViewState> = liveData

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
                    reducer.createNextState(
                        event = Event.CountryFetched(country),
                        currentState = liveData.value!!
                    )
                }
                .doOnSubscribe {
                    liveData.postValue(ViewState.Loading())
                }
                .subscribe({
                    liveData.postValue(it)
                }, {
                    liveData.postValue(ViewState.Error)
                })
        )
    }

    private fun fetchBorders(name: String) {
        compositeDisposable.add(
            useCase.getBorderCountries(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    reducer.createNextState(
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

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}