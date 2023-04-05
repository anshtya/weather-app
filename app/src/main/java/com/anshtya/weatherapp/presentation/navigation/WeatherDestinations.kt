package com.anshtya.weatherapp.presentation.navigation

interface WeatherDestination {
    val route: String
}

object SelectLocation : WeatherDestination {
    override val route = "select_location"
}

object SavedLocation : WeatherDestination {
    override val route = "saved_location"
}

object Weather : WeatherDestination {
    override val route = "weather"
    const val locationUrl = "location-url"
    val routeWithArgs = "${route}/{${locationUrl}}"
}
