package com.axmor.ipc_sample.utils

import android.app.Notification
import android.content.Context
import android.support.v4.app.NotificationCompat
import com.axmor.ipc_sample.R

/**
 * Created by akolodyazhnyy on 11/10/2017.
 */
class NotificationUtils private constructor() {

    init {
        throw UnsupportedOperationException()
    }

    companion object {
        val NOTIFICATION_ID_TRACKING_SERVICE = 1

        fun buildTrackingServiceNotification(context: Context): Notification {
            return NotificationCompat.Builder(context)
                    .setContentTitle(context.getText(R.string.notification_title))
                    .setContentText(context.getText(R.string.notification_content))
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setShowWhen(false)
                    .build()
        }
    }
}
