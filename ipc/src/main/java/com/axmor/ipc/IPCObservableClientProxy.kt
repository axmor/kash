package com.axmor.ipc

import android.os.Bundle
import android.os.Message
import android.os.Messenger
import android.os.RemoteException
import android.util.Log


/**
 * Created by akolodyazhnyy on 11/10/2017.
 */
class IPCObservableClientProxy<T>(private val messenger: Messenger) {

    fun onObservableCreated(observableWrapper: IPCObservableWrapper<T>) {
        val message = Message.obtain()
        message.what = IPCObservableClient.MSG_OBSERVABLE_CREATED

        val data = Bundle()
        data.putParcelable(IPCObservableClient.KEY_OBSERVABLE, observableWrapper.messenger)
        message.setData(data)

        try {
            messenger.send(message)
        } catch (e: RemoteException) {
            Log.e(TAG, "onObservableCreated: Failed to send message to WorkerObservableClient", e)
        }

    }

    companion object {

        val TAG = "WorkerObservableCP"
    }
}
