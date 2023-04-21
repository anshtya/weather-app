package com.anshtya.weatherapp.presentation.screens.weather

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.anshtya.weatherapp.R
import com.anshtya.weatherapp.domain.model.Weather
import kotlin.math.roundToInt

@Composable
fun WeatherScreen(
    uiState: WeatherState,
    onManageLocationsClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onErrorShown: () -> Unit,
    modifier: Modifier = Modifier
) {
    val weatherLocations = uiState.weatherList

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = modifier.fillMaxSize()
    ) {
        WeatherDrawer(
            weatherLocations = weatherLocations,
            onSettingsClick = onSettingsClick,
            onManageLocationsClick = onManageLocationsClick,
        )
        uiState.errorMessage?.let {message ->
            Toast.makeText(LocalContext.current, message, Toast.LENGTH_SHORT).show()
            onErrorShown()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherDetails(
    weather: Weather,
    onMenuClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = onMenuClicked
                    ) {
                        Icon(Icons.Default.Menu, contentDescription = "Show Navigation Menu")
                    }
                },
                title = { Text(weather.name) }
            )
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(stringResource(R.string.temperature, weather.temp_c.roundToInt()))
                    Text(weather.condition.text.trim())

                    Spacer(modifier = Modifier.size(5.dp))

                    Text(weather.name)
                    Text(stringResource(R.string.feels_like, weather.feelslike_c.roundToInt()))
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Adaptive(150.dp),
                contentPadding = PaddingValues(10.dp),
                userScrollEnabled = false
            ) {
                item {
                    GridItem(
                        name = "UV",
                        description = "${weather.uv}"
                    )
                }
                item {
                    GridItem(
                        name = "Wind",
                        description = "${weather.wind_kph}, ${weather.wind_dir}"
                    )
                }
                item {
                    GridItem(
                        name = "Humidity",
                        description = "${weather.humidity}"
                    )
                }
            }
        }
    }
}

@Composable
fun GridItem(
    name: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(150.dp)
            .padding(5.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(5.dp)
                .fillMaxHeight()
        ) {
            Text(name)
            Text(description)
        }
    }
}