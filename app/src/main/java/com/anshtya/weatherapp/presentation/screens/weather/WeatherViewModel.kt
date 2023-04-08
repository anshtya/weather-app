package com.anshtya.weatherapp.presentation.screens.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anshtya.weatherapp.domain.useCase.GetSavedLocationUseCase
import com.anshtya.weatherapp.domain.useCase.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    getSavedLocationUseCase: GetSavedLocationUseCase,
    getWeatherUseCase: GetWeatherUseCase,
): ViewModel() {
    val savedWeatherLocation = getSavedLocationUseCase.getSavedLocations().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

//    private val _weather = MutableStateFlow<Resource<CurrentWeather>>(Resource.Loading)
//    val weather = _weather.asStateFlow()
//
//    init {
//        viewModelScope.launch {
//            getWeatherUseCase().collect {
//                _weather.value = it
//            }
//        }
//    }
}