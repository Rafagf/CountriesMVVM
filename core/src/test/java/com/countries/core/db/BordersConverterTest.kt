package com.countries.core.db

import org.junit.Assert.assertEquals
import org.junit.Test

class BordersConverterTest {

    @Test
    fun `given borders as list of strings then convert to string`() {
        val bordersAsList = listOf("Spain", "France", "Portugal", "Andorra")

        val result = BordersConverter.toString(bordersAsList)

        assertEquals("Spain,France,Portugal,Andorra", result)
    }

    @Test
    fun `given border as a string then convert to list of string`() {
        val bordersAsList = "Spain,France,Portugal,Andorra"

        val result = BordersConverter.toList(bordersAsList)

        assertEquals(listOf("Spain", "France", "Portugal", "Andorra"), result)
    }
}