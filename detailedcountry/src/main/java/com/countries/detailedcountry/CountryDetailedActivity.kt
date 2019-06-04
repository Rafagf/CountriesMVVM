package com.countries.detailedcountry

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class CountryDetailedActivity : AppCompatActivity() {

    companion object {
        const val COUNTRY_NAME_TAG = "name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_detailed)
    }
}
