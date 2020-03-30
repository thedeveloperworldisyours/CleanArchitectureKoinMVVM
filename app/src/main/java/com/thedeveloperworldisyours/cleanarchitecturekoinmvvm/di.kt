package com.thedeveloperworldisyours.cleanarchitecturekoinmvvm

import android.app.Application
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.data.repository.PermissionChecker
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.data.repository.RegionRepository
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.data.repository.WeatherRepository
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.data.source.*
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.data.source.database.RoomDataSource
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.data.source.database.WeatherDatabase
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.data.source.server.WeatherDB
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.data.source.server.WeatherDataSource
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.domain.usecases.FindByCity
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.domain.usecases.FindByTimestamp
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.domain.usecases.GetWeather
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.ui.detail.DetailActivity
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.ui.detail.DetailViewModel
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.ui.main.MainActivity
import com.thedeveloperworldisyours.cleanarchitecturekoinmvvm.ui.main.MainViewModel
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