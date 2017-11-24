package com.axmor.kash.sample.core.reddit

import com.axmor.kash.sample.core.reddit.local.RedditDBServiceInterface
import com.axmor.kash.sample.core.reddit.net.RedditApiServiceInterface
import com.axmor.kash.sample.domain.local.RedditNews
import com.axmor.kash.sample.domain.local.RedditNewsData
import com.axmor.kash.toolset.repository.KashEntityRepositoryServiceInterface
import io.reactivex.Observable

/**
 * Created by akolodyazhnyy on 8/29/2017.
 */
interface RedditRepositoryServiceInterface : KashEntityRepositoryServiceInterface<RedditApiServiceInterface, RedditDBServiceInterface> {

    fun getNews(after: String, limit: String): Observable<RedditNewsData>

    fun getFavoritesNews(): Observable<List<RedditNews>>

    fun makeFavorite(news: RedditNews)

    fun unmakeFavorite(news: RedditNews)

}