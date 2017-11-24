package com.axmor.ipc

import android.os.*
import android.support.v4.app.BundleCompat.getBinder
import android.util.Log


/**
 * Created by akolodyazhnyy on 11/10/2017.
 */
internal class IPCObserverProxy<T>(private val messenger: Messenger) : IPCObserver<T> {
    private var deathListener: ProxyDeathListener<T>? = null
    private var deathNotified: Boolean = false

    internal interface ProxyDeathListener<T> {
        /** Can be called on any thread  */
        fun onProxyDead(observerMessenger: Messenger)
    }

    override fun onChanged(value: T) {
        if (deathListener == null) {
            throw RuntimeException("No death listener registered")
        }

        val message = Message.obtain()
        message.what = IPCObserverWrapper.MSG_ON_CHANGED

        val data = Bundle()
        data.putParcelable(IPCObserverWrapper.KEY_VALUE, IPCParcelableValue(value))
        message.setData(data)

        try {
            messenger.send(message)
        } catch (e: RemoteException) {
            Log.e(TAG, "Caught exception while trying to deliver value: ", e)
            notifyDeath()
        }

    }

    fun registerDeathListener(deathListener: ProxyDeathListener<T>) {
        if (this.deathListener != null) {
            throw RuntimeException("Already have death listener")
        }

        this.deathListener = deathListener
        try {
            messenger.binder.linkToDeath(IBinder.DeathRecipient { this.notifyDeath() }, 0)
        } catch (e: RemoteException) {
            notifyDeath()
        }

    }

    @Synchronized private fun notifyDeath() {
        if (deathNotified) {
            Log.d(TAG, "notifyDeath: Already notified")
            return
        }
        deathListener!!.onProxyDead(messenger)
        deathNotified = true
    }

    companion object {

        val TAG = "WorkerObserverProxy"
    }
}
