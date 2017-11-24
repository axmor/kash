package com.axmor.kash.sample.domain.db

import android.arch.persistence.room.Database
import com.axmor.kash.sample.domain.local.RedditNews
import com.axmor.kash.toolset.local.db.KashDatabase

/**
 * Created by akolodyazhnyy on 9/1/2017.
 */
@Database(entities = arrayOf(RedditNews::class), version = 1)
abstract class RedditDatabase : KashDatabase() {

    abstract fun redditNewsDao(): RedditNewsDao

}