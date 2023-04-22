package com.anshtya.weatherapp.presentation.screens.weather

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
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
    val swipeState = rememberSwipeRefreshState(isRefreshing = isLoading)
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
        SwipeRefresh(
            state = swipeState,
            onRefresh = onRefresh,
            modifier = Modifier.padding(paddingValues)
        ) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    Text(stringResource(R.string.temperature, weather.temp_c.roundToInt()))
                    Text(weather.condition.text.trim())
                }

                item {Spacer(modifier = Modifier.size(5.dp))}

                item {
                    Text(weather.name)
                    Text(stringResource(R.string.feels_like, weather.feelslike_c.roundToInt()))
                }

                item{Spacer(modifier = Modifier.size(10.dp))}
            }
            errorMessage?.let { message ->
                Toast.makeText(LocalContext.current, message, Toast.LENGTH_SHORT).show()
                onErrorShown()
            }
        }
    }
}

//        Box(
//            modifier = Modifier
//                .padding(paddingValues)
//                .fillMaxSize()
//                .
//        ) {
//            Column(
//                horizontalAlignment = Alignment.CenterHorizontally,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text(stringResource(R.string.temperature, weather.temp_c.roundToInt()))
//                Text(weather.condition.text.trim())
//
//                Spacer(modifier = Modifier.size(5.dp))
//
//                Text(weather.name)
//                Text(stringResource(R.string.feels_like, weather.feelslike_c.roundToInt()))
//
//                Spacer(modifier = Modifier.size(10.dp))
//
//                LazyVerticalGrid(
//                    columns = GridCells.Adaptive(150.dp),
//                    contentPadding = PaddingValues(10.dp),
//                    userScrollEnabled = false
//                ) {
//                    item {
//                        GridItem(
//                            name = "UV",
//                            description = "${weather.uv}"
//                        )
//                    }
//                    item {
//                        GridItem(
//                            name = "Wind",
//                            description = "${weather.wind_kph}, ${weather.wind_dir}"
//                        )
//                    }
//                    item {
//                        GridItem(
//                            name = "Humidity",
//                            description = "${weather.humidity}"
//                        )
//                    }
//                }
//            }
//
//

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