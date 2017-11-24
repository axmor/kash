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
 * Android Components view model that binds to CompositeService and contains error and progress live data.
 * @see com.axmor.kash.sample.ui.favorites.FavoritesListViewModel
 */

abstract class KashViewModel<AppService>(application: Application) : AndroidViewModel(application), ConnectionNode where  AppService : CompositeService {

    private var serviceConnection: CompositeServiceConnection<AppService>
    private var isConnectedToServices = false
    protected var services: Composite? = null
    val progress = ProgressLiveData()
    val error = ErrorLiveData()

    init {
        serviceConnection = CompositeServiceConnection(application, this, getAppServiceClass())
        serviceConnection.onStart()
    }

    abstract fun getAppServiceClass(): Class<AppService>

    override fun onCleared() {
        super.onCleared()
        serviceConnection.onStop()
    }

    override fun onServicesConnected(composite: Composite) {
        services = composite
        isConnectedToServices = true
    }

    override fun onServicesDisconnected() {
        services = null
        isConnectedToServices = false
    }

    override fun isConnected() = isConnectedToServices
}