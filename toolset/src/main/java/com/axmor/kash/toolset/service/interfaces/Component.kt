package com.axmor.kash.toolset.service.interfaces

import com.axmor.kash.toolset.service.ComponentContext

/**
 * Created by akolodyazhnyy on 8/22/2017.
 */

/**
 * Interface describe component that will be ran in CompositeService by Service Core.
 * @see com.axmor.kash.sample.core.reddit or com.axmor.kash.toolset.network .repository .local.db.service packages, contains classes that implements this interface.
 */

interface Component<TServiceComponentInterface> {

    /**
     * lock unlock for prevent service by shout downing
     */
    interface LifetimeListener {
        fun lock()
        fun unlock()
    }

    /**
     * Called when service get onBind
     */
    fun activate(context: ComponentContext)

    /**
     * Called when service closed
     */
    fun deactivate()

    /**
     * return implementation of component like Interface that it implements
     */
    fun getService(): TServiceComponentInterface?
}