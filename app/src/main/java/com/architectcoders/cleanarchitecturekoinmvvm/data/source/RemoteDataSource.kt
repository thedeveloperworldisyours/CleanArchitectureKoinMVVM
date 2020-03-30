package com.architectcoders.cleanarchitecturekoinmvvm.data.source

import com.architectcoders.cleanarchitecturekoinmvvm.domain.Weather

interface RemoteDataSource {
    suspend fun getWeather(lat: String, lon: String, appID: String): Weather
}