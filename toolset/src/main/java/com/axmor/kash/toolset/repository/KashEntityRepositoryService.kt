package com.axmor.kash.toolset.repository

import com.axmor.kash.toolset.local.db.service.KashDatabaseService
import com.axmor.kash.toolset.local.db.service.KashDatabaseServiceInterface
import com.axmor.kash.toolset.network.KashNetworkApiService
import com.axmor.kash.toolset.network.KashNetworkApiServiceInterface
import com.axmor.kash.toolset.service.ComponentContext
import com.axmor.kash.toolset.service.interfaces.Component

/**
 * Created by akolodyazhnyy on 8/29/2017.
 */

/**
 * Service that include Network Service and DatabaseService and send it to implementation (activate() method).
 * @see com.axmor.kash.sample.core.reddit.KashEntityRepositoryService
 */

abstract class KashEntityRepositoryService<Entity, Dao, ApiInterface, TNetworkService, TDBService, TService>
(private val netService: KashNetworkApiService<TNetworkService, ApiInterface>,
 private val dbService: KashDatabaseService<TDBService, Entity, Dao>,
 private val serviceImplClass: Class<*>)
    : Component<TService>
        where TNetworkService : KashNetworkApiServiceInterface<ApiInterface>, TDBService : KashDatabaseServiceInterface<Entity, Dao>,
              TService : KashEntityRepositoryServiceInterface<TNetworkService, TDBService> {

    private var service: TService? = null

    override fun activate(context: ComponentContext) {
        netService.activate(context)
        dbService.activate(context)
        service = serviceImplClass.newInstance() as TService
        service?.setServices(netService.getService(), dbService.getService())
    }

    override fun deactivate() {
        service?.deactivate()
    }

    override fun getService(): TService? {
        return service
    }
}