package com.axmor.kash.sample.core

import com.axmor.kash.sample.core.reddit.RedditRepositoryService
import com.axmor.kash.sample.core.reddit.RedditRepositoryServiceInterface
import com.axmor.kash.toolset.service.CompositeService
import com.axmor.kash.toolset.service.interfaces.CompositeBuilder

/**
 * Created by akolodyazhnyy on 8/29/2017.
 */
class AppService : CompositeService() {
    override fun buildComposite(builder: CompositeBuilder?) {
        builder?.addComponent(RedditRepositoryServiceInterface::class.java, RedditRepositoryService())
    }
}
