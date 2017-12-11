package com.axmor.kash.ui.mvvm

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.axmor.kash.toolset.service.CompositeService
import com.axmor.kash.ui.error.ErrorListener
import com.axmor.kash.ui.error.ErrorLiveData
import com.axmor.kash.ui.progress.ProgressListener

/**
 * Created by akolodyazhnyy on 8/28/2017.
 */

/**
 * Base activity that implements Lifecycle by Android Components (using AppCompatActivity), progress and error listeners.
 * It also contains view model on which subscribes.
 * @see com.axmor.kash.sample.ui.main.MainMenuActivity
 */

abstract class KashActivity<ViewModel> : AppCompatActivity(), ProgressListener, ErrorListener where ViewModel : KashViewModel {

    protected lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (getViewModelClass() != null) {
            viewModel = ViewModelProviders.of(this).get<ViewModel>(getViewModelClass()!!)
            viewModel.progress.observe(this, Observer { progress ->
                progress?.apply {
                    if (show) onShowProgress() else onHideProgress()
                }
            })
            viewModel.error.observe(this, Observer { error ->
                onError(error!!)
            })
        }
    }

    open fun onError(error: ErrorLiveData.ErrorData) {
        showError(error.message)
    }

    abstract protected fun getViewModelClass(): Class<ViewModel>?

    override fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    override fun onShowProgress() {
        //show progress
    }

    override fun onHideProgress() {
        //hide progress
    }

}