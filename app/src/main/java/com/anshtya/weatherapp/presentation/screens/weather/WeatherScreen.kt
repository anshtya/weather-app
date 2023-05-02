package com.anshtya.weatherapp.presentation.screens.weather

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
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
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = modifier.fillMaxSize()
    ) {
        WeatherDrawer(
            uiState = uiState,
            onSettingsClick = onSettingsClick,
            onManageLocationsClick = onManageLocationsClick,
            onErrorShown = onErrorShown,
            onRefresh = onRefresh
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherDetails(
    weather: Weather,
    isLoading: Boolean,
    errorMessage: String?,
    onErrorShown: () -> Unit,
    onMenuClicked: () -> Unit,
    onRefresh: () -> Unit,
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
                actions = {
                    IconButton(
                        onClick = onRefresh
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh Weather")
                    }
                },
                title = { Text(weather.name) }
            )
        },
        modifier = modifier
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            if (isLoading) {
                CircularProgressIndicator(Modifier.align(Alignment.TopCenter))
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(10.dp),
                userScrollEnabled = true
            ) {
                item(span = { GridItemSpan(2) }) {
                    Box {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(stringResource(R.string.temperature, weather.temp_c.roundToInt()))
                            Text(weather.condition.text.trim())

                            Spacer(modifier = Modifier.size(5.dp))

                            Text(weather.name)
                            Text(
                                stringResource(
                                    R.string.feels_like,
                                    weather.feelslike_c.roundToInt()
                                )
                            )

                            Spacer(modifier = Modifier.size(10.dp))
                        }
                    }
                }
                item {
                    WeatherGridItem(
                        name = "UV",
                        description = "${weather.uv}"
                    )
                }
                item {
                    WeatherGridItem(
                        name = "Wind",
                        description = "${weather.wind_kph}, ${weather.wind_dir}"
                    )
                }
                item {
                    WeatherGridItem(
                        name = "Humidity",
                        description = "${weather.humidity}"
                    )
                }
            }
            errorMessage?.let { message ->
                Toast.makeText(LocalContext.current, message, Toast.LENGTH_SHORT).show()
                onErrorShown()
            }
        }
    }
}