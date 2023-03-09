package com.anshtya.weatherapp.data.model

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromCondition(condition: Condition) {
        return
    }

}