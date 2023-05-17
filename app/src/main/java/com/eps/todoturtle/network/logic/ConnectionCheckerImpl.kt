package com.eps.todoturtle.network.logic

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import com.eps.todoturtle.MainActivity
import com.eps.todoturtle.shared.logic.extensions.dataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

enum class NetworkPreference {
    ONLY_WIFI,
    DATA_AND_WIFI
}

enum class WifiStatus {
    INTERNET_CONNECTION,
    ERROR_NO_INTERNET
}

enum class CellularStatus {
    INTERNET_CONNECTION,
    ERROR_NO_INTERNET
}

enum class NetworkAvailability {
    AVAILABLE,
    ERROR_WIFI_NOT_CONNECTED,
    ERROR_NOT_CONNECTED
}

class ConnectionCheckerImpl(private val context: Context) : ConnectionChecker {
    private val connectivityManager =
        context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val networkPreferenceChannel: Channel<NetworkPreference> = Channel(UNLIMITED)
    private val wifiStatusChannel: Channel<WifiStatus> = Channel(UNLIMITED)
    private val cellularStatusChannel: Channel<CellularStatus> = Channel(UNLIMITED)

    private val networkPreference: Flow<NetworkPreference> =
        networkPreferenceChannel.receiveAsFlow()
    private val wifiStatus: Flow<WifiStatus> = wifiStatusChannel.receiveAsFlow()
    private val cellularStatus: Flow<CellularStatus> = cellularStatusChannel.receiveAsFlow()

    override val networkAvailability: Flow<NetworkAvailability>


    init {
        runBlocking(Dispatchers.IO) {
            networkPreferenceChannel.send(NetworkPreference.ONLY_WIFI)
            wifiStatusChannel.send(WifiStatus.ERROR_NO_INTERNET)
            cellularStatusChannel.send(CellularStatus.ERROR_NO_INTERNET)
        }
        registerForWifiStateUpdates()
        registerForCellularStateUpdates()
        registerForNetworkPreferenceUpdates()
        networkAvailability =
            combine(networkPreference, wifiStatus, cellularStatus) { pref, wifi, cellular ->
                Log.e("ConnectionCheckerImpl", "pref: $pref, wifi: $wifi, cellular: $cellular")
                when (pref) {
                    NetworkPreference.ONLY_WIFI -> {
                        when (wifi) {
                            WifiStatus.INTERNET_CONNECTION -> NetworkAvailability.AVAILABLE
                            WifiStatus.ERROR_NO_INTERNET -> NetworkAvailability.ERROR_WIFI_NOT_CONNECTED
                        }
                    }

                    NetworkPreference.DATA_AND_WIFI -> {
                        when (wifi) {
                            WifiStatus.INTERNET_CONNECTION -> NetworkAvailability.AVAILABLE
                            WifiStatus.ERROR_NO_INTERNET -> {
                                when (cellular) {
                                    CellularStatus.INTERNET_CONNECTION -> NetworkAvailability.AVAILABLE
                                    CellularStatus.ERROR_NO_INTERNET -> NetworkAvailability.ERROR_NOT_CONNECTED
                                }
                            }
                        }
                    }
                }
            }
    }

    private fun registerForNetworkPreferenceUpdates() {
        context.dataStore.data.onEach { preferences ->
            if (preferences.onlyWifi)
                networkPreferenceChannel.send(NetworkPreference.ONLY_WIFI)
            else
                networkPreferenceChannel.send(NetworkPreference.DATA_AND_WIFI)
        }.launchIn(scope)
    }

    private fun registerForWifiStateUpdates() {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()

        connectivityManager.registerNetworkCallback(
            networkRequest,
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    runBlocking(Dispatchers.IO) {
                        wifiStatusChannel.send(WifiStatus.INTERNET_CONNECTION)
                    }
                }

                override fun onLost(network: Network) {
                    runBlocking(Dispatchers.IO){
                        wifiStatusChannel.send(WifiStatus.ERROR_NO_INTERNET)
                    }
                }

                override fun onCapabilitiesChanged(
                    network: Network,
                    networkCapabilities: NetworkCapabilities
                ) {
                    super.onCapabilitiesChanged(network, networkCapabilities)
                    if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET))
                        runBlocking(Dispatchers.IO) {
                            cellularStatusChannel.send(CellularStatus.INTERNET_CONNECTION)
                        }
                    else
                        runBlocking(Dispatchers.IO) {
                            cellularStatusChannel.send(CellularStatus.ERROR_NO_INTERNET)
                        }
                }
            })
    }


    private fun registerForCellularStateUpdates() {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()

        connectivityManager.registerNetworkCallback(
            networkRequest,
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    runBlocking(Dispatchers.IO) {
                        cellularStatusChannel.send(CellularStatus.INTERNET_CONNECTION)
                    }
                }

                override fun onLost(network: Network) {
                    runBlocking(Dispatchers.IO) {
                        cellularStatusChannel.send(CellularStatus.ERROR_NO_INTERNET)
                    }
                }

                override fun onCapabilitiesChanged(
                    network: Network,
                    networkCapabilities: NetworkCapabilities
                ) {
                    super.onCapabilitiesChanged(network, networkCapabilities)
                    if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET))
                        runBlocking(Dispatchers.IO) {
                            cellularStatusChannel.send(CellularStatus.INTERNET_CONNECTION)
                        }
                    else
                        runBlocking(Dispatchers.IO) {
                            cellularStatusChannel.send(CellularStatus.ERROR_NO_INTERNET)
                        }
                }
            })
    }
}