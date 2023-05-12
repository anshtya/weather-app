package com.anshtya.weatherapp.data.mapper

import com.anshtya.weatherapp.core.model.Astro
import com.anshtya.weatherapp.core.model.Day
import com.anshtya.weatherapp.core.model.Hour
import com.anshtya.weatherapp.data.local.entity.CurrentWeatherEntity
import com.anshtya.weatherapp.data.local.entity.WeatherForecastEntity
import com.anshtya.weatherapp.data.local.entity.WeatherLocationEntity
import com.anshtya.weatherapp.data.remote.model.NetworkAstro
import com.anshtya.weatherapp.data.remote.model.NetworkCurrentWeather
import com.anshtya.weatherapp.data.remote.model.NetworkDay
import com.anshtya.weatherapp.data.remote.model.NetworkForecastDay
import com.anshtya.weatherapp.data.remote.model.NetworkHour
import com.anshtya.weatherapp.data.remote.model.NetworkWeatherLocation

fun NetworkWeatherLocation.toEntity() = WeatherLocationEntity(
    id = "${this.name}-${this.region}-${this.country}",
    country = country,
    timezoneId = timezoneId,
    name = name,
    region = region
)

fun NetworkCurrentWeather.toEntity(locationId: String) = CurrentWeatherEntity(
    locationId = locationId,
    cloud = cloud,
    condition = condition,
    feelsLikeC = feelsLikeC,
    feelsLikeF = feelsLikeF,
    humidity = humidity,
    isDay = isDay,
    tempC = tempC,
    tempF = tempF,
    uv = uv,
    visKm = visKm,
    visMiles = visMiles,
    windDir = windDir,
    windKph = windKph,
    windMph = windMph
)

fun NetworkForecastDay.toEntity(locationId: String) = WeatherForecastEntity(
    locationId = locationId,
    astro =  astro.toExternalModel(),
    day = day.toExternalModel(),
    hour = hour.map { it.toExternalModel() }
)

fun NetworkAstro.toExternalModel() = Astro(
    isMoonUp = isMoonUp,
    isSunUp = isSunUp,
    moonrise = moonrise,
    moonset = moonset,
    sunrise = sunrise,
    sunset = sunset
)

fun NetworkDay.toExternalModel() = Day(
    dailyChanceOfRain = dailyChanceOfRain,
    dailyChanceOfSnow = dailyChanceOfSnow,
    maxTempC = maxTempC,
    maxTempF = maxTempF,
    minTempC = minTempC,
    minTempF = minTempF,
)

fun NetworkHour.toExternalModel() = Hour(
    chanceOfRain = chanceOfRain,
    chanceOfSnow = chanceOfSnow,
    condition = condition,
    isDay = isDay,
    tempC = tempC,
    tempF = tempF,
    time = time
)