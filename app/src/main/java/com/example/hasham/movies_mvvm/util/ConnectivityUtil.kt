@file:Suppress("SENSELESS_COMPARISON")

package com.example.hasham.projectk.util

import android.content.Context
import android.net.ConnectivityManager

/**
 * Developed by hasham on 11/15/17.
 */

class ConnectivityUtil {
    companion object {
        fun isConnectingToInternet(context: Context): Boolean {
            val connectivity = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivity != null) {
                val netInfo = connectivity.activeNetworkInfo
                return netInfo != null && netInfo.isConnected
            } else {
                return false
            }
        }
    }
}