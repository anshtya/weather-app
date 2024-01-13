package com.anshtya.weatherapp.presentation.screens.addLocation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anshtya.weatherapp.domain.connectivity.NetworkConnectionObserver
import com.anshtya.weatherapp.domain.connectivity.NetworkStatus
import com.anshtya.weatherapp.domain.location.LocationTracker
import com.anshtya.weatherapp.domain.model.SearchLocation
import com.anshtya.weatherapp.domain.repository.WeatherRepository
import com.anshtya.weatherapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class AddLocationViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationTracker: LocationTracker,
    private val connectionObserver: NetworkConnectionObserver
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddLocationUiState())
    val uiState = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private var isNetworkAvailable: Boolean = false

    init {
        observeNetworkStatus()
    }

    val searchLocations: StateFlow<List<SearchLocation>> = _searchQuery
        .mapLatest { query ->
            delay(100L)
            if (query.isEmpty()) {
                emptyList()
            } else {
                if (isNetworkAvailable) {
                    _uiState.update { it.copy(isLoading = true) }

                    when (val response = weatherRepository.getSearchLocations(query)) {
                        is Resource.Success -> response.data

                        is Resource.Error -> {
                            _uiState.update { it.copy(errorMessage = "An error occurred") }
                            emptyList()
                        }
                    }

                } else {
                    _uiState.update { it.copy(errorMessage = "Network unavailable") }
                    emptyList()
                }
            }
        }
        .onEach { _uiState.update { it.copy(isLoading = false) } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    fun getUserCurrentLocation() {
        viewModelScope.launch {
            if (isNetworkAvailable) {
                _uiState.update { it.copy(isLoading = true) }
                locationTracker.getCurrentLocation()?.let { location ->
                    val response =
                        weatherRepository.getSearchLocations(
                            "${location.latitude},${location.longitude}"
                        )

                    when (response) {
                        is Resource.Success -> {
                            val locationUrl = response.data.first().url
                            onLocationClick(locationUrl)
                        }

                        is Resource.Error -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = response.message
                                )
                            }
                        }
                    }

                } ?: _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Can't retrieve current location"
                    )
                }
            } else {
                _uiState.update { it.copy(errorMessage = "Network unavailable") }
            }
        }
    }

    fun onLocationClick(locationUrl: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when (val response = weatherRepository.addWeather(locationUrl)) {
                is Resource.Success -> {
                    _uiState.update { it.copy(isLocationAdded = true) }
                }

                is Resource.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = response.message
                        )
                    }
                }
            }
        }
    }

    fun errorShown() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.update { query }
    }

    private fun observeNetworkStatus() {
        connectionObserver.networkStatus
            .map { it == NetworkStatus.Available }
            .onEach { isNetworkAvailable = it }
            .launchIn(viewModelScope)
    }
}

data class AddLocationUiState(
    val isLoading: Boolean = false,
    val isLocationAdded: Boolean? = null,
    val errorMessage: String? = null
)