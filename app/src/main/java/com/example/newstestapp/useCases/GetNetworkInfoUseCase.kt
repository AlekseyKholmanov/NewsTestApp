package com.example.newstestapp.useCases

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class GetNetworkInfoUseCase(
    private val networkManager: ConnectivityManager
) {
    fun isNetworkAvailable(): Boolean {
        val nw = networkManager.activeNetwork ?: return false
        val actNw = networkManager.getNetworkCapabilities(nw) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            //for other device how are able to connect with Ethernet
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> true
            //for check internet over Bluetooth
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            else -> false
        }
    }
}