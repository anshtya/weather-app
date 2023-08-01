package com.anshtya.weatherapp.util.network

sealed class NetworkStatus {
    object Available: NetworkStatus()
    object Unavailable: NetworkStatus()
}