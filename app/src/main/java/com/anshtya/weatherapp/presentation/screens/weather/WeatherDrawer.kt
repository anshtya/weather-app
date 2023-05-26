package com.anshtya.weatherapp.presentation.screens.weather

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherDrawer(
    uiState: WeatherUiState,
    onChangeSelectedLocation: (String) -> Unit,
    onSettingsClick: () -> Unit,
    onManageLocationsClick: () -> Unit,
    onErrorShown: () -> Unit,
    onUpdate: (UpdateOption) -> Unit,
    modifier: Modifier = Modifier
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val selectedWeatherLocationId = uiState.userWeather.selectedLocationId
    val weatherLocations = uiState.userWeather.weatherList

    BackHandler(enabled = drawerState.isOpen) { scope.launch { drawerState.close() } }
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            ModalDrawerSheet(modifier.requiredWidth(250.dp)) {
                Column(Modifier.padding(horizontal = 15.dp, vertical = 10.dp)) {
                    IconButton(
                        onClick = { onSettingsClick() },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Settings,
                            contentDescription = "Weather Settings"
                        )
                    }

                    Spacer(Modifier.size(10.dp))

                    weatherLocations.forEach {
                        if (weatherLocations.isNotEmpty() && selectedWeatherLocationId == "") {
                            onChangeSelectedLocation(weatherLocations.first().id)
                        }
                        NavigationDrawerItem(
                            label = { Text(it.name) },
                            selected = selectedWeatherLocationId == it.id,
                            onClick = {
                                onChangeSelectedLocation(it.id)
                                scope.launch { drawerState.close() }
                            }
                        )
                    }

                    Spacer(Modifier.size(15.dp))

                    Button(
                        onClick = { onManageLocationsClick() },
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth()
                    ) {
                        Text("Manage Locations")
                    }
                }
            }
        },
        content = {
            if (selectedWeatherLocationId != "") {
                WeatherDetails(
                    weather = weatherLocations.first { it.id == selectedWeatherLocationId },
                    isLoading = uiState.isLoading,
                    errorMessage = uiState.errorMessage,
                    showCelsius = uiState.userWeather.showCelsius,
                    onErrorShown = onErrorShown,
                    onMenuClicked = { scope.launch { drawerState.open() } },
                    onUpdate = onUpdate
                )
            }
        }
    )
}

