package com.architectcoders.cleanarchitecturekoinmvvm.data.source.database

import com.architectcoders.cleanarchitecturekoinmvvm.data.source.LocalDataSource
import com.architectcoders.cleanarchitecturekoinmvvm.data.source.toDomainWeather
import com.architectcoders.cleanarchitecturekoinmvvm.data.source.toRoomWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.architectcoders.cleanarchitecturekoinmvvm.domain.Weather as DomainWeathear

import com.architectcoders.cleanarchitecturekoinmvvm.data.source.database.Weather as RoomWeather

class RoomDataSource(db: WeatherDatabase) :
    LocalDataSource {

    val dao: WeatherDao = db.weatherDao()

    override suspend fun getListWeather(city: String): List<DomainWeathear> =
        withContext(Dispatchers.IO) {
            dao.findByCity(city).map(RoomWeather::toDomainWeather)
        }

    override suspend fun saveWeather(weather: DomainWeathear) =
        withContext(Dispatchers.IO) {
        dao.insertWeather(weather.toRoomWeather())
    }

    override suspend fun findByTimestamp(timestamp: String): DomainWeathear =
        withContext(Dispatchers.IO) {
            dao.findByTimestamp(timestamp).toDomainWeather()
        }

    override suspend fun findByCity(city: String): List<DomainWeathear> =
        withContext(Dispatchers.IO) {
            dao.findByCity(city).map { it.toDomainWeather() }
        }
}
