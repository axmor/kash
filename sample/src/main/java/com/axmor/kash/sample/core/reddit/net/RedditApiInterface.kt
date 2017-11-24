package com.axmor.kash.sample.core.reddit.net

import com.axmor.kash.sample.domain.net.RedditNewsResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by akolodyazhnyy on 8/29/2017.
 */
interface RedditApiInterface {

    @GET("/top.json")
    fun getTop(@Query("after") after: String,
               @Query("limit") limit: String): Observable<RedditNewsResponse>

}