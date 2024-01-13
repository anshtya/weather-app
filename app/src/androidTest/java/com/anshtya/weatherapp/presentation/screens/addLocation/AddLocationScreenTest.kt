package com.anshtya.weatherapp.presentation.screens.addLocation

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.anshtya.weatherapp.R
import org.junit.Rule
import org.junit.Test

class AddLocationScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun circularProgressIndicator_whenIsLoadingTrue_exists() {
        composeTestRule.setContent {
            AddLocationScreen(
                uiState = AddLocationUiState(isLoading = true),
                searchQuery = "search",
                searchLocations = emptyList(),
                onBackClick = {},
                onSearchTextChange = {},
                onLocationClick = {},
                onAddCurrentLocationClick = {},
                onErrorShown = {}
            )
        }

        composeTestRule
            .onNodeWithContentDescription(
                composeTestRule.activity.resources.getString(R.string.loading)
            )
            .assertExists()
    }

    @Test
    fun searchResult_IsLoadingFalse_SearchQueryIsNotEmpty_exists() {
        composeTestRule.setContent {
            AddLocationScreen(
                uiState = AddLocationUiState(isLoading = false),
                searchQuery = "text",
                searchLocations = testSearchResults,
                onBackClick = {},
                onSearchTextChange = {},
                onLocationClick = {},
                onAddCurrentLocationClick = {},
                onErrorShown = {}
            )
        }

        testSearchResults.forEach { result ->
            composeTestRule
                .onNodeWithText(result.name)
                .assertExists()
                .assertHasClickAction()
        }
    }
}