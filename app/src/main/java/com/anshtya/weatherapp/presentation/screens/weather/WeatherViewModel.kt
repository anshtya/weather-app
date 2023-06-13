package com.anshtya.weatherapp.presentation.screens.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anshtya.weatherapp.domain.util.Resource
import com.anshtya.weatherapp.domain.model.WeatherWithPreferences
import com.anshtya.weatherapp.domain.repository.LocationRepository
import com.anshtya.weatherapp.domain.repository.UserDataRepository
import com.anshtya.weatherapp.domain.useCase.GetWeatherUseCase
import com.anshtya.weatherapp.domain.useCase.UpdateWeatherUseCase
import com.anshtya.weatherapp.presentation.connectionTracker.CheckConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val updateWeatherUseCase: UpdateWeatherUseCase,
    private val userDataRepository: UserDataRepository,
    private val locationRepository: LocationRepository,
    private val checkConnection: CheckConnection
) : ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState = _uiState.asStateFlow()

    init {
        checkTableNotEmpty()
        getWeather()
    }

    private fun checkTableNotEmpty() {
        viewModelScope.launch {
            locationRepository.checkIfTableEmpty().collect { isTableNotEmpty ->
                _uiState.update { it.copy(isTableNotEmpty = isTableNotEmpty) }
            }
        }
    }

    private fun getWeather() {
        viewModelScope.launch {
            getWeatherUseCase().collect { userWeather ->
                _uiState.update { it.copy(userWeather = userWeather) }
            }
        }
    }

    fun sendUpdateWeatherOption(updateOption: UpdateOption) {
        viewModelScope.launch {
            if (checkConnection.hasConnection()) {

                when (updateOption) {
                    UpdateOption.APPSTART -> {
                        val currentTime = Calendar.getInstance().timeInMillis
                        val apiCallTime = userDataRepository.userData.first().apiCallTime
                        val isOneHourDifference = (currentTime - apiCallTime) / 3_600_000 >= 1

                        if (isOneHourDifference) {
                            updateWeather()
                        }
                    }

                    UpdateOption.CLICK -> {
                        updateWeather()
                    }
                }

            } else {
                _uiState.update { it.copy(errorMessage = "Update Failed, Network unavailable") }
            }
        }
    }

    private fun updateWeather() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when (updateWeatherUseCase()) {
                is Resource.Success -> {
                    userDataRepository.setApiCallTime(Calendar.getInstance().timeInMillis)
                }

                is Resource.Error -> {
                    _uiState.update { it.copy(errorMessage = "Update Failed") }
                }
            }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun errorShown() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}

data class WeatherUiState(
    val userWeather: WeatherWithPreferences = WeatherWithPreferences(),
    val isLoading: Boolean = false,
    val isTableNotEmpty: Boolean? = null,
    val errorMessage: String? = null
)

enum class UpdateOption {
    APPSTART, CLICK
}