package com.anshtya.weatherapp.data.local

import androidx.room.DeleteColumn
import androidx.room.migration.AutoMigrationSpec

object DatabaseMigrations {

    @DeleteColumn(tableName = "current_weather", columnName = "cloud")
    class Migration4to5: AutoMigrationSpec
}