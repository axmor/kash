package com.axmor.kash.toolset.service.connection

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.axmor.kash.toolset.BuildConfig
import com.axmor.kash.toolset.service.CompositeService
import com.axmor.kash.toolset.service.ServiceBinder
import com.axmor.kash.toolset.service.interfaces.Composite

/**
 * Created by akolodyazhnyy on 8/31/2017.
 */

/**
 * Wrapper on ServiceConnection that connect ConnectionNode to CompositeService.
 * @see com.axmor.kash.ui.mvvm.KashViewModel - usage.
 */

open class CompositeServiceConnection<T>(private val context: Context, val rootConnection: ConnectionNode, val serviceClass: Class<T>) where T : CompositeService {

    private var serviceIntent: Intent? = null

    init {
        this.serviceIntent = Intent(context, serviceClass)
    }

    fun onStart() {
        var bindFlags = Context.BIND_AUTO_CREATE
        if (BuildConfig.DEBUG) {
            bindFlags = bindFlags or Context.BIND_DEBUG_UNBIND
        }

        context.bindService(serviceIntent, serviceConnection, bindFlags)
    }

    fun onStop() {
        if (rootConnection.isConnected(serviceClass)) {
            onDisconnect()
        }

        context.unbindService(serviceConnection)
    }

    private fun onConnect(composite: Composite) {
        rootConnection.onServicesConnected(composite, serviceClass)
    }

    private fun onDisconnect() {
        rootConnection.onServicesDisconnected(serviceClass)
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, binder: IBinder?) {
            if (null == binder || binder !is ServiceBinder) {
                throw RuntimeException("Bad service binder.")
            }

            val composite = binder.getComposite() ?: throw IllegalStateException("Composite is null.")

            onConnect(composite)
        }

        override fun onServiceDisconnected(name: ComponentName) {
            onDisconnect()
        }
    }


}