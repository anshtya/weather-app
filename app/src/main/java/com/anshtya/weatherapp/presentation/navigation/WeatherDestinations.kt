package com.anshtya.weatherapp.presentation.navigation

sealed class Destinations(val route: String) {
    object SelectLocation: Destinations("SelectLocation")
    object SavedLocation: Destinations("SavedLocation")
    object Weather: Destinations("Weather")
}