package com.axmor.ipc

import android.os.*
import android.util.Log


/**
 * Created by akolodyazhnyy on 11/10/2017.
 */
class IPCObservableClient<T>(private val callbackLooper: Looper) : IPCObservable<T> {
    val messenger: Messenger

    private var observableProxy: IPCObservableProxy<T>? = null

    private val observers = HashMap<IPCObserver<T>, IPCObserverWrapper<T>>()

    init {
        this.messenger = Messenger(CallbackHandler(callbackLooper))
    }

    private fun onObservableCreated(observableProxy: IPCObservableProxy<T>) {
        if (this.observableProxy != null) {
            throw IllegalStateException("observableProxy already set")
        }

        this.observableProxy = observableProxy
        registerAllObservers()
    }

    private fun registerAllObservers() {
        synchronized(observers) {
            for (wrapper in observers.values) {
                observableProxy!!.sendAddObserver(wrapper)
            }
        }
    }

    override fun addObserver(observer: IPCObserver<T>) {
        if (observer == null) {
            throw NullPointerException()
        }

        synchronized(observers) {
            var wrapper: IPCObserverWrapper<T>? = observers.get(observer)

            if (wrapper != null) {
                Log.e(TAG, "addObserver: Already have wrapper for observer, ignoring")
                return
            }

            wrapper = IPCObserverWrapper(observer, callbackLooper)
            observers.put(observer, wrapper)

            if (observableProxy != null) {
                observableProxy!!.sendAddObserver(wrapper)
            }
        }
    }

    override fun removeObserver(observer: IPCObserver<T>) {
        if (observer == null) {
            throw NullPointerException()
        }

        synchronized(observers) {
            val wrapper = observers.get(observer)

            if (wrapper == null) {
                Log.e(TAG, "removeObserver: No wrapper found for observer, ignoring")
                return
            }

            wrapper!!.destroy()
            observers.remove(observer)

            if (observableProxy != null) {
                observableProxy!!.sendRemoveObserver(wrapper)
            }
        }
    }

    private inner class CallbackHandler(looper: Looper) : Handler(looper) {

        override fun handleMessage(msg: Message) {
            if (msg.what != MSG_OBSERVABLE_CREATED) {
                throw RuntimeException("Unknown message")
            }

            val data = msg.getData()
            data.setClassLoader(IPCObservableClient::class.java.classLoader)

            val observableMessenger = data.getParcelable<Messenger>(KEY_OBSERVABLE)
            val observableProxy = IPCObservableProxy<T>(observableMessenger)
            onObservableCreated(observableProxy)
        }
    }

    companion object {
        val TAG = "WorkerObservableClient"

        val MSG_OBSERVABLE_CREATED = 1
        val KEY_OBSERVABLE = "observable"
    }
}
