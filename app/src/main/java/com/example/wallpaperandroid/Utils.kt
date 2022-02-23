package com.example.wallpaperandroid

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

object Utils {

    /*checking internet connectivity*/
    fun isConnected(context: Context?): Boolean {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

}