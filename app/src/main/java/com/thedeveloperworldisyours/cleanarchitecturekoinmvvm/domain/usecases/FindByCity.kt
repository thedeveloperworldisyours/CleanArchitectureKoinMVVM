package com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.domain.usecases

import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.data.repository.WeatherRepository
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.domain.Weather

class FindByCity(private val weatherRepository: WeatherRepository) {

    suspend fun invoke(city: String): List<Weather> = weatherRepository.findByCity(city)
}