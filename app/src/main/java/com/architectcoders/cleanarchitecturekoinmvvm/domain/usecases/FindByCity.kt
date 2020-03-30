package com.architectcoders.cleanarchitecturekoinmvvm.domain.usecases

import com.architectcoders.cleanarchitecturekoinmvvm.data.repository.WeatherRepository
import com.architectcoders.cleanarchitecturekoinmvvm.domain.Weather

class FindByCity(private val weatherRepository: WeatherRepository) {

    suspend fun invoke(city: String): List<Weather> = weatherRepository.findByCity(city)
}