package com.axmor.kash.sample.core.reddit.local

import com.axmor.kash.sample.KashSampleApp
import com.axmor.kash.sample.domain.db.RedditNewsDao
import com.axmor.kash.sample.domain.local.RedditNews
import com.axmor.kash.toolset.local.db.service.KashDatabaseService

/**
 * Created by akolodyazhnyy on 9/1/2017.
 */
class RedditDBService : KashDatabaseService<RedditDBServiceInterface, RedditNews, RedditNewsDao>
(KashSampleApp.db, RedditDBServiceImpl::class.java)