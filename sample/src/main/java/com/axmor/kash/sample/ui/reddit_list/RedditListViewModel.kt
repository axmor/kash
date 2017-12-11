package com.axmor.kash.sample.ui.reddit_list

import android.app.Application
import com.axmor.kash.sample.core.AppService
import com.axmor.kash.sample.core.reddit.RedditRepositoryServiceInterface
import com.axmor.kash.sample.domain.local.RedditNews
import com.axmor.kash.sample.domain.local.RedditNewsData
import com.axmor.kash.toolset.data.KashLiveData
import com.axmor.kash.toolset.network.KashNetworkResponseSubscriber
import com.axmor.kash.toolset.service.interfaces.Composite
import com.axmor.kash.ui.error.ErrorLiveData
import com.axmor.kash.ui.mvvm.KashServiceViewModel
import com.axmor.kash.ui.mvvm.KashViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by akolodyazhnyy on 8/29/2017.
 */
class RedditListViewModel(application: Application) : KashServiceViewModel(application) {

    var redditsData: KashLiveData<List<RedditNews>> = KashLiveData()

    private var after: String = ""
    private var limit = "10"

    init {
        connectToService(AppService::class.java)
    }

    override fun onServicesConnected(composite: Composite, serviceClass: Class<*>) {
        super.onServicesConnected(composite, serviceClass)
        requestNews()
    }

    fun requestNews() {
        getService(AppService::class.java)?.getComponent(RedditRepositoryServiceInterface::class.java)!!.getNews(after, limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe({ progress.show(1) })
                .subscribe(object : KashNetworkResponseSubscriber<RedditNewsData>() {
                    override fun onError(e: Throwable) {
                        error.value = ErrorLiveData.ErrorData(e.localizedMessage, e)
                    }

                    override fun onComplete() {
                        progress.hide()
                    }

                    override fun onNext(r: RedditNewsData) {
                        after = r.after
                        if (redditsData.value == null) {
                            redditsData.value = r.news
                        } else {
                            val liveData = redditsData.value as ArrayList
                            liveData.addAll(r.news)
                            redditsData.value = liveData
                        }
                    }
                })

    }

    fun makeFavorite(news: RedditNews) {
        getService(AppService::class.java)!!.getComponent(RedditRepositoryServiceInterface::class.java).makeFavorite(news)
    }

    fun unmakeFavorite(news: RedditNews) {
        getService(AppService::class.java)!!.getComponent(RedditRepositoryServiceInterface::class.java).unmakeFavorite(news)
    }


}