package com.countries.listofcountries

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
private const val COUNTRY_NAME_2 = "England"
private val COUNTRY_FIXTURE_1 = CountryFixture.aCountry(name = COUNTRY_NAME_1)
private val COUNTRY_FIXTURE_2 = CountryFixture.aCountry(name = COUNTRY_NAME_2)
private val COUNTRY_LIST_MODEL_FIXTURE_1 = CountryListModelFixture.aCountry(COUNTRY_NAME_1)
private val COUNTRY_LIST_MODEL_FIXTURE_2 = CountryListModelFixture.aCountry(COUNTRY_NAME_2)

class CountryListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private val useCase = mock<CountryListUseCase>()
    private val mapper = mock<CountryListModelMapper>()

    private val viewModel = CountryListViewModel(useCase, mapper)
    private val observer: TestObserver<CountryListViewModel.Model> = TestObserver()

    @Before
    fun setUp() {
        viewModel.liveData.observeForever(observer)
    }

    @Test
    fun `when starting then emits loading model`() {
        whenever(useCase.getCountries()).thenReturn(Single.never())

        viewModel.start()

        observer.assertValues(
            CountryListViewModel.Model.Empty,
            CountryListViewModel.Model.Loading
        )
    }

    @Test
    fun `given error when getting countries then emits error model`() {
        whenever(useCase.getCountries()).thenReturn(Single.error(Throwable()))

        viewModel.start()

        observer.assertValues(
            CountryListViewModel.Model.Empty,
            CountryListViewModel.Model.Loading,
            CountryListViewModel.Model.Error
        )
    }

    @Test
    fun `given success when getting countries then emits content model`() {
        whenever(useCase.getCountries()).thenReturn(Single.just(listOf(COUNTRY_FIXTURE_1, COUNTRY_FIXTURE_2)))
        whenever(mapper.map(listOf(COUNTRY_FIXTURE_1, COUNTRY_FIXTURE_2)))
            .thenReturn(listOf(COUNTRY_LIST_MODEL_FIXTURE_1, COUNTRY_LIST_MODEL_FIXTURE_2))

        viewModel.start()

        observer.assertValues(
            CountryListViewModel.Model.Empty,
            CountryListViewModel.Model.Loading,
            CountryListViewModel.Model.Content(
                baseCountries = listOf(COUNTRY_LIST_MODEL_FIXTURE_1, COUNTRY_LIST_MODEL_FIXTURE_2),
                countriesToDisplay = listOf(COUNTRY_LIST_MODEL_FIXTURE_1, COUNTRY_LIST_MODEL_FIXTURE_2)
            )
        )
    }

    @Test
    fun `given content state when user searches for countries then show filtered list`() {
        viewModel.liveData.value = CountryListViewModel.Model.Content(
            baseCountries = listOf(COUNTRY_LIST_MODEL_FIXTURE_1, COUNTRY_LIST_MODEL_FIXTURE_2),
            countriesToDisplay = listOf(COUNTRY_LIST_MODEL_FIXTURE_1, COUNTRY_LIST_MODEL_FIXTURE_2)
        )

        viewModel.onCountriesFiltered("Spain")

        observer.assertValues(
            CountryListViewModel.Model.Empty,
            CountryListViewModel.Model.Content(
                baseCountries = listOf(COUNTRY_LIST_MODEL_FIXTURE_1, COUNTRY_LIST_MODEL_FIXTURE_2),
                countriesToDisplay = listOf(COUNTRY_LIST_MODEL_FIXTURE_1, COUNTRY_LIST_MODEL_FIXTURE_2)
            ),
            CountryListViewModel.Model.Content(
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