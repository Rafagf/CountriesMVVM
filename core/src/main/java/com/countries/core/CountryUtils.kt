package com.countries.core

import java.math.RoundingMode
import java.text.DecimalFormat

private const val FLAGS_URL = "http://www.geonames.org/flags/x/"
private const val ZERO = 0f
private const val THOUSAND = 1_000f
private const val TEN_THOUSAND = 10_000f
private const val MILLION = 1_000_000f

fun getFlagUrl(alphaCode2: String) = FLAGS_URL + alphaCode2.toLowerCase() + ".gif"

private val areaFormat = DecimalFormat("#.#").apply {
    roundingMode = RoundingMode.DOWN
}

fun Float.toMeters() = "$this m²"
fun Float.toKiloMeters() = "${areaFormat.format(this / THOUSAND)} km²"
fun Float.toMegaMeters() = "${areaFormat.format(this / MILLION)}M km²"

fun Float.toAreaFormat(): String {
    return when {
        this <= ZERO -> "0 m²"
        this < THOUSAND -> toMeters()
        this < MILLION -> toKiloMeters()
        else -> toMegaMeters()
    }
}

fun Long.toPopulationFormat(): String {
    return when {
        this <= ZERO -> "uninhabited"
        this < TEN_THOUSAND -> this.toString()
        this < MILLION -> {
            val decimalFormat = DecimalFormat("#.#")
            decimalFormat.roundingMode = RoundingMode.DOWN
            decimalFormat.format((this / THOUSAND)) + "K"
        }
        else -> {
            val decimalFormat = DecimalFormat("#.#")
            decimalFormat.roundingMode = RoundingMode.DOWN
            decimalFormat.format((this / MILLION)) + "M"
        }
    }
}