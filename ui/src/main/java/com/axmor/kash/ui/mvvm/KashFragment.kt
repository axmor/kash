package com.axmor.kash.ui.mvvm

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.widget.Toast
import com.axmor.kash.toolset.service.CompositeService
import com.axmor.kash.ui.error.ErrorListener
import com.axmor.kash.ui.error.ErrorLiveData
import com.axmor.kash.ui.progress.ProgressListener

/**
 * Created by akolodyazhnyy on 8/28/2017.
 */

/**
 * Base fragment that implements Lifecycle by Android Components (using Fragment), progress and error listeners.
 * It also contains view model on which subscribes.
 * @see com.axmor.kash.sample.ui.favorites.FavoritesListFragment
 */

abstract class KashFragment<ViewModel> : Fragment(), ProgressListener, ErrorListener  where ViewModel : KashViewModel {

    protected lateinit var viewModel: ViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(getViewModelClass())
        viewModel.progress.observe(this, Observer { progress ->
            progress?.apply {
                if (show) onShowProgress() else onHideProgress()
            }
        })
        viewModel.error.observe(this, Observer { error ->
            onError(error!!)
        })
    }

    open fun onError(error: ErrorLiveData.ErrorData) {
        showError(error.message)
    }

    abstract protected fun getViewModelClass(): Class<ViewModel>

    override fun onShowProgress() {
        //show progress
    }

    override fun onHideProgress() {
        //hide progress
    }

    override fun showError(error: String) {
        Toast.makeText(this.context, error, Toast.LENGTH_SHORT).show()
    }

    abstract fun getFragmentTag(): String

}