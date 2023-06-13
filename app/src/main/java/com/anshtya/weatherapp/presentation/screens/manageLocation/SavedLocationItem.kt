package com.anshtya.weatherapp.presentation.screens.manageLocation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anshtya.weatherapp.R
import com.anshtya.weatherapp.domain.model.Weather
import com.anshtya.weatherapp.presentation.ui.theme.Typography
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SavedLocationItem(
    savedLocation: Weather,
    showCelsius: Boolean,
    onCheck: (String) -> Unit,
    onLongClick: () -> Unit,
    onLocationClick: (String) -> Unit,
    isCheckEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    val weatherLocation = savedLocation.weatherLocation
    val currentWeather = savedLocation.currentWeather
    val weatherForecast = savedLocation.weatherForecast.day
    var checkedState by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 10.dp)
            .combinedClickable(
                onClick = {
                    if (isCheckEnabled) {
                        checkedState = !checkedState
                        onCheck(weatherLocation.id)
                    } else {
                        onLocationClick(weatherLocation.id)
                    }
                },
                onLongClick = {
                    onLongClick()
                    checkedState = true
                    onCheck(weatherLocation.id)
                }
            ),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                if (isCheckEnabled) {
                    Checkbox(
                        checked = checkedState,
                        onCheckedChange = {
                            checkedState = it
                            onCheck(weatherLocation.id)
                        }
                    )
                }
                Column {
                    Text(
                        savedLocation.weatherLocation.name,
                        style = Typography.titleMedium,
                        fontSize = 18.sp
                    )
                    Text(
                        "${weatherLocation.region}, ${weatherLocation.country}",
                        style = Typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }
            Column {
                Row {
                    if (currentWeather.isDay == 1) {
                        Image(
                            painterResource(currentWeather.weatherType.dayIconRes),
                            contentDescription = null
                        )
                    } else {
                        Image(
                            painterResource(currentWeather.weatherType.nightIconRes),
                            contentDescription = null
                        )
                    }
                    if (showCelsius) {
                        Text(
                            stringResource(
                                R.string.temperature,
                                currentWeather.tempC.roundToInt()
                            ),
                            style = Typography.titleMedium,
                            fontSize = 18.sp
                        )
                    } else {
                        Text(
                            stringResource(
                                R.string.temperature,
                                currentWeather.tempF.roundToInt()
                            ),
                            style = Typography.titleMedium,
                            fontSize = 18.sp
                        )
                    }
                }
                if (showCelsius) {
                    Text(
                        stringResource(
                            R.string.max_min_temp,
                            weatherForecast.maxTempC.roundToInt(),
                            weatherForecast.minTempC.roundToInt()
                        ),
                        style = Typography.bodyMedium,
                        color = Color.Gray
                    )
                } else {
                    Text(
                        stringResource(
                            R.string.max_min_temp,
                            weatherForecast.maxTempF.roundToInt(),
                            weatherForecast.minTempF.roundToInt()
                        ),
                        style = Typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}