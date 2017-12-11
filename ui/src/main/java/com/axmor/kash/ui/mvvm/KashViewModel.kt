package com.axmor.kash.ui.mvvm

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.axmor.kash.toolset.service.CompositeService
import com.axmor.kash.toolset.service.connection.CompositeServiceConnection
import com.axmor.kash.toolset.service.connection.ConnectionNode
import com.axmor.kash.toolset.service.interfaces.Composite
import com.axmor.kash.ui.error.ErrorLiveData
import com.axmor.kash.ui.progress.ProgressLiveData

/**
 * Created by akolodyazhnyy on 8/28/2017.
 */

/**
 * Android Components view model that contains error and progress live data.
 * @see com.axmor.kash.sample.ui.favorites.FavoritesListViewModel
 */

abstract class KashViewModel(application: Application) : AndroidViewModel(application) {

    val progress = ProgressLiveData()
    val error = ErrorLiveData()


}