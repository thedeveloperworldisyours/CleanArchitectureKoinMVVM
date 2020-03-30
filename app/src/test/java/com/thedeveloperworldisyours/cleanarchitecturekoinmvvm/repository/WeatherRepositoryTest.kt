package com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.repository

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.data.repository.RegionRepository
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.data.repository.WeatherRepository
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.data.source.LocalDataSource
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.data.source.RemoteDataSource
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.domain.Weather
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class WeatherRepositoryTest {

    @Mock
    lateinit var localDataSource: LocalDataSource

    @Mock
    lateinit var  remoteDataSource: RemoteDataSource

    @Mock
    lateinit var  regionRepository: RegionRepository

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


    val appID = "123456"

    lateinit var  repository: WeatherRepository

    @Before
    fun setup(){
        repository = WeatherRepository(localDataSource, remoteDataSource, regionRepository, appID)
    }

    @Test
    fun `get Weather`(){
        runBlocking {

            whenever(remoteDataSource.getWeather(regionRepository.findLat(), regionRepository.findlon(), appID)).thenReturn(mockedWeather)


            val retur =repository.getWeather()

            Assert.assertEquals(retur, mockedWeather)
            verify(localDataSource).saveWeather(mockedWeather)
        }
    }

    @Test
    fun `check to find by timestamp`() {
        runBlocking {
            val timestamp = "123456"

            whenever(localDataSource.findByTimestamp(timestamp)).thenReturn(mockedWeather)
            val returnValue = repository.findByTimestamp(timestamp)

            Assert.assertEquals(returnValue, mockedWeather)
        }
    }

    @Test
    fun `check to find by city`() {
        runBlocking {
            val city = "Madrid"
            val weathers = listOf<Weather>(mockedWeather.copy(id = 1))

            whenever(localDataSource.findByCity(city)).thenReturn(weathers)
            val returnValue = repository.findByCity(city)

            Assert.assertEquals(returnValue, weathers)
        }
    }
}