package com.countries.persistency

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun providesRoomDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "myDB"
        ).build()
    }

    @Provides
    @Singleton
    fun providesCountryDao(db: AppDatabase) = db.countryDao()
}
