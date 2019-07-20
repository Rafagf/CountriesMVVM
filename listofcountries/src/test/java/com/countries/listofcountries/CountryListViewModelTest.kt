package com.countries.listofcountries

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.countries.listofcountries.dagger.CountryListViewModelReducer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

private const val COUNTRY_NAME_1 = "Spain"
private const val COUNTRY_NAME_2 = "England"
private val COUNTRY_FIXTURE_1 = CountryFixture.aCountry(name = COUNTRY_NAME_1)
private val COUNTRY_FIXTURE_2 = CountryFixture.aCountry(name = COUNTRY_NAME_2)
private val COUNTRY_LIST_MODEL_FIXTURE_1 = CountryListModelFixture.aCountry(COUNTRY_NAME_1)
private val COUNTRY_LIST_MODEL_FIXTURE_2 = CountryListModelFixture.aCountry(COUNTRY_NAME_2)

class CountryListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val schedulers = RxImmediateSchedulerRule()

    private val useCase = mock<CountryListUseCase>()
    private val mapper = mock<CountryListModelMapper>()
    private val reducer = CountryListViewModelReducer(mapper)
    private val viewModel = CountryListViewModel(useCase, reducer)
    private val observer: TestObserver<CountryListViewModel.ViewState> = TestObserver()

    @Before
    fun setUp() {
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
        whenever(useCase.getCountries()).thenReturn(Single.just(listOf(COUNTRY_FIXTURE_1, COUNTRY_FIXTURE_2)))
        whenever(mapper.map(listOf(COUNTRY_FIXTURE_1, COUNTRY_FIXTURE_2)))
            .thenReturn(listOf(COUNTRY_LIST_MODEL_FIXTURE_1, COUNTRY_LIST_MODEL_FIXTURE_2))

        viewModel.start()

        observer.assertValues(
            CountryListViewModel.ViewState.Empty,
            CountryListViewModel.ViewState.Loading,
            CountryListViewModel.ViewState.Content(
                baseCountries = listOf(COUNTRY_LIST_MODEL_FIXTURE_1, COUNTRY_LIST_MODEL_FIXTURE_2),
                countriesToDisplay = listOf(COUNTRY_LIST_MODEL_FIXTURE_1, COUNTRY_LIST_MODEL_FIXTURE_2)
            )
        )
    }

    @Test
    fun `given content state when user searches for countries then show filtered list`() {
        whenever(useCase.getCountries()).thenReturn(Single.just(listOf(COUNTRY_FIXTURE_1, COUNTRY_FIXTURE_2)))
        whenever(mapper.map(listOf(COUNTRY_FIXTURE_1, COUNTRY_FIXTURE_2)))
            .thenReturn(listOf(COUNTRY_LIST_MODEL_FIXTURE_1, COUNTRY_LIST_MODEL_FIXTURE_2))
        viewModel.start()

        viewModel.onCountriesFiltered(COUNTRY_NAME_1)

        observer.assertValues(
            CountryListViewModel.ViewState.Empty,
            CountryListViewModel.ViewState.Loading,
            CountryListViewModel.ViewState.Content(
                baseCountries = listOf(COUNTRY_LIST_MODEL_FIXTURE_1, COUNTRY_LIST_MODEL_FIXTURE_2),
                countriesToDisplay = listOf(COUNTRY_LIST_MODEL_FIXTURE_1, COUNTRY_LIST_MODEL_FIXTURE_2)
            ),
            CountryListViewModel.ViewState.Content(
                baseCountries = listOf(COUNTRY_LIST_MODEL_FIXTURE_1, COUNTRY_LIST_MODEL_FIXTURE_2),
                countriesToDisplay = listOf(COUNTRY_LIST_MODEL_FIXTURE_1)
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