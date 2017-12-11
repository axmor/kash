package com.axmor.kash.toolset.service.connection

import com.axmor.kash.toolset.service.CompositeService
import com.axmor.kash.toolset.service.interfaces.Composite

/**
 * Created by akolodyazhnyy on 8/31/2017.
 */

/**
 * Interface that need to be implemented by classes to connect to CompositeService to giving components.
 * @see com.axmor.kash.ui.mvvm.KashViewModel - usage.
 */

interface ConnectionNode {
    fun onServicesConnected(composite: Composite, serviceClass: Class<*>)

    fun onServicesDisconnected(serviceClass: Class<*>)

    fun isConnected(serviceClass: Class<*>): Boolean
}