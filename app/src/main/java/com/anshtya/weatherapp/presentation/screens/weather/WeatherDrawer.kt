package com.anshtya.weatherapp.presentation.screens.weather

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.anshtya.weatherapp.domain.model.Weather
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherDrawer(
    weatherLocations: List<Weather>,
    onSettingsClick: () -> Unit,
    onManageLocationsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedWeatherLocationId by rememberSaveable { mutableStateOf("") }

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
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Weather Settings"
                        )
                    }

                    Spacer(Modifier.size(10.dp))

                    weatherLocations.forEach {
                        if (weatherLocations.isNotEmpty() && selectedWeatherLocationId == "") {
                            selectedWeatherLocationId = weatherLocations.first().id
                        }
                        NavigationDrawerItem(
                            label = { Text(it.name) },
                            selected = selectedWeatherLocationId == it.id,
                            onClick = {
                                scope.launch { drawerState.close() }
                                selectedWeatherLocationId = it.id
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
                    onMenuClicked = { scope.launch { drawerState.open() } }
                )
            }
        }
    )
}

