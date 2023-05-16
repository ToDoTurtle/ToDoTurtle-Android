package com.eps.todoturtle.network

import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.lifecycleScope
import com.eps.todoturtle.MainActivity
import com.eps.todoturtle.shared.logic.extensions.dataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ConnectionCheckerImpl(private val context: MainActivity) : ConnectionChecker {
    private val connectivityManager =
        context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    private var hasInternetOverWifi = false
    override val isNetworkAvailable = MutableStateFlow(false)


    init {
        registerForNetworkState()
        context.lifecycleScope.launch(Dispatchers.IO) {
            context.dataStore.data.collect { preferences ->
                isNetworkAvailable.value =
                    !preferences.onlyWifi || (preferences.onlyWifi && hasInternetOverWifi)
            }
        }
    }

    private fun registerForNetworkState() {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()

        connectivityManager.registerNetworkCallback(
            networkRequest,
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    hasInternetOverWifi = true
                }

                override fun onLost(network: Network) {
                    hasInternetOverWifi = false
                }

                override fun onCapabilitiesChanged(
                    network: Network,
                    networkCapabilities: NetworkCapabilities
                ) {
                    super.onCapabilitiesChanged(network, networkCapabilities)
                    hasInternetOverWifi =
                        networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                }
            })
    }
}