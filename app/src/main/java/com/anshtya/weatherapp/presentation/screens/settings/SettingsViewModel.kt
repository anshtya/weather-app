package com.anshtya.weatherapp.presentation.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anshtya.weatherapp.domain.repository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    val uiState = userDataRepository.userData
        .map{
            SettingsUiState(
                showCelsius = it.showCelsius
            )
        }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = SettingsUiState()
    )

    fun useCelsius(useCelsius: Boolean) {
        viewModelScope.launch {
            userDataRepository.setWeatherUnit(useCelsius)
        }
    }
}

data class SettingsUiState(
    val showCelsius: Boolean = true
)