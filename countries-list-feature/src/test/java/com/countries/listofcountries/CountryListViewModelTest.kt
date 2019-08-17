package com.countries.listofcountries

import android.content.res.Resources
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

private const val COUNTRY_NAME_1 = "Spain"
private const val COUNTRY_ALPHA_1 = "ES"
private const val COUNTRY_POPULATION_1 = "46000000"
private const val COUNTRY_NAME_2 = "Portugal"
private const val COUNTRY_ALPHA_2 = "PT"
private const val COUNTRY_POPULATION_2 = "7100000"
private val COUNTRY_1 = CountryFixture.aCountry(COUNTRY_NAME_1, COUNTRY_ALPHA_1, COUNTRY_POPULATION_1)
private val COUNTRY_2 = CountryFixture.aCountry(COUNTRY_NAME_2, COUNTRY_ALPHA_2, COUNTRY_POPULATION_2)

private const val COUNTRY_LIST_COUNTRY_1_FLAG = "http://www.geonames.org/flags/x/es.gif"
private const val COUNTRY_LIST_COUNTRY_1_POPULATION = "Population: 46M"
private const val COUNTRY_LIST_COUNTRY_2_FLAG = "http://www.geonames.org/flags/x/pt.gif"
private const val COUNTRY_LIST_COUNTRY_2_POPULATION = "Population: 7M"
private val COUNTRY_LIST_COUNTRY_1 =
    CountryListModelFixture.aCountry(COUNTRY_NAME_1, COUNTRY_LIST_COUNTRY_1_FLAG, COUNTRY_LIST_COUNTRY_1_POPULATION)
private val COUNTRY_LIST_COUNTRY_2 =
    CountryListModelFixture.aCountry(COUNTRY_NAME_2, COUNTRY_LIST_COUNTRY_2_FLAG, COUNTRY_LIST_COUNTRY_2_POPULATION)

class CountryListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val schedulers = RxImmediateSchedulerRule()

    private val useCase = mock<CountryListUseCase>()
    private val resources = mock<Resources>()
    private val reducer = CountryListViewModelReducer(resources)
    private val viewModel = CountryListViewModel(useCase, reducer)
    private val observer: TestObserver<CountryListViewModel.ViewState> = TestObserver()

    @Before
    fun setUp() {
        whenever(resources.getString(R.string.population)).thenReturn("Population")
        viewModel.getLiveData().observeForever(observer)
    }

    @Test
    fun `when starting then emits loading view state`() {
        whenever(useCase.getCountries()).thenReturn(Single.never())

        viewModel.start()

        observer.assertValues(
            CountryListViewModel.ViewState.Empty,
            CountryListViewModel.ViewState.Loading
        )
    }

    @Test
    fun `given error when getting countries then emits error view state`() {
        whenever(useCase.getCountries()).thenReturn(Single.error(Throwable()))

        viewModel.start()

        observer.assertValues(
            CountryListViewModel.ViewState.Empty,
            CountryListViewModel.ViewState.Loading,
            CountryListViewModel.ViewState.Error
        )
    }

    @Test
    fun `given success when getting countries then emits content view state`() {
        whenever(useCase.getCountries()).thenReturn(Single.just(listOf(COUNTRY_1, COUNTRY_2)))

        viewModel.start()

        observer.assertValues(
            CountryListViewModel.ViewState.Empty,
            CountryListViewModel.ViewState.Loading,
            CountryListViewModel.ViewState.Content(
                baseCountries = listOf(COUNTRY_LIST_COUNTRY_1, COUNTRY_LIST_COUNTRY_2),
                countriesToDisplay = listOf(COUNTRY_LIST_COUNTRY_1, COUNTRY_LIST_COUNTRY_2)
            )
        )
    }

    @Test
    fun `given content state when user searches for countries then show filtered list`() {
        whenever(useCase.getCountries()).thenReturn(Single.just(listOf(COUNTRY_1, COUNTRY_2)))
        viewModel.start()

        viewModel.onCountriesFiltered(COUNTRY_NAME_1)

        observer.assertValues(
            CountryListViewModel.ViewState.Empty,
            CountryListViewModel.ViewState.Loading,
            CountryListViewModel.ViewState.Content(
                baseCountries = listOf(COUNTRY_LIST_COUNTRY_1, COUNTRY_LIST_COUNTRY_2),
                countriesToDisplay = listOf(COUNTRY_LIST_COUNTRY_1, COUNTRY_LIST_COUNTRY_2)
            ),
            CountryListViewModel.ViewState.Content(
                baseCountries = listOf(COUNTRY_LIST_COUNTRY_1, COUNTRY_LIST_COUNTRY_2),
                countriesToDisplay = listOf(COUNTRY_LIST_COUNTRY_1)
            )
        )
    }
}

class TestObserver<T> : Observer<T> {

    private val actualValues = mutableListOf<T?>()

    override fun onChanged(t: T?) {
        actualValues.add(t)
    }

    fun assertValues(vararg expectedValues: T) {
        assertThat(this.actualValues).isEqualTo(listOf(*expectedValues))
    }
}