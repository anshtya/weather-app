package com.anshtya.weatherapp.data.local.model

import androidx.room.ColumnInfo

data class SavedLocationModel(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "region") val region: String,
    @ColumnInfo(name = "country") val country: String,
    @ColumnInfo(name = "tempC") val tempC: Double,
    @ColumnInfo(name = "tempF") val tempF: Double,
    @ColumnInfo(name = "maxTempC") val maxTempC: Double,
    @ColumnInfo(name = "maxTempF") val maxTempF: Double,
    @ColumnInfo(name = "minTempC") val minTempC: Double,
    @ColumnInfo(name = "minTempF") val minTempF: Double,
    @ColumnInfo(name = "code") val code: Int,
    @ColumnInfo(name = "text") val text: String,
)
