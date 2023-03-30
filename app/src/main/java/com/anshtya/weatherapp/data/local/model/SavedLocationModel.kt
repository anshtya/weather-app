package com.anshtya.weatherapp.data.local.model

import androidx.room.ColumnInfo
import com.anshtya.weatherapp.core.model.WeatherCondition

data class SavedLocationModel(
    @ColumnInfo(name = "condition") val condition: WeatherCondition,
    @ColumnInfo(name = "last_updated") val last_updated: String,
    @ColumnInfo(name = "temp_c") val temp_c: Double,
    @ColumnInfo(name = "country") val country: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "region") val region: String
)
