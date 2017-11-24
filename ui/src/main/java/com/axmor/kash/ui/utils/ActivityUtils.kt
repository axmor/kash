package com.axmor.kash.ui.utils

import android.app.ActivityManager
import android.content.Context
import android.os.Build

/**
 * Created by akolodyazhnyy on 9/25/2017.
 */

fun hasAnyTask(context: Context): Boolean {
    val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val recentTasks = activityManager.appTasks
        for (task in recentTasks) {
            if (task.taskInfo!!.topActivity.packageName.toString()
                    .equals(context.getPackageName().toString(), ignoreCase = true)) return true
        }
        return false
    } else {
        val recentTasks = activityManager.getRunningTasks(Integer.MAX_VALUE)
        for (task in recentTasks) {
            if (task.topActivity.packageName.toString()
                    .equals(context.getPackageName().toString(), ignoreCase = true)) return true
        }
        return false
    }
}