package com.anshtya.weatherapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.anshtya.weatherapp.core.common.Constants
import com.anshtya.weatherapp.data.local.WeatherDatabase
import com.anshtya.weatherapp.data.local.dao.WeatherDao
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
        return Room.databaseBuilder(app, WeatherDatabase::class.java, "weather.db").build()
    }

    @Provides
    @Singleton
    fun provideCurrentWeatherDao(db: WeatherDatabase): WeatherDao {
        return db.getCurrentWeatherDao()
    }

    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { appContext.preferencesDataStoreFile(Constants.USER_PREFERENCES) }
        )
    }
}