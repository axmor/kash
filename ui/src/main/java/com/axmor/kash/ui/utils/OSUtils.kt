package com.axmor.kash.ui.utils

import android.os.Build

/**
 * Created by akolodyazhnyy on 11/17/2017.
 */

fun is50(): Boolean {
    return android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
}