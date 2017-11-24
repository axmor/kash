package com.axmor.kash.sample.core.reddit.local

import com.axmor.kash.sample.domain.db.RedditNewsDao
import com.axmor.kash.sample.domain.local.RedditNews

/**
 * Created by akolodyazhnyy on 9/1/2017.
 */
class RedditDBServiceImpl : RedditDBServiceInterface {

    private lateinit var dao: RedditNewsDao

    override fun getDaoInterfaceClass() = RedditNewsDao::class.java

    override fun setDao(dao: RedditNewsDao) {
        this.dao = dao
    }

    override fun getAll(): List<RedditNews> {
        return dao.getAll()
    }

    override fun loadEntityById(id: Int): RedditNews {
        return dao.loadEntityById(id)
    }

    override fun insertAll(entities: Array<RedditNews>) {
        dao.insertAll(*entities)
    }

    override fun delete(entity: RedditNews) {
        dao.delete(entity.title, entity.created)
    }

    override fun hasEntity(item: RedditNews): Boolean {
        return dao.hasEntity(item.title, item.created) == 1
    }

    override fun deactivate() {

    }
}