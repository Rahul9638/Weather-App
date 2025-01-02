/*
 * *
 *  * Created by rahul on 12/18/24, 2:14 PM
 *  * Copyright (c) 2024 . All rights reserved.
 *  * Last modified 12/18/24, 2:14 PM
 *
 */

package com.example.weatherapp.data

import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import com.example.weatherapp.pages.ConnectivityState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

interface ConnectivityRepository {
    val connectivityState: StateFlow<ConnectivityState>;
}

class DefaultConnectivityRepository @Inject constructor (
    private val connectivityManager: ConnectivityManager
) : ConnectivityRepository {

    private val _connectivityState =
        MutableStateFlow<ConnectivityState>(ConnectivityState.Unavailable);
    override val connectivityState: StateFlow<ConnectivityState> = _connectivityState.asStateFlow();

    private val callback = object : NetworkCallback() {

        override fun onLost(network: Network) {
            _connectivityState.value = ConnectivityState.Unavailable;
            super.onLost(network)
        }

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            _connectivityState.value = ConnectivityState.Available;
        }
    }

    init {
        connectivityManager.registerDefaultNetworkCallback(callback);
    }
}