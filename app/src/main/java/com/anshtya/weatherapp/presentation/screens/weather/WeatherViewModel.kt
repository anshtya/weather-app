package com.anshtya.weatherapp.presentation.screens.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anshtya.weatherapp.domain.model.WeatherWithPreferences
import com.anshtya.weatherapp.domain.repository.LocationRepository
import com.anshtya.weatherapp.domain.useCase.GetWeatherUseCase
import com.anshtya.weatherapp.domain.useCase.UpdateWeatherUseCase
import com.anshtya.weatherapp.presentation.connectionTracker.CheckConnection
import com.anshtya.weatherapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val updateWeatherUseCase: UpdateWeatherUseCase,
    private val checkConnection: CheckConnection,
    locationRepository: LocationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState = _uiState.asStateFlow()

    val hasLocations = locationRepository.isLocationTableNotEmpty.shareIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        replay = 1
    )

    init {
        getWeather()
    }

    private fun getWeather() {
        viewModelScope.launch {
            getWeatherUseCase().collect { userWeather ->
                _uiState.update { it.copy(userWeather = userWeather) }
            }
        }
    }

    fun updateWeather() {
        viewModelScope.launch {
            if (checkConnection.hasConnection()) {
                _uiState.update { it.copy(isLoading = true) }
                when (updateWeatherUseCase()) {
                    is Resource.Success -> {
                        _uiState.update { it.copy(isLoading = false) }
                    }
                    is Resource.Error -> {
                        _uiState.update { it.copy(
                            isLoading = false,
                            errorMessage = "Update Failed"
                        ) }
                    }
                }
                _uiState.update { it.copy(isLoading = false) }
            } else {
                _uiState.update { it.copy(errorMessage = "Update Failed, Network unavailable") }
            }
        }
    }

    fun errorShown() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}

data class WeatherUiState(
    val userWeather: WeatherWithPreferences = WeatherWithPreferences(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)