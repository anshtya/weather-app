package com.anshtya.weatherapp.presentation.screens.manageLocation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anshtya.weatherapp.domain.model.WeatherWithPreferences
import com.anshtya.weatherapp.domain.repository.LocationRepository
import com.anshtya.weatherapp.domain.useCase.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageLocationViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val locationRepository: LocationRepository
) : ViewModel() {

//    val shouldNavigate = locationRepository.getSavedLocations().map {
//        it.isEmpty()
//    }.shareIn(
//        scope = viewModelScope,
//        started = SharingStarted.WhileSubscribed(5000L),
//        replay = 1
//    )

    private val _uiState = MutableStateFlow(ManageLocationUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getSavedLocations()
    }

    private fun getSavedLocations() {
        viewModelScope.launch {
            getWeatherUseCase().collect { savedLocations ->
                _uiState.update { it.copy(savedLocations = savedLocations) }
            }
        }
    }

    fun deleteLocation(locationId: String) {
        viewModelScope.launch { locationRepository.deleteWeatherLocation(locationId) }
    }
}

data class ManageLocationUiState(
    val savedLocations: WeatherWithPreferences = WeatherWithPreferences(),
)