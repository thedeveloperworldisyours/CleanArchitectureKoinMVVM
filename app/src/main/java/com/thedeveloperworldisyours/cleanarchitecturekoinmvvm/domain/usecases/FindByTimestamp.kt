package com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.domain.usecases

import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.data.repository.WeatherRepository
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.domain.Weather

class FindByTimestamp(private val weatherRepository: WeatherRepository) {

    suspend fun invoke(timestamp: String): Weather = weatherRepository.findByTimestamp(timestamp)
}