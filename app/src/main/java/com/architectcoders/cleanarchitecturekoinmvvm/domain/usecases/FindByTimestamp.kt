package com.architectcoders.cleanarchitecturekoinmvvm.domain.usecases

import com.architectcoders.cleanarchitecturekoinmvvm.data.repository.WeatherRepository
import com.architectcoders.cleanarchitecturekoinmvvm.domain.Weather

class FindByTimestamp(private val weatherRepository: WeatherRepository) {

    suspend fun invoke(timestamp: String): Weather = weatherRepository.findByTimestamp(timestamp)
}