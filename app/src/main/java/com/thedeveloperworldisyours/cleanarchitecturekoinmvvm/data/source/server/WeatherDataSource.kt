package com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.data.source.server

import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.data.source.RemoteDataSource
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.domain.Weather
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.data.source.toDomainWeather

class WeatherDataSource(private  val weatherDB: WeatherDB): RemoteDataSource {
    override suspend fun getWeather(lat: String, lon: String, appID: String): Weather {
        return weatherDB.service
            .getLocationWeather(lat, lon, appID).await().toDomainWeather()
    }
}