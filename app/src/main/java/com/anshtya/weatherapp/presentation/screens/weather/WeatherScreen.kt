package com.anshtya.weatherapp.presentation.screens.weather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.anshtya.weatherapp.core.common.Resource

@Composable
fun WeatherScreen(
    locationUrl: String?,
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel = hiltViewModel()
) {
//    val weather by viewModel.weather.collectAsStateWithLifecycle()
//    val w = weather
    Column(
        modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {
        locationUrl?.let { locationUrl ->
            Button(onClick = {}) {
                Text(locationUrl)
            }
        }
//        when (w) {
//            is Resource.Success -> {
//                Text(
//                    text = w.data.last_updated,
////            modifier = Modifier.align(CenterHorizontally)
//                )
//            }
//            else -> {}
//        }

    }
}