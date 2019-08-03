package com.countries.dagger

import android.app.Application
import android.content.Context
import com.countries.network.CountryEndpoints
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {

    @Provides
    internal fun provideContext(application: Application) = application.applicationContext

    @Provides
    internal fun provideResources(context: Context) = context.resources

}