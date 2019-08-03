package com.countries.persistency

import androidx.room.TypeConverter


class LatLngConverter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun toLatLng(string: String): LatLng {
            val split = string.split(",")
            return LatLng(lat = split[0].toDouble(), lng = split[1].toDouble())
        }

        @TypeConverter
        @JvmStatic
        fun toString(latLng: LatLng): String {
            return "${latLng.lat} , ${latLng.lng}"
        }
    }
}