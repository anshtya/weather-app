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
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddLocationViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationTracker: LocationTracker,
    private val connectionObserver: NetworkConnectionObserver
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddLocationUiState())
    val uiState = _uiState.asStateFlow()

    private val _isLocationAdded = MutableSharedFlow<Boolean>()
    val isLocationAdded = _isLocationAdded.asSharedFlow()

    private val searchText = MutableStateFlow("")

    private var isNetworkAvailable: Boolean = false

    init {
        observeNetworkStatus()
        updateSearch()
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

    @OptIn(FlowPreview::class)
    private fun updateSearch() {
        searchText
            .onEach { text ->
                _uiState.update { it.copy(searchText = text) }
                if (text.isNotEmpty()) {
                    _uiState.update { it.copy(isSearching = true) }
                }
            }
            .debounce(500L)
            .onEach { text ->
                if (text.isNotEmpty()) {
                    executeSearch(text)
                } else {
                    _uiState.update { it.copy(isSearching = false) }
                }
            }
            .launchIn(viewModelScope)
    }

    fun onSearchTextChange(text: String) {
        searchText.value = text
    }

    private fun executeSearch(text: String) {
        viewModelScope.launch {
            if (isNetworkAvailable) {
                _uiState.update { it.copy(isLoading = true) }
                when (val response = weatherRepository.getSearchLocations(text)) {
                    is Resource.Success -> {
                        _uiState.update { it.copy(searchLocations = response.data) }
                    }

                    is Resource.Error -> {
                        _uiState.update { it.copy(errorMessage = response.message) }
                    }
                }
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isSearching = false,
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isSearching = false,
                        errorMessage = "Network unavailable"
                    )
                }
            }
        }
    }

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
            when (val response = weatherRepository.addWeather(locationUrl)) {
                is Resource.Success -> {
                    _isLocationAdded.emit(true)
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

data class AddLocationUiState(
    val searchText: String = "",
    val isSearching: Boolean = false,
    val searchLocations: List<SearchLocation> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)