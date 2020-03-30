package com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.usecases

import com.nhaarman.mockitokotlin2.whenever
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.data.repository.WeatherRepository
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.domain.Weather
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.domain.usecases.GetWeather
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetWeatherTest {

    @Mock
    lateinit var repository: WeatherRepository

    lateinit var getWeather: GetWeather

    val mockedWeather =  Weather(1,
        "Madrid",
        "main",
        "description",
        "temperature",
        "pressure",
        "humidity",
        "temp_min",
        "temp_max",
        "123456")

    @Before
    fun setUp() {
        getWeather = GetWeather(repository)
    }

    @Test
    fun `invoke calls weather`(){
        runBlocking {

            whenever(repository.getWeather()).thenReturn(mockedWeather)

            val result = getWeather.invoke()

            Assert.assertEquals(mockedWeather, result)
        }
    }

}