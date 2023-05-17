package com.anshtya.weatherapp.presentation.screens.weather

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

@Composable
fun WeatherScreen(
    uiState: WeatherUiState,
    onManageLocationsClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onErrorShown: () -> Unit,
    onUpdate: (UpdateOption) -> Unit,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {

    DisposableEffect(lifecycleOwner) {

        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                onUpdate(UpdateOption.APPSTART)
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    WeatherDrawer(
        uiState = uiState,
        onSettingsClick = onSettingsClick,
        onManageLocationsClick = onManageLocationsClick,
        onErrorShown = onErrorShown,
        onUpdate = onUpdate
    )
}