package com.countries.listofcountries

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.countries.navigator.AppNavigator
import com.countries.ui.*
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
        applyTheme()
        setStatusBarColor(getColorFromTheme(R.attr.countriesColorPrimary))
        setContentView(R.layout.activity_country_list)
        setSupportActionBar(toolbar)
        setSearchView()
        setCountryList()
        setScrollToTopButton()
        setErrorView()

        viewModel = ViewModelProviders.of(this, viewModelFactory)[CountryListViewModel::class.java]
        viewModel.getLiveData().observe(this, Observer {
            when (it) {
                is CountryListViewModel.ViewState.Loading -> {
                    searchView.enabled(false)
                    loadingView.visible()
                    errorView.gone()
                    countriesRecyclerView.gone()
                    scrollToTop.gone()

                }

                is CountryListViewModel.ViewState.Error -> {
                    searchView.enabled(false)
                    loadingView.gone()
                    errorView.visible()
                    countriesRecyclerView.gone()
                    scrollToTop.gone()
                }

                is CountryListViewModel.ViewState.Content -> {
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
        menuInflater.inflate(R.menu.main_menu, menu)
        val item = menu.findItem(R.id.action_search)
        searchView.setMenuItem(item)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.theme_toggle -> {
                toggleTheme()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setCountryList() {
        countriesRecyclerView.adapter = CountryListAdapter(onClick = {
            navigator.countryDetailedNavigator.open(this, it)
        })
    }

    private fun setSearchView() {
        searchView.apply {
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

    private fun setErrorView() {
        errorView.onClick {
            viewModel.start()
        }
    }
}
