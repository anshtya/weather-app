package com.anshtya.weatherapp.presentation.screens.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anshtya.weatherapp.domain.model.WeatherWithPreferences
import com.anshtya.weatherapp.domain.repository.WeatherRepository
import com.anshtya.weatherapp.domain.useCase.GetWeatherWithPreferencesUseCase
import com.anshtya.weatherapp.util.Resource
import com.anshtya.weatherapp.domain.connectivity.NetworkConnectionObserver
import com.anshtya.weatherapp.domain.connectivity.NetworkStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherWithPreferencesUseCase: GetWeatherWithPreferencesUseCase,
    private val weatherRepository: WeatherRepository,
    private val connectionObserver: NetworkConnectionObserver
) : ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState = _uiState.asStateFlow()

    val hasLocations = weatherRepository.isLocationTableNotEmpty.shareIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        replay = 1
    )

    private var isNetworkAvailable: Boolean = false

    init {
        getWeather()
        observeNetworkStatus()
    }

    private fun observeNetworkStatus() {
        connectionObserver.networkStatus
            .map {
                it == NetworkStatus.Available
            }
            .onEach {
                isNetworkAvailable = it
            }
            .launchIn(viewModelScope)
    }

    private fun getWeather() {
        viewModelScope.launch {
            getWeatherWithPreferencesUseCase().collect { userWeather ->
                _uiState.update { it.copy(userWeather = userWeather) }
            }
        }
    }

    fun updateWeather() {
        viewModelScope.launch {
            if (isNetworkAvailable) {
                _uiState.update { it.copy(isLoading = true) }
                when (weatherRepository.updateWeather()) {
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