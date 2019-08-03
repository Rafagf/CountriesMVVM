package com.countries.detailedcountry

import android.content.res.Resources
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

private const val COUNTRY_NAME_1 = "Spain"
private const val COUNTRY_NAME_2 = "Portugal"
private val BORDER_COUNTRY_1 = BorderFixture.aBorder(COUNTRY_NAME_1)
private val BORDER_COUNTRY_2 = BorderFixture.aBorder(COUNTRY_NAME_2)
private val COUNTRY_1 = CountryFixture.aCountry(name = COUNTRY_NAME_1)
private val COUNTRY_DETAILED_1 = CountryDetailedModelFixture.aCountry(COUNTRY_NAME_1)
private val COUNTRY_BORDERS = CountryBordersModel(listOf(BORDER_COUNTRY_1, BORDER_COUNTRY_2))

class CountryDetailedViewModelTest {

    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val schedulers = RxImmediateSchedulerRule()

    private val useCase = mock<CountryDetailedUseCase>()
    private val resources = mock<Resources>()
    private val reducer = CountryDetailedViewModelReducer(resources)
    private val viewModel = CountryDetailedViewModel(useCase, reducer)
    private val observer: TestObserver<CountryDetailedViewModel.ViewState> = TestObserver()

    @Before
    fun setUp() {
        mockResources()
        whenever(useCase.getCountry(COUNTRY_NAME_1)).thenReturn(Single.never())
        whenever(useCase.getBorderCountries(COUNTRY_NAME_1)).thenReturn(Single.never())
        viewModel.getLiveData().observeForever(observer)
    }

    private fun mockResources() {
        whenever(resources.getText(R.string.demonym)).thenReturn("Demonym")
        whenever(resources.getText(R.string.native_name)).thenReturn("Native name")
        whenever(resources.getText(R.string.area)).thenReturn("Area")
        whenever(resources.getText(R.string.population)).thenReturn("Population")
    }

    @Test
    fun `when starting then emits loading view state`() {
        viewModel.start(COUNTRY_NAME_1)

        observer.assertValues(
            CountryDetailedViewModel.ViewState.Empty,
            CountryDetailedViewModel.ViewState.Loading()
        )
    }

    @Test
    fun `given error when fetching country then emits error view state`() {
        whenever(useCase.getCountry(COUNTRY_NAME_1)).thenReturn(Single.error(Throwable()))

        viewModel.start(COUNTRY_NAME_1)

        observer.assertValues(
            CountryDetailedViewModel.ViewState.Empty,
            CountryDetailedViewModel.ViewState.Loading(),
            CountryDetailedViewModel.ViewState.Error
        )
    }

    @Test
    fun `when country fetched then emits country content view state`() {
        whenever(useCase.getCountry(COUNTRY_NAME_1)).thenReturn(Single.just(COUNTRY_1))

        viewModel.start(COUNTRY_NAME_1)

        observer.assertValues(
            CountryDetailedViewModel.ViewState.Empty,
            CountryDetailedViewModel.ViewState.Loading(),
            CountryDetailedViewModel.ViewState.Content(
                country = COUNTRY_DETAILED_1
            )
        )
    }

    @Test
    fun `when borders fetched then emits borders content view state`() {
        whenever(useCase.getCountry(COUNTRY_NAME_1)).thenReturn(Single.never())
        whenever(useCase.getBorderCountries(COUNTRY_NAME_1)).thenReturn(Single.just(COUNTRY_BORDERS))

        viewModel.start(COUNTRY_NAME_1)

        observer.assertValues(
            CountryDetailedViewModel.ViewState.Empty,
            CountryDetailedViewModel.ViewState.Loading(),
            CountryDetailedViewModel.ViewState.Content(
                country = null,
                borders = COUNTRY_BORDERS
            )
        )
    }

    @Test
    fun `when both borders and country fetched then emits full content view state`() {
        whenever(useCase.getCountry(COUNTRY_NAME_1)).thenReturn(Single.just(COUNTRY_1))
        whenever(useCase.getBorderCountries(COUNTRY_NAME_1)).thenReturn(Single.just(COUNTRY_BORDERS))

        viewModel.start(COUNTRY_NAME_1)

        observer.assertValues(
            CountryDetailedViewModel.ViewState.Empty,
            CountryDetailedViewModel.ViewState.Loading(),
            CountryDetailedViewModel.ViewState.Content(
                country = COUNTRY_DETAILED_1,
                borders = null
            ),
            CountryDetailedViewModel.ViewState.Content(
                country = COUNTRY_DETAILED_1,
                borders = COUNTRY_BORDERS
            )
        )
    }

    class TestObserver<T> : Observer<T> {

        private val actualValues = mutableListOf<T?>()

        override fun onChanged(t: T?) {
            actualValues.add(t)
        }

        fun assertValues(vararg expectedValues: T) {
            Assertions.assertThat(this.actualValues).isEqualTo(listOf(*expectedValues))
        }
    }
}