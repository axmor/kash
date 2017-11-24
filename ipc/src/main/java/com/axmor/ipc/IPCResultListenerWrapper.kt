package com.axmor.ipc

import android.os.*


/**
 * Created by akolodyazhnyy on 11/10/2017.
 */
//TODO: Implement DeathRecipient.
//TODO: Guarantee that onResult will be called only once.
class IPCResultListenerWrapper<T>(private val delegate: IPCResultListener<T>, callbackLooper: Looper) {

    private val callbackHandler: CallbackHandler

    val messenger: Messenger

    init {
        callbackHandler = CallbackHandler(callbackLooper)
        messenger = Messenger(callbackHandler)
    }

    private inner class CallbackHandler(looper: Looper) : Handler(looper) {

        override fun handleMessage(msg: Message) {
            if (msg.what !== MSG_ON_RESULT) {
                throw RuntimeException("Unknown message")
            }

            val data = msg.getData()
            data.setClassLoader(IPCResultListener::class.java!!.getClassLoader())
            val result = data.getParcelable<IPCResult<T>>(KEY_RESULT)

            delegate.onResult(result)
        }
    }

    companion object {

        val MSG_ON_RESULT = 1

        val KEY_RESULT = "result"
    }
}
