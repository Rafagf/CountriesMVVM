package com.countries.dagger

import android.app.Application
import com.countries.CountriesApplication
import com.countries.core.dagger.NetworkModule
import com.countries.core.dagger.PersistencyModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ApplicationModule::class,
        ActivityBindingModule::class,
        AppNavigatorModule::class,
        NetworkModule::class,
        PersistencyModule::class,
        AndroidSupportInjectionModule::class]
)
interface ApplicationComponent : AndroidInjector<CountriesApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    override fun inject(application: CountriesApplication)
}
