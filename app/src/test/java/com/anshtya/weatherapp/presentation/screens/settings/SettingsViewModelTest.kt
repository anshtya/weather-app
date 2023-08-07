package com.anshtya.weatherapp.presentation.screens.settings

import com.anshtya.weatherapp.MainDispatcherRule
import com.anshtya.weatherapp.repository.TestUserDataRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SettingsViewModelTest {

    private lateinit var viewModel: SettingsViewModel
    private val userDataRepository = TestUserDataRepository()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        viewModel = SettingsViewModel(
            userDataRepository = userDataRepository
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun whenTemperatureUnitChanges_uiStateGetsUpdated() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.uiState.collect {}
        }

        viewModel.useCelsius(useCelsius = false)

        assertEquals(
            SettingsUiState(showCelsius = false),
            viewModel.uiState.value
        )

        viewModel.useCelsius(useCelsius = true)

        assertEquals(
            SettingsUiState(showCelsius = true),
            viewModel.uiState.value
        )

        collectJob.cancel()
    }
}