package com.axmor.ipc_sample.tracking

import android.app.Service
import android.content.Intent
import android.os.*
import android.support.annotation.WorkerThread
import android.util.Log
import com.axmor.ipc.IPCObservable
import com.axmor.ipc.IPCValueObservable
import com.axmor.ipc_sample.tracking.ipc.TrackingServiceStub
import com.axmor.ipc_sample.utils.NotificationUtils
import java.io.Serializable


/**
 * Created by akolodyazhnyy on 11/10/2017.
 */

/**
 * Foreground service that tracks location.
 * <p>
 * <b>WARNING:</b> Due to a <a href="https://issuetracker.google.com/issues/37070074#comment15">Bug in Android 6</a>
 * this service runs in a separate process!
 */

class TrackingService : Service() {

    private var wakeLock: PowerManager.WakeLock? = null

    private var workerThread: HandlerThread? = null
    private var workerServiceImpl: TrackingServiceImpl? = null

    private var workHandler: Handler? = null

    private var state: State? = null
    var publicState: IPCValueObservable<PublicState> = IPCValueObservable(PublicState(State.IDLE))

    private val lock = Any()

    enum class State {
        IDLE,
        TRACKING
    }

    class PublicState(val state: State) : Serializable

    override fun onCreate() {
        Log.d(TAG, "onCreate: Creating!")
        super.onCreate()
        setState(State.IDLE)

        createAndAcquireWakeLock()
        createWorkerThread()

        tryToRestoreUserContext()
    }

    override fun onBind(intent: Intent): IBinder {
        return workerServiceImpl!!.binder
    }

    private fun createAndAcquireWakeLock() {
        //TODO: Acquire wake lock only when really needed.
        val powerManager = getSystemService(POWER_SERVICE) as PowerManager
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, WAKELOCK_TAG)
        wakeLock!!.acquire()
    }

    private fun releaseWakeLock() {
        wakeLock!!.release()
    }

    private fun createWorkerThread() {
        workerThread = HandlerThread("Worker")
        workerThread!!.start()

        workerServiceImpl = TrackingServiceImpl(workerThread!!.looper)
        workHandler = Handler(workerThread!!.looper)
    }

    private fun startAsForegroundService() {
        startService(Intent(this, TrackingService::class.java))
        val notification = NotificationUtils.buildTrackingServiceNotification(this)
        startForeground(NotificationUtils.NOTIFICATION_ID_TRACKING_SERVICE, notification)
    }

    private fun stopForegroundService() {
        stopSelf()
        stopForeground(true)
    }

    private fun setState(state: State) {
        this.state = state
        publicState.setValue(PublicState(state))
    }

    private fun tryToRestoreUserContext() {

        synchronized(lock) {
            if (state != State.IDLE) {
                throw IllegalStateException("Current state must be IDLE, but found " + state!!)
            }
            setState(State.TRACKING)
        }

        startAsForegroundService()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: Destroying!")
        super.onDestroy()

        releaseWakeLock()
    }

    @WorkerThread
    private fun doGetStateObservable(): IPCObservable<PublicState> {
        return publicState
    }




    private inner class TrackingServiceImpl(looper: Looper) : TrackingServiceStub(looper) {

        override fun getStateObservable() =  doGetStateObservable()
    }

    companion object {

        val TAG = "TrackingService"
        val WAKELOCK_TAG = "TrackingService"

    }
}
