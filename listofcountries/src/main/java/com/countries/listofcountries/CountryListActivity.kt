package com.countries.listofcountries

import android.os.Bundle
import com.countries.core.api.CountryApi
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_country_list.*
import javax.inject.Inject

class CountryListActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var countryApi: CountryApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_list)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, countryApi.toString(), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }
}
