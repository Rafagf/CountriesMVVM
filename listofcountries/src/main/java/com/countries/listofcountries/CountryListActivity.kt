package com.countries.listofcountries

import android.os.Bundle
import android.view.Menu
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.countries.core.*
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
        setScrollToTopButton()

        viewModel = ViewModelProviders.of(this, viewModelFactory)[CountryListViewModel::class.java]
        viewModel.liveData.observe(this, Observer {
            when (it) {
                is CountryListViewModel.Model.Loading -> {
                    searchView.enabled(false)
                    loadingView.visible()
                    errorView.gone()
                    countriesRecyclerView.gone()
                    scrollToTop.gone()

                }

                is CountryListViewModel.Model.Error -> {
                    searchView.enabled(false)
                    loadingView.gone()
                    errorView.visible()
                    countriesRecyclerView.gone()
                    scrollToTop.gone()
                    errorView.onClick {
                        viewModel.start()
                    }
                }

                is CountryListViewModel.Model.Content -> {
                    searchView.enabled(true)
                    loadingView.gone()
                    errorView.gone()
                    countriesRecyclerView.visible()
                    scrollToTop.visible()

                    (countriesRecyclerView.adapter as CountryListAdapter).run {
                        list.clear()
                        list.addAll(it.countriesToDisplay)
                        notifyDataSetChanged()
                    }
                }
            }
        })

        viewModel.start()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        val item = menu.findItem(R.id.action_search)
        searchView.setMenuItem(item)
        return true
    }

    private fun setCountryList() {
        countriesRecyclerView.adapter = CountryListAdapter(onClick = {
            navigator.countryDetailedNavigator.open(this, it)
        })
    }

    private fun setSearchView() {
        searchView.run {
            searchView.enabled(false)
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
                    setStatusBarColor(R.color.plain_grey)
                }

                override fun onSearchViewClosed() {
                    setStatusBarColor(R.color.color_primary)
                }
            })
        }
    }

    private fun setScrollToTopButton() {
        countriesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                scrollToTop.visibleOrGone(!(countriesRecyclerView.layoutManager as LinearLayoutManager).isAtTop())
            }
        })

        scrollToTop.setOnClickListener {
            (countriesRecyclerView.layoutManager as LinearLayoutManager).scrollToPosition(0)
            appBarLayout.setExpanded(true)
        }
    }
}
