package com.countries.detailedcountry

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.countries.navigator.AppNavigator
import com.countries.ui.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_country_content_state.*
import kotlinx.android.synthetic.main.activity_country_detailed.*
import javax.inject.Inject

const val MAP_ZOOM = 2.8

class CountryDetailedActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var navigator: AppNavigator

    lateinit var viewModel: CountryDetailedViewModel

    lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyTheme()
        setStatusBarColor(getColorFromTheme(R.attr.countriesColorPrimary))
        setContentView(R.layout.activity_country_detailed)
        overridePendingTransition(R.anim.slide_in_up, R.anim.stay)
        setToolbar()
        setMapView(savedInstanceState)
        setErrorView()

        viewModel = ViewModelProviders.of(this, viewModelFactory)[CountryDetailedViewModel::class.java]
        viewModel.start(intent.getStringExtra(COUNTRY_NAME_TAG))
        viewModel.getLiveData().observe(this, Observer {
            when (it) {
                is CountryDetailedViewModel.ViewState.Loading -> {
                    errorView.gone()
                    contentView.gone()
                    loadingView.visible()
                }

                is CountryDetailedViewModel.ViewState.Error -> {
                    loadingView.gone()
                    contentView.gone()
                    errorView.visible()
                }

                is CountryDetailedViewModel.ViewState.Content -> {
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
                        setCountryMap(it.country.name, LatLng(latLng.lat, latLng.lng))
                        loadImage(flag, flagImageView)
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

    private fun setMapView(savedInstanceState: Bundle?) {
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync {
            googleMap = it
            googleMap.uiSettings.setAllGesturesEnabled(false)
        }
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

    private fun setCountryMap(country: String, latLng: LatLng) {
        googleMap.addMarker(
            MarkerOptions()
                .position(latLng)
                .title(country)
        )
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, MAP_ZOOM.toFloat()))
    }

    companion object {
        const val COUNTRY_NAME_TAG = "name"
    }
}
