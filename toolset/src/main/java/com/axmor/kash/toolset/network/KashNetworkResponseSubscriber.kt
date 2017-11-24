package com.axmor.kash.toolset.network

import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Created by akolodyazhnyy on 8/29/2017.
 */

/**
 * Just need to avoid overriding onSubscribe() method.
 * And for further extension.
 */

abstract class KashNetworkResponseSubscriber<T> : Observer<T> {
    override fun onSubscribe(d: Disposable) {
    }
}