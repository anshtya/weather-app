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
                uiState = AddLocationUiState(
                    isLoading = true
                ),
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
    fun circularProgressIndicator_whenIsSearchingTrue_SearchTextIsNotEmpty_exists() {
        composeTestRule.setContent {
            AddLocationScreen(
                uiState = AddLocationUiState(
                    isSearching = true,
                    searchText = "text"
                ),
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
    fun searchResult_whenIsSearchingFalse_IsLoadingFalse_SearchTextIsNotEmpty_exists() {
        composeTestRule.setContent {
            AddLocationScreen(
                uiState = AddLocationUiState(
                    isSearching = false,
                    isLoading = false,
                    searchText = "text",
                    searchLocations = testSearchResults
                ),
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