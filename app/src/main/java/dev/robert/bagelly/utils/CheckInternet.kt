package dev.robert.bagelly.utils

import android.content.Context
import android.net.ConnectivityManager

object CheckInternet {
    fun isConnected(context: Context): Boolean {
        val connectivityManager = context
            .getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val wifiNetInfo = connectivityManager
            .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        val mobileNetInfo = connectivityManager
            .getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        return wifiNetInfo != null && wifiNetInfo.isConnected
                || mobileNetInfo != null && mobileNetInfo.isConnected
    }
}