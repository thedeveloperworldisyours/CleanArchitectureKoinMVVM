package com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.domain.usecases

import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.data.repository.WeatherRepository
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.domain.Weather

class GetWeather(private val weatherRepository: WeatherRepository) {

    suspend fun invoke(): Weather = weatherRepository.getWeather()
}