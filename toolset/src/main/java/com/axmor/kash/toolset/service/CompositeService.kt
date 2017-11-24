package com.axmor.kash.toolset.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.axmor.kash.toolset.service.interfaces.CompositeBuilder

/**
 * Created by akolodyazhnyy on 8/22/2017.
 */

/**
 * Android service that delegate logic to ServiceCore.
 * @see com.axmor.kash.sample.core.AppService - implementation.
 * @see com.axmor.kash.toolset.service.ServiceCore
 */

abstract class CompositeService : Service() {

    private var core: ServiceCore? = null

    override fun onCreate() {
        super.onCreate()

        core = ServiceCore(this)
        buildComposite(core)
    }

    protected abstract fun buildComposite(builder: CompositeBuilder?)

    override fun onDestroy() {
        super.onDestroy()

        core!!.shutdown()
        core = null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        core!!.activate()
        return Service.START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        core!!.activate()
        core!!.setHaveBindings(true)
        return ServiceBinder(core)
    }

    override fun onRebind(intent: Intent) {
        core!!.setHaveBindings(true)
    }

    override fun onUnbind(intent: Intent): Boolean {
        core!!.setHaveBindings(false)
        return true
    }
}