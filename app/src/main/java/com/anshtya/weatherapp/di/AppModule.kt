package com.anshtya.weatherapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.anshtya.weatherapp.data.remote.WeatherApi
import com.anshtya.weatherapp.core.common.Constants.Companion.BASE_URL
import com.anshtya.weatherapp.core.common.Constants.Companion.USER_PREFERENCES
import com.anshtya.weatherapp.data.local.dao.CurrentWeatherDao
import com.anshtya.weatherapp.data.local.WeatherDatabase
import com.anshtya.weatherapp.data.local.dao.WeatherLocationDao
import com.anshtya.weatherapp.data.repository.LocationRepositoryImpl
import com.anshtya.weatherapp.data.repository.WeatherRepositoryImpl
import com.anshtya.weatherapp.domain.repository.LocationRepository
import com.anshtya.weatherapp.domain.repository.WeatherRepository
import com.anshtya.weatherapp.domain.useCase.GetSavedLocationsUseCase
import com.anshtya.weatherapp.domain.useCase.GetSearchLocationUseCase
import com.anshtya.weatherapp.domain.useCase.GetWeatherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideApi(): WeatherApi {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
            .create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherDatabase(@ApplicationContext app: Context): WeatherDatabase {
        return Room.databaseBuilder(app, WeatherDatabase::class.java, "weather.db").build()
    }

    @Provides
    @Singleton
    fun provideCurrentWeatherDao(db: WeatherDatabase): CurrentWeatherDao {
        return db.getCurrentWeatherDao()
    }

    @Provides
    @Singleton
    fun provideWeatherLocationDao(db: WeatherDatabase): WeatherLocationDao {
        return db.getWeatherLocationDao()
    }

    @Provides
    @Singleton
    fun provideLocationRepository(api: WeatherApi, dao: WeatherLocationDao): LocationRepository {
        return LocationRepositoryImpl(api, dao)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(
        api: WeatherApi,
        currentWeatherDao: CurrentWeatherDao,
        weatherLocationDao: WeatherLocationDao
    ): WeatherRepository {
        return WeatherRepositoryImpl(api, currentWeatherDao, weatherLocationDao)
    }

    @Provides
    @Singleton
    fun provideSearchLocationUseCase(repository: LocationRepository): GetSearchLocationUseCase {
        return GetSearchLocationUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSavedLocationUseCase(repository: LocationRepository): GetSavedLocationsUseCase {
        return GetSavedLocationsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideWeatherUseCase(repository: WeatherRepository): GetWeatherUseCase {
        return GetWeatherUseCase(repository)
    }

    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { appContext.preferencesDataStoreFile(USER_PREFERENCES) }
        )
    }
}