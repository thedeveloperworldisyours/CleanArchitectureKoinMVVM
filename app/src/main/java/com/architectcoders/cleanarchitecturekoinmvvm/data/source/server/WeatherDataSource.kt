package com.architectcoders.cleanarchitecturekoinmvvm.data.source.server

import com.architectcoders.cleanarchitecturekoinmvvm.data.source.RemoteDataSource
import com.architectcoders.cleanarchitecturekoinmvvm.domain.Weather
import com.architectcoders.cleanarchitecturekoinmvvm.data.source.toDomainWeather

class WeatherDataSource(private  val weatherDB: WeatherDB): RemoteDataSource {
    override suspend fun getWeather(lat: String, lon: String, appID: String): Weather {
        return weatherDB.service
            .getLocationWeather(lat, lon, appID).await().toDomainWeather()
    }
}