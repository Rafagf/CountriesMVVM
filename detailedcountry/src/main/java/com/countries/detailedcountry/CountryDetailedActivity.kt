package com.countries.detailedcountry

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.countries.core.AppNavigator
import com.squareup.picasso.Picasso
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
                    it.country.run {
                        supportActionBar?.title = name
                        continentTextView.text = continent
                        regionTextView.text = region
                        capitalTextView.text = capital
                        populationTextView.text = population
                        areaTextView.text = area
                        demonymTextView.text = demonym
                        nativeNameTextView.text = nativeName
                    }

                    Picasso.with(this)
                        .load(it.country.flag)
                        .placeholder(R.color.plain_grey)
                        .into(flagImageView)
                }
            }
        })
    }

    companion object {
        const val COUNTRY_NAME_TAG = "name"
    }
}
