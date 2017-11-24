package com.axmor.ipc

import android.os.*
import android.util.Log


/**
 * Created by akolodyazhnyy on 11/10/2017.
 */
class IPCObservableWrapper<T>(private val delegate: IPCObservable<T>, callbackLooper: Looper) : IPCObserverProxy.ProxyDeathListener<T> {
    private val callbackHandler: CallbackHandler
    val messenger: Messenger

    private val observers = HashMap<IBinder, IPCObserverProxy<T>>()

    init {
        this.callbackHandler = CallbackHandler(callbackLooper)
        this.messenger = Messenger(callbackHandler)
    }

    private fun addObserver(observerMessenger: Messenger) {
        val binder = observerMessenger.binder

        if (observers.containsKey(binder)) {
            Log.e(TAG, "addObserver: Observer already registered")
            return
        }

        val observerProxy = IPCObserverProxy<T>(observerMessenger)
        //NOTE: We need to call this before delegate.addObserver() because delegate can call onChange.
        //On the other hand onProxyDead can be called from inside of registerDeathListener().
        //That's ok, since we just post runnable on CallbackHandler that will actually remove observer.
        observerProxy.registerDeathListener(this)

        observers.put(binder, observerProxy)
        delegate.addObserver(observerProxy)
    }

    private fun removeObserver(observerMessenger: Messenger) {
        val binder = observerMessenger.binder

        val observerProxy = observers.get(binder)

        if (observerProxy == null) {
            Log.e(TAG, "removeObserver: Observer not registered")
            return
        }

        observers.remove(binder)
        delegate.removeObserver(observerProxy)
    }

    override fun onProxyDead(observerMessenger: Messenger) {
        callbackHandler.post { removeObserver(observerMessenger) }
    }

    private inner class CallbackHandler(looper: Looper) : Handler(looper) {

        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MSG_ADD_OBSERVER -> {
                    val data = msg.getData()

                    var observerMessenger = data.getParcelable<Messenger>(KEY_OBSERVER)
                    if (observerMessenger == null) {
                        throw NullPointerException()
                    }
                    addObserver(observerMessenger)
                }
                MSG_REMOVE_OBSERVER -> {
                    val data = msg.getData()

                    var observerMessenger = data.getParcelable<Messenger>(KEY_OBSERVER)
                    if (observerMessenger == null) {
                        throw NullPointerException()
                    }
                    removeObserver(observerMessenger)
                }
                else -> throw RuntimeException("Unknown message")
            }
        }
    }

    companion object {

        val TAG = "WorkerObservableWrapper"

        val MSG_ADD_OBSERVER = 1
        val MSG_REMOVE_OBSERVER = 2
        val KEY_OBSERVER = "observer"
    }
}
