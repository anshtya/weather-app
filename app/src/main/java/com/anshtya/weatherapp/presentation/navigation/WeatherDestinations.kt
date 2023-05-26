package com.anshtya.weatherapp.presentation.navigation

sealed class Destinations(val route: String) {
    object AddLocation: Destinations("AddLocation")
    object ManageLocation: Destinations("ManageLocation")
    object Weather: Destinations("Weather")
    object Settings: Destinations("Settings")
}