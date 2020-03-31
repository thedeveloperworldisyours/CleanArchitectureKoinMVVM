package com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.data.source.LocalDataSource
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.domain.usecases.FindByCity
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.domain.usecases.FindByTimestamp
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.ui.detail.DetailViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailIntegrationTests : AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<DetailViewModel.UiModel>

    val city = "Madrid"

    lateinit var detailViewModel: DetailViewModel
    private lateinit var localDataSource: FakeLocalDataSource

    @Before
    fun setup() {
        val vmModule = module {
            factory { (timestamp: String) -> DetailViewModel(timestamp, get(), get(), get()) }
            factory { FindByTimestamp(get()) }
            factory { FindByCity(get()) }
        }

        initMockedDi(vmModule)
        detailViewModel= get { parametersOf("123456") }

        localDataSource = get<LocalDataSource>() as FakeLocalDataSource
        localDataSource.listWeather = defaultFakeWeather
    }

    @Test
    fun `finding weather by city`() {
        detailViewModel.model.observeForever(observer)

        verify(observer).onChanged(DetailViewModel.UiModel.Content(mockedWeather))
    }

    @Test
    fun `finding weather by timestand`() {


        detailViewModel.model.observeForever(observer)
        detailViewModel.findWeatherByCity(city)

        verify(observer).onChanged(DetailViewModel.UiModel.ShowWeatherByCity(defaultFakeWeather))
    }

}