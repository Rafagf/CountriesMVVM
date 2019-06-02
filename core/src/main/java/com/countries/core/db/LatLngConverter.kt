package com.countries.core.db

import androidx.room.TypeConverter
import com.countries.core.models.LatLng


class LatLngConverter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun toLatLng(string: String): LatLng? {
            return LatLng(string[0].toDouble(), string[1].toDouble())
        }

        @TypeConverter
        @JvmStatic
        fun toString(latLng: LatLng?): String {
            return "{$latLng.lat, $latLng.lon}"
        }
    }
}