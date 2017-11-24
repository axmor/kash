package com.axmor.kash.toolset.local.db.service

/**
 * Created by akolodyazhnyy on 8/29/2017.
 */

/**
 * Describe data base interface.
 * @see com.axmor.kash.sample.core.reddit.local.RedditDBServiceImpl that implements com.axmor.kash.sample.core.reddit.local.RedditDBServiceInterface for example.
 */

interface KashDatabaseServiceInterface<Entity, Dao> {

    fun getDaoInterfaceClass(): Class<Dao>

    fun setDao(dao: Dao)

    fun insertAll(entities: Array<Entity>)

    fun delete(entity: Entity)

    fun deactivate()

}