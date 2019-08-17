package com.countries.core

import com.countries.core.models.Country

private const val FLAGS_URL = "http://www.geonames.org/flags/x/"

fun Country.getFlagUrl() = FLAGS_URL + this.alpha2Code.toLowerCase() + ".gif"