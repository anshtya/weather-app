package com.anshtya.weatherapp.presentation.screens.weather

import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.anshtya.weatherapp.domain.model.Weather
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherDrawer(
    weatherLocations: List<Weather>,
    content: @Composable (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedWeatherLocationId by rememberSaveable { mutableStateOf("") }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet(modifier.requiredWidth(180.dp)) {
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
            }
        },
        content = {
            if (selectedWeatherLocationId != "") {
                content(selectedWeatherLocationId)
            }
        }
    )
}

