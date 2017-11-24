package com.axmor.ipc

import android.os.Bundle
import android.os.Message
import android.os.Messenger
import android.os.RemoteException
import android.util.Log


/**
 * Created by akolodyazhnyy on 11/10/2017.
 */
class IPCResultListenerProxy<T>(private val messenger: Messenger) : IPCResultListener<T> {

    override fun onResult(result: IPCResult<T>) {
        val message = Message.obtain()
        message.what = IPCResultListenerWrapper.MSG_ON_RESULT

        val data = Bundle()
        data.putParcelable(IPCResultListenerWrapper.KEY_RESULT, result)
        message.setData(data)

        try {
            messenger.send(message)
        } catch (e: RemoteException) {
            Log.e(TAG, "Caught exception while trying to deliver result: ", e)
        }

    }

    companion object {

        val TAG = "WorkerResultLProxy"
    }
}
