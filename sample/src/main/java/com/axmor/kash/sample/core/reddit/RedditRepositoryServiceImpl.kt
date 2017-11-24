package com.axmor.kash.sample.core.reddit

import com.axmor.kash.sample.core.reddit.local.RedditDBServiceInterface
import com.axmor.kash.sample.core.reddit.net.RedditApiServiceInterface
import com.axmor.kash.sample.domain.local.RedditNews
import com.axmor.kash.sample.domain.local.RedditNewsData
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * Created by akolodyazhnyy on 8/29/2017.
 */
class RedditRepositoryServiceImpl : RedditRepositoryServiceInterface {

    private var apiService: RedditApiServiceInterface? = null
    private var dbService: RedditDBServiceInterface? = null


    override fun getNews(after: String, limit: String): Observable<RedditNewsData> {
        return apiService!!.getTop(after, limit)
                .flatMap<RedditNewsData> { response ->
                    val redditNews = mutableListOf<RedditNews>()
                    for (item in response.children) {
                        val news = RedditNews(null, item.data.author, item.data.title, item.data.num_comments, item.data.created,
                                item.data.thumbnail, item.data.url)
                        news.fav = dbService?.hasEntity(news) ?: false
                        redditNews.add(news)
                    }
                    Observable.just(RedditNewsData(response.after!!, redditNews))
                }
    }


    override fun getFavoritesNews(): Observable<List<RedditNews>> {
        return Observable.create {
            it.onNext(dbService!!.getAll())
            it.onComplete()
        }
    }

    override fun makeFavorite(news: RedditNews) {
        Observable.create<Boolean> {
            dbService?.insertAll(arrayOf(news))
            it.onNext(true)
            it.onComplete()
        }.subscribeOn(Schedulers.io()).subscribe()
    }

    override fun unmakeFavorite(news: RedditNews) {
        Observable.create<Boolean> {
            dbService?.delete(news)
            it.onNext(true)
            it.onComplete()
        }.subscribeOn(Schedulers.io()).subscribe()
    }

    override fun setServices(netService: RedditApiServiceInterface?, dbService: RedditDBServiceInterface?) {
        this.apiService = netService
        this.dbService = dbService
    }

    override fun deactivate() {
    }
}