package com.countries.core

import org.junit.Assert.*
import org.junit.Test

class CountryUtilsTest {

    @Test
    fun toPopulationFormatTest() {
        var result = 0L.toPopulationFormat()
        assertEquals("uninhabited", result)

        result = 1L.toPopulationFormat()
        assertEquals("1", result)

        result = 9999L.toPopulationFormat()
        assertEquals("9999", result)

        result = 10000L.toPopulationFormat()
        assertEquals("10K", result)

        result = 14530L.toPopulationFormat()
        assertEquals("14.5K", result)

        result = 999999L.toPopulationFormat()
        assertEquals("999.9K", result)

        result = 1000000L.toPopulationFormat()
        assertEquals("1M", result)

        result = 1423263L.toPopulationFormat()
        assertEquals("1.4M", result)
    }

    @Test
    fun toAreaFormatTest() {
        var result = (-10f).toAreaFormat()
        assertEquals("0 m²", result)

        result = (0f).toAreaFormat()
        assertEquals("0 m²", result)

        result = (0.1f).toAreaFormat()
        assertEquals("0.1 m²", result)

        result = (26.3f).toAreaFormat()
        assertEquals("26.3 m²", result)

        result = 999.9f.toAreaFormat()
        assertEquals("999.9 m²", result)

        result = 1000f.toAreaFormat()
        assertEquals("1 km²", result)

        result = 14530f.toAreaFormat()
        assertEquals("14.5 km²", result)

        result = 999999f.toAreaFormat()
        assertEquals("999.9 km²", result)

        result = 1000000f.toAreaFormat()
        assertEquals("1M km²", result)

        result = 1423263f.toAreaFormat()
        assertEquals("1.4M km²", result)
    }
}