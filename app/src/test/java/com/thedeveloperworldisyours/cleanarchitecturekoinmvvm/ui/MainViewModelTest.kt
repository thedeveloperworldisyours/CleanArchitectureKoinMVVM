package com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.domain.Weather
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.domain.usecases.GetWeather
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.ui.main.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getWeather: GetWeather

    @Mock
    lateinit var observer: Observer<MainViewModel.UiModel>

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

    lateinit var mainViewModel: MainViewModel

    @Before
    fun `set Up`() {
        mainViewModel = MainViewModel(getWeather, Dispatchers.Unconfined)
    }

    @Test
    fun `observing LiveData launches location permission request`() {

        mainViewModel.model.observeForever(observer)

        verify(observer).onChanged(MainViewModel.UiModel.RequestLocationPermission)
    }

    @Test
    fun `check internet there is not internet`() {

        mainViewModel.model.observeForever(observer)

        mainViewModel.checkInternet(false)

        verify(observer).onChanged(MainViewModel.UiModel.ShowCanCheckYourInternet)
    }

    @Test
    fun `check location there is not location`() {

        mainViewModel.model.observeForever(observer)

        mainViewModel.checkLocation(false)

        verify(observer).onChanged(MainViewModel.UiModel.ShowTurnOnLocation)
    }

    @Test
    fun `check permission successful requested`() {

        mainViewModel.model.observeForever(observer)

        mainViewModel.onCoarsePermissionRequested(true)

        verify(observer).onChanged(MainViewModel.UiModel.RequestCheckLocation)
    }

    @Test
    fun `no allow permission requested`() {

        mainViewModel.model.observeForever(observer)

        mainViewModel.onCoarsePermissionRequested(false)

        verify(observer).onChanged(MainViewModel.UiModel.ShowTurnOnPermission)
    }

    @Test
    fun `check location`() {

        mainViewModel.model.observeForever(observer)

        mainViewModel.onCoarsePermissionRequested(true)
        mainViewModel.checkLocation(false)


        verify(observer).onChanged(MainViewModel.UiModel.RequestCheckLocation)
        verify(observer).onChanged(MainViewModel.UiModel.ShowTurnOnLocation)
    }

    @Test
    fun `check location and internet`() {

        mainViewModel.model.observeForever(observer)

        mainViewModel.onCoarsePermissionRequested(true)
        mainViewModel.checkLocation(true)
        mainViewModel.checkInternet(false)

        verify(observer).onChanged(MainViewModel.UiModel.RequestCheckInternet)
        verify(observer).onChanged(MainViewModel.UiModel.ShowCanCheckYourInternet)
    }

    @Test
    fun `check location, internet and launch`() {
        runBlocking {
            whenever(getWeather.invoke()).thenReturn(mockedWeather)
            mainViewModel.model.observeForever(observer)
            mainViewModel.onCoarsePermissionRequested(true)
            mainViewModel.checkLocation(true)
            mainViewModel.checkInternet(true)
            val weatherResult = getWeather.invoke()

            verify(observer).onChanged(MainViewModel.UiModel.RequestCheckInternet)
            verify(observer).onChanged(MainViewModel.UiModel.Loading)
            verify(observer).onChanged(MainViewModel.UiModel.Content(mockedWeather))
            Assert.assertEquals(weatherResult, mockedWeather)
        }
    }
}