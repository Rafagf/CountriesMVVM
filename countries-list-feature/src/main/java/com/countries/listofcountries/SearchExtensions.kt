package com.countries.listofcountries

import android.widget.EditText
import com.miguelcatalan.materialsearchview.MaterialSearchView

fun MaterialSearchView.enabled(enabled: Boolean) {
    val searchEditText = findViewById<EditText>(R.id.searchTextView)
    searchEditText.isEnabled = enabled
}


