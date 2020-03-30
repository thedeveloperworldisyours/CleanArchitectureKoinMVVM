package com.architectcoders.cleanarchitecturekoinmvvm.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.architectcoders.cleanarchitecturekoinmvvm.domain.Weather
import com.architectcoders.cleanarchitecturekoinmvvm.domain.usecases.FindByCity
import com.architectcoders.cleanarchitecturekoinmvvm.domain.usecases.FindByTimestamp
import com.architectcoders.cleanarchitecturekoinmvvm.ui.common.ScopedViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class DetailViewModel(
    private val timestamp: String,
    private var findByCity: FindByCity,
    private var findByTimestamp: FindByTimestamp,
    override val uiDispatcher: CoroutineDispatcher
) :
    ScopedViewModel(uiDispatcher) {

    sealed class UiModel {
        data class ShowWeatherByCity(val weatherList: List<Weather>): UiModel()
        data class Content(val weather: Weather) : UiModel()
    }

    private val _model = MutableLiveData<UiModel>()

    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) findWeather()
            return _model
        }

    private fun findWeather() = launch {
        _model.value = UiModel.Content(findByTimestamp.invoke(timestamp))
    }

    fun findWeatherByCity(city: String) = launch {
        _model.value = UiModel.ShowWeatherByCity(findByCity.invoke(city))
    }

}