package com.axmor.kash.toolset.service

import android.os.Binder
import com.axmor.kash.toolset.service.interfaces.Composite

/**
 * Created by akolodyazhnyy on 8/22/2017.
 */

/**
 * Binder that return from Android Service to Client (Activity or Fragment) Composite Interface that can give Components.
 * @see com.axmor.kash.toolset.service.CompositeService - sending.
 * @see com.axmor.kash.toolset.service.connection.CompositeServiceConnection - usage.
 */

open class ServiceBinder(private var core: ServiceCore?) : Binder() {

    fun getComposite(): Composite? {
        return core
    }

}