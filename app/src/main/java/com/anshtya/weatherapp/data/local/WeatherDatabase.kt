package com.anshtya.weatherapp.data.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.anshtya.weatherapp.data.local.dao.WeatherDao
import com.anshtya.weatherapp.data.local.entity.CurrentWeatherEntity
import com.anshtya.weatherapp.data.local.entity.WeatherForecastEntity
import com.anshtya.weatherapp.data.local.entity.WeatherLocationEntity

@Database(
    entities = [WeatherLocationEntity::class, CurrentWeatherEntity::class, WeatherForecastEntity::class],
    version = 5,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 4, to = 5, spec = DatabaseMigrations.Migration4to5::class)
    ]
)
@TypeConverters(Converters::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao

    companion object {
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "ALTER TABLE weather_forecast ADD COLUMN dateEpoch INTEGER DEFAULT 0 NOT NULL"
                )
            }
        }
        val MIGRATION_3_4 = object: Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "ALTER TABLE weather_location ADD COLUMN localtimeEpoch INTEGER DEFAULT 0 NOT NULL"
                )
            }
        }
    }
}