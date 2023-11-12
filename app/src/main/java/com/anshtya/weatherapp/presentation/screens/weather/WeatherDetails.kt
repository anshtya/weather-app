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
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
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
    val astro = weatherForecast[0].astro

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
                .fillMaxSize()
                .padding(paddingValues)
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
                    WeatherImage(
                        image = if (currentWeather.isDay == 1) {
                            currentWeather.weatherType.dayIconRes
                        } else {
                            currentWeather.weatherType.nightIconRes
                        },
                        modifier = Modifier.size(120.dp)
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(2.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            stringResource(
                                R.string.temperature,
                                if (showCelsius) {
                                    currentWeather.tempC.roundToInt()
                                } else {
                                    currentWeather.tempF.roundToInt()
                                }
                            )
                        )

                        Text(currentWeather.weatherType.weatherDescription.trim())
                        Text(weatherLocation.name)

                        Text(
                            stringResource(
                                R.string.feels_like,
                                if (showCelsius) {
                                    currentWeather.feelsLikeC.roundToInt()
                                } else {
                                    currentWeather.feelsLikeF.roundToInt()
                                }
                            )
                        )
                    }
                }

                Forecast(
                    forecastList = weatherForecast,
                    showCelsius = showCelsius
                )

                Row(Modifier.fillMaxWidth()) {
                    WeatherGridItem(
                        name = stringResource(R.string.uv),
                        description = currentWeather.uv.toString(),
                        animationResource = R.raw.animation_sun,
                        modifier = Modifier.weight(1f)
                    )
                    WeatherGridItem(
                        name = stringResource(R.string.wind),
                        description = if (showCelsius) {
                            stringResource(
                                R.string.wind_kph, currentWeather.windKph, currentWeather.windDir
                            )
                        } else {
                            stringResource(
                                R.string.wind_mph, currentWeather.windMph, currentWeather.windDir
                            )
                        },
                        animationResource = R.raw.animation_wind,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(Modifier.fillMaxWidth()) {
                    WeatherGridItem(
                        name = stringResource(R.string.humidity),
                        description = "${currentWeather.humidity}%",
                        animationResource = R.raw.animation_humidity,
                        modifier = Modifier.weight(1f)
                    )

                    AstroGridItem(
                        sunrise = astro.sunrise,
                        sunset = astro.sunset,
                        animationResource = R.raw.animation_sunset,
                        modifier = Modifier.weight(1f)
                    )
                }
                Column(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = if (showCelsius) {
                            stringResource(
                                R.string.visibility_km, currentWeather.visKm.roundToInt()
                            )
                        } else {
                            stringResource(
                                R.string.visibility_mi, currentWeather.visMiles.roundToInt()
                            )
                        }
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
            contentDescription = contentDescription
        )
    }
}

@Composable
fun WeatherGridItem(
    name: String,
    description: String,
    animationResource: Int,
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
            Animation(
                animationResource = animationResource,
                modifier = Modifier.size(50.dp)
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium
            )
            Text(text = description)
        }
    }
}

@Composable
fun AstroGridItem(
    sunrise: String,
    sunset: String,
    animationResource: Int,
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.sunrise),
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = sunrise,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.sunset),
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = sunset,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Spacer(Modifier.height(5.dp))
            Animation(
                animationResource = animationResource,
                modifier = Modifier.size(60.dp)
            )
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
            Text(
                text = dayName,
                style = MaterialTheme.typography.titleMedium
            )

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
            if (forecast.hour.isEmpty()) {
                Text(
                    text = stringResource(R.string.no_forecasts),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
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

        WeatherImage(
            image = if (isDay == 1) {
                weatherType.dayIconRes
            } else {
                weatherType.nightIconRes
            },
            modifier = Modifier.size(25.dp)
        )

        Text(
            text = if (showCelsius) {
                stringResource(R.string.temperature, tempC.roundToInt())
            } else {
                stringResource(R.string.temperature, tempF.roundToInt())
            }
        )

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

@Composable
fun Animation(
    animationResource: Int,
    modifier: Modifier = Modifier
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(animationResource))
    LottieAnimation(
        composition = composition,
        modifier = modifier
    )
}