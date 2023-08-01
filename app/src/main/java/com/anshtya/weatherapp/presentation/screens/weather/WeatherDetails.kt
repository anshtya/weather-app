package com.anshtya.weatherapp.presentation.screens.weather

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.anshtya.weatherapp.R
import com.anshtya.weatherapp.domain.model.Weather
import com.anshtya.weatherapp.presentation.components.WeatherGridItem
import com.anshtya.weatherapp.presentation.components.WeatherImage
import com.anshtya.weatherapp.presentation.components.WeatherTopAppBar
import kotlin.math.roundToInt

@Composable
fun WeatherDetails(
    weather: Weather,
    isLoading: Boolean,
    errorMessage: String?,
    showCelsius: Boolean,
    onErrorShown: () -> Unit,
    onMenuClick: () -> Unit,
    onUpdateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val weatherLocation = remember(weather) { weather.weatherLocation }
    val currentWeather = remember(weather) { weather.currentWeather }
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            WeatherTopAppBar(
                title = weatherLocation.name,
                onMenuClick = onMenuClick,
                onUpdateClick = onUpdateClick
            )
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            if (isLoading) {
                CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (currentWeather.isDay == 1) {
                    WeatherImage(
                        currentWeather.weatherType.dayIconRes,
                        contentDescription = null
                    )
                } else {
                    WeatherImage(
                        currentWeather.weatherType.nightIconRes,
                        contentDescription = null
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        if (showCelsius) {
                            stringResource(
                                R.string.temperature,
                                currentWeather.tempC.roundToInt()
                            )
                        } else {
                            stringResource(
                                R.string.temperature,
                                currentWeather.tempF.roundToInt()
                            )
                        }
                    )

                    Text(currentWeather.weatherType.weatherDescription.trim())
                    Text(weatherLocation.name)

                    if (showCelsius) {
                        Text(
                            stringResource(
                                R.string.feels_like,
                                currentWeather.feelsLikeC.roundToInt()
                            )
                        )
                    } else {
                        Text(
                            stringResource(
                                R.string.feels_like,
                                currentWeather.feelsLikeF.roundToInt()
                            )
                        )
                    }
                }
            }

            Row(Modifier.fillMaxWidth()) {
                WeatherGridItem(
                    name = "UV",
                    description = "${currentWeather.uv}",
                    modifier = Modifier.weight(1f)
                )

                if (showCelsius) {
                    WeatherGridItem(
                        name = "Wind",
                        description = stringResource(
                            R.string.wind_kph,
                            currentWeather.windKph,
                            currentWeather.windDir
                        ),
                        modifier = Modifier.weight(1f)
                    )
                } else {
                    WeatherGridItem(
                        name = "Wind",
                        stringResource(
                            R.string.wind_mph,
                            currentWeather.windMph,
                            currentWeather.windDir
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Row(Modifier.fillMaxWidth()) {
                WeatherGridItem(
                    name = "Humidity",
                    description = "${currentWeather.humidity}",
                    modifier = Modifier.weight(1f)
                )
            }

            errorMessage?.let { message ->
                Toast.makeText(LocalContext.current, message, Toast.LENGTH_SHORT).show()
                onErrorShown()
            }
        }
    }
}