package com.anshtya.weatherapp.presentation.screens.addLocation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anshtya.weatherapp.core.model.Result
import com.anshtya.weatherapp.domain.model.SearchLocation
import com.anshtya.weatherapp.domain.useCase.AddLocationUseCase
import com.anshtya.weatherapp.domain.useCase.GetSavedLocationUseCase
import com.anshtya.weatherapp.domain.useCase.GetSearchResultUseCase
import com.anshtya.weatherapp.presentation.connectionTracker.CheckConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddLocationViewModel @Inject constructor(
    private val getSearchResultUseCase: GetSearchResultUseCase,
    private val addLocationUseCase: AddLocationUseCase,
    getSavedLocationUseCase: GetSavedLocationUseCase,
    private val checkConnection: CheckConnection
) : ViewModel() {

    val isTableNotEmpty = getSavedLocationUseCase.checkIfTableEmpty().shareIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        replay = 1
    )

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
                executeSearch(text)
            } else {
                _uiState.update { it.copy(errorMessage = "Network unavailable") }
            }
        }
    }

    private fun executeSearch(text: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when (val response = getSearchResultUseCase(text)) {
                is Result.Success -> {
                    _uiState.update { it.copy(searchLocations = response.data.list) }
                }
                is Result.Error -> {
                    _uiState.update { it.copy(errorMessage = response.message) }
                }
            }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun onLocationClick(locationUrl: String) = viewModelScope.launch {
        addLocationUseCase(locationUrl)
    }

    fun errorShown() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}

data class SearchLocationUiState(
    val searchText: String = "",
    val searchLocations: List<SearchLocation>? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)