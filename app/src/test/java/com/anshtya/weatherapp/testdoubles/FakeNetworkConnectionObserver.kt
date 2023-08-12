package com.anshtya.weatherapp.testdoubles

import com.anshtya.weatherapp.domain.connectivity.NetworkConnectionObserver
import com.anshtya.weatherapp.domain.connectivity.NetworkStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeNetworkConnectionObserver: NetworkConnectionObserver {
    private val _networkStatus = MutableStateFlow<NetworkStatus>(NetworkStatus.Available)
    override val networkStatus: Flow<NetworkStatus> = _networkStatus.asStateFlow()

    fun unavailableNetwork() {
        _networkStatus.tryEmit(NetworkStatus.Unavailable)
    }
}