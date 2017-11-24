package com.axmor.ipc

import android.os.*


/**
 * Created by akolodyazhnyy on 11/10/2017.
 */
class IPCObserverWrapper<T>(@field:Volatile private var delegate: IPCObserver<T>?, callbackLooper: Looper) {

    private val callbackHandler: CallbackHandler

    val messenger: Messenger

    init {
        if (delegate == null) {
            throw NullPointerException()
        }
        callbackHandler = CallbackHandler(callbackLooper)
        messenger = Messenger(callbackHandler)
    }

    fun destroy() {
        delegate = null
    }

    private inner class CallbackHandler(looper: Looper) : Handler(looper) {

        override fun handleMessage(msg: Message) {
            if (msg.what !== MSG_ON_CHANGED) {
                throw RuntimeException("Unknown message")
            }

            val data = msg.getData()
            data.setClassLoader(IPCObserverWrapper::class.java.classLoader)
            var value = data.getParcelable<IPCParcelableValue<T>>(KEY_VALUE)
            if (value == null) {
                throw NullPointerException("ParcelableValue must be not null itself")
            }
            if (delegate != null) {
                delegate!!.onChanged(value.value)
            }
        }
    }

    companion object {

        val MSG_ON_CHANGED = 1

        val KEY_VALUE = "value"
    }
}
