package com.countries.listofcountries

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.countries.core.AppNavigator
import com.miguelcatalan.materialsearchview.MaterialSearchView
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_country_list.*
import javax.inject.Inject

class CountryListActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var navigator: AppNavigator

    lateinit var viewModel: CountryListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_list)
        setSupportActionBar(toolbar)
        setSearchView()
        setCountryList()
        setScrollToTopListener()

        viewModel = ViewModelProviders.of(this, viewModelFactory)[CountryListViewModel::class.java]
        viewModel.liveData.observe(this, Observer {
            when (it) {
                is CountryListViewModel.Model.Loading -> {
                    //todo implement
                }

                is Error -> {
                    //todo implement
                }

                is CountryListViewModel.Model.Content -> {
                    //todo enable search button (should be disabled until this point)
                    (countriesRecyclerView.adapter as CountryListAdapter).run {
                        list.clear()
                        list.addAll(it.countriesToDisplay)
                        notifyDataSetChanged()
                    }
                }
            }
        })
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

    private fun setCountryList() {
        countriesRecyclerView.adapter = CountryListAdapter(onClick = {
            navigator.countryDetailedNavigator.open(this, it)
        })

        countriesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                //todo should this go to the view model?
                val firstVisibleItem =
                    (countriesRecyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                when (firstVisibleItem > 0) {
                    true -> scrollToTop.visibility = View.VISIBLE
                    false -> scrollToTop.visibility = View.GONE
                }
            }
        })
    }

    private fun setSearchView() {
        searchView.run {
            setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    viewModel.onCountriesFiltered(query)
                    return false
                }

                override fun onQueryTextChange(query: String): Boolean {
                    viewModel.onCountriesFiltered(query)
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

        }
    }
}
