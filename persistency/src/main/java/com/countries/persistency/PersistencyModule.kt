package com.countries.persistency

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PersistencyModule {

    @Provides
    @Singleton
    fun providesRoomDatabase(context: Context): com.countries.persistency.AppDatabase {
        return Room.databaseBuilder(
            context,
            com.countries.persistency.AppDatabase::class.java,
            "myDB"
        ).build()
    }

    @Provides
    @Singleton
    fun providesCountryDao(db: com.countries.persistency.AppDatabase) = db.countryDao()
}
