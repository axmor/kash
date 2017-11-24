package com.axmor.kash.ui.utils

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager

/**
 * Created by akolodyazhnyy on 8/28/2017.
 */

@Throws(PackageManager.NameNotFoundException::class)
private fun getPackageInfo(context: Context): PackageInfo {
    return context.getPackageManager().getPackageInfo(context.getPackageName(), 0)
}

fun Context.getVersionName(): String {
    try {
        return getPackageInfo(this).versionName
    } catch (ignored: PackageManager.NameNotFoundException) {
        return "unknown"
    }

}

fun Context.getVersionCode(): String {
    try {
        return getPackageInfo(this).versionCode.toString()
    } catch (ignored: PackageManager.NameNotFoundException) {
        return "unknown"
    }

}