package com.axmor.kash.toolset.service

import android.app.Service
import android.content.Context
import com.axmor.kash.toolset.safety.aFalse
import com.axmor.kash.toolset.safety.aTrue
import com.axmor.kash.toolset.service.interfaces.Component
import com.axmor.kash.toolset.service.interfaces.Composite
import com.axmor.kash.toolset.service.interfaces.CompositeBuilder

/**
 * Created by akolodyazhnyy on 8/22/2017.
 */

/**
 * Main logic of Android Service (CompositeService class), managing Components and service LifeTime.
 * Clients works with this instance as Composite to get Components by getComponent(ComponentInterface).
 * @see com.axmor.kash.ui.mvvm.KashViewModel onServicesConnected - usage as Composite.
 */

open class ServiceCore : Composite, CompositeBuilder {
    private val lifeline: ServiceLifeline
    private val context: Context

    private val components: HashMap<Class<*>, Component<*>> = hashMapOf()
    private var active: Boolean = false
    private var shutdown: Boolean = false

    constructor(hostService: Service) {
        context = hostService
        lifeline = ServiceLifeline(hostService)
    }

    /**
     * activate all components when there new bind (and if isn't already active).
     * @see com.axmor.kash.toolset.service.CompositeService
     */
    fun activate() {
        if (active) {
            return
        }

        active = true
        for (entry in components.entries) {
            activateComponent(entry.value)
        }
    }

    private fun activateComponent(component: Component<*>) {
        val componentLifetimeListener = ComponentLifetimeListener(lifeline)
        component.activate(object : ComponentContext(context, componentLifetimeListener) {
            override fun <TService> getService(tClass: Class<TService>): TService {
                return this@ServiceCore.getComponent(tClass)
            }
        })
    }

    /**
     * deactivate all components when there no binds plus after lifeline waiting.
     * @see com.axmor.kash.toolset.service.ServiceLifeline
     */
    fun deactivate() {
        if (!active) {
            return
        }

        active = false
        for (entry in components.entries) {
            deactivateComponent(entry.value)
        }
    }

    private fun deactivateComponent(component: Component<*>) {
        component.deactivate()
    }

    fun shutdown() {
        aFalse(shutdown)
        if (shutdown) {
            return
        }
        shutdown = true

        deactivate()
        components.clear()

        // TODO: we need to ensure somehow that all subscribers have been cleared
    }

    fun setHaveBindings(haveBindings: Boolean) {
        aFalse(shutdown)
        lifeline.setHaveBindings(haveBindings)
    }


    override fun <TService> addComponent(key: Class<TService>, component: Component<TService>) {
        aFalse(shutdown)
        aFalse(components.containsKey(key))
        if (shutdown) {
            return
        }

        components.put(key, component)
        if (active) {
            activateComponent(component)
        }
    }

    override fun <TService> getComponent(tClass: Class<TService>): TService {
        aFalse(shutdown)
        if (shutdown) {
            throw IllegalStateException("This service has been shutdown.")
        }

        aTrue(active)
        if (!active) {
            throw IllegalStateException("This service has not been activated.")
        }

        var component = components[tClass]

        component ?: throw IllegalStateException("Requested unknown component.");


        var service = tClass.cast(component.getService())
        service ?: throw IllegalStateException("Component failed to provide it's service.");

        return service
    }
}
