package com.anshtya.weatherapp.presentation.ui.screens.weather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.anshtya.weatherapp.core.common.Resource

@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel = viewModel()
) {
    val weather by viewModel.weather.collectAsStateWithLifecycle()
    val w = weather
    Column(
        modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {
        Button(onClick = {}) {
            Text("hi")
        }
        when (w) {
            is Resource.Success -> {
                Text(
                    text = w.data.condition.text,
//            modifier = Modifier.align(CenterHorizontally)
                )
            }
            else -> {}
        }

    }
}