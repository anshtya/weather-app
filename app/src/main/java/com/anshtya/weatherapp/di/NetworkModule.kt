package com.anshtya.weatherapp.di

import android.content.Context
import com.anshtya.weatherapp.util.Constants
import com.anshtya.weatherapp.data.remote.WeatherApi
import com.anshtya.weatherapp.data.connectivity.NetworkConnectionObserverImpl
import com.anshtya.weatherapp.domain.connectivity.NetworkConnectionObserver
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
object NetworkModule {
    @Provides
    @Singleton
    fun provideApi(): WeatherApi {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
            .create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNetworkConnectionTracker(@ApplicationContext context: Context): NetworkConnectionObserver {
        return NetworkConnectionObserverImpl(context)
    }
}