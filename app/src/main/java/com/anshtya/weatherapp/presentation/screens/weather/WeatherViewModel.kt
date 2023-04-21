package com.anshtya.weatherapp.presentation.screens.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anshtya.weatherapp.domain.model.Weather
import com.anshtya.weatherapp.domain.useCase.GetWeatherUseCase
import com.anshtya.weatherapp.domain.useCase.UpdateWeatherUseCase
import com.anshtya.weatherapp.presentation.connectionTracker.CheckConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val updateWeatherUseCase: UpdateWeatherUseCase,
    private val checkConnection: CheckConnection
): ViewModel() {

    private val _uiState = MutableStateFlow(WeatherState())
    val uiState = _uiState.asStateFlow()

    init {
        getWeather()
    }

    private fun getWeather() {
        viewModelScope.launch {
//            updateWeather()
            getWeatherUseCase().collect { weatherList ->
                _uiState.update { it.copy(weatherList = weatherList) }
            }
        }
    }

    private fun updateWeather() {
        viewModelScope.launch {
            if (checkConnection.hasConnection()) {
                updateWeatherUseCase()
            }
            else {
                _uiState.update { it.copy(errorMessage = "Update Failed. No Internet Connection") }
            }
        }
    }

    fun errorShown() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}

data class WeatherState(
    val weatherList: List<Weather> = emptyList(),
    val errorMessage: String? = null
)