package com.example.weatherapp.pages

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.weatherapp.R
import com.example.weatherapp.customui.AppBackground
import com.example.weatherapp.data.CurrentWeather
import com.example.weatherapp.data.WeatherForecast
import com.example.weatherapp.utils.formattedDate
import com.example.weatherapp.utils.getIconUrl

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherHomeScreen(
    isConnected: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    uiState: WeatherHomeUiState
) {
    Box(modifier = modifier.fillMaxSize()) {
        AppBackground(photoId = R.drawable.flower_background, modifier = modifier.fillMaxSize())
        Scaffold(containerColor = Color.Transparent, topBar = {
            TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,

                ), title = {
                Text(text = "Weather App", fontSize = 22.sp)
            }

            )
        }) {
            Surface(
                color = Color.Transparent,
                modifier = modifier
                    .padding(it)
                    .fillMaxSize()
                    .wrapContentSize()
            ) {
                if (!isConnected) {
                    Text(text = "No Internet Access")
                } else {
                    when (uiState) {
                        is WeatherHomeUiState.Loading -> Text(text = "Loading")
                        is WeatherHomeUiState.Success -> WeatherPreview(weather = uiState.weather)
                        is WeatherHomeUiState.Error -> ErrorSection(message = "Error loading data", onRefresh = onRefresh)
                    }
                }

            }
        }
    }


}


@Composable
fun ErrorSection(
    modifier: Modifier = Modifier,
    message: String,
    onRefresh: () -> Unit
) {
    Column {
        Text(text = message, style = MaterialTheme.typography.titleSmall)
        Spacer(modifier = Modifier.height(10.dp))
        IconButton(onClick = onRefresh) {
            Icon(Icons.Default.Refresh, contentDescription = null)
        }
    }
}

@Composable
fun WeatherPreview(modifier: Modifier = Modifier, weather: Weather) {
    Column(modifier = modifier.fillMaxSize()) {
        CurrentWeatherComp(modifier = modifier.weight(1f), currentWeather = weather.currentWeather)
        ForecastSection(forecast = weather.forecast.list!!)
    }
}

@Composable
fun CurrentWeatherComp(
    modifier: Modifier = Modifier,
    currentWeather: CurrentWeather,

    ) {
    Column(
        modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${currentWeather.name!!} ${currentWeather.sys!!.country}",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = formattedDate(currentWeather.dt!!, pattern = "MMM dd YYYY"),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "${currentWeather.main!!.temp!!} ",
            style = MaterialTheme.typography.displayMedium
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Feels like ${currentWeather.main.feelsLike}",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(
                    data = getIconUrl(currentWeather.weather!![0]!!.icon!!)
                ).crossfade(true).build(),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Text(
                text = "${currentWeather.weather!![0]!!.description}",
                style = MaterialTheme.typography.titleMedium
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text(
                    text = "Humidity ${currentWeather.main.humidity} %",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Pressure ${currentWeather.main.pressure} hPA",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Visibility ${currentWeather.visibility} 10 KM ",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
            Surface(
                modifier = Modifier
                    .width(2.dp)
                    .height(100.dp), color = Color.White
            ) {}
            Spacer(modifier = Modifier.width(20.dp))
            Column {
                Text(
                    text = "Sunrise :  ${
                        formattedDate(
                            currentWeather.sys.sunrise!!, pattern = "HH mm"
                        )
                    }", style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Sunset :  ${
                        formattedDate(
                            currentWeather.sys.sunset!!, pattern = "HH mm"
                        )
                    }", style = MaterialTheme.typography.titleMedium
                )

            }

        }
    }
}

@Composable
fun ForecastSection(
    modifier: Modifier = Modifier,
    forecast: List<WeatherForecast.ForecastItem?>,
) {

    LazyRow() {
        items(items = forecast, itemContent = { item ->
            ForecastItemContainer(forecast = item!!);
        })
    }

}

@Composable
fun ForecastItemContainer(modifier: Modifier = Modifier, forecast: WeatherForecast.ForecastItem) {
    Card(
        modifier = Modifier.padding(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = (0.4f))),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(10.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            Text(text = formattedDate(dt = forecast.dt!!, pattern = "EEE"))
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = formattedDate(dt = forecast.dt!!, pattern = "HH:mm"))
            Spacer(modifier = Modifier.height(4.dp))
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(
                    data = getIconUrl(forecast.weather?.get(0)!!.icon!!)
                ).build(),
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                error = painterResource(id = androidx.core.R.drawable.ic_call_answer_low),
                onError = { error ->
                    Log.e(
                        "AsyncImageError",
                        "Error loading image: ${error.result.throwable?.message}"
                    )
                }
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "${forecast.main!!.temp!!}")
            Spacer(modifier = Modifier.height(10.dp))

        }
    }
}

@Preview
@Composable
private fun WeatherHomeScreenPreview() {
    WeatherHomeScreen(
        uiState = WeatherHomeUiState.Loading,
        isConnected = true,
        onRefresh = {}
    )
}