package com.axmor.kash.toolset.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.util.Log

/**
 * Created by akolodyazhnyy on 8/22/2017.
 */

/**
 * Class that detects bindings to Android Service (com.axmor.kash.toolset.service.CompositeService).
 * If last connection unbinds by component it starts Timer for SHUTDOWN_TIMEOUT_MILLIS after that stop Android Service.
 * If there new connection while Timer works it interrupt Timer.
 * Also u can lock this logic by #lock method.
 * @see com.axmor.kash.toolset.service.ServiceCore - usage.
 */

open class ServiceLifeline {

    protected var SHUTDOWN_TIMEOUT_MILLIS = (5 * 60 * 1000).toLong()

    protected var SERVICE_LOCK_FREE = 0

    protected val shutdownHandler = Handler()

    protected val serviceLockContext: Context
    protected val serviceLockIntent: Intent
    protected val shutdownRunnable: Runnable

    private var haveBindings = false
    private var serviceLockCounter = SERVICE_LOCK_FREE

    constructor(hostService: Service) {
        serviceLockContext = hostService
        serviceLockIntent = Intent(hostService, hostService::class.java)
        shutdownRunnable = Runnable {
            Log.i("ServiceLifeline", "shutdownRunnable::run:: shutting down")
            serviceLockContext.stopService(serviceLockIntent)
        }

    }

    fun setHaveBindings(haveBindings: Boolean) {
        this.haveBindings = haveBindings
        if (haveBindings) {
            ensureServiceRunning()
        } else {
            tryScheduleShutdown()
        }
    }

    private fun isLockFree(): Boolean {
        return SERVICE_LOCK_FREE == serviceLockCounter
    }

    fun lock() {
        if (isLockFree()) {
            ensureServiceRunning()
        }

        serviceLockCounter++
    }

    fun unlock() {
        serviceLockCounter--

        if (isLockFree()) {
            tryScheduleShutdown()
        }
    }

    private fun ensureServiceRunning() {
        shutdownHandler.removeCallbacks(shutdownRunnable)
        serviceLockContext.startService(serviceLockIntent)

        Log.i("ServiceLifeline", "ensureServiceRunning:: ensured service running")
    }

    private fun tryScheduleShutdown() {
        if (haveBindings || !isLockFree()) {
            return
        }

        shutdownHandler.removeCallbacks(shutdownRunnable)
        shutdownHandler.postDelayed(shutdownRunnable, SHUTDOWN_TIMEOUT_MILLIS)

        Log.i("ServiceLifeline", "tryScheduleShutdown:: scheduled shutdown in " + (SHUTDOWN_TIMEOUT_MILLIS / 1000).toString() + " seconds")
    }

}