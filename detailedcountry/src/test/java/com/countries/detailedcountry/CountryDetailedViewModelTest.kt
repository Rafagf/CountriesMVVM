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
private val COUNTRY_FIXTURE_1 = CountryFixture.aCountry(name = COUNTRY_NAME)

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
    private val observer: TestObserver<CountryDetailedViewModel.Model> = TestObserver()

    @Before
    fun setUp() {
        whenever(useCase.getCountry(COUNTRY_NAME)).thenReturn(Single.never())
        whenever(useCase.getBorderCountries(COUNTRY_NAME)).thenReturn(Single.never())
        viewModel.liveData.observeForever(observer)
    }

    @Test
    fun `when starting then emits loading model`() {
        viewModel.start(COUNTRY_NAME)

        observer.assertValues(
            CountryDetailedViewModel.Model.Empty,
            CountryDetailedViewModel.Model.Loading()
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