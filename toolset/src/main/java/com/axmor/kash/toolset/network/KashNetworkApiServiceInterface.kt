package com.axmor.kash.toolset.network

/**
 * Created by akolodyazhnyy on 8/25/2017.
 */

/**
 * Interface for network components that can get retrofit api instance.
 */

interface KashNetworkApiServiceInterface<ApiInterface> {

    fun setApi(api : ApiInterface?)
    fun deactivate()
}