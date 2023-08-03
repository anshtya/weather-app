package com.anshtya.weatherapp.presentation.screens.weather

import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.anshtya.weatherapp.R
import com.anshtya.weatherapp.domain.model.Weather
import com.anshtya.weatherapp.domain.model.WeatherForecast
import com.anshtya.weatherapp.domain.model.WeatherType
import com.anshtya.weatherapp.presentation.components.WeatherTopAppBar
import com.anshtya.weatherapp.util.getDayNameFromEpoch
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
    val weatherLocation = weather.weatherLocation
    val currentWeather = weather.currentWeather
    val weatherForecast = weather.weatherForecast
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            WeatherTopAppBar(
                title = weatherLocation.name,
                onMenuClick = onMenuClick,
                onUpdateClick = onUpdateClick
            )
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Column(
                modifier = modifier
                    .padding(5.dp)
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
                            modifier = Modifier.size(150.dp)
                        )
                    } else {
                        WeatherImage(
                            currentWeather.weatherType.nightIconRes,
                            modifier = Modifier.size(150.dp)
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(2.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (showCelsius) {
                            Text(
                                stringResource(
                                    R.string.temperature,
                                    currentWeather.tempC.roundToInt()
                                )
                            )
                        } else {
                            Text(
                                stringResource(
                                    R.string.temperature,
                                    currentWeather.tempF.roundToInt()
                                )
                            )
                        }

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

                Forecast(
                    forecastList = weatherForecast,
                    showCelsius = showCelsius
                )

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
}

@Composable
fun WeatherImage(
    @DrawableRes image: Int,
    modifier: Modifier = Modifier,
    contentDescription: String? = null

) {
    Surface(
        modifier = modifier
    ) {
        Image(
            painterResource(image),
            contentDescription = contentDescription,
        )
    }
}

@Composable
fun WeatherGridItem(
    name: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier
            .height(150.dp)
            .padding(5.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxHeight()
        ) {
            Text(name)
            Text(description)
        }
    }
}

@Composable
fun Forecast(
    forecastList: List<WeatherForecast>,
    showCelsius: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(5.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Column {
            forecastList.forEach {
                ForecastItem(
                    forecast = it,
                    showCelsius = showCelsius
                )
            }
        }
    }
}

@Composable
fun ForecastItem(
    forecast: WeatherForecast,
    showCelsius: Boolean,
    modifier: Modifier = Modifier
) {
    val dayName = remember(forecast) {
        getDayNameFromEpoch(forecast.dateEpoch.toLong())
    }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(10.dp)
        ) {
            Text(dayName)

            Row(
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                if (showCelsius) {
                    Text(
                        stringResource(R.string.temperature, forecast.day.maxTempC.roundToInt())
                    )
                    Spacer(Modifier.width(5.dp))
                    Text(
                        stringResource(R.string.temperature, forecast.day.minTempC.roundToInt())
                    )
                } else {
                    Text(
                        stringResource(R.string.temperature, forecast.day.maxTempF.roundToInt())
                    )
                    Spacer(Modifier.width(5.dp))
                    Text(
                        stringResource(R.string.temperature, forecast.day.minTempF.roundToInt())
                    )
                }
            }
        }

        if (expanded) {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 5.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(
                    items = forecast.hour
                ) {
                    HourForecastItem(
                        showCelsius = showCelsius,
                        chanceOfRain = it.chanceOfRain,
                        chanceOfSnow = it.chanceOfSnow,
                        weatherType = it.weatherType,
                        isDay = it.isDay,
                        tempC = it.tempC,
                        tempF = it.tempF,
                        time = it.time.split(" ")[1]
                    )
                }
            }
        }
    }
}

@Composable
fun HourForecastItem(
    showCelsius: Boolean,
    chanceOfRain: Int,
    chanceOfSnow: Int,
    weatherType: WeatherType,
    isDay: Int,
    tempC: Double,
    tempF: Double,
    time: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp),
        modifier = modifier
            .width(80.dp)
            .padding(5.dp)
    ) {
        Text(time)

        if (isDay == 1) {
            WeatherImage(
                weatherType.dayIconRes,
                modifier = Modifier.size(25.dp)
            )
        } else {
            WeatherImage(
                weatherType.nightIconRes,
                modifier = Modifier.size(25.dp)
            )
        }

        if (showCelsius) {
            Text(
                stringResource(R.string.temperature, tempC.roundToInt())
            )
        } else {
            Text(
                stringResource(R.string.temperature, tempF.roundToInt())
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_water_drop),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(20.dp)
            )
            Text("$chanceOfRain%")
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_snow),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(20.dp)
            )
            Text("$chanceOfSnow%")
        }
    }
}