package com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.usecases

import com.nhaarman.mockitokotlin2.whenever
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.data.repository.WeatherRepository
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.domain.Weather
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.domain.usecases.FindByTimestamp
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FindByTimestampTest {

    @Mock
    lateinit var repository: WeatherRepository

    lateinit var findByTimestamp: FindByTimestamp

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
        findByTimestamp = FindByTimestamp(repository)
    }

    @Test
    fun `invoke calls weather repository`() {
        runBlocking {

            whenever(repository.findByTimestamp("123456")).thenReturn(mockedWeather)

            val result = findByTimestamp.invoke("123456")

            Assert.assertEquals(mockedWeather, result)
        }
    }
}