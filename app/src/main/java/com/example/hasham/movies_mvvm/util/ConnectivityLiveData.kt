package com.example.hasham.movies_mvvm.util

/**
 * Developed by hasham on 11/27/17.
 */

import android.arch.lifecycle.LiveData
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.os.Build

//This LiveData emits NetWork information when network availability status changes and there is an active observer to it
class ConnectivityLiveData(context: Context) : LiveData<Network>() {
    private val connectivityManager: ConnectivityManager

    private val listener = object : NetworkCallback() {
        override fun onAvailable(network: Network) {
            //this part runs on background thread so use postValue
            postValue(network)
        }

        override fun onLost(network: Network) {
            postValue(network)
        }
    }

    init {
        //get connectivity system service
        connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    override fun onActive() {
        //onActive is called when there is an active observer to this LiveData
        //since active LiveData observers are there, add network callback listener to connectivity manager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(listener)
        }
    }

    override fun onInactive() {
        //onActive is called when there is no active observer to this LiveData
        //as no active observers exist, remove network callback from connectivity manager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            connectivityManager.unregisterNetworkCallback(listener)
        }
    }
}