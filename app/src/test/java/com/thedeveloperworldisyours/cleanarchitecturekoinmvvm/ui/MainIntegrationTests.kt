package com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.domain.Weather
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.domain.usecases.GetWeather
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.ui.main.MainViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainIntegrationTests : AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<MainViewModel.UiModel>

    private lateinit var mainViewModel: MainViewModel

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
    fun setUp() {
        val vmModule = module {
            factory { MainViewModel(get(), get()) }
            factory { GetWeather(get()) }
        }

        initMockedDi(vmModule)
        mainViewModel = get()
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
    fun `check location and internet`() {

        mainViewModel.model.observeForever(observer)

        mainViewModel.checkLocation(true)
        mainViewModel.checkInternet(false)

        verify(observer).onChanged(MainViewModel.UiModel.RequestCheckInternet)
        verify(observer).onChanged(MainViewModel.UiModel.ShowCanCheckYourInternet)
    }

    @Test
    fun `check location, internet and launch`() {
        runBlocking {
            mainViewModel.model.observeForever(observer)
            mainViewModel.checkLocation(true)
            mainViewModel.checkInternet(true)

            verify(observer).onChanged(MainViewModel.UiModel.RequestCheckInternet)
            verify(observer).onChanged(MainViewModel.UiModel.Loading)
            verify(observer).onChanged(MainViewModel.UiModel.Content(mockedWeather))
        }
    }
}