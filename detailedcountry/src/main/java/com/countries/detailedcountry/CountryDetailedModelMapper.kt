package com.countries.detailedcountry

import android.content.res.Resources
import com.countries.core.models.Country
import javax.inject.Inject


class CountryDetailedModelMapper @Inject constructor(private val resources: Resources) {

    fun map(country: Country): CountryDetailedModel {
        return CountryDetailedModel(
            name = country.name
        )
    }

    fun map(countryList: List<Country>): List<CountryDetailedModel> {
        return countryList.map {
            map(it)
        }
    }

}

