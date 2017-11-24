package com.axmor.kash.ui.progress

import android.arch.lifecycle.LiveData


/**
 * Created by akolodyazhnyy on 8/28/2017.
 */

/**
 * LiveData to handling progress.
 * @see KashActivity and KashFragment that subscribes on it in KashViewModel.
 * @see com.axmor.kash.sample.ui.reddit_list.RedditListViewModel requestNews() - sending error.
 */

class ProgressLiveData : LiveData<ProgressLiveData.Progress>() {

    fun show(requestId: Int) {
        value = Progress(requestId, true)
    }

    fun hide(requestId: Int) {
        val progress = value
        if (progress == null || !progress.show || progress.requestId != requestId) {
            return
        }
        value = Progress(requestId, false)
    }

    fun hide() {
        val progress = value
        if (progress == null || !progress.show) {
            return
        }
        value = Progress(progress.requestId, false)
    }

    open class Progress constructor(val requestId: Int, val show: Boolean)
}