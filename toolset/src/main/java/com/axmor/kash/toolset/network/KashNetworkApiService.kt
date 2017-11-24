package com.axmor.kash.toolset.network

import com.axmor.kash.toolset.service.ComponentContext
import com.axmor.kash.toolset.service.interfaces.Component
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

/**
 * Created by akolodyazhnyy on 8/25/2017.
 */

/**
 * Base implementation for working with Retrofit.
 * It creates http client and retrofit instance, and set it to KashNetworkApiServiceInterface implementation (activate() method).
 * @see com.axmor.kash.sample.core.reddit.net.RedditApiService
 */

abstract class KashNetworkApiService<TService, ApiInterface> : Component<TService> where TService : KashNetworkApiServiceInterface<ApiInterface> {

    private var service: TService? = null
    protected var retrofit: Retrofit? = null

    init {
        initRetrofitBuilder()
    }

    open protected fun initRetrofitBuilder() {
        val httpClient = OkHttpClient.Builder()
        onInitHttpClientBuilder(httpClient)

        val retrofitBuilder = Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .client(httpClient.build())
        retrofitBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        onInitRetrofitBuilder(retrofitBuilder)
        retrofit = retrofitBuilder.build()
    }

    /**set interceptors here
     */
    protected abstract fun onInitHttpClientBuilder(builder: OkHttpClient.Builder)

    /**set converters here
     */
    protected abstract fun onInitRetrofitBuilder(builder: Retrofit.Builder)

    /**get retrofit api description interface
     */
    protected abstract fun getApiInterfaceClass(): Class<ApiInterface>

    /**get service logic implementation of TService
     */
    protected abstract fun getServiceImplementationClass(): Class<*>

    protected abstract fun getBaseUrl(): String

    override fun activate(context: ComponentContext) {
        service = getServiceImplementationClass().newInstance() as TService
        service?.setApi(retrofit?.create(getApiInterfaceClass()))
    }

    override fun deactivate() {
        service?.deactivate()
        service = null
    }

    override fun getService(): TService? {
        return service
    }
}