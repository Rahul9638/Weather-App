package com.example.weatherapp.data

import com.example.weatherapp.network.WeatherApiService
import javax.inject.Inject


interface WeatherRepository {
    suspend fun getCurrentWeather(endUrl: String): CurrentWeather
    suspend fun getForecastWeather(endUrl: String): WeatherForecast
}


class WeatherRepositoryImpl @Inject constructor (private val weatherApiService: WeatherApiService) : WeatherRepository {
    override suspend fun getCurrentWeather(endUrl: String): CurrentWeather {
        return weatherApiService.getCurrentWeather(endUrl)
    }

    override suspend fun getForecastWeather(endUrl: String): WeatherForecast {
        return weatherApiService.getForecasetWeather(endUrl)
    }
}