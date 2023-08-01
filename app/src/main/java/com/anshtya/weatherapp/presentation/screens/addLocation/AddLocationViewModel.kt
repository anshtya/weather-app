package com.anshtya.weatherapp.presentation.screens.addLocation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anshtya.weatherapp.domain.location.LocationTracker
import com.anshtya.weatherapp.domain.model.SearchLocation
import com.anshtya.weatherapp.domain.repository.LocationRepository
import com.anshtya.weatherapp.domain.useCase.GetSearchResultUseCase
import com.anshtya.weatherapp.util.network.NetworkConnectionTracker
import com.anshtya.weatherapp.util.network.NetworkStatus
import com.anshtya.weatherapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddLocationViewModel @Inject constructor(
    private val getSearchResultUseCase: GetSearchResultUseCase,
    private val locationRepository: LocationRepository,
    private val locationTracker: LocationTracker,
    private val connectionTracker: NetworkConnectionTracker
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchLocationUiState())
    val uiState = _uiState.asStateFlow()

    private var isNetworkAvailable: Boolean = false

    init {
        observeNetworkStatus()
    }

    private fun observeNetworkStatus() {
        connectionTracker.networkStatus
            .map {
                it == NetworkStatus.Available
            }
            .onEach {
                isNetworkAvailable = it
            }
            .launchIn(viewModelScope)
    }

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
            if (isNetworkAvailable) {
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
            if (isNetworkAvailable) {
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
    val isLocationAdded: Boolean? = null,
    val errorMessage: String? = null
)