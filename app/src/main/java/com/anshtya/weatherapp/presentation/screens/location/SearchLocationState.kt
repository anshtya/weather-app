package com.anshtya.weatherapp.presentation.screens.location

import com.anshtya.weatherapp.domain.model.SearchLocation


data class SearchLocationState(
    val searchText: String = "",
    val searchLocations: List<SearchLocation>? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
