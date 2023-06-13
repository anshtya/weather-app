package com.anshtya.weatherapp.presentation.screens.weather

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.anshtya.weatherapp.domain.model.WeatherWithPreferences
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherDrawer(
    userWeather: WeatherWithPreferences,
    isLoading: Boolean,
    errorMessage: String?,
    weatherId: String?,
    onSettingsClick: () -> Unit,
    onManageLocationsClick: () -> Unit,
    onErrorShown: () -> Unit,
    onUpdate: (UpdateOption) -> Unit,
    modifier: Modifier = Modifier
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedWeatherLocationId by rememberSaveable { mutableStateOf("") }
    var previousVisitedWeatherId by rememberSaveable { mutableStateOf("") }
    val weatherLocations = userWeather.weatherList

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

                    if (previousVisitedWeatherId != weatherId && weatherId != null) {
                        selectedWeatherLocationId = weatherId
                        previousVisitedWeatherId = weatherId
                    } else if (selectedWeatherLocationId == "" && weatherLocations.isNotEmpty()) {
                        selectedWeatherLocationId = weatherLocations.first().weatherLocation.id
                    }

                    weatherLocations.forEach {
                        NavigationDrawerItem(
                            label = { Text(it.weatherLocation.name) },
                            selected = false,
                            onClick = {
                                selectedWeatherLocationId = it.weatherLocation.id
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
                if (weatherLocations.find { it.weatherLocation.id == selectedWeatherLocationId } != null) {
                    WeatherDetails(
                        weather = weatherLocations.first { it.weatherLocation.id == selectedWeatherLocationId },
                        isLoading = isLoading,
                        errorMessage = errorMessage,
                        showCelsius = userWeather.showCelsius,
                        onErrorShown = onErrorShown,
                        onMenuClicked = { scope.launch { drawerState.open() } },
                        onUpdate = onUpdate
                    )
                } else {
                    selectedWeatherLocationId = ""
                }
            }
        }
    )
}

