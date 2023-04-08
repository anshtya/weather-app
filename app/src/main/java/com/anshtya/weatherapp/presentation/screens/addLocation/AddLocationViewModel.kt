package com.anshtya.weatherapp.presentation.screens.addLocation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anshtya.weatherapp.core.common.Resource
import com.anshtya.weatherapp.domain.model.SearchLocation
import com.anshtya.weatherapp.domain.useCase.GetSavedLocationUseCase
import com.anshtya.weatherapp.domain.useCase.GetSearchResultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddLocationViewModel @Inject constructor(
    private val getSearchResultUseCase: GetSearchResultUseCase,
    getSavedLocationUseCase: GetSavedLocationUseCase
) : ViewModel() {

    val isTableEmpty = getSavedLocationUseCase.checkIfTableEmpty().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = false
    )

    private val _uiState = MutableStateFlow(SearchLocationState())
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
            _uiState.update { it.copy(isLoading = true) }
            when (val response = getSearchResultUseCase.getLocations(text)) {
                is Resource.Success -> {
                    _uiState.update { it.copy(searchLocations = response.data.list) }
                }
                is Resource.Error -> {
                    _uiState.update { it.copy(errorMessage = response.message) }
                }
            }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun onLocationClick(locationUrl: String) = viewModelScope.launch {
        getSearchResultUseCase.onLocationClick(locationUrl)
    }
}

data class SearchLocationState(
    val searchText: String = "",
    val searchLocations: List<SearchLocation>? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)