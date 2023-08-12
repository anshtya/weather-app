package com.anshtya.weatherapp.presentation.screens.addLocation

import com.anshtya.weatherapp.MainDispatcherRule
import com.anshtya.weatherapp.domain.model.SearchLocation
import com.anshtya.weatherapp.testdoubles.FakeNetworkConnectionObserver
import com.anshtya.weatherapp.testdoubles.repository.FakeWeatherRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AddLocationViewModelTest {

    private lateinit var viewModel: AddLocationViewModel
    private val weatherRepository = FakeWeatherRepository()
    private val locationTracker = FakeLocationTracker()
    private val connectivityObserver = FakeNetworkConnectionObserver()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        viewModel = AddLocationViewModel(
            weatherRepository = weatherRepository,
            locationTracker = locationTracker,
            connectionObserver = connectivityObserver
        )
    }

    @Test
    fun testInitialUiState() = runTest {
        assertEquals(
            AddLocationUiState(),
            viewModel.uiState.value
        )
    }

    @Test
    fun afterErrorShown_MessageSetToNull() = runTest {
        viewModel.errorShown()

        assertEquals(
            AddLocationUiState(
                errorMessage = null
            ),
            viewModel.uiState.value
        )
    }

    @Test
    fun whenSearchTextEntered_SearchIsExecuted() = runTest {
        viewModel.onSearchTextChange("name")

        // delay to let debounce trigger and search to execute
        delay(600L)

        assertEquals(
            AddLocationUiState(
                searchLocations = listOf(
                    SearchLocation(
                        name = "name",
                        region = "region",
                        country = "country",
                        url = "url"
                    )
                ),
                isLoading = false,
                searchText = "name"
            ),
            viewModel.uiState.value
        )
    }

    @Test
    fun whenSearchTextIsEmpty_SearchNotExecuted() = runTest {
        viewModel.onSearchTextChange("")

        // delay to let debounce trigger and search to execute
        delay(600L)

        assertEquals(
            AddLocationUiState(
                isLoading = false,
                searchText = ""
            ),
            viewModel.uiState.value
        )
    }

    @Test
    fun ifNetworkUnavailable_SearchFails() = runTest {
        connectivityObserver.unavailableNetwork()
        viewModel.onSearchTextChange("name")

        // delay to let debounce trigger and search to execute
        delay(600L)

        assertEquals(
            AddLocationUiState(
                errorMessage = "Network unavailable",
                isLoading = false,
                searchText = "name"
            ),
            viewModel.uiState.value
        )
    }

    @Test
    fun ifSearchErrorOccurs_ErrorResponseRecieved() = runTest {
        viewModel.onSearchTextChange("error")

        // delay to let debounce trigger and search to execute
        delay(600L)

        assertEquals(
            AddLocationUiState(
                errorMessage = "An Error Occurred",
                isLoading = false,
                isSearching = false,
                searchText = "error"
            ),
            viewModel.uiState.value
        )
    }

    @Test
    fun ifNetworkUnavailable_CurrentLocationNotRetrieved() = runTest {
        connectivityObserver.unavailableNetwork()
        viewModel.getUserCurrentLocation()


        assertEquals(
            AddLocationUiState(
                errorMessage = "Network unavailable",
                isLoading = false
            ),
            viewModel.uiState.value
        )
    }

    @Test
    fun ifLocationAdded_SuccessResponseReceived() = runTest {

        var isLocationAdded = false
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.isLocationAdded.collect {
                isLocationAdded = it
            }
        }

        viewModel.onLocationClick("name")

        assertEquals(true, isLocationAdded)

        collectJob.cancel()
    }

    @Test
    fun ifLocationExists_ErrorResponseReceived() = runTest {
        viewModel.onLocationClick("exist")

        assertEquals(
            AddLocationUiState(
                errorMessage = "Location already exists"
            ),
            viewModel.uiState.value
        )
    }
}