/*
 * *
 *  * Created by rahul on 12/18/24, 6:26 PM
 *  * Copyright (c) 2024 . All rights reserved.
 *  * Last modified 12/18/24, 6:26 PM
 *
 */

package com.example.weatherapp

import android.content.Context
import android.net.ConnectivityManager
import com.example.weatherapp.data.ConnectivityRepository
import com.example.weatherapp.data.DefaultConnectivityRepository
import com.example.weatherapp.data.WeatherRepository
import com.example.weatherapp.data.WeatherRepositoryImpl
import com.example.weatherapp.network.WeatherApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/";

    @Provides
    fun provideRetrofitClient(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()

    }
    @Provides
    fun provideWeatherApiService(retrofit: Retrofit): WeatherApiService {
        return retrofit.create(WeatherApiService::class.java)
    }
    @Provides
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager {
        return context.getSystemService(ConnectivityManager::class.java)
    }
    @Provides
    fun provideConnectivityRepository(connectivityManager: ConnectivityManager): ConnectivityRepository {
        return DefaultConnectivityRepository(connectivityManager);
    }
    @Provides
    fun weatherApiRepository(weatherApiService: WeatherApiService): WeatherRepository {
        return WeatherRepositoryImpl(weatherApiService);
    }

}