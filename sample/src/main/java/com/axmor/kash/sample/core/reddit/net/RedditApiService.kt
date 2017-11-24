package com.axmor.kash.sample.core.reddit.net

import com.axmor.kash.toolset.network.KashNetworkApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by akolodyazhnyy on 8/29/2017.
 */
class RedditApiService : KashNetworkApiService<RedditApiServiceInterface, RedditApiInterface>() {

    override fun onInitHttpClientBuilder(builder: OkHttpClient.Builder) {
    }

    override fun onInitRetrofitBuilder(builder: Retrofit.Builder) {
        builder.addConverterFactory(MoshiConverterFactory.create())
    }

    override fun getApiInterfaceClass(): Class<RedditApiInterface> {
        return RedditApiInterface::class.java
    }

    override fun getServiceImplementationClass(): Class<*> {
        return RedditApiServiceImpl::class.java
    }

    override fun getBaseUrl(): String {
        return "https://www.reddit.com"
    }


}