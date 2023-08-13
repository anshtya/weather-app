package com.anshtya.weatherapp.data.remote

import com.anshtya.weatherapp.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class WeatherApiTest {

    private lateinit var weatherApi: WeatherApi
    private lateinit var mockWebServer: MockWebServer

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        weatherApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    @Test
    fun testSearchLocation() = runTest {
        val mockResponse = MockResponse()
            .setBody(resourceReader(this,"/search_location.json"))
        mockWebServer.enqueue(mockResponse)

        val request = weatherApi.searchLocation("Jaipur")
        mockWebServer.takeRequest()

        assertEquals(
            true,
            request.isNotEmpty()
        )
    }

    @Test
    fun testWeatherForecast() = runTest {
        val mockResponse = MockResponse()
            .setBody(resourceReader(this,"/weather.json"))
        mockWebServer.enqueue(mockResponse)

        val request = weatherApi.getWeatherForecast("Jaipur")
        mockWebServer.takeRequest()

        assertEquals(
            "Jaipur",
            request.location.name
        )
    }

    @After
    fun tearDown() {
        mockWebServer.close()
    }
}

private fun <T> resourceReader(caller: T, filepath: String): String {
    return caller!!::class.java.getResource(filepath)!!.readText()
}