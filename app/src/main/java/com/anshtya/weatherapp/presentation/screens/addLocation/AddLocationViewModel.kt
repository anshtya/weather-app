package com.anshtya.weatherapp.presentation.screens.addLocation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anshtya.weatherapp.domain.location.LocationTracker
import com.anshtya.weatherapp.domain.util.Resource
import com.anshtya.weatherapp.domain.model.SearchLocation
import com.anshtya.weatherapp.domain.repository.LocationRepository
import com.anshtya.weatherapp.domain.repository.UserDataRepository
import com.anshtya.weatherapp.domain.useCase.GetSearchResultUseCase
import com.anshtya.weatherapp.presentation.connectionTracker.CheckConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class AddLocationViewModel @Inject constructor(
    private val getSearchResultUseCase: GetSearchResultUseCase,
    private val locationRepository: LocationRepository,
    private val userDataRepository: UserDataRepository,
    private val locationTracker: LocationTracker,
    private val checkConnection: CheckConnection
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchLocationUiState())
    val uiState = _uiState.asStateFlow()

    fun onSearchTextChange(text: String) {
        if (text.isEmpty()) {
            _uiState.update {
                it.copy(
                    searchText = text,
                    searchLocations = null
                )
            }
        } else {
            _uiState.update { it.copy(searchText = text) }
        }
    }

    fun onSubmitSearch(text: String) {
        viewModelScope.launch {
            if (checkConnection.hasConnection()) {
                _uiState.update { it.copy(isLoading = true) }
                when (val response = getSearchResultUseCase(text)) {
                    is Resource.Success -> {
                        _uiState.update { it.copy(searchLocations = response.data) }
                    }

                    is Resource.Error -> {
                        _uiState.update { it.copy(errorMessage = response.message) }
                    }
                }
                _uiState.update { it.copy(isLoading = false) }
            } else {
                _uiState.update { it.copy(errorMessage = "Network unavailable") }
            }
        }
    }

    fun getUserCurrentLocation() {
        viewModelScope.launch {
            if (checkConnection.hasConnection()) {
                _uiState.update { it.copy(isLoading = true) }
                locationTracker.getCurrentLocation()?.let { location ->
                    val response =
                        locationRepository.getLocations("${location.latitude},${location.longitude}")
                    val locationUrl = response.first().url
                    onLocationClick(locationUrl)
                } ?: _uiState.update {
                    it.copy(isLoading = false, errorMessage = "Can't retrieve current location")
                }
            } else {
                _uiState.update { it.copy(errorMessage = "Network unavailable") }
            }
        }
    }

    fun onLocationClick(locationUrl: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when (val response = locationRepository.addWeatherLocation(locationUrl)) {
                is Resource.Success -> {
                    userDataRepository.setApiCallTime(Calendar.getInstance().timeInMillis)
                    _uiState.update { it.copy(isLocationAdded = true) }
                }

                is Resource.Error -> {
                    _uiState.update { it.copy(errorMessage = response.message) }
                }
            }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun errorShown() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}

data class SearchLocationUiState(
    val searchText: String = "",
    val searchLocations: List<SearchLocation>? = null,
    val isLoading: Boolean = false,
    val isLocationAdded: Boolean = false,
    val errorMessage: String? = null
)