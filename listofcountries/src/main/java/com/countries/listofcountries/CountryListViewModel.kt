package com.countries.listofcountries

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.countries.core.models.Country
import javax.inject.Inject

class CountryListViewModel @Inject constructor(private val useCase: CountryListUseCase) : ViewModel() {

    val liveData = MutableLiveData<Model>(Model.Empty)

    fun start() {
        //todo add composite
        useCase.getCountries()
    }

    sealed class Model {
        object Empty : Model()
        object Error : Model()
        object Loading : Model()
        data class Content(val countries: List<Country>)
    }
}