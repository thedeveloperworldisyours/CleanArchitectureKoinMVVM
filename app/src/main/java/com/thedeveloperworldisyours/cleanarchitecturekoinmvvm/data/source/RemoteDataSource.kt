package com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.data.source

import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.domain.Weather

interface RemoteDataSource {
    suspend fun getWeather(lat: String, lon: String, appID: String): Weather
}