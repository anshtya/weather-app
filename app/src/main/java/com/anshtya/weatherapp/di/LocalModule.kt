package com.anshtya.weatherapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.anshtya.weatherapp.data.local.WeatherDatabase
import com.anshtya.weatherapp.data.local.WeatherDatabase.Companion.MIGRATION_2_3
import com.anshtya.weatherapp.data.local.WeatherDatabase.Companion.MIGRATION_3_4
import com.anshtya.weatherapp.data.local.dao.WeatherDao
import com.anshtya.weatherapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    @Singleton
    fun provideWeatherDatabase(@ApplicationContext app: Context): WeatherDatabase {
        return Room
            .databaseBuilder(app, WeatherDatabase::class.java, "weather.db")
            .addMigrations(MIGRATION_2_3)
            .addMigrations(MIGRATION_3_4)
            .build()
    }

    @Provides
    @Singleton
    fun provideWeatherDao(db: WeatherDatabase): WeatherDao {
        return db.weatherDao()
    }

    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { appContext.preferencesDataStoreFile(Constants.USER_SETTINGS) }
        )
    }
}