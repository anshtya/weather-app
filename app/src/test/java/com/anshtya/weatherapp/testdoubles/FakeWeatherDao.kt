package com.anshtya.weatherapp.testdoubles

import com.anshtya.weatherapp.data.local.dao.WeatherDao
import com.anshtya.weatherapp.data.local.entity.CurrentWeatherEntity
import com.anshtya.weatherapp.data.local.entity.WeatherForecastEntity
import com.anshtya.weatherapp.data.local.entity.WeatherLocationEntity
import com.anshtya.weatherapp.data.local.model.SavedLocationModel
import com.anshtya.weatherapp.data.local.model.WeatherModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class FakeWeatherDao : WeatherDao {

    private val weatherStateFlow = MutableStateFlow<List<WeatherModel>>(emptyList())
    override suspend fun getLocationIds(): List<String> {
        return weatherStateFlow.value.map {
            it.weatherLocation.id
        }
    }

    override suspend fun getCurrentWeatherId(locationId: String): Long {
        return weatherStateFlow.value
            .find {
                it.currentWeather.locationId == locationId
            }!!
            .currentWeather.id
    }

    override suspend fun getWeatherForecastIds(locationId: String): List<Long> {
        return weatherStateFlow.value
            .find {
                it.weatherForecast.first().locationId == locationId
            }!!
            .weatherForecast.map { it.id }
    }

    override fun getWeather(): Flow<List<WeatherModel>> {
        return weatherStateFlow
    }

    override fun getSavedLocations(): Flow<List<SavedLocationModel>> {
        return weatherStateFlow.map {
            it.map { weather ->
                SavedLocationModel(
                    id = weather.weatherLocation.id,
                    country = "",
                    name = "",
                    region = "",
                    code = 0,
                    isDay = 0,
                    tempC = 0.0,
                    tempF = 0.0,
                    maxTempC = 0.0,
                    maxTempF = 0.0,
                    minTempC = 0.0,
                    minTempF = 0.0
                )
            }
        }
    }

    override suspend fun checkWeatherExist(locationId: String): Boolean {
        return weatherStateFlow.value
            .find {
                it.weatherLocation.id == locationId
            }?.let {
                true
            } ?: false
    }

    override fun checkTableNotEmpty(): Flow<Boolean> {
        return weatherStateFlow.map {
            it.isNotEmpty()
        }
    }

    override suspend fun upsertWeather(
        weatherLocation: WeatherLocationEntity,
        currentWeather: CurrentWeatherEntity,
        weatherForecast: List<WeatherForecastEntity>
    ) {
        val newWeather = WeatherModel(weatherLocation, currentWeather, weatherForecast)
        weatherStateFlow.update { currentList ->
            val updatedList = currentList.toMutableList()
            val existingIndex = updatedList.indexOfFirst {
                it.weatherLocation.id == weatherLocation.id
            }
            if(existingIndex == -1) {
                updatedList.add(newWeather)
            } else {
                updatedList[existingIndex] = newWeather
            }

            updatedList
        }
    }

    override suspend fun deleteWeatherLocation(locationId: String) {
        weatherStateFlow.update {
            it - it.find { weatherModel ->
                weatherModel.weatherLocation.id == locationId
            }!!
        }
    }

    override suspend fun upsertWeatherLocation(weatherLocation: WeatherLocationEntity) {
        return
    }

    override suspend fun upsertCurrentWeather(currentWeather: CurrentWeatherEntity) {
        return
    }

    override suspend fun upsertWeatherForecast(weatherForecast: List<WeatherForecastEntity>) {
        return
    }
}