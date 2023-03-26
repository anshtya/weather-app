package com.anshtya.weatherapp.presentation.screens.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anshtya.weatherapp.core.common.Resource
import com.anshtya.weatherapp.domain.useCase.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase
): ViewModel() {
    val weather = getWeatherUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = Resource.Error(null)
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