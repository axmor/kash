package com.axmor.kash.sample.core.reddit.net

import com.axmor.kash.sample.domain.net.RedditDataResponse
import com.axmor.kash.toolset.network.KashNetworkApiServiceInterface
import io.reactivex.Observable

/**
 * Created by akolodyazhnyy on 8/29/2017.
 */
interface RedditApiServiceInterface : KashNetworkApiServiceInterface<RedditApiInterface> {

    fun getTop(after: String,
               limit: String): Observable<RedditDataResponse>
}