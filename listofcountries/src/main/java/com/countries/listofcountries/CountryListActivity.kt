package com.countries.listofcountries

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.miguelcatalan.materialsearchview.MaterialSearchView
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_country_list.*
import javax.inject.Inject

class CountryListActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: CountryListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_list)
        setSupportActionBar(toolbar)
        setViewModel()
        setSearchView()
        setScrollToTopListener()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        val item = menu.findItem(R.id.action_search)
        searchView.setMenuItem(item)
        return true
    }

    override fun onStart() {
        super.onStart()
        viewModel.start()
    }

    private fun setViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory)[CountryListViewModel::class.java]
        viewModel.liveData.observe(this, Observer {
            Log.d("LiveData", it.toString())
        })
    }

    private fun setSearchView() {
        searchView.run {
            setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    //todo implement
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    //todo implement
                    return false
                }
            })

            setOnSearchViewListener(object : MaterialSearchView.SearchViewListener {
                override fun onSearchViewShown() {
                    //todo implement
                }

                override fun onSearchViewClosed() {
                    //todo implement
                }
            })
        }
    }

    private fun setScrollToTopListener() {
        scrollToTop.setOnClickListener {
            //todo implement
        }
    }
}
