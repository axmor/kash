package com.axmor.kash.toolset.service

import android.content.Context
import com.axmor.kash.toolset.service.interfaces.Component
import com.axmor.kash.toolset.service.interfaces.CompositeContext

/**
 * Created by akolodyazhnyy on 8/22/2017.
 */

/**
 * Base implementation of com.axmor.kash.toolset.service.CompositeContext that adding application context.
 * @see com.axmor.kash.toolset.service.ServiceCore activateComponent() -  usage.
 */

abstract class ComponentContext(val aContext: Context,val lifetimeListener: Component.LifetimeListener) : CompositeContext {

    fun aContext(): Context {
        return aContext
    }

    override fun lifetimeListener(): Component.LifetimeListener {
        return lifetimeListener
    }
}