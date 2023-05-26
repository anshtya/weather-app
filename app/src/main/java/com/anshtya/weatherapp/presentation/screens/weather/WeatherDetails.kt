package com.anshtya.weatherapp.presentation.screens.weather

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.anshtya.weatherapp.R
import com.anshtya.weatherapp.core.model.Weather
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherDetails(
    weather: Weather,
    isLoading: Boolean,
    errorMessage: String?,
    showCelsius: Boolean,
    onErrorShown: () -> Unit,
    onMenuClicked: () -> Unit,
    onUpdate: (UpdateOption) -> Unit,
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
                        onClick = { onUpdate(UpdateOption.CLICK) }
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
                            Text(
                                if (showCelsius) {
                                    stringResource(
                                        R.string.temperature,
                                        weather.currentWeather.tempC.roundToInt()
                                    )
                                } else {
                                    stringResource(
                                        R.string.temperature,
                                        weather.currentWeather.tempF.roundToInt()
                                    )
                                }
                            )
                            Text(weather.currentWeather.condition.text.trim())

                            Spacer(modifier = Modifier.size(5.dp))

                            Text(weather.name)
                            Text(
                                if (showCelsius) {
                                    stringResource(
                                        R.string.feels_like,
                                        weather.currentWeather.feelsLikeC.roundToInt()
                                    )
                                } else {
                                    stringResource(
                                        R.string.feels_like,
                                        weather.currentWeather.feelsLikeF.roundToInt()
                                    )
                                }
                            )

                            Spacer(modifier = Modifier.size(10.dp))
                        }
                    }
                }
                item {
                    WeatherGridItem(
                        name = "UV",
                        description = "${weather.currentWeather.uv}"
                    )
                }
                item {
                    WeatherGridItem(
                        name = "Wind",
                        description = if (showCelsius) {
                            stringResource(
                                R.string.wind_kph,
                                weather.currentWeather.windKph,
                                weather.currentWeather.windDir
                            )
                        } else {
                            stringResource(
                                R.string.wind_mph,
                                weather.currentWeather.windMph,
                                weather.currentWeather.windDir
                            )
                        }
                    )
                }
                item {
                    WeatherGridItem(
                        name = "Humidity",
                        description = "${weather.currentWeather.humidity}"
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