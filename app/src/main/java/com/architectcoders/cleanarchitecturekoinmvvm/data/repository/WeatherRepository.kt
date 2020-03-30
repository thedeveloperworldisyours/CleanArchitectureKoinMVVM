package com.architectcoders.cleanarchitecturekoinmvvm.data.repository

import com.architectcoders.cleanarchitecturekoinmvvm.data.source.LocalDataSource
import com.architectcoders.cleanarchitecturekoinmvvm.data.source.RemoteDataSource
import com.architectcoders.cleanarchitecturekoinmvvm.domain.Weather

class WeatherRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val regionRepository: RegionRepository,
    private val appID: String
) {

    suspend fun getWeather(): Weather {
        val weather = remoteDataSource.getWeather(
            regionRepository.findLat(),
            regionRepository.findlon(),
            appID
        )
        localDataSource.saveWeather(weather)
        return weather
    }

    suspend fun findByTimestamp(timestamp: String): Weather {
        return localDataSource.findByTimestamp(timestamp)
    }

    suspend fun findByCity(city: String): List<Weather> {
        return localDataSource.findByCity(city)
    }

}