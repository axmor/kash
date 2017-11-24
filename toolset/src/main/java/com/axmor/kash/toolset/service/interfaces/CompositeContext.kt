package com.axmor.kash.toolset.service.interfaces

/**
 * Created by akolodyazhnyy on 8/22/2017.
 */

/**
 * Interface that constrain implementation to return Component and lifetimeListener of component lifetime.
 * @see com.axmor.kash.toolset.service.ComponentContext - base implementation.
 */

interface CompositeContext {
    fun <TServiceComponentInterface> getService(tClass: Class<TServiceComponentInterface>): TServiceComponentInterface

    fun lifetimeListener(): Component.LifetimeListener
}