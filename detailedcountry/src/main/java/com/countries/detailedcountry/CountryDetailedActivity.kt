package com.countries.detailedcountry

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.countries.core.AppNavigator
import javax.inject.Inject

class CountryDetailedActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var navigator: AppNavigator

    lateinit var viewModel: CountryDetailedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_detailed)
        overridePendingTransition(R.anim.slide_in_up, R.anim.stay)
    }

    companion object {
        const val COUNTRY_NAME_TAG = "name"
    }
}
