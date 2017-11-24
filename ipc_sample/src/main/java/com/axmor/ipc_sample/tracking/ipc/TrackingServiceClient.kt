package com.axmor.ipc_sample.tracking.ipc

import android.os.*
import android.util.Log
import com.axmor.ipc.*
import com.axmor.ipc_sample.tracking.TrackingService


/**
 * Created by akolodyazhnyy on 11/10/2017.
 */
class TrackingServiceClient(messengerBinder: IBinder, private val callbackLooper: Looper) : ITrackingService {

    private val serviceMessenger: Messenger

    override fun getStateObservable(): IPCObservable<TrackingService.PublicState> {
        val message = Message.obtain()
        message.what = TrackingServiceContract.MSG_GET_STATE_OBSERVABLE

        val observableClient = IPCObservableClient<TrackingService.PublicState>(callbackLooper)
        message.replyTo = observableClient.messenger

        sendOrThrow(message)
        return observableClient
    }

    init {
        this.serviceMessenger = Messenger(messengerBinder)
    }


    private fun <T> sendOrSendError(message: Message, listenerWrapper: IPCResultListenerWrapper<T>) {
        try {
            serviceMessenger.send(message)
        } catch (e: RemoteException) {
            Log.e(TAG, "sendOrSendError: ", e)

            //Call through wrapper's messenger, so that it will be called on callbackLooper.
            val proxy = IPCResultListenerProxy<T>(listenerWrapper.messenger)
            val errorResult = IPCResult.error<T>("Failed to send message to WorkerService")
            proxy.onResult(errorResult)
        }

    }

    //Our observables don't have any way to receive errors :(
    private fun sendOrThrow(message: Message) {
        try {
            serviceMessenger.send(message)
        } catch (e: RemoteException) {
            throw RuntimeException(e)
        }

    }

    companion object {

        val TAG = "WorkerServiceClient"
    }
}
