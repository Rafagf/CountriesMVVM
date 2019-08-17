package com.countries.data.models

private const val FLAGS_URL = "http://www.geonames.org/flags/x/"

fun Country.getFlagUrl() = FLAGS_URL + this.alpha2Code.toLowerCase() + ".gif"