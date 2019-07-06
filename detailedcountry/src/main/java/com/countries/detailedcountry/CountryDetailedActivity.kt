package com.countries.detailedcountry

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.countries.core.*
import com.squareup.picasso.Picasso
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_country_content_state.*
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
        applyTheme()
        setContentView(R.layout.activity_country_detailed)
        overridePendingTransition(R.anim.slide_in_up, R.anim.stay)
        setToolbar()
        setErrorView()

        viewModel = ViewModelProviders.of(this, viewModelFactory)[CountryDetailedViewModel::class.java]
        viewModel.start(intent.getStringExtra(COUNTRY_NAME_TAG))
        viewModel.liveData.observe(this, Observer {
            when (it) {
                is CountryDetailedViewModel.Model.Loading -> {
                    errorView.gone()
                    contentView.gone()
                    loadingView.visible()
                }

                is CountryDetailedViewModel.Model.Error -> {
                    loadingView.gone()
                    contentView.gone()
                    errorView.visible()
                }

                is CountryDetailedViewModel.Model.Content -> {
                    loadingView.gone()
                    errorView.gone()
                    contentView.visible()

                    it.country?.apply {
                        supportActionBar?.title = name
                        continentTextView.text = continent
                        regionTextView.text = region
                        capitalTextView.text = capital
                        populationTextView.text = population
                        areaTextView.text = area
                        demonymTextView.text = demonym
                        nativeNameTextView.text = nativeName

                        Picasso.with(this@CountryDetailedActivity)
                            .load(it.country.flag)
                            .placeholder(R.color.grey_primary)
                            .into(flagImageView)
                    }

                    it.borders?.let { borders ->
                        bordersTextView.visibleOrGone(borders.list.isNotEmpty())
                        borders.list.forEach { country ->
                            val borderView = BorderView(this@CountryDetailedActivity)
                            borderView.bind(country) {
                                navigator.countryDetailedNavigator.open(this@CountryDetailedActivity, country.name)
                            }
                            bordersLayout.addView(borderView)
                        }
                    }
                }
            }
        })
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun setErrorView() {
        errorView.onClick {
            viewModel.start(intent.getStringExtra(COUNTRY_NAME_TAG))
        }
    }

    companion object {
        const val COUNTRY_NAME_TAG = "name"
    }
}
