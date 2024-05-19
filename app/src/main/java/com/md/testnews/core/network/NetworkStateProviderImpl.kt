package com.md.testnews.core.network

import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class NetworkStateProviderImpl(
    private val connectivityManager: ConnectivityManager
) : NetworkStateProvider {

    override fun isOnline(): Boolean {
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            ?.let { networkCapabilities ->
                return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                        || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            }
        return false
    }
}