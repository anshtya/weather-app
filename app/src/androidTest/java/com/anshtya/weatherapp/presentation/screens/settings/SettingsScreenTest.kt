package com.anshtya.weatherapp.presentation.screens.settings

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.anshtya.weatherapp.R
import org.junit.Rule
import org.junit.Test

class SettingsScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private fun getString(id: Int) = composeTestRule.activity.resources.getString(id)

    @Test
    fun allSettingsAreDisplayed() {
        composeTestRule.setContent {
            SettingsScreen(
                uiState = SettingsUiState(),
                onBackClick = {},
                onShowCelsius = {}
            )
        }

        composeTestRule.onNodeWithText(getString(R.string.celsius)).assertExists()
        composeTestRule.onNodeWithText(getString(R.string.fahrenheit)).assertExists()
    }

    @Test
    fun whenUseCelsiusTrue_CelsiusIsSelected() {
        composeTestRule.setContent {
            SettingsScreen(
                uiState = SettingsUiState(showCelsius = true),
                onBackClick = {},
                onShowCelsius = {}
            )
        }

        composeTestRule.onNodeWithText(getString(R.string.celsius))
            .assertExists()
            .assertIsSelected()
    }

    @Test
    fun whenUseCelsiusFalse_FahrenheitIsSelected() {
        composeTestRule.setContent {
            SettingsScreen(
                uiState = SettingsUiState(showCelsius = false),
                onBackClick = {},
                onShowCelsius = {}
            )
        }

        composeTestRule.onNodeWithText(getString(R.string.fahrenheit))
            .assertExists()
            .assertIsSelected()
    }
}