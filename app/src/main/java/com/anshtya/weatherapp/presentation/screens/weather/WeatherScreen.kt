package com.anshtya.weatherapp.presentation.screens.weather

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.anshtya.weatherapp.domain.model.WeatherWithPreferences

@Composable
fun WeatherScreen(
    userWeather: WeatherWithPreferences,
    isLoading: Boolean,
    errorMessage: String?,
    weatherId: String?,
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
        userWeather = userWeather,
        isLoading = isLoading,
        errorMessage = errorMessage,
        weatherId = weatherId,
        onSettingsClick = onSettingsClick,
        onManageLocationsClick = onManageLocationsClick,
        onErrorShown = onErrorShown,
        onUpdate = onUpdate
    )
}