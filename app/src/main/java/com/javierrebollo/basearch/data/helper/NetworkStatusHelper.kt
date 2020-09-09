package com.javierrebollo.basearch.data.helper

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import com.javierrebollo.basearch.utils.Tracker
import com.javierrebollo.basearch.utils.Tracker.NETWORK_STATE_CALLBACK
import java.lang.Exception

class NetworkStatusHelper(private val context: Context) {

    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    var isOnline: Boolean = false
        private set
        get() {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                NetworkStatusCallbackHelper.isOnline
            } else {
                val netInfo = connectivityManager.activeNetworkInfo
                netInfo != null && netInfo.isConnected
            }
        }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            NetworkStatusCallbackHelper.initNetworkCallback(connectivityManager)
        }
    }

    fun unregisterNetworkCallback() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            NetworkStatusCallbackHelper.unregisterNetworkCallback(connectivityManager)
        }
    }

}

@SuppressLint("NewApi")
object NetworkStatusCallbackHelper {

    private val TAG = NetworkStatusHelper::class.java.simpleName

    var isOnline = false
        private set

    @Volatile private var networkCallback: ConnectivityManager.NetworkCallback? = null

    fun initNetworkCallback(connectivityManager: ConnectivityManager) {
        networkCallback ?: synchronized(this) {
            networkCallback ?: object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)

                    isOnline = true
                }

                override fun onUnavailable() {
                    super.onUnavailable()

                    isOnline = false
                }
            }.also {
                networkCallback = it
                connectivityManager.registerDefaultNetworkCallback(it)
            }
        }
    }

    fun unregisterNetworkCallback(connectivityManager: ConnectivityManager) {
        networkCallback?.let {
            try {
                connectivityManager.unregisterNetworkCallback(it)
            } catch (ex: Exception) {
                Tracker.logError(NETWORK_STATE_CALLBACK, TAG, ex.message.toString(), ex)
            }

        }
    }

}