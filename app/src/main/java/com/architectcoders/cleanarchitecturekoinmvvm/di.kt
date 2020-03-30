package com.architectcoders.cleanarchitecturekoinmvvm

import android.app.Application
import com.architectcoders.cleanarchitecturekoinmvvm.data.repository.PermissionChecker
import com.architectcoders.cleanarchitecturekoinmvvm.data.repository.RegionRepository
import com.architectcoders.cleanarchitecturekoinmvvm.data.repository.WeatherRepository
import com.architectcoders.cleanarchitecturekoinmvvm.data.source.*
import com.architectcoders.cleanarchitecturekoinmvvm.data.source.database.RoomDataSource
import com.architectcoders.cleanarchitecturekoinmvvm.data.source.database.WeatherDatabase
import com.architectcoders.cleanarchitecturekoinmvvm.data.source.server.WeatherDB
import com.architectcoders.cleanarchitecturekoinmvvm.data.source.server.WeatherDataSource
import com.architectcoders.cleanarchitecturekoinmvvm.domain.usecases.FindByCity
import com.architectcoders.cleanarchitecturekoinmvvm.domain.usecases.FindByTimestamp
import com.architectcoders.cleanarchitecturekoinmvvm.domain.usecases.GetWeather
import com.architectcoders.cleanarchitecturekoinmvvm.ui.detail.DetailActivity
import com.architectcoders.cleanarchitecturekoinmvvm.ui.detail.DetailViewModel
import com.architectcoders.cleanarchitecturekoinmvvm.ui.main.MainActivity
import com.architectcoders.cleanarchitecturekoinmvvm.ui.main.MainViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module


fun Application.initDI() {
    startKoin {
        androidLogger()
        androidContext(this@initDI)
        modules(listOf(appModule, dataModule, scopesModule))
    }
}

private val appModule = module {
    single(named("apiKey")) { androidApplication().getString(R.string.key_app) }
    single { WeatherDatabase.build(get()) }
    factory<LocalDataSource> { RoomDataSource(get()) }
    factory<RemoteDataSource> { WeatherDataSource(get()) }
    factory<LocationDataSource> { PlayServicesLocationDataSource(get()) }
    factory<PermissionChecker> { AndroidPermissionChecker(get()) }
    single<CoroutineDispatcher> { Dispatchers.Main }
    single(named("baseUrl")) { "https://api.openweathermap.org/data/2.5/" }
    single { WeatherDB(get(named("baseUrl"))) }
}

val dataModule = module {
    factory { RegionRepository(get(), get()) }
    factory { WeatherRepository(get(), get(), get(), get(named("apiKey"))) }
}

private val scopesModule = module {
    scope(named<MainActivity>()) {
        viewModel { MainViewModel(get(), get()) }
        scoped { GetWeather(get()) }
    }

    scope(named<DetailActivity>()) {
        viewModel { (timestamp: String) -> DetailViewModel(timestamp, get(), get(), get()) }
        scoped { FindByCity(get()) }
        scoped { FindByTimestamp(get()) }
    }
}