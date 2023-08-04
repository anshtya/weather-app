package com.anshtya.weatherapp.presentation.screens.weather

import androidx.activity.compose.BackHandler
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.anshtya.weatherapp.presentation.components.WeatherDrawer
import kotlinx.coroutines.launch

@Composable
fun WeatherScreen(
    uiState: WeatherUiState,
    weatherId: String?,
    onManageLocationsClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onErrorShown: () -> Unit,
    onUpdateClick: () -> Unit,
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed)
) {
    val scope = rememberCoroutineScope()
    val weatherLocations = uiState.userWeather.weather
    var selectedWeatherLocationId by rememberSaveable { mutableStateOf("") }
    var weatherDisplayed by rememberSaveable { mutableStateOf(false) }

    BackHandler(drawerState.isOpen) {
        scope.launch {
            drawerState.close()
        }
    }

    WeatherDrawer(
        weatherLocations = weatherLocations,
        drawerState = drawerState,
        onDrawerClose = {
            scope.launch {
                drawerState.close()
            }
        },
        onChangeSelectedId = {
            selectedWeatherLocationId = it
        },
        onSettingsClick = onSettingsClick,
        onManageLocationsClick = onManageLocationsClick,
    ) {
        weatherLocations.takeIf { it.isNotEmpty() }?.let { locations ->

            weatherId?.let {
                if (selectedWeatherLocationId != weatherId && !weatherDisplayed) {
                    selectedWeatherLocationId = it
                    weatherDisplayed = true
                }
            }

            locations.find { it.weatherLocation.id == selectedWeatherLocationId }?.let {

                WeatherDetails(
                    weather = it,
                    isLoading = uiState.isLoading,
                    errorMessage = uiState.errorMessage,
                    showCelsius = uiState.userWeather.showCelsius,
                    onErrorShown = onErrorShown,
                    onMenuClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    },
                    onUpdateClick = onUpdateClick
                )

            } ?: WeatherDetails(
                weather = locations.first(),
                isLoading = uiState.isLoading,
                errorMessage = uiState.errorMessage,
                showCelsius = uiState.userWeather.showCelsius,
                onErrorShown = onErrorShown,
                onMenuClick = {
                    scope.launch {
                        drawerState.open()
                    }
                },
                onUpdateClick = onUpdateClick
            )
        }
    }
}