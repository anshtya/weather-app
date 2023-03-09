package com.anshtya.weatherapp.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import com.anshtya.weatherapp.ui.selectLocation.SelectLocationScreen

@Composable
fun WeatherApp(){
    val locationExist by rememberSaveable { mutableStateOf(false) }

    if(!locationExist) {
        SelectLocationScreen()
    }
}