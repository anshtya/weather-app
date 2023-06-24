package com.anshtya.weatherapp.presentation.screens.settings

import com.anshtya.weatherapp.FakeUserDataRepository
import com.anshtya.weatherapp.MainDispatcherRule
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SettingsViewModelTest {

    private lateinit var viewModel: SettingsViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        viewModel = SettingsViewModel(
            userDataRepository = FakeUserDataRepository()
        )
    }

    @Test
    fun `when user selects temperature unit, it should get updated in ui state`() = runTest {
        viewModel.showCelsius(false)
        assertEquals(
            false,
            viewModel.uiState.map { it.showCelsius }.first()
        )
        viewModel.showCelsius(true)
        assertEquals(
            true,
            viewModel.uiState.map { it.showCelsius }.first()
        )
    }
}