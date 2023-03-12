package com.anshtya.weatherapp.presentation.ui.screens.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anshtya.weatherapp.core.Resource
import com.anshtya.weatherapp.domain.model.SearchLocationResponse
import com.anshtya.weatherapp.domain.useCase.GetSearchLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val getSearchLocationUseCase: GetSearchLocationUseCase
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _locations = MutableStateFlow<Resource<SearchLocationResponse>>(Resource.Loading)

    @OptIn(FlowPreview::class)
    val locations = searchText
        .debounce(500L)
        .onEach { _isSearching.update { true } }
        .combine(_locations) { text, location ->
            if (text.isBlank()) location
            else getSearchLocationUseCase(text)
        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = _locations.value
        )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }
}