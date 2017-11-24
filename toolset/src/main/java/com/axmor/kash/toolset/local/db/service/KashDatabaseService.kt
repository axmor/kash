package com.axmor.kash.toolset.local.db.service

import com.axmor.kash.toolset.local.db.KashDatabase
import com.axmor.kash.toolset.service.ComponentContext
import com.axmor.kash.toolset.service.interfaces.Component

/**
 * Created by akolodyazhnyy on 8/28/2017.
 */

/**
 * Service for database logic that manage component which implements KashDatabaseServiceInterface.
 * It sets dao object to component by class of dao that define in component.
 * @see com.axmor.kash.sample.core.reddit.local.RedditDBService for example.
 */

open class KashDatabaseService<TServiceComponent, Entity, Dao>(private val database: KashDatabase, private val serviceImplClass: Class<*>) : Component<KashDatabaseServiceInterface<Entity, Dao>> where TServiceComponent : KashDatabaseServiceInterface<Entity, Dao> {
    private var serviceComponent: TServiceComponent? = null

    override fun activate(context: ComponentContext) {
        serviceComponent = serviceImplClass.newInstance() as TServiceComponent
        serviceComponent?.apply {
            setDao(database.getDaoReference(getDaoInterfaceClass()))
        }
    }

    override fun deactivate() {
        serviceComponent?.deactivate()
    }

    override fun getService(): TServiceComponent? {
        return serviceComponent
    }
}