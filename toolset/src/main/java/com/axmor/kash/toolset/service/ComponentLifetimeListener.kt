package com.axmor.kash.toolset.service

import com.axmor.kash.toolset.service.interfaces.Component

/**
 * Created by akolodyazhnyy on 8/22/2017.
 */

/**
 * Catcher of lock unlock by clients to lock unlock CompositeService.
 * @see com.axmor.kash.toolset.service.ServiceCore activateComponent() - usage.
 */

open class ComponentLifetimeListener(var lifetime: ServiceLifeline) : Component.LifetimeListener {

    private var locked = false

    override fun lock() {
        if (locked) {
            return
        }

        locked = true
        lifetime.lock()
    }

    override fun unlock() {
        if (!locked) {
            return
        }

        locked = false
        lifetime.unlock()
    }
}
