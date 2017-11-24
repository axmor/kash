package com.axmor.kash.ui.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by akolodyazhnyy on 9/3/2017.
 */

fun Context.isNetworkConnectionAvailable(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = cm.activeNetworkInfo
    if (activeNetwork != null) {
        return activeNetwork.isConnectedOrConnecting
    } else {
        return false
    }
}