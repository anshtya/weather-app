package com.anshtya.weatherapp.domain.connectivity

sealed class NetworkStatus {
    object Available: NetworkStatus()
    object Unavailable: NetworkStatus()
}