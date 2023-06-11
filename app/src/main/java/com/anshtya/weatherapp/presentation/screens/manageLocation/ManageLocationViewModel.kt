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

    private val _uiState = MutableStateFlow(ManageLocationUiState())
    val uiState = _uiState.asStateFlow()

    private val _selectedItems = mutableListOf<String>()

    init {
        checkTableEmpty()
        getSavedLocations()
    }

    private fun checkTableEmpty() {
        viewModelScope.launch {
            locationRepository.checkIfTableEmpty().collect { isEmpty ->
                _uiState.update { it.copy(isTableNotEmpty = isEmpty) }
            }
        }
    }

    private fun getSavedLocations() {
        viewModelScope.launch {
            getWeatherUseCase().collect { savedLocations ->
                _uiState.update { it.copy(savedLocations = savedLocations) }
            }
        }
    }

    fun selectLocation(id: String) {
        if(_selectedItems.contains(id)) {
            _selectedItems.remove(id)
        } else {
            _selectedItems.add(id)
        }
    }

    fun deleteLocation() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            _selectedItems.forEach {
                locationRepository.deleteWeatherLocation(it)
                _selectedItems.remove(it)
            }
            _uiState.update { it.copy(isLoading = false) }
        }
    }
}

data class ManageLocationUiState(
    val savedLocations: WeatherWithPreferences = WeatherWithPreferences(),
    val isLoading: Boolean = false,
    val isTableNotEmpty: Boolean? = null
)