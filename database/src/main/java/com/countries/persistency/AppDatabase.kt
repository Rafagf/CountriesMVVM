package com.countries.persistency

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [CountryDB::class], version = 1)
@TypeConverters(LatLngConverter::class, BordersConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun countryDao(): CountryDao
}