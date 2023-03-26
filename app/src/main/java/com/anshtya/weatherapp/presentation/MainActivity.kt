package com.anshtya.weatherapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.anshtya.weatherapp.presentation.navigation.Destinations
import com.anshtya.weatherapp.presentation.screens.location.SavedLocationScreen
import com.anshtya.weatherapp.presentation.screens.location.SelectLocationScreen
import com.anshtya.weatherapp.presentation.screens.weather.WeatherScreen
import com.anshtya.weatherapp.presentation.ui.theme.WeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherApp()
        }
    }
}

@Composable
fun WeatherApp() {
    WeatherAppTheme {
        WeatherScreen()
//        val navController = rememberNavController()
//        NavHost(
//            navController = navController,
//            startDestination = Destinations.selectLocationScreen
//        ) {
//            composable(Destinations.weatherScreen) {
//                WeatherScreen()
//            }
//            composable(Destinations.selectLocationScreen) {
//                SelectLocationScreen()
//            }
//            composable(Destinations.savedLocationsScreen) {
//                SavedLocationScreen()
//            }
//        }
    }
}