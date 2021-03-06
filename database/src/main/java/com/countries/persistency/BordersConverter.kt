package com.countries.persistency

import androidx.room.TypeConverter

class BordersConverter {

    companion object {
        @TypeConverter
        @JvmStatic
        fun toString(list: List<String>): String {
            var string = ""
            list.forEach {
                string = "$string$it,"
            }

            return string.removeSuffix(",")
        }

        @TypeConverter
        @JvmStatic
        fun toList(string: String): List<String> {
            val list = mutableListOf<String>()
            if (string.isNotEmpty()) {
                string.split(",")
                    .forEach {
                        list.add(it)
                    }
            }
            return list
        }
    }
}