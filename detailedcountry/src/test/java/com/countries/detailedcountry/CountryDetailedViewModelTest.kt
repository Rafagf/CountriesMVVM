package com.countries.detailedcountry

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

private const val COUNTRY_NAME = "Spain"
private const val COUNTRY_NAME_2 = "Italy"
private val BORDER = BorderFixture.aBorder(COUNTRY_NAME)
private val BORDER_2 = BorderFixture.aBorder(COUNTRY_NAME_2)
private val COUNTRY_FIXTURE = CountryFixture.aCountry(name = COUNTRY_NAME)
private val COUNTRY_DETAILED_MODEL_FIXTURE = CountryDetailedModelFixture.aCountry(COUNTRY_NAME)
private val COUNTRY_BORDERS = CountryBordersModel(listOf(BORDER, BORDER_2))

class CountryDetailedViewModelTest {

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private val useCase = mock<CountryDetailedUseCase>()
    private val mapper = mock<CountryDetailedModelMapper>()

    private val viewModel = CountryDetailedViewModel(useCase, mapper)
    private val observer: TestObserver<CountryDetailedViewModel.ViewState> = TestObserver()

    @Before
    fun setUp() {
        whenever(useCase.getCountry(COUNTRY_NAME)).thenReturn(Single.never())
        whenever(useCase.getBorderCountries(COUNTRY_NAME)).thenReturn(Single.never())
        viewModel.getLiveData().observeForever(observer)
    }

    @Test
    fun `when starting then emits loading view state`() {
        viewModel.start(COUNTRY_NAME)

        observer.assertValues(
            CountryDetailedViewModel.ViewState.Empty,
            CountryDetailedViewModel.ViewState.Loading()
        )
    }

    @Test
    fun `given error when fetching country then emits error view state`() {
        whenever(useCase.getCountry(COUNTRY_NAME)).thenReturn(Single.error(Throwable()))

        viewModel.start(COUNTRY_NAME)

        observer.assertValues(
            CountryDetailedViewModel.ViewState.Empty,
            CountryDetailedViewModel.ViewState.Loading(),
            CountryDetailedViewModel.ViewState.Error
        )
    }

    @Test
    fun `when country fetched then emits country content view state`() {
        whenever(useCase.getCountry(COUNTRY_NAME)).thenReturn(Single.just(COUNTRY_FIXTURE))
        whenever(mapper.map(COUNTRY_FIXTURE)).thenReturn(COUNTRY_DETAILED_MODEL_FIXTURE)

        viewModel.start(COUNTRY_NAME)

        observer.assertValues(
            CountryDetailedViewModel.ViewState.Empty,
            CountryDetailedViewModel.ViewState.Loading(),
            CountryDetailedViewModel.ViewState.Content(
                country = COUNTRY_DETAILED_MODEL_FIXTURE
            )
        )
    }

    @Test
    fun `when borders fetched then emits borders content view state`() {
        whenever(useCase.getCountry(COUNTRY_NAME)).thenReturn(Single.never())
        whenever(useCase.getBorderCountries(COUNTRY_NAME)).thenReturn(Single.just(COUNTRY_BORDERS))

        viewModel.start(COUNTRY_NAME)

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
        whenever(useCase.getCountry(COUNTRY_NAME)).thenReturn(Single.just(COUNTRY_FIXTURE))
        whenever(mapper.map(COUNTRY_FIXTURE)).thenReturn(COUNTRY_DETAILED_MODEL_FIXTURE)
        whenever(useCase.getBorderCountries(COUNTRY_NAME)).thenReturn(Single.just(COUNTRY_BORDERS))

        viewModel.start(COUNTRY_NAME)

        observer.assertValues(
            CountryDetailedViewModel.ViewState.Empty,
            CountryDetailedViewModel.ViewState.Loading(),
            CountryDetailedViewModel.ViewState.Content(
                country = COUNTRY_DETAILED_MODEL_FIXTURE,
                borders = null
            ),
            CountryDetailedViewModel.ViewState.Content(
                country = COUNTRY_DETAILED_MODEL_FIXTURE,
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