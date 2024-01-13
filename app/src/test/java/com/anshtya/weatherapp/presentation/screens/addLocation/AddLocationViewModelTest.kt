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
        val testSearchLocations = listOf(
            SearchLocation(
                name = "name",
                region = "region",
                country = "country",
                url = "url"
            )
        )
        var searchLocations = emptyList<SearchLocation>()
        viewModel.onSearchQueryChange("name")
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.searchLocations.collect {
               searchLocations = it
            }
        }

        // delay for search to execute
        delay(200L)

        assertEquals(searchLocations, testSearchLocations)

        collectJob.cancel()
    }

    @Test
    fun ifNetworkUnavailable_SearchFails() = runTest {
        val testUIState = AddLocationUiState(
            errorMessage = "Network unavailable",
            isLoading = false
        )
        var uiState: AddLocationUiState? = null

        connectivityObserver.unavailableNetwork()
        viewModel.onSearchQueryChange("name")

        val searchLocationsCollectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.searchLocations.collect{}
        }
        val uiStateCollectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.uiState.collect {
                uiState = it
            }
        }

        // delay for search to execute
        delay(200L)

        assertEquals(testUIState, uiState)

        uiStateCollectJob.cancel()
        searchLocationsCollectJob.cancel()
    }

    @Test
    fun ifSearchErrorOccurs_ErrorResponseReceived() = runTest {
        val testUIState = AddLocationUiState(
            errorMessage = "An error occurred",
            isLoading = false
        )
        var uiState: AddLocationUiState? = null
        viewModel.onSearchQueryChange("error")

        val searchLocationsCollectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.searchLocations.collect{}
        }
        val uiStateCollectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.uiState.collect {
                println("$it")
                uiState = it
            }
        }

        // delay for search to execute
        delay(200L)

        assertEquals(testUIState, uiState)

        uiStateCollectJob.cancel()
        searchLocationsCollectJob.cancel()
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

        var isLocationAdded: Boolean? = false
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.uiState.collect {
                isLocationAdded = it.isLocationAdded
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