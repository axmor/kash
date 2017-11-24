package com.axmor.kash.sample.core.reddit.net

import com.axmor.kash.sample.domain.net.RedditDataResponse
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * Created by akolodyazhnyy on 8/29/2017.
 */
class RedditApiServiceImpl : RedditApiServiceInterface {

    private lateinit var api: RedditApiInterface

    override fun setApi(api: RedditApiInterface?) {
        this.api = api!!
    }

    override fun deactivate() {
    }

    override fun getTop(after: String, limit: String): Observable<RedditDataResponse> {
        return api.getTop(after, limit)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap<RedditDataResponse> { redditNewsResponse ->
                    Observable.just(redditNewsResponse.data)
                }
    }

}