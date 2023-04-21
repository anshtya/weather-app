package com.anshtya.weatherapp.presentation.navigation

sealed class Destinations(val route: String) {
    object SelectLocation: Destinations("SelectLocation")
    object ManageLocation: Destinations("SavedLocation")
    object Weather: Destinations("Weather")
}