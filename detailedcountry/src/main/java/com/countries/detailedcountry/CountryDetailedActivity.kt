package com.countries.detailedcountry

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.countries.core.AppNavigator
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_country_detailed.*
import javax.inject.Inject

class CountryDetailedActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var navigator: AppNavigator

    lateinit var viewModel: CountryDetailedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_detailed)
        overridePendingTransition(R.anim.slide_in_up, R.anim.stay)
        setSupportActionBar(toolbar)

        viewModel = ViewModelProviders.of(this, viewModelFactory)[CountryDetailedViewModel::class.java]
        viewModel.start(intent.getStringExtra(COUNTRY_NAME_TAG))
        viewModel.liveData.observe(this, Observer {
            when (it) {
                is CountryDetailedViewModel.Model.Content -> {
                    supportActionBar?.title = it.country.name
                }
            }
        })
    }

    companion object {
        const val COUNTRY_NAME_TAG = "name"
    }
}
