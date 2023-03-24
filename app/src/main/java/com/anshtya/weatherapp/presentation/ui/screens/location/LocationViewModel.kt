package com.anshtya.weatherapp.presentation.ui.screens.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anshtya.weatherapp.core.common.Resource
import com.anshtya.weatherapp.domain.useCase.GetSearchLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val getSearchLocationUseCase: GetSearchLocationUseCase
) : ViewModel() {

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
            when (val response = getSearchLocationUseCase(text)) {
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
}