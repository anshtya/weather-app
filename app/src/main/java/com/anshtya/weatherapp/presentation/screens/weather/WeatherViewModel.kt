package com.anshtya.weatherapp.presentation.screens.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo.State
import com.anshtya.weatherapp.domain.model.Weather
import com.anshtya.weatherapp.domain.useCase.GetWeatherUseCase
import com.anshtya.weatherapp.worker.WeatherWorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val weatherWorkManager: WeatherWorkManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(WeatherState())
    val uiState = _uiState.asStateFlow()

    init {
        getWeather()
        refreshWeather()
        updateWeather()
    }

    private fun getWeather() {
        viewModelScope.launch {
            getWeatherUseCase().collect { weatherList ->
                _uiState.update { it.copy(weatherList = weatherList) }
            }
        }
    }

    private fun updateWeather() {
        weatherWorkManager.updateWeather()
    }

    fun refreshWeather() {
        viewModelScope.launch {
            val workId = weatherWorkManager.refreshWeather()
            weatherWorkManager.observeWork(workId).collect { workInfo ->
                workInfo?.let {
                    when (workInfo.state) {
                        State.ENQUEUED, State.RUNNING, State.BLOCKED -> {
                            _uiState.update { it.copy(isLoading = true) }
                        }

                        State.SUCCEEDED, State.CANCELLED -> {
                            _uiState.update { it.copy(isLoading = false) }
                        }

                        State.FAILED -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = "Update failed"
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun errorShown() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}

data class WeatherState(
    val weatherList: List<Weather> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)