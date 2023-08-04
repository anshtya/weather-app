package com.anshtya.weatherapp.data.mapper

import com.anshtya.weatherapp.domain.model.Astro
import com.anshtya.weatherapp.domain.model.Day
import com.anshtya.weatherapp.data.local.model.HourModel
import com.anshtya.weatherapp.data.local.entity.CurrentWeatherEntity
import com.anshtya.weatherapp.data.local.entity.WeatherForecastEntity
import com.anshtya.weatherapp.data.local.entity.WeatherLocationEntity
import com.anshtya.weatherapp.data.remote.model.NetworkAstro
import com.anshtya.weatherapp.data.remote.model.NetworkCurrentWeather
import com.anshtya.weatherapp.data.remote.model.NetworkDay
import com.anshtya.weatherapp.data.remote.model.NetworkForecastDay
import com.anshtya.weatherapp.data.remote.model.NetworkHour
import com.anshtya.weatherapp.data.remote.model.NetworkWeatherLocation
import com.anshtya.weatherapp.domain.model.Hour
import com.anshtya.weatherapp.domain.model.WeatherType

fun NetworkWeatherLocation.toEntity(locationUrl: String) = WeatherLocationEntity(
    id = locationUrl,
    country = country,
    timezoneId = timezoneId,
    localtimeEpoch = localtimeEpoch,
    name = name,
    region = region
)

fun NetworkCurrentWeather.toEntity(locationId: String) = CurrentWeatherEntity(
    locationId = locationId,
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

fun NetworkCurrentWeather.toUpdatedEntity(locationId: String, id: Long) = CurrentWeatherEntity(
    id = id,
    locationId = locationId,
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

fun NetworkForecastDay.toEntity(locationId: String, currentEpochTime: Int) = WeatherForecastEntity(
    locationId = locationId,
    dateEpoch = dateEpoch,
    astro = astro.toExternalModel(),
    day = day.toExternalModel(),
    hour = hour
        .filter { it.timeEpoch >= currentEpochTime }
        .map { it.toModel() }
)

fun NetworkForecastDay.toUpdatedEntity(locationId: String, id: Long, currentEpochTime: Int) = WeatherForecastEntity(
    id = id,
    locationId = locationId,
    dateEpoch = dateEpoch,
    astro = astro.toExternalModel(),
    day = day.toExternalModel(),
    hour = hour
        .filter { it.timeEpoch >= currentEpochTime }
        .map { it.toModel() }
)

fun NetworkAstro.toExternalModel() = Astro(
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

fun NetworkHour.toModel() = HourModel(
    chanceOfRain = chanceOfRain,
    chanceOfSnow = chanceOfSnow,
    condition = condition,
    isDay = isDay,
    tempC = tempC,
    tempF = tempF,
    time = time,
    timeEpoch = timeEpoch
)

fun HourModel.toExternalModel() = Hour(
    chanceOfRain = chanceOfRain,
    chanceOfSnow = chanceOfSnow,
    weatherType = WeatherType.fromWeatherCondition(condition.code),
    isDay = isDay,
    tempC = tempC,
    tempF = tempF,
    time = time,
    timeEpoch = timeEpoch
)