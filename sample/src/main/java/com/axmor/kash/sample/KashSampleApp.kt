package com.axmor.kash.sample

import android.app.Application
import android.arch.persistence.room.Room
import com.axmor.kash.sample.domain.db.RedditDatabase

/**
 * Created by akolodyazhnyy on 8/29/2017.
 */

class KashSampleApp : Application() {

    companion object {
        lateinit var context: Application
        lateinit var db: RedditDatabase
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        db = Room.databaseBuilder<RedditDatabase>(applicationContext,
                RedditDatabase::class.java, "RedditDatabase.db").build()
    }


}
