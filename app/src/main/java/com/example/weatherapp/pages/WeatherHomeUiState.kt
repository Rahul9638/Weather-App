package com.example.weatherapp.pages

import com.example.weatherapp.data.CurrentWeather
import com.example.weatherapp.data.WeatherForecast

data class Weather(
    val currentWeather: CurrentWeather,
    val forecast: WeatherForecast,
)


sealed interface WeatherHomeUiState {
    data class Success(val weather: Weather) : WeatherHomeUiState;
    data object Error : WeatherHomeUiState
    data object Loading : WeatherHomeUiState
}

sealed interface ConnectivityState {
    data object Available : ConnectivityState;
    data object Unavailable : ConnectivityState;
}
