package com.example.weatherapp.pages

import android.app.Application
import android.net.ConnectivityManager
import android.text.Editable.Factory
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.weatherapp.data.ConnectivityRepository
import com.example.weatherapp.data.CurrentWeather
import com.example.weatherapp.data.DefaultConnectivityRepository
import com.example.weatherapp.data.WeatherForecast
import com.example.weatherapp.data.WeatherRepository
import com.example.weatherapp.data.WeatherRepositoryImpl
import com.example.weatherapp.utils.WEATHER_API_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WeatherHomeViewModel @Inject constructor (
    private val connectivityRepository: ConnectivityRepository,
    private val weatherRepository: WeatherRepository,
) : ViewModel() {
    private var latitude: Double = 0.0;
    private var longitude: Double = 0.0;
     val connectivityState: StateFlow<ConnectivityState> =
        connectivityRepository.connectivityState;
    var uiState: WeatherHomeUiState by mutableStateOf(WeatherHomeUiState.Loading)
    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        uiState = WeatherHomeUiState.Error
    }

    fun updateLocation(lat: Double, lng: Double): Unit {
        latitude = lat;
        longitude = lng
    }

    fun getWeatherData(): Unit {
        viewModelScope.launch(exceptionHandler) {
            try {
                val currentWeather = async { getCurrentData() }.await();
                val forecastWeather = async { getForecastData() }.await();
                uiState = WeatherHomeUiState.Success(Weather(currentWeather, forecastWeather));
                //   Log.d("WeatherHomeViewModel", "currentWeather : ${currentWeather.main!!.temp} ")
                //  Log.d("WeatherHomeViewModel", "forecastWeather : ${forecastWeather.list!!.size} ")
            } catch (e: Exception) {
                Log.d("WeatherHomeViewModel", e.message.toString())
                uiState = WeatherHomeUiState.Error;
            }
        }
    }

    private suspend fun getCurrentData(): CurrentWeather {
        Log.e("WeatherHomeViewModel", "Latitude ${latitude} Longitude ${longitude}");
        val endUrl = "weather?lat=$latitude&lon=$latitude&appid=${WEATHER_API_KEY}";
        return weatherRepository.getCurrentWeather(endUrl);
    }

    private suspend fun getForecastData(): WeatherForecast {
        val endUrl = "forecast?lat=$latitude&lon=$longitude&appid=${WEATHER_API_KEY}";
        return weatherRepository.getForecastWeather(endUrl);
    }

//    companion object {
//        val Factory : ViewModelProvider.Factory = viewModelFactory {
//            initializer {
//                val application = (this[APPLICATION_KEY] as Application);
//                val connectivityManager = application.getSystemService(ConnectivityManager::class.java);
//                WeatherHomeViewModel(connectivityRepository = DefaultConnectivityRepository(connectivityManager))
//            }
//        }
//    }
}