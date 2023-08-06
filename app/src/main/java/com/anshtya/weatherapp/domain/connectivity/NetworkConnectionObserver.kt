package com.anshtya.weatherapp.domain.connectivity

import kotlinx.coroutines.flow.Flow

interface NetworkConnectionObserver {
    val networkStatus: Flow<NetworkStatus>
}