package com.axmor.kash.ui.mvvm

import android.app.Application
import com.axmor.kash.toolset.service.CompositeService
import com.axmor.kash.toolset.service.connection.CompositeServiceConnection
import com.axmor.kash.toolset.service.connection.ConnectionNode
import com.axmor.kash.toolset.service.interfaces.Composite

/**
 * Created by akolodyazhnyy on 12/11/2017.
 */
abstract class KashServiceViewModel(application: Application) : KashViewModel(application), ConnectionNode {

    protected var serviceConnections: MutableList<CompositeServiceConnection<CompositeService>> = ArrayList()
    protected var services: HashMap<Class<*>, Composite> = HashMap()

    protected fun <T> connectToService(serviceClass: Class<T>) where T : CompositeService {
        val conn = CompositeServiceConnection(getApplication(), this, serviceClass)
        conn.onStart()
        serviceConnections.add(conn as CompositeServiceConnection<CompositeService>)
    }

    /**
     * If u want to put service directly (for tests)
     * */
    fun setService(service: CompositeService) {
        services.put(service.javaClass, service.getComposite())
    }

    fun getService(clazz: Class<*>): Composite? {
        if (services.containsKey(clazz)) {
            return services[clazz]
        }
        return null
    }

    override fun onServicesConnected(composite: Composite, serviceClass: Class<*>) {
        services.put(serviceClass, composite)
    }

    override fun onServicesDisconnected(serviceClass: Class<*>) {
        services.remove(serviceClass)
    }

    override fun isConnected(serviceClass: Class<*>) = services.containsKey(serviceClass)

    override fun onCleared() {
        super.onCleared()
        serviceConnections.forEach { it.onStop() }
    }

}