package com.axmor.kash.sample.core.reddit.local

import com.axmor.kash.sample.domain.db.RedditNewsDao
import com.axmor.kash.sample.domain.local.RedditNews
import com.axmor.kash.toolset.local.db.service.KashDatabaseServiceInterface

/**
 * Created by akolodyazhnyy on 9/1/2017.
 */
interface RedditDBServiceInterface : KashDatabaseServiceInterface<RedditNews, RedditNewsDao> {

    fun getAll(): List<RedditNews>

    fun loadEntityById(id: Int): RedditNews

    fun hasEntity(item: RedditNews): Boolean
}