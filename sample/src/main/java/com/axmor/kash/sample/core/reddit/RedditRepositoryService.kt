package com.axmor.kash.sample.core.reddit

import com.axmor.kash.sample.core.reddit.local.RedditDBService
import com.axmor.kash.sample.core.reddit.local.RedditDBServiceInterface
import com.axmor.kash.sample.core.reddit.net.RedditApiInterface
import com.axmor.kash.sample.core.reddit.net.RedditApiService
import com.axmor.kash.sample.core.reddit.net.RedditApiServiceInterface
import com.axmor.kash.sample.domain.db.RedditNewsDao
import com.axmor.kash.sample.domain.local.RedditNews
import com.axmor.kash.toolset.repository.KashEntityRepositoryService

/**
 * Created by akolodyazhnyy on 8/29/2017.
 */
class RedditRepositoryService : KashEntityRepositoryService<RedditNews, RedditNewsDao, RedditApiInterface,
        RedditApiServiceInterface, RedditDBServiceInterface, RedditRepositoryServiceInterface>
(RedditApiService(),
        RedditDBService(), RedditRepositoryServiceImpl::class.java)