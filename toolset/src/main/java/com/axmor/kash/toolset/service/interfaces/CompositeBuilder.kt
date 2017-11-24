package com.axmor.kash.toolset.service.interfaces

/**
 * Created by akolodyazhnyy on 8/22/2017.
 */

/**
 * Interface that constrain implementation to allow adding Service Components.
 * @see com.axmor.kash.toolset.service.ServiceCore - implementation.
 * @see com.axmor.kash.sample.core.AppService (sample module) for example of using.
 */

interface CompositeBuilder {
    /**
     * @param key Service Component Interface.
     * @param component Object that contains implementation of 'key' interface.
     */
    fun <TServiceComponentInterface> addComponent(key: Class<TServiceComponentInterface>, component: Component<TServiceComponentInterface>)
}