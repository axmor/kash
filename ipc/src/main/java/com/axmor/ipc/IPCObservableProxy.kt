package com.axmor.ipc

import android.os.Bundle
import android.os.Message
import android.os.Messenger
import android.os.RemoteException
import android.util.Log


/**
 * Created by akolodyazhnyy on 11/10/2017.
 */
class IPCObservableProxy<T>(private val messenger: Messenger) {

    fun sendAddObserver(observerWrapper: IPCObserverWrapper<T>) {
        val message = Message.obtain()
        message.what = IPCObservableWrapper.MSG_ADD_OBSERVER

        val data = Bundle()
        data.putParcelable(IPCObservableWrapper.KEY_OBSERVER, observerWrapper.messenger)

        message.setData(data)
        sendMessageOrThrow(message)
    }

    fun sendRemoveObserver(observerWrapper: IPCObserverWrapper<T>) {
        val message = Message.obtain()
        message.what = IPCObservableWrapper.MSG_REMOVE_OBSERVER

        val data = Bundle()
        data.putParcelable(IPCObservableWrapper.KEY_OBSERVER, observerWrapper.messenger)

        message.setData(data)
        sendMessageOrThrow(message)
    }

    /**
     * Since we add and remove observers asynchronously and can't signal errors to clients,
     * so just let it crash and hope that this won't happen.
     */
    private fun sendMessageOrThrow(message: Message) {
        try {
            messenger.send(message)
        } catch (e: RemoteException) {
            Log.e(TAG, "sendMessageOrThrow: Failed to send message, rethrowing ", e)
            throw RuntimeException("Failed to send message to service", e)
        }

    }

    companion object {
        val TAG = "WorkerObservableProxy"
    }
}
