package com.axmor.kash.sample.ui.main

import android.app.Application
import com.axmor.kash.sample.core.AppService
import com.axmor.kash.ui.mvvm.KashViewModel

/**
 * Created by akolodyazhnyy on 8/31/2017.
 */
class MainMenuViewModel(application: Application) : KashViewModel<AppService>(application) {

    override fun getAppServiceClass() = AppService::class.java
}