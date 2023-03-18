package com.anshtya.weatherapp.di

import android.content.Context
import androidx.room.Room
import com.anshtya.weatherapp.data.remote.WeatherApi
import com.anshtya.weatherapp.common.Constants.Companion.BASE_URL
import com.anshtya.weatherapp.data.local.WeatherDao
import com.anshtya.weatherapp.data.local.WeatherDatabase
import com.anshtya.weatherapp.data.repository.SearchLocationRepositoryImpl
import com.anshtya.weatherapp.data.repository.WeatherRepositoryImpl
import com.anshtya.weatherapp.domain.repository.SearchLocationRepository
import com.anshtya.weatherapp.domain.repository.WeatherRepository
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
    fun provideWeatherDao(db: WeatherDatabase): WeatherDao {
        return db.getWeatherDao()
    }

    @Provides
    @Singleton
    fun provideSearchLocationRepository(api: WeatherApi): SearchLocationRepository {
        return SearchLocationRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(api: WeatherApi, dao: WeatherDao): WeatherRepository {
        return WeatherRepositoryImpl(api, dao)
    }

    @Provides
    @Singleton
    fun provideSearchLocationUseCase(repository: SearchLocationRepository): GetSearchLocationUseCase {
        return GetSearchLocationUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideWeatherUseCase(repository: WeatherRepository): GetWeatherUseCase {
        return GetWeatherUseCase(repository)
    }
}