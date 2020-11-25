package com.example.gelderlandplein.helpers

import android.content.Context
import android.net.ConnectivityManager

object NetworkMonitorHelper {
    fun isConnectedToNetwork(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var isConnected = false
        val activeNetwork = connectivityManager.activeNetworkInfo
        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting
        return isConnected
    }
}