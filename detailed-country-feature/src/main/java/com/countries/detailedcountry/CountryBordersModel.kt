package com.countries.detailedcountry

data class CountryBordersModel(val list: List<BorderModel> = emptyList())

data class BorderModel(val name: String, val capital: String?)