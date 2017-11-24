package com.axmor.kash.toolset.repository

/**
 * Created by akolodyazhnyy on 8/29/2017.
 */

/**
 * Interface describe which services contains repository service.
 */

interface KashEntityRepositoryServiceInterface<TNetService, TDBService> {

    fun setServices(netService: TNetService?, dbService: TDBService?)

    fun deactivate()

}