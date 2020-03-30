package com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.usecases

import com.nhaarman.mockitokotlin2.whenever
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.data.repository.WeatherRepository
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.domain.Weather
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.domain.usecases.FindByCity
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FindByCityTest {

    @Mock
    lateinit var repository: WeatherRepository


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

    lateinit var findByCity: FindByCity

    @Before
    fun setUp(){
        findByCity = FindByCity(repository)
    }

    @Test
    fun `invoke calls city`() {
        runBlocking {

            var list=listOf(mockedWeather.copy(id=1))


            whenever(repository.findByCity("Madrid")).thenReturn(list)

            val result = findByCity.invoke("Madrid")

            Assert.assertEquals(list, result)
        }
    }

}