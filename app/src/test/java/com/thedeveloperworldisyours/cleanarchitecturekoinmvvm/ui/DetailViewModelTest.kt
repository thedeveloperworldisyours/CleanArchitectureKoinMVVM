package com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.domain.Weather
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.domain.usecases.FindByCity
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.domain.usecases.FindByTimestamp
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.ui.detail.DetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var findByCity: FindByCity

    @Mock
    lateinit var findByTimestamp: FindByTimestamp

    @Mock
    lateinit var observer: Observer<DetailViewModel.UiModel>

    lateinit var detailViewmodel: DetailViewModel

    val timestamp = "123456"

    val city = "Madrid"

    val mockedWeather = Weather(
        1,
        "Madrid",
        "main",
        "description",
        "temperature",
        "pressure",
        "humidity",
        "temp_min",
        "temp_max",
        "123456"
    )

    @Before
    fun `setup`() {
        detailViewmodel =
            DetailViewModel(timestamp, findByCity, findByTimestamp, Dispatchers.Unconfined)
    }

    @Test
    fun `finding weather by city`() {
        runBlocking {
            whenever(findByTimestamp.invoke(timestamp)).thenReturn(mockedWeather)
            detailViewmodel.model.observeForever(observer)

            verify(observer).onChanged(DetailViewModel.UiModel.Content(mockedWeather))
        }
    }

    @Test
    fun `finding weather by timestand`() {
        runBlocking {

            val weathers = listOf(mockedWeather.copy(id = 1))

            whenever(findByTimestamp.invoke(timestamp)).thenReturn(mockedWeather)
            whenever(findByCity.invoke(city)).thenReturn(weathers)
            detailViewmodel.model.observeForever(observer)
            detailViewmodel.findWeatherByCity(city)

            verify(observer).onChanged(DetailViewModel.UiModel.Content(mockedWeather))
            verify(observer).onChanged(DetailViewModel.UiModel.ShowWeatherByCity(weathers))
        }
    }
}