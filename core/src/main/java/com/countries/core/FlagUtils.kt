package com.countries.core

private const val FLAGS_URL = "http://www.geonames.org/flags/x/"

fun getFlagUrl(alphaCode2: String) = FLAGS_URL + alphaCode2.toLowerCase() + ".gif"
