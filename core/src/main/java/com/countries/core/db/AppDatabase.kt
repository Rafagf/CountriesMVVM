package com.countries.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.countries.core.models.CountryDB

@Database(entities = [CountryDB::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun countryDao(): CountryDao
}