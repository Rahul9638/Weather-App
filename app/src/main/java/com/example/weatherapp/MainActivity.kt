package com.example.weatherapp

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.pages.ConnectivityState
import com.example.weatherapp.pages.WeatherHomeScreen
import com.example.weatherapp.pages.WeatherHomeViewModel
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val client: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this);
        enableEdgeToEdge()
        setContent {
            WeatherAppTheme {
                MyApp(client = client)
            }
        }
    }
}


@Composable
fun MyApp(
    modifier: Modifier = Modifier,
    client: FusedLocationProviderClient,
) {
    var cancellationTokenSource = CancellationTokenSource();
    val weatherHomeViewModel: WeatherHomeViewModel = viewModel();
    val context = LocalContext.current;
    var permssionGranted by remember {
        mutableStateOf(false)
    }


    Log.d("HomeScreen", "This is home screen");
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { granted ->
            Log.e("HomeScreen", "Permission ${granted}");
            permssionGranted = granted

        }

    Log.d("HomeScreen", "Activity Launcher finished");


    LaunchedEffect(Unit) {
        val isPermissionGranted = ContextCompat.checkSelfPermission(
            context, android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        Log.d("HomeScreen", "Check Location Granted ${isPermissionGranted}");

        if (!isPermissionGranted) {
            launcher.launch(android.Manifest.permission.ACCESS_COARSE_LOCATION)
        } else {
            permssionGranted = true;
        }
    }

    LaunchedEffect(permssionGranted) {
        if (permssionGranted) {
            client.getCurrentLocation(
                PRIORITY_HIGH_ACCURACY, cancellationTokenSource.token
            ).addOnSuccessListener { location ->
                weatherHomeViewModel.updateLocation(
                    lat = location.latitude, lng = location.longitude
                );
                weatherHomeViewModel.getWeatherData();

            }

        }
    }
    val isConnected by weatherHomeViewModel.connectivityState.collectAsState();
    WeatherHomeScreen(
        onRefresh = {
            weatherHomeViewModel.getWeatherData();
        },
        isConnected = isConnected == ConnectivityState.Available,
        uiState = weatherHomeViewModel.uiState
    )
}




