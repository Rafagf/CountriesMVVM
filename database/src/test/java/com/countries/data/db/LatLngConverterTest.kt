package com.countries.data.db

import com.countries.persistency.LatLng
import com.countries.persistency.LatLngConverter
import org.junit.Assert.assertEquals
import org.junit.Test

class LatLngConverterTest {

    @Test
    fun `given lat lng then convert to string`() {
        val latLng = LatLng(23.9, 24.2)

        val result = LatLngConverter.toString(latLng)

        assertEquals("23.9 , 24.2", result)
    }

    @Test
    fun `given lat lng as string then convert to lat lng`() {
        val latLngAsString = "0.5 , 43.5"

        val result = LatLngConverter.toLatLng(latLngAsString)

        assertEquals(LatLng(0.5, 43.5), result)
    }
}