package com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.ui

import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.data.repository.PermissionChecker
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.data.source.LocalDataSource
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.data.source.LocationDataSource
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.data.source.RemoteDataSource
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.dataModule
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.domain.Weather
import kotlinx.coroutines.Dispatchers
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun initMockedDi(vararg modules: Module) {
    startKoin {
        modules(listOf(mockedAppModule, dataModule) + modules)
    }
}

private val mockedAppModule = module {
    single(named("apiKey")) { "12456" }
    single<LocalDataSource> { FakeLocalDataSource() }
    single<RemoteDataSource> { FakeRemoteDataSource() }
    single<LocationDataSource> { FakeLocationDataSource() }
    single<PermissionChecker> { FakePermissionChecker() }
    single { Dispatchers.Unconfined }
}
val mockedWeather = Weather(
    1,
    "Madrid",
    "main",
    "description",
    "temperature",
    "pressure",
    "humidity",
    "temp_min",
    "temp_max",
    "123456"
)

val defaultFakeWeather = listOf(
    mockedWeather.copy(1),
    mockedWeather.copy(2),
    mockedWeather.copy(3),
    mockedWeather.copy(4)
)
class FakeLocalDataSource : LocalDataSource {

    var listWeather= listOf(
        mockedWeather.copy(1),
        mockedWeather.copy(2),
        mockedWeather.copy(3),
        mockedWeather.copy(4))

    override suspend fun getListWeather(city: String): List<Weather> = listWeather

    override suspend fun saveWeather(weather: Weather) {
        listWeather.toMutableList().add(weather)
    }

    override suspend fun findByTimestamp(timestamp: String): Weather = listWeather.first { it.timestamp == timestamp }

    override suspend fun findByCity(city: String): List<Weather> =listWeather
}

class FakeRemoteDataSource : RemoteDataSource {

    override suspend fun getWeather(lat: String, lon: String, appID: String) = mockedWeather
}

class FakeLocationDataSource : LocationDataSource {
    var location = "11"

    override suspend fun findLat(): String? = location
    override suspend fun findLon(): String? = location
}

class FakePermissionChecker : PermissionChecker {
    var permissionGranted = true

    override suspend fun check(permission: PermissionChecker.Permission): Boolean =
        permissionGranted
}