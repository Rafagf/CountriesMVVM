package com.countries.core.dagger

import android.content.Context
import androidx.room.Room
import com.countries.core.db.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PersistencyModule {

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
