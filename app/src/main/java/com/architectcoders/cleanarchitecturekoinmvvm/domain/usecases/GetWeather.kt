package com.architectcoders.cleanarchitecturekoinmvvm.domain.usecases

import com.architectcoders.cleanarchitecturekoinmvvm.data.repository.WeatherRepository
import com.architectcoders.cleanarchitecturekoinmvvm.domain.Weather

class GetWeather(private val weatherRepository: WeatherRepository) {

    suspend fun invoke(): Weather = weatherRepository.getWeather()
}