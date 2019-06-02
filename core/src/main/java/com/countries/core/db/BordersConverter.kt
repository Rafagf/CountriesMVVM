package com.countries.core.db

import androidx.room.TypeConverter


class BordersConverter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun toString(list: List<String>): String {
            val string = ""
            list.forEach {
                string.plus("$it,")
            }

            return string.removePrefix(",")
        }

        @TypeConverter
        @JvmStatic
        fun toList(string: String): List<String> {
            val list = mutableListOf<String>()
            string.split(",")
                .forEach {
                    list.add(it)
                }
            return list
        }
    }
}