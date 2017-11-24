package com.axmor.ipc_sample.tracking.ipc

import android.os.*
import com.axmor.ipc.IPCObservableClientProxy
import com.axmor.ipc.IPCObservableWrapper
import com.axmor.ipc_sample.tracking.TrackingService


/**
 * Created by akolodyazhnyy on 11/10/2017.
 */
abstract class TrackingServiceStub
/**
 * @param looper looper on which requests will be processed.
 */
(private val looper: Looper) : ITrackingService {
    private val handler: Handler
    private val messenger: Messenger

    val binder: IBinder
        get() = messenger.binder

    init {
        this.handler = WorkHandler(looper)
        this.messenger = Messenger(handler)
    }

    private inner class WorkHandler(looper: Looper) : Handler(looper) {

        override fun handleMessage(msg: Message) {
            when (msg.what) {
                TrackingServiceContract.MSG_GET_STATE_OBSERVABLE -> {
                    val clientProxy = IPCObservableClientProxy<TrackingService.PublicState>(msg.replyTo)

                    val observable = getStateObservable()
                    val observableWrapper = IPCObservableWrapper(observable, looper)

                    clientProxy.onObservableCreated(observableWrapper)
                }
                else -> throw RuntimeException("Unknown message, what =  " + msg.what)
            }
        }
    }
}
