package com.axmor.kash.toolset.service.interfaces

/**
 * Created by akolodyazhnyy on 8/22/2017.
 */

/**
 * Interface that constrain implementation to return Service Components by Component Interface Class.
 * That what client get when binds to Android Service (com.axmor.kash.toolset.service.CompositeService)
 * @see com.axmor.kash.toolset.service.ServiceCore - implementation.
 * @see com.axmor.kash.sample.ui.favorites.FavoritesListViewModel requestNews() (sample module) for example of using.
 */

interface Composite {
    fun <TServiceComponentInterface> getService(tClass: Class<TServiceComponentInterface>): TServiceComponentInterface
}