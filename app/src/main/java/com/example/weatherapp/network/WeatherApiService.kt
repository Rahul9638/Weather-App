/*
 * *
 *  * Created by rahul on 12/10/24, 9:08 PM
 *  * Copyright (c) 2024 . All rights reserved.
 *  * Last modified 12/10/24, 9:08 PM
 *
 */

package com.example.weatherapp.network

import com.example.weatherapp.data.CurrentWeather
import com.example.weatherapp.data.WeatherForecast
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url


interface WeatherApiService {
    @GET()
    suspend fun getCurrentWeather(@Url endUrl: String): CurrentWeather

    @GET()
    suspend fun getForecasetWeather(@Url endUrl: String): WeatherForecast
}

