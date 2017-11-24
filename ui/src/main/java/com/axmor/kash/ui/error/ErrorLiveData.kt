package com.axmor.kash.ui.error

import android.arch.lifecycle.LiveData

/**
 * Created by akolodyazhnyy on 9/4/2017.
 */

/**
 * Live that contains error with message and throwable.
 * @see KashActivity and KashFragment that subscribes on it in KashViewModel.
 * @see com.axmor.kash.sample.ui.reddit_list.RedditListViewModel requestNews() - sending error.
 */
class ErrorLiveData : LiveData<ErrorLiveData.ErrorData>() {
    public override fun setValue(value: ErrorData?) {
        super.setValue(value)
    }

    class ErrorData(val message: String, val error: Throwable)
}