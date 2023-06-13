package com.anshtya.weatherapp.presentation.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Destinations(val route: String) {
    object AddLocation : Destinations("AddLocation")
    object ManageLocation : Destinations("ManageLocation")
    object Weather : Destinations("Weather") {
        const val argumentType = "location_id"
        val routeWithArg = "${route}?id={${argumentType}}"
        val argument = listOf(navArgument(argumentType) {
            type = NavType.StringType
            nullable = true
        })

        fun passId(id: String): String {
            return "${route}?id=$id"
        }
    }

    object Settings : Destinations("Settings")
}