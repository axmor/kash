package com.axmor.kash.sample.domain.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.axmor.kash.sample.domain.local.RedditNews
import com.axmor.kash.toolset.local.db.KashBaseDao

/**
 * Created by akolodyazhnyy on 9/1/2017.
 */
@Dao
interface RedditNewsDao : KashBaseDao<RedditNews> {

    @Query("SELECT * FROM RedditNews")
    fun getAll(): List<RedditNews>

    @Query("SELECT * from RedditNews where id = :arg0 LIMIT 1")
    fun loadEntityById(id: Int): RedditNews

    @Query("SELECT COUNT(created) from RedditNews where title = :arg0 and created = :arg1")
    fun hasEntity(title: String, created: Long): Int

    @Query("DELETE from RedditNews where title = :arg0 and created = :arg1")
    fun delete(title: String, created: Long): Int
}